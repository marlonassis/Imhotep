package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.Doacao;
import br.com.imhotep.entidade.DoacaoItem;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.excecoes.ExcecaoItemLoteDuplicado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class DoacaoItemRaiz extends PadraoRaiz<DoacaoItem>{
	private Doacao doacao;
	private boolean exibirDilogItens;
	private String lote;
	private Boolean achouLote;
	private List<DoacaoItem> itens = new ArrayList<DoacaoItem>();
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setAchouLote(null);
		setLote(null);
	}
	
	public void gravarItemDoacao(){
		if(isEdicao()){
			super.atualizar();
		}else{
			cadastrarNovoItem();
		}
		novaInstancia();
	}
	
	public void setEditarLinha(DoacaoItem linha){
		setInstancia(linha);
		setLote(linha.getEstoque().getLote());
		setAchouLote(true);
	}
	
	private void cadastrarNovoItem() {
		try {
			PadraoFluxoTemp.limparFluxo();
			getInstancia().setDoacao(getDoacao());
			Date dataInclusao = new Date();
			getInstancia().getEstoque().setDataInclusao(dataInclusao);
			getInstancia().setDataCadastro(dataInclusao);
			prepararLoteNovo();
			adicionarProfissional();
			if(getInstancia().getEstoque().getIdEstoque() == 0)
				PadraoFluxoTemp.getObjetoSalvar().put("Estoque-"+getInstancia().getEstoque().hashCode(), getInstancia().getEstoque());
			PadraoFluxoTemp.getObjetoSalvar().put("ItemD-"+getInstancia().hashCode(), getInstancia());
			PadraoFluxoTemp.finalizarFluxo();
			novaInstancia();
			atualizarListaItens();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}finally{
			PadraoFluxoTemp.limparFluxo();
		}
	}
	
	private void prepararLoteNovo() {
		if(getInstancia().getEstoque().getIdEstoque() == 0){
			getInstancia().getEstoque().setQuantidadeAtual(0);
			getInstancia().getEstoque().setBloqueado(true);
			int idDoacao = getDoacao().getIdDoacao();
			getInstancia().getEstoque().setMotivoBloqueio("Lote novo não liberado - Doação: "+ idDoacao);
		}
	}
	
	private void adicionarProfissional() {
		try {
			Profissional profissionalLogado = Autenticador.getProfissionalLogado();
			getInstancia().getEstoque().setProfissionalInclusao(profissionalLogado);
			getInstancia().setProfissionalCadastro(profissionalLogado);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	public void procurarLote(){
		try {
			limparStringLote(getLote());
			verificarLoteDuplicado(getLote(), getDoacao());
			carregarEstoque(getInstancia());
		} catch (ExcecaoItemLoteDuplicado e) {
			e.printStackTrace();
		}
	}
	
	private void carregarEstoque(DoacaoItem instancia) {
		Estoque estoque = consultarLote();
		achouLote = estoque != null;
		if(achouLote){
			validarLote(instancia, estoque);
		}else{
			instancia.setEstoque(new Estoque());
			instancia.getEstoque().setLote(getLote());
			achouLote = false;
		}
	}

	private void validarLote(DoacaoItem instancia, Estoque estoque) {
		try {
			new ControleEstoque().validarManipulacaoLote(estoque);
			instancia.setEstoque(estoque);
			achouLote = true;
		} catch (Exception e) {
			e.printStackTrace();
			achouLote = null;
		}
	}

	private Estoque consultarLote() {
		String hql = "select o from Estoque o where o.lote = '"+getLote()+"'";
		Estoque estoque = new ConsultaGeral<Estoque>(new StringBuilder(hql)).consultaUnica();
		return estoque;
	}
	
	private void verificarLoteDuplicado(String lote2, Doacao doacao) throws ExcecaoItemLoteDuplicado {
		int id = doacao.getIdDoacao();
		StringBuilder hql = new StringBuilder();
		hql.append("select o from DoacaoItem o where o.estoque.lote = '");
		hql.append(lote2);
		hql.append("' and o.doacao.idDoacao = ");
		hql.append(id);
		DoacaoItem item = new ConsultaGeral<DoacaoItem>(hql).consultaUnica();
		if(item != null){
			throw new ExcecaoItemLoteDuplicado();
		}
	}
	
	private void limparStringLote(String lote) {
		if(lote != null) {
			lote = lote.trim();
		}
	}
	
	public void apagarItens(){
		for(DoacaoItem item : getItens()){
			removerItem(item);
		}
	}
	
	public void removerItem(DoacaoItem item){
		String lote = item.getEstoque().getLote();
		if(super.apagarGenerico(item)){
			super.executa("delete from Estoque where lote = '"+lote+"'");
			getItens().remove(item);
		}
	}
	
	public void exibirDilogItens(){
		atualizarListaItens();
		setExibirDilogItens(true);
	}

	private void atualizarListaItens() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from DoacaoItem o where o.doacao.idDoacao = ");
		stringBuilder.append(getDoacao().getIdDoacao());
		stringBuilder.append(" order by o.estoque.lote");
		Collection<DoacaoItem> consulta = new ConsultaGeral<DoacaoItem>(new StringBuilder(stringBuilder.toString())).consulta();
		if(consulta != null)
			setItens(new ArrayList<DoacaoItem>(consulta));
	}
	
	public void ocultarDilogItens(){
		setExibirDilogItens(false);
	}
	
	public Doacao getDoacao() {
		return doacao;
	}

	public void setDoacao(Doacao doacao) {
		this.doacao = doacao;
	}

	public boolean isExibirDilogItens() {
		return exibirDilogItens;
	}

	public void setExibirDilogItens(boolean exibirDilogItens) {
		this.exibirDilogItens = exibirDilogItens;
	}

	public List<DoacaoItem> getItens() {
		return itens;
	}

	public void setItens(List<DoacaoItem> itens) {
		this.itens = itens;
	}

	public Boolean getAchouLote() {
		return achouLote;
	}

	public void setAchouLote(Boolean achouLote) {
		this.achouLote = achouLote;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}
}
