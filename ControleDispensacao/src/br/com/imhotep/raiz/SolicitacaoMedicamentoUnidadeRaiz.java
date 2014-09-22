package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.RestringirAcessoRedeHU;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.consulta.raiz.SolicitacaoMedicamentoUnidadeConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.extra.EstoqueDispensacao;
import br.com.imhotep.entidade.extra.ItemDispensacao;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueQuantidadeAcima;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoForaRedeHU;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.excecoes.ExcecaoSemJustificativa;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoMedicamentoSemItens;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoRecusadaSemJustificativa;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoSemReceptor;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeRaiz extends PadraoRaiz<SolicitacaoMedicamentoUnidade>{
	
	private SolicitacaoMedicamentoUnidadeItem itemEdicao;
	private SolicitacaoMedicamentoUnidadeItem itemSolicitacao = new SolicitacaoMedicamentoUnidadeItem();
	private EstoqueDispensacao estoqueEdicao;
	
	private List<SolicitacaoMedicamentoUnidade> solicitacoesPendentes = new ArrayList<SolicitacaoMedicamentoUnidade>();
	
	private List<ItemDispensacao> itensDispensacao = new ArrayList<ItemDispensacao>();
	private List<EstoqueDispensacao> estoquesEdicao = null;
	
	private boolean exibirDialogDispensacao;
	private boolean exibirDialogRecusaSolicitacao;
	private boolean exibirDialogProfissionalReceptor;
	private boolean exibirDialogItemSolicitacaoRecusado;
	private boolean exibirDialogJustificativaQuantidadeDiferente;
	
	private Integer quantidadeAlterada;
	private Integer quantidadeAlteradaEstoque;
	
	private String justificativaRecusaItem;
	private String justificativaRecusaSolicitacao;
	private String justificativaLiberarQuantidadeDiferente;
	
	public int somaTotalQuantidadeLiberada(SolicitacaoMedicamentoUnidadeItem item){
		int total = 0;
		for(DispensacaoSimples obj : item.getDispensacoes()){
			total += obj.getMovimentoLivro().getQuantidadeMovimentacao();
		}
		return total;
	}
	
	public void fecharDispensacao(){
		try {
			if(getInstancia().getProfissionalReceptor() == null || getInstancia().getProfissionalReceptor().getIdProfissional() == 0){
				throw new ExcecaoSolicitacaoSemReceptor();
			}
			finalizarDispensacao();
		} catch (ExcecaoSolicitacaoSemReceptor e) {
			e.printStackTrace();
		}
	}

	private void finalizarDispensacao() {
		PadraoFluxoTemp.limparFluxo();
		try{
			TipoMovimento tipoMovimentoDS = Parametro.tipoMovimentoDispensacaoSimples();
			Date date = new Date();
			for(ItemDispensacao item : getItensDispensacao()){
				item.getItem().setDataLiberacao(date);
				item.getItem().setProfissionalLiberacao(Autenticador.getProfissionalLogado());
				if(!item.getItem().getStatusItem().equals(TipoStatusSolicitacaoItemEnum.R)){
					for(EstoqueDispensacao ed : item.getEstoques()){
						MovimentoLivro ml = new MovimentoLivro();
						ml.setEstoque(ed.getEstoque());
						ml.setTipoMovimento(tipoMovimentoDS);
						ml.setQuantidadeMovimentacao(ed.getQuantidadeDispensada());
						ml.setJustificativa("RM: "+getInstancia().getIdSolicitacaoMedicamentoUnidade());
						new ControleEstoqueTemp().liberarAjuste(date, ml);
						
						PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro"+ml.hashCode(), ml);
						
						DispensacaoSimples ds = new DispensacaoSimples();
						ds.setSolicitacaoMedicamentoUnidadeItem(item.getItem());
						ds.setMovimentoLivro(ml);
						ds.setUnidadeDispensada(item.getItem().getSolicitacaoMedicamentoUnidade().getUnidadeDestino());
						PadraoFluxoTemp.getObjetoSalvar().put("DispensacaoSimples"+ds.hashCode(), ds);
					}
				}
				PadraoFluxoTemp.getObjetoAtualizar().put("ItemDispensacao"+item.hashCode(), item.getItem());
			}
			getInstancia().setDataDispensacao(date);
			getInstancia().setProfissionalDispensacao(Autenticador.getProfissionalLogado());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.D);
			PadraoFluxoTemp.getObjetoAtualizar().put("Dispensacao"+getInstancia().hashCode(), getInstancia());
			PadraoFluxoTemp.finalizarFluxo();
			setExibirDialogProfissionalReceptor(false);
			setExibirDialogDispensacao(true);
			SolicitacaoMedicamentoUnidadeConsultaRaiz.getInstanciaAtual().consultarSolicitacoesPendentes();
		}catch(Exception e){
			e.printStackTrace();
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.P);
		}finally{
			PadraoFluxoTemp.limparFluxo();
			unlockEstoques();
			setInstancia(new SolicitacaoMedicamentoUnidadeConsultaRaiz().solicitacaoId(getInstancia().getIdSolicitacaoMedicamentoUnidade()));
		}
	}
	
	private void unlockEstoques() {
		for(ItemDispensacao item : getItensDispensacao()){
			for(EstoqueDispensacao ed : item.getEstoques()){
				try {
					new ControleEstoqueTemp().unLockEstoque(ed.getEstoque());
				} catch (ExcecaoEstoqueUnLock e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void preRecusaSolicitacao(){
		setExibirDialogRecusaSolicitacao(true);
	}
	
	public void fecharTelaDispensacaoAtual(){
		super.novaInstancia();
		itensDispensacao = new ArrayList<ItemDispensacao>();
		estoquesEdicao = null;
		setEstoqueEdicao(null);
		setItemEdicao(null);
		
		itemSolicitacao = new SolicitacaoMedicamentoUnidadeItem();
		
		exibirDialogDispensacao = false;
		exibirDialogRecusaSolicitacao = false;
		exibirDialogProfissionalReceptor = false;
		exibirDialogItemSolicitacaoRecusado = false;
		exibirDialogJustificativaQuantidadeDiferente = false;
		
		quantidadeAlterada = null;
		quantidadeAlteradaEstoque  = null;
		
		justificativaRecusaItem = null;
		justificativaRecusaSolicitacao = null;
		justificativaLiberarQuantidadeDiferente = null;
		
		
	}
	
	public void cancelarEstoqueEdicao(){
		setEstoqueEdicao(null);
	}
	
	public void preFechamentoDispensacao(){
		setExibirDialogProfissionalReceptor(true);
		setExibirDialogDispensacao(false);
		getInstancia().setProfissionalReceptor(getInstancia().getProfissionalInsercao());
	}
	
	public void atualizarEdicaoEstoqueQuantidade() throws ExcecaoEstoqueQuantidadeAcima{
		if(getQuantidadeAlteradaEstoque() > getEstoqueEdicao().getEstoque().getQuantidadeAtual()){
			throw new ExcecaoEstoqueQuantidadeAcima();
		}else{
			int indexOf = getEstoquesEdicao().indexOf(getEstoqueEdicao());
			getEstoquesEdicao().get(indexOf).setQuantidadeDispensada(getQuantidadeAlteradaEstoque());
			setQuantidadeAlteradaEstoque(null);
			setEstoqueEdicao(null);
		}
	}
	
	public void cancelarItemLiberado(){
		setItemEdicao(null);
		setEstoqueEdicao(null);
		setEstoquesEdicao(null);
		setExibirDialogDispensacao(true);
		setExibirDialogJustificativaQuantidadeDiferente(false);
	}
	
	
	public void preFechamentoItemLiberado() throws ExcecaoQuantidadeZero {
		if(getQuantidadeLiberada() == 0)
			throw new ExcecaoQuantidadeZero();
		else
			if(getQuantidadeLiberada() != getItemEdicao().getQuantidadeSolicitada()){
				setExibirDialogJustificativaQuantidadeDiferente(true);
				setExibirDialogDispensacao(false);
			}else{
				finalizarEdicao();
			}
	}
	
	private int getQuantidadeLiberada(){
		int total = 0;
		for(EstoqueDispensacao ed : getEstoquesEdicao()){
			total += ed.getQuantidadeDispensada();
		}
		return total;
	}
	
	private int quantidadeDispensadaItemEstoque(Estoque estoque){
		for(ItemDispensacao item : getItensDispensacao()){
			if(item.getItem().equals(getItemEdicao())){
				for(EstoqueDispensacao ed : item.getEstoques()){
					if(ed.getEstoque().getLote().equals(estoque.getLote())){
						return ed.getQuantidadeDispensada();
					}
				}
				return 0;
			}
		}
		return 0;
	}
	
	public void carregarEstoquesDispensacao(){
		List<Estoque> estoques = new EstoqueConsultaRaiz().consultarEstoquesMaterial(getItemEdicao().getMaterial());
		setEstoquesEdicao(new ArrayList<EstoqueDispensacao>());
		for(Estoque estoque : estoques){
			EstoqueDispensacao ed = new EstoqueDispensacao();
			ed.setEstoque(estoque);
			ed.setQuantidadeDispensada(quantidadeDispensadaItemEstoque(estoque));
			getEstoquesEdicao().add(ed);
		}
	}
	
	public List<EstoqueDispensacao> estoquesDispensacaoItem(SolicitacaoMedicamentoUnidadeItem item){
		for(ItemDispensacao id : getItensDispensacao()){
			if(id.getItem().equals(item)){
				return id.getEstoques();
			}
		}
		return null;
	}
	
	public void preRecusaItemSolicitado(){
		setExibirDialogDispensacao(false);
		setExibirDialogItemSolicitacaoRecusado(true);
		setJustificativaRecusaItem(null);
	}
	
	public void cancelarJustificativaQuantidadeAlterada(){
		setJustificativaLiberarQuantidadeDiferente(null);
		setExibirDialogDispensacao(true);
		setExibirDialogJustificativaQuantidadeDiferente(false);
	}
	
	public void atualizarItemEstoqueQuantidadeAlterada() throws ExcecaoSemJustificativa{
		if(getJustificativaLiberarQuantidadeDiferente() == null || getJustificativaLiberarQuantidadeDiferente().isEmpty()){
			throw new ExcecaoSemJustificativa();
		}else{
			finalizarEdicao();
		}
	}

	private void finalizarEdicao() {
		atualizarItensEstoqueEdicao();
		setExibirDialogDispensacao(true);
		setExibirDialogJustificativaQuantidadeDiferente(false);
		setJustificativaLiberarQuantidadeDiferente(null);
		setItemEdicao(null);
		setEstoquesEdicao(null);
	}

	private void atualizarItensEstoqueEdicao() {
		for(ItemDispensacao item : getItensDispensacao()){
			if(item.getItem().equals(getItemEdicao())){
				item.setEstoques(new ArrayList<EstoqueDispensacao>());
				for(EstoqueDispensacao ed : getEstoquesEdicao()){
					if(ed.getQuantidadeDispensada() != 0){
						item.getEstoques().add(ed);
					}
				}
				break;
			}
		}
	}
	
	public void recusarItemSolicitacao() throws ExcecaoSolicitacaoRecusadaSemJustificativa{
		if(getJustificativaRecusaItem() == null || getJustificativaRecusaItem().isEmpty()){
			throw new ExcecaoSolicitacaoRecusadaSemJustificativa();
		}else{
			getItemEdicao().setJustificativa(getJustificativaRecusaItem());
			getItemEdicao().setStatusItem(TipoStatusSolicitacaoItemEnum.R);
			removerItemDispensacaoSugestao(getItemEdicao());
			setJustificativaRecusaItem(null);
			setItemEdicao(null);
			setExibirDialogDispensacao(true);
			setExibirDialogItemSolicitacaoRecusado(false);
		}
	}
	
	public void desfazerRecusaItem(){
		addSugestaoItemSolicitacao(getItemEdicao());
		getItemEdicao().setStatusItem(TipoStatusSolicitacaoItemEnum.P);
		setItemEdicao(null);
	}
	
	public void cancelarRecusaItem(){
		setExibirDialogDispensacao(true);
		setExibirDialogItemSolicitacaoRecusado(false);
		setItemEdicao(new SolicitacaoMedicamentoUnidadeItem());
	}
	
	private void removerItemDispensacaoSugestao(SolicitacaoMedicamentoUnidadeItem solicitacaoMedicamentoUnidadeItem) {
		for(ItemDispensacao id : getItensDispensacao()){
			if(getItemEdicao().equals(id.getItem())){
				getItensDispensacao().remove(id);
				break;
			}
		}
	}
	
	public void cancelarConfirmacaoReceptor(){
		getInstancia().setProfissionalReceptor(null);
		setExibirDialogProfissionalReceptor(false);
		setExibirDialogDispensacao(true);
	}
	
	public void recusarSolicitacao() throws ExcecaoSolicitacaoRecusadaSemJustificativa{
		if(getJustificativaRecusaSolicitacao() == null || getJustificativaRecusaSolicitacao().isEmpty()){
			throw new ExcecaoSolicitacaoRecusadaSemJustificativa();
		}else{
			iniciarRecusa();
		}
	}

	private void iniciarRecusa() {
		PadraoFluxoTemp.limparFluxo();
		try {
			processarRecusa();
			setExibirDialogRecusaSolicitacao(false);
			setJustificativaRecusaSolicitacao(null);
			SolicitacaoMedicamentoUnidadeConsultaRaiz.getInstanciaAtual().consultarSolicitacoesPendentes();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
		PadraoFluxoTemp.limparFluxo();
	}

	private void processarRecusa() throws ExcecaoProfissionalLogado, ExcecaoPadraoFluxo {
		iniciarRecusaSolicitacao();
		recusarItens();
		PadraoFluxoTemp.getObjetoAtualizar().put("solicitacaoMedicamentoUnidade-"+getInstancia().hashCode(), getInstancia());
		PadraoFluxoTemp.finalizarFluxo();
	}

	private void iniciarRecusaSolicitacao() throws ExcecaoProfissionalLogado {
		getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.R);
		getInstancia().setProfissionalReceptor(null);
		getInstancia().setDataDispensacao(new Date());
		getInstancia().setProfissionalDispensacao(Autenticador.getProfissionalLogado());
		getInstancia().setJustificativa(getJustificativaRecusaSolicitacao());
	}

	private void recusarItens() {
		for(SolicitacaoMedicamentoUnidadeItem item : getInstancia().getItens()){
			item.setJustificativa(Constantes.JUSTIFICATIVA_RECUSA_SOLICITACAO_ITEM);
			item.setStatusItem(TipoStatusSolicitacaoItemEnum.R);
			PadraoFluxoTemp.getObjetoAtualizar().put("solicitacaoMedicamentoUnidadeItem-"+item.hashCode(), item);
		}
	}
	
	public void cancelarRecusa(){
		setExibirDialogRecusaSolicitacao(false);
		setJustificativaRecusaSolicitacao(null);
	}
	
	public void iniciarDispensacao(){
		setExibirDialogDispensacao(true);
		montarSugestaoDispensacao();
	}
	
	private void montarSugestaoDispensacao() {
		for(SolicitacaoMedicamentoUnidadeItem item : getInstancia().getItens()){
			addSugestaoItemSolicitacao(item);
		}
	}

	private void addSugestaoItemSolicitacao(SolicitacaoMedicamentoUnidadeItem item) {
		List<Estoque> estoques = new EstoqueConsultaRaiz().consultarEstoquesMaterial(item.getMaterial());
		int quantidadeTemp = item.getQuantidadeSolicitada();
		ItemDispensacao id = new ItemDispensacao();
		id.setItem(item);
		for(Estoque estoque : estoques){
			EstoqueDispensacao ed = new EstoqueDispensacao();
			if(quantidadeTemp <= estoque.getQuantidadeAtual()){
				ed.setEstoque(estoque);
				ed.setQuantidadeDispensada(quantidadeTemp);
				id.getEstoques().add(ed);
				break;
			}else{
				if(quantidadeTemp > estoque.getQuantidadeAtual()){
					ed.setEstoque(estoque);
					ed.setQuantidadeDispensada(estoque.getQuantidadeAtual());
					id.getEstoques().add(ed);
					quantidadeTemp = quantidadeTemp - estoque.getQuantidadeAtual();
				}
			}
		}
		if(estoques == null || estoques.isEmpty()){
			id.getItem().setStatusItem(TipoStatusSolicitacaoItemEnum.R);
			id.getItem().setJustificativa("Item recusado por n‹o possuir algum lote com saldo");
		}
		getItensDispensacao().add(id);
	}
	
	@Override
	public boolean enviar() {
		try {
			boolean liberadoGeral = Parametro.getLiberadoSolicitacaoMedicamentoForaHU();
			if(!liberadoGeral)
				new RestringirAcessoRedeHU().validarAcessoRedeHU();
			getInstancia().setDataInsercao(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.A);
			return super.enviar();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoForaRedeHU e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static SolicitacaoMedicamentoUnidadeRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMedicamentoUnidadeRaiz) Utilitarios.procuraInstancia(SolicitacaoMedicamentoUnidadeRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean atualizar() {
		try {
			if(getInstancia().getItens() == null || getInstancia().getItens().isEmpty()){
				throw new ExcecaoSolicitacaoMedicamentoSemItens();
			}
			getInstancia().setDataFechamento(new Date());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.P);
			if(super.atualizar()){
				ControlePainelAviso.getInstancia().gerarAvisoRM(getInstancia().getIdSolicitacaoMedicamentoUnidade(), getInstancia().getUnidadeDestino());
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoSolicitacaoMedicamentoSemItens e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public SolicitacaoMedicamentoUnidadeItem getItemEdicao() {
		return itemEdicao;
	}

	public void setItemEdicao(SolicitacaoMedicamentoUnidadeItem itemEdicao) {
		this.itemEdicao = itemEdicao;
	}

	public List<SolicitacaoMedicamentoUnidade> getSolicitacoesPendentes() {
		return solicitacoesPendentes;
	}

	public void setSolicitacoesPendentes(List<SolicitacaoMedicamentoUnidade> solicitacoesPendentes) {
		this.solicitacoesPendentes = solicitacoesPendentes;
	}

	public Integer getQuantidadeAlterada() {
		return quantidadeAlterada;
	}

	public void setQuantidadeAlterada(Integer quantidadeAlterada) {
		this.quantidadeAlterada = quantidadeAlterada;
	}

	public boolean isExibirDialogDispensacao() {
		return exibirDialogDispensacao;
	}

	public void setExibirDialogDispensacao(boolean exibirDialogDispensacao) {
		this.exibirDialogDispensacao = exibirDialogDispensacao;
	}

	public boolean isExibirDialogRecusaSolicitacao() {
		return exibirDialogRecusaSolicitacao;
	}

	public void setExibirDialogRecusaSolicitacao(
			boolean exibirDialogRecusaSolicitacao) {
		this.exibirDialogRecusaSolicitacao = exibirDialogRecusaSolicitacao;
	}

	public boolean isExibirDialogProfissionalReceptor() {
		return exibirDialogProfissionalReceptor;
	}

	public void setExibirDialogProfissionalReceptor(
			boolean exibirDialogProfissionalReceptor) {
		this.exibirDialogProfissionalReceptor = exibirDialogProfissionalReceptor;
	}

	public List<ItemDispensacao> getItensDispensacao() {
		return itensDispensacao;
	}

	public void setItensDispensacao(List<ItemDispensacao> itensDispensacao) {
		this.itensDispensacao = itensDispensacao;
	}

	public boolean isExibirDialogItemSolicitacaoRecusado() {
		return exibirDialogItemSolicitacaoRecusado;
	}

	public void setExibirDialogItemSolicitacaoRecusado(
			boolean exibirDialogItemSolicitacaoRecusado) {
		this.exibirDialogItemSolicitacaoRecusado = exibirDialogItemSolicitacaoRecusado;
	}

	public boolean isExibirDialogJustificativaQuantidadeDiferente() {
		return exibirDialogJustificativaQuantidadeDiferente;
	}

	public void setExibirDialogJustificativaQuantidadeDiferente(
			boolean exibirDialogJustificativaQuantidadeDiferente) {
		this.exibirDialogJustificativaQuantidadeDiferente = exibirDialogJustificativaQuantidadeDiferente;
	}

	public String getJustificativaRecusaItem() {
		return justificativaRecusaItem;
	}

	public void setJustificativaRecusaItem(String justificativaRecusaItem) {
		this.justificativaRecusaItem = justificativaRecusaItem;
	}

	public String getJustificativaRecusaSolicitacao() {
		return justificativaRecusaSolicitacao;
	}

	public void setJustificativaRecusaSolicitacao(
			String justificativaRecusaSolicitacao) {
		this.justificativaRecusaSolicitacao = justificativaRecusaSolicitacao;
	}

	public List<EstoqueDispensacao> getEstoquesEdicao() {
		return estoquesEdicao;
	}

	public void setEstoquesEdicao(List<EstoqueDispensacao> estoquesEdicao) {
		this.estoquesEdicao = estoquesEdicao;
	}

	public String getJustificativaLiberarQuantidadeDiferente() {
		return justificativaLiberarQuantidadeDiferente;
	}

	public void setJustificativaLiberarQuantidadeDiferente(
			String justificativaLiberarQuantidadeDiferente) {
		this.justificativaLiberarQuantidadeDiferente = justificativaLiberarQuantidadeDiferente;
	}

	public EstoqueDispensacao getEstoqueEdicao() {
		return estoqueEdicao;
	}

	public void setEstoqueEdicao(EstoqueDispensacao estoqueEdicao) {
		this.estoqueEdicao = estoqueEdicao;
	}

	public Integer getQuantidadeAlteradaEstoque() {
		return quantidadeAlteradaEstoque;
	}

	public void setQuantidadeAlteradaEstoque(Integer quantidadeAlteradaEstoque) {
		this.quantidadeAlteradaEstoque = quantidadeAlteradaEstoque;
	}

	public SolicitacaoMedicamentoUnidadeItem getItemSolicitacao() {
		return itemSolicitacao;
	}

	public void setItemSolicitacao(SolicitacaoMedicamentoUnidadeItem itemSolicitacao) {
		this.itemSolicitacao = itemSolicitacao;
	}
	
}
