package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.CellEditEvent;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.EstoqueDispensacaoComparador;
import br.com.imhotep.comparador.SolicitacaoMedicamentoUnidadeItemComparador;
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
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
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
	private Map<SolicitacaoMedicamentoUnidadeItem, List<EstoqueDispensacao>> itensLiberados = new HashMap<SolicitacaoMedicamentoUnidadeItem, List<EstoqueDispensacao>>();
	private Boolean quantidadeDispensadaDiferenteSolicitada;
	private Boolean itemRecusado;
	private String justificativaRecusa;
	
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
		getInstancia().setQuantidadeDipensada(0);
	}
	
	public Integer somaTotalDispensadaItem(SolicitacaoMedicamentoUnidadeItem item){
		Set<SolicitacaoMedicamentoUnidadeItem> keys = itensLiberados.keySet();
		Integer total = 0; 
		for(SolicitacaoMedicamentoUnidadeItem key : keys){
			if(key.equals(item)){
				List<EstoqueDispensacao> edList = itensLiberados.get(key);
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
		itensLiberados = new HashMap<SolicitacaoMedicamentoUnidadeItem, List<EstoqueDispensacao>>();
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
	
	@Override
	public void novaInstancia() {
		estoquesDispensacao = new ArrayList<EstoqueDispensacao>();
		itens = new ArrayList<SolicitacaoMedicamentoUnidadeItem>();
		estoqueDispensacaoEdicao=null;
		itensLiberados = new HashMap<SolicitacaoMedicamentoUnidadeItem, List<EstoqueDispensacao>>();
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
		List<EstoqueDispensacao> lotesDispensados = new ArrayList<EstoqueDispensacao>();
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
			List<EstoqueDispensacao> estoquesDispensacao = getItensLiberados().get(item);
			gerarMovimento(item, estoquesDispensacao);
		}
	}

	private void atualizarItem(SolicitacaoMedicamentoUnidadeItem item) {
		SolicitacaoMedicamentoUnidadeItemRaiz smuir = new SolicitacaoMedicamentoUnidadeItemRaiz(false, false);
		smuir.setInstancia(item);
		smuir.atualizar();
	}

	private void gerarMovimento(SolicitacaoMedicamentoUnidadeItem item, List<EstoqueDispensacao> estoquesDispensacao) {
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
	
	public void onCellEdit(CellEditEvent event) {  
		Integer oldValue = Integer.valueOf(String.valueOf(event.getOldValue()));  
		Integer newValue = Integer.valueOf(String.valueOf(event.getNewValue()));  
          
        if(newValue != null && !newValue.equals(oldValue)) {
        		int index = event.getRowIndex();
				EstoqueDispensacao ed = getEstoquesDispensacao().get(index);
    			ed.setQuantidadeDispensada(newValue);
    			getEstoquesDispensacao().remove(ed);
    			getEstoquesDispensacao().add(ed);
    			super.mensagem("Quantidade Alterada", "Anterior: " + oldValue + ", Atual:" + newValue, Constantes.INFO);
        }  
    }  
	
	public void iniciarDispensacao(){
		if(itensLiberados.containsKey(getInstancia())){
			getEstoquesDispensacao().addAll(itensLiberados.get(getInstancia()));
		}else{
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
	}
	
	public void dispensar(){
		if(getInstancia().getQuantidadeDipensada() != getInstancia().getQuantidadeSolicitada() && (getInstancia().getJustificativa() == null || getInstancia().getJustificativa().isEmpty())){
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
	
	private void validarQuantidade(SolicitacaoMedicamentoUnidadeItem instancia) throws ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque {
		ControleEstoque ce = new ControleEstoque();
		ce.liberarReserva(instancia.getQuantidadeSolicitada(), getInstancia().getMaterial());
	}
	
	private void verificaItemInseridoDuasVezes() throws ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoSolicitacaoItemInseridaDuasVezes{
		for(SolicitacaoMedicamentoUnidadeItem item : getItens()){
			if(item.getMaterial().equals(getInstancia().getMaterial())){
				Integer total = item.getQuantidadeSolicitada();
				total += getInstancia().getQuantidadeSolicitada();
				item.setQuantidadeSolicitada(total);
				validarQuantidade(item);
				if(new SolicitacaoMedicamentoUnidadeItemRaiz(item).atualizar()){
					getItens().remove(item);
					getItens().add(item);
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
		} catch (ExcecaoSaldoInsuficienteEstoque e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoItemInseridaDuasVezes e) {
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
	
	public List<EstoqueDispensacao> getEstoquesDispensacao() {
		Collections.sort(estoquesDispensacao, new EstoqueDispensacaoComparador());
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

	public Map<SolicitacaoMedicamentoUnidadeItem, List<EstoqueDispensacao>> getItensLiberados() {
		return itensLiberados;
	}

	public void setItensLiberados(Map<SolicitacaoMedicamentoUnidadeItem, List<EstoqueDispensacao>> itensLiberados) {
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
	
}
