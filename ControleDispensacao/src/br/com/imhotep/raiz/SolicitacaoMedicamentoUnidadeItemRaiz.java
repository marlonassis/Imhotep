package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.EstoqueDispensacaoValidadeComparador;
import br.com.imhotep.comparador.SolicitacaoMedicamentoUnidadeItemComparador;
import br.com.imhotep.consulta.raiz.DispensacaoSimplesConsulaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.extra.EstoqueDispensacao;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoDispensacaoSolicitacaoItemPendente;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeAcimaEstoqueSolicitacaoUnidade;
import br.com.imhotep.excecoes.ExcecaoReservaVazia;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoItemInseridaDuasVezes;
import br.com.imhotep.excecoes.ExcecaoUnidadeAtual;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeItemRaiz extends PadraoHome<SolicitacaoMedicamentoUnidadeItem>{
	
	private List<EstoqueDispensacao> estoquesDispensacao = new ArrayList<EstoqueDispensacao>();
	private List<SolicitacaoMedicamentoUnidadeItem> itens = new ArrayList<SolicitacaoMedicamentoUnidadeItem>();
	private EstoqueDispensacao estoqueDispensacaoEdicao=null;
	private Map<SolicitacaoMedicamentoUnidadeItem, Set<EstoqueDispensacao>> itensLiberados = new HashMap<SolicitacaoMedicamentoUnidadeItem, Set<EstoqueDispensacao>>();
	private Boolean quantidadeDispensadaDiferenteSolicitada;
	private Boolean itemRecusado;
	private String justificativaRecusa;
	private boolean exibirDialogAlterarQuantidade = false;
	private Integer quantidadeAlterada;
	
	public void alterarStatusAlterarQuantidade(){
		setQuantidadeAlterada(null);
		setExibirDialogAlterarQuantidade(!getExibirDialogAlterarQuantidade());
	}
	
	public void cancelarAjusteQuantidade(){
		alterarStatusAlterarQuantidade();
		super.novaInstancia();
	}
	
	public void alterarQuantidade(){
		Integer qtdOriginal = getInstancia().getQuantidadeSolicitada();
		boolean sucesso = false;
		try {
			getInstancia().setQuantidadeSolicitada(getQuantidadeAlterada());
			validarQuantidade(getInstancia());
			getInstancia().setQuantidadeSolicitada(getQuantidadeAlterada());
			super.atualizar();
			sucesso = true;
			setExibirDialogAlterarQuantidade(false);
			super.novaInstancia();
		} catch (ExcecaoEstoqueVazio e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueSaldoInsuficiente e) {
			e.printStackTrace();
		} catch (ExcecaoReservaVazia e) {
			e.printStackTrace();
		} finally{
			if(!sucesso){
				getInstancia().setQuantidadeSolicitada(qtdOriginal);
			}
		}
	}
	
	public SolicitacaoMedicamentoUnidadeItemRaiz(){
		super();
	}
	
	public SolicitacaoMedicamentoUnidadeItemRaiz(SolicitacaoMedicamentoUnidadeItem instancia){
		super();
		setInstancia(instancia);
	}
	
	public SolicitacaoMedicamentoUnidadeItemRaiz(boolean exibeMensagemInsercao, boolean exibeMensagemAtualizacao){
		super();
		setExibeMensagemAtualizacao(exibeMensagemAtualizacao);
		setExibeMensagemInsercao(exibeMensagemInsercao);
	}
	
	public void cancelarRecusaItem(){
		getInstancia().setJustificativa(null);
		setItemRecusado(false);
		setJustificativaRecusa(null);
		getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.P);
		getItens().remove(getInstancia());
		getItens().add(getInstancia());
		super.novaInstancia();
	}
	
	public void preRecusarItem(){
		setJustificativaRecusa(null);
		setItemRecusado(true);
	}
	
	public void recusarItem(){
		if(getJustificativaRecusa() != null && !getJustificativaRecusa().isEmpty()){
			getInstancia().setJustificativa(getJustificativaRecusa());
			getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.R);
			getItensLiberados().remove(getInstancia());
			getItens().remove(getInstancia());
			getItens().add(getInstancia());
			setItemRecusado(false);
			super.novaInstancia();
		}else{
			super.mensagem("Informe a justificativa", null, Constantes.WARN);
		}
	}
	
	public void cancelarJustificativa(){
		getInstancia().setJustificativa(null);
		setQuantidadeDispensadaDiferenteSolicitada(false);
	}
	
	public Integer quantidadeSolicitadaItem(SolicitacaoMedicamentoUnidadeItem item){
		Set<SolicitacaoMedicamentoUnidadeItem> keys = itensLiberados.keySet();
		return keys.iterator().next().getQuantidadeSolicitada();
	}
	
	public Integer somaTotalDispensadaItem(SolicitacaoMedicamentoUnidadeItem item){
		Set<SolicitacaoMedicamentoUnidadeItem> keys = itensLiberados.keySet();
		Integer total = 0; 
		for(SolicitacaoMedicamentoUnidadeItem key : keys){
			if(key.equals(item)){
				Set<EstoqueDispensacao> edList = itensLiberados.get(key);
				for(EstoqueDispensacao ed : edList){
					total += ed.getQuantidadeDispensada();
				}
			}
		}
		return total;
	}
	
	public Integer quantidadeDispensada(){
		Integer total = 0; 
		for(EstoqueDispensacao ed : estoquesDispensacao){
			total += ed.getQuantidadeDispensada();
		}
		return total;
	}
	
	public void cancelarItemLiberado(){
		estoquesDispensacao = new ArrayList<EstoqueDispensacao>();
		estoqueDispensacaoEdicao = null;
 		super.novaInstancia();
	}
	
	public void posFechamentoItemLiberado(){
		estoquesDispensacao = new ArrayList<EstoqueDispensacao>();
		estoqueDispensacaoEdicao = null;
		super.novaInstancia();
	}
	
	public void preFechamentoItemLiberado(){
		if(quantidadeDispensada().equals(getInstancia().getQuantidadeSolicitada())){
			getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.D);
			fecharItemLiberado();
			setQuantidadeDispensadaDiferenteSolicitada(false);
		}else{
			getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.DP);
			setQuantidadeDispensadaDiferenteSolicitada(true);
		}
	}
	
	public void limparInstancia() {
		estoquesDispensacao = new ArrayList<EstoqueDispensacao>();
		itens = new ArrayList<SolicitacaoMedicamentoUnidadeItem>();
		estoqueDispensacaoEdicao=null;
		itensLiberados = new HashMap<SolicitacaoMedicamentoUnidadeItem, Set<EstoqueDispensacao>>();
		quantidadeDispensadaDiferenteSolicitada = false;
		itemRecusado = false;
		justificativaRecusa = null;
		super.novaInstancia();
	}
	
	public void fecharItemLiberadoComJustificativa(){
		if(getInstancia().getJustificativa() == null || getInstancia().getJustificativa().isEmpty()){
			super.mensagem("Informe a justificativa", null, Constantes.WARN);
		}else{
			fecharItemLiberado();
			setQuantidadeDispensadaDiferenteSolicitada(false);
		}
	}
	
	private void fecharItemLiberado(){
		Set<EstoqueDispensacao> lotesDispensados = new HashSet<EstoqueDispensacao>();
		for(EstoqueDispensacao ed : estoquesDispensacao){
			if(ed.getQuantidadeDispensada() > 0){
				lotesDispensados.add(ed);
			}
		}
		
		if(!getInstancia().getStatusItem().equals(TipoStatusSolicitacaoItemEnum.R)){
			if(quantidadeDispensada().equals(0)){
				getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.P);
			}else{
				if(quantidadeDispensada().compareTo(getInstancia().getQuantidadeSolicitada()) != 0){
					getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.DP);
				}else{
					if(quantidadeDispensada().compareTo(getInstancia().getQuantidadeSolicitada()) == 0){
						getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.D);
					}
				}
			}
		}
		itensLiberados.put(getInstancia(), lotesDispensados);
		getItens().remove(getInstancia());
		getItens().add(getInstancia());
		posFechamentoItemLiberado();
	}
	
	public void atualizarEstoqueDispensacaoEdicao(){
		if(getEstoqueDispensacaoEdicao().getQuantidadeDispensada().compareTo(getEstoqueDispensacaoEdicao().getEstoque().getQuantidadeAtual()) < 1){
			for(EstoqueDispensacao ed : getEstoquesDispensacao()){
				if(ed.getEstoque().getLote().equals(getEstoqueDispensacaoEdicao().getEstoque().getLote())){
					getEstoquesDispensacao().remove(ed);
					getEstoquesDispensacao().add(getEstoqueDispensacaoEdicao());
					setEstoqueDispensacaoEdicao(null);
					break;
				}
			}
		}else{
			try {
				throw new ExcecaoQuantidadeAcimaEstoqueSolicitacaoUnidade();
			} catch (ExcecaoQuantidadeAcimaEstoqueSolicitacaoUnidade e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cancelarEstoqueEdicao(){
		setEstoqueDispensacaoEdicao(null);
	}
	
	public static SolicitacaoMedicamentoUnidadeItemRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMedicamentoUnidadeItemRaiz) Utilitarios.procuraInstancia(SolicitacaoMedicamentoUnidadeItemRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void verificaItensPendentes() throws ExcecaoDispensacaoSolicitacaoItemPendente{
		for(SolicitacaoMedicamentoUnidadeItem smi : getItens()){
			if(smi.getStatusItem().equals(TipoStatusSolicitacaoItemEnum.P)){
				throw new ExcecaoDispensacaoSolicitacaoItemPendente();
			}
		}
	}
	
	//TODO Criar o fluxo de dispensação
	public void fecharSolicitacaoItens() throws ExcecaoDispensacaoSolicitacaoItemPendente{
		for(SolicitacaoMedicamentoUnidadeItem item : getItens()){
			atualizarItem(item);
			Set<EstoqueDispensacao> estoquesDispensacao = getItensLiberados().get(item);
			gerarMovimento(item, estoquesDispensacao);
		}
	}

	private void atualizarItem(SolicitacaoMedicamentoUnidadeItem item) {
		SolicitacaoMedicamentoUnidadeItemRaiz smuir = new SolicitacaoMedicamentoUnidadeItemRaiz(false, false);
		smuir.setInstancia(item);
		smuir.atualizar();
	}

	private void gerarMovimento(SolicitacaoMedicamentoUnidadeItem item, Set<EstoqueDispensacao> estoquesDispensacao) {
		if(estoquesDispensacao == null)
			return;
		
		for(EstoqueDispensacao ed : estoquesDispensacao){
			try {
				DispensacaoSimples ds = new DispensacaoSimples();
				ds.setSolicitacaoMedicamentoUnidadeItem(item);
				ds.setUnidadeDispensada(SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().getInstancia().getUnidadeDestino());
				MovimentoLivro ml = new MovimentoLivro();
				ml.setDataMovimento(new Date());
				ml.setEstoque(ed.getEstoque());
				ml.setQuantidadeMovimentacao(ed.getQuantidadeDispensada());
				ml.setTipoMovimento(Parametro.tipoMovimentoDispensacaoSimples());
				ml.setUnidadeCadastrante(Autenticador.getUnidadeProfissional());
				ml.setUsuarioMovimentacao(Autenticador.getProfissionalLogado().getUsuario());
				ds.setMovimentoLivro(ml);
				DispensacaoSimplesRaiz dsr = new DispensacaoSimplesRaiz(false, false);
				dsr.setAtivarMensagem(false);
				dsr.setInstancia(ds);
				dsr.setLoteEncontrado(true);
				dsr.enviar();
			} catch (ExcecaoUnidadeAtual e) {
				e.printStackTrace();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}
	}
	
	public void montarSugestao(){
		List<SolicitacaoMedicamentoUnidadeItem> lista = new ArrayList<SolicitacaoMedicamentoUnidadeItem>(getItens());
		for(SolicitacaoMedicamentoUnidadeItem smi : lista){
			setInstancia(smi);
			iniciarDispensacao();
			fecharItemLiberado();
			super.novaInstancia();
		}
	}
	
	public void carregarEstoquesDispensacao(SolicitacaoMedicamentoUnidadeItem linha){
		getEstoquesDispensacao().addAll(getItensLiberados().get(linha));
		List<Estoque> estoques = new EstoqueConsultaRaiz().consultarEstoquesMaterial(getInstancia().getMaterial());
		for(Estoque estoque : estoques){
			boolean achou = false;
			for(EstoqueDispensacao ed : getEstoquesDispensacao()){
				if(ed.getEstoque().equals(estoque)){
					achou = true;
					break;
				}
			}
			if(!achou)
				getEstoquesDispensacao().add(new EstoqueDispensacao(estoque, 0, getInstancia().getQuantidadeSolicitada()));
		}
	}
	
	public void iniciarDispensacao(){
		List<Estoque> estoques = new EstoqueConsultaRaiz().consultarEstoquesMaterial(getInstancia().getMaterial());
		setEstoquesDispensacao(new ArrayList<EstoqueDispensacao>());
		int quantidadeRestante = getInstancia().getQuantidadeSolicitada();
		for(Estoque estoque : estoques){
			if(quantidadeRestante == 0){
				getEstoquesDispensacao().add(new EstoqueDispensacao(estoque, 0, getInstancia().getQuantidadeSolicitada()));
			}else{
				int quantidadeAtual = estoque.getQuantidadeAtual();
				if((quantidadeAtual - quantidadeRestante) < 0){
					getEstoquesDispensacao().add(new EstoqueDispensacao(estoque, quantidadeAtual, getInstancia().getQuantidadeSolicitada()));
					quantidadeRestante = quantidadeRestante - quantidadeAtual;
				}else{
					if((quantidadeAtual - quantidadeRestante) >= 0){
						getEstoquesDispensacao().add(new EstoqueDispensacao(estoque, quantidadeRestante, getInstancia().getQuantidadeSolicitada()));
						quantidadeRestante = 0;
					}
				}
			}
		}
	}
	
	public void dispensar(){
		Integer qtdDispensado = somaTotalDispensadaItem(getInstancia());
		if(qtdDispensado != getInstancia().getQuantidadeSolicitada() && (getInstancia().getJustificativa() == null || getInstancia().getJustificativa().isEmpty())){
			super.mensagem("Justifique o motivo da quantidade dispensada ser diferente da quantidade solicitada", "Preencha a justificativa", Constantes.FATAL);
		}else{
			try {
				getInstancia().setProfissionalLiberacao(Autenticador.getProfissionalLogado());
				getInstancia().setUnidadeProfissionalLiberacao(Autenticador.getUnidadeProfissional());
				getInstancia().setDataLiberacao(new Date());
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			} catch (ExcecaoUnidadeAtual e) {
				e.printStackTrace();
			}
		}
	}
	
	private void validarQuantidade(SolicitacaoMedicamentoUnidadeItem instancia) throws ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoReservaVazia {
		ControleEstoque ce = new ControleEstoque();
		ce.liberarReserva(instancia.getQuantidadeSolicitada(), getInstancia().getMaterial());
	}
	
	private void verificaItemInseridoDuasVezes() throws ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoSolicitacaoItemInseridaDuasVezes, ExcecaoReservaVazia{
		for(SolicitacaoMedicamentoUnidadeItem item : getItens()){
			if(item.getMaterial().equals(getInstancia().getMaterial())){
				Integer total = item.getQuantidadeSolicitada();
				total += getInstancia().getQuantidadeSolicitada();
				validarQuantidade(new SolicitacaoMedicamentoUnidadeItem(total, item.getMaterial()));
				item.setQuantidadeSolicitada(total);
				if(new SolicitacaoMedicamentoUnidadeItemRaiz(item).atualizar()){
					getItens().remove(item);
					getItens().add(item);
					super.novaInstancia();
					throw new ExcecaoSolicitacaoItemInseridaDuasVezes();
				}
			}
		}
	}
	
	@Override
	public boolean enviar() {
		try {
			verificaItemInseridoDuasVezes();
			validarQuantidade(getInstancia());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setDataInsercao(new Date());
			getInstancia().setSolicitacaoMedicamentoUnidade(SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().getInstancia());
			getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.P);
			if(super.enviar()){
				getItens().add(getInstancia());
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoEstoqueVazio e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueSaldoInsuficiente e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoItemInseridaDuasVezes e) {
			e.printStackTrace();
		} catch (ExcecaoReservaVazia e) {
			e.printStackTrace();
		} 
		return false;
	}

	public List<EstoqueDispensacao> getEstoqueDispensado() {
		List<EstoqueDispensacao> estoqueDispensado = new ArrayList<EstoqueDispensacao>();
		for(EstoqueDispensacao ed : estoquesDispensacao){
			if(ed.getQuantidadeDispensada() != 0){
				estoqueDispensado.add(ed);
			}
		}
		return estoqueDispensado;
	}
	
	@Override
	public boolean apagar() {
		int id = getInstancia().getIdSolicitacaoMedicamentoUnidadeItem();
		if(super.apagar()){
			for(SolicitacaoMedicamentoUnidadeItem item : getItens()){
				if(item.getIdSolicitacaoMedicamentoUnidadeItem() == id){
					getItens().remove(item);
					break;
				}
			}
			return true;
		}
		return false;
	}
	
	public List<EstoqueDispensacao> listaEstoqueDispensacao(SolicitacaoMedicamentoUnidadeItem linha){
		Set<EstoqueDispensacao> itens = itensLiberados.get(linha);
		if(itens != null)
			return new ArrayList<EstoqueDispensacao>(itens);
		return new ArrayList<EstoqueDispensacao>();
	}
	
	public List<EstoqueDispensacao> getEstoquesDispensacao() {
		Collections.sort(estoquesDispensacao, new EstoqueDispensacaoValidadeComparador());
		return estoquesDispensacao;
	}

	public void setEstoquesDispensacao(List<EstoqueDispensacao> estoquesDispensacao) {
		this.estoquesDispensacao = estoquesDispensacao;
	}

	public List<SolicitacaoMedicamentoUnidadeItem> getItens() {
		Collections.sort(itens, new SolicitacaoMedicamentoUnidadeItemComparador());
		return itens;
	}

	public void setItens(List<SolicitacaoMedicamentoUnidadeItem> itens) {
		this.itens = itens;
	}

	public EstoqueDispensacao getEstoqueDispensacaoEdicao() {
		return estoqueDispensacaoEdicao;
	}
	
	public void setEstoqueDispensacaoEdicao(EstoqueDispensacao estoqueDispensacaoEdicao) {
		this.estoqueDispensacaoEdicao = estoqueDispensacaoEdicao;
	}

	public Map<SolicitacaoMedicamentoUnidadeItem, Set<EstoqueDispensacao>> getItensLiberados() {
		return itensLiberados;
	}

	public List<EstoqueDispensacao> pegarEstoqueItensLiberados(SolicitacaoMedicamentoUnidadeItem linha) {
		Set<EstoqueDispensacao> set = getItensLiberados().get(linha);
		if(set != null && !set.isEmpty())
			return new ArrayList<EstoqueDispensacao>(set);
		return new ArrayList<EstoqueDispensacao>();
	}
	
	public void setItensLiberados(Map<SolicitacaoMedicamentoUnidadeItem, Set<EstoqueDispensacao>> itensLiberados) {
		this.itensLiberados = itensLiberados;
	}

	public Boolean getQuantidadeDispensadaDiferenteSolicitada() {
		return quantidadeDispensadaDiferenteSolicitada;
	}

	public void setQuantidadeDispensadaDiferenteSolicitada(
			Boolean quantidadeDispensadaDiferenteSolicitada) {
		this.quantidadeDispensadaDiferenteSolicitada = quantidadeDispensadaDiferenteSolicitada;
	}

	public Boolean getItemRecusado() {
		return itemRecusado;
	}

	public void setItemRecusado(Boolean itemRecusado) {
		this.itemRecusado = itemRecusado;
	}

	public String getJustificativaRecusa() {
		return justificativaRecusa;
	}

	public void setJustificativaRecusa(String justificativaRecusa) {
		this.justificativaRecusa = justificativaRecusa;
	}

	public void montarImpressao() {
		List<SolicitacaoMedicamentoUnidadeItem> lista = new ArrayList<SolicitacaoMedicamentoUnidadeItem>(getItens());
		for(SolicitacaoMedicamentoUnidadeItem smi : lista){
			List<DispensacaoSimples> dispensacoesSolicitacaoItem = new DispensacaoSimplesConsulaRaiz().consultarDispensacoesSolicitacaoItem(smi);
			Set<EstoqueDispensacao> setED = new HashSet<EstoqueDispensacao>();
			for(DispensacaoSimples e : dispensacoesSolicitacaoItem){
				setED.add(new EstoqueDispensacao(e.getMovimentoLivro().getEstoque(), e.getMovimentoLivro().getQuantidadeMovimentacao(), smi.getQuantidadeSolicitada()));
			}
			getItensLiberados().put(smi, setED);
		}
	}

	public boolean getExibirDialogAlterarQuantidade() {
		return exibirDialogAlterarQuantidade;
	}

	public void setExibirDialogAlterarQuantidade(
			boolean exibirDialogAlterarQuantidade) {
		this.exibirDialogAlterarQuantidade = exibirDialogAlterarQuantidade;
	}

	public Integer getQuantidadeAlterada() {
		return quantidadeAlterada;
	}

	public void setQuantidadeAlterada(Integer quantidadeAlterada) {
		this.quantidadeAlterada = quantidadeAlterada;
	}
	
}
