package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.excecoes.ExcecaoItemLoteDuplicado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class NotaFiscalEstoqueRaiz extends PadraoRaiz<NotaFiscalEstoque>{
	private NotaFiscal notaFiscal;
	private String lote;
	private boolean exibirDialogItens;
	private Boolean loteEncontrado;
	private List<NotaFiscalEstoque> itensNotaFiscal = new ArrayList<NotaFiscalEstoque>();
	
	
	@Override
	public boolean apagarInstancia() {
		String lote = getInstanciaDelecao().getEstoque().getLote();
		if(super.apagarInstancia()){
			super.executa("delete from Estoque where lote = '"+lote+"'");
			carregarItensNF(getNotaFiscal().getIdNotaFiscal());
			return true;
		}
		return false;
	}
	
	public void setEditarLinha(NotaFiscalEstoque linha){
		setInstancia(linha);
		setLote(linha.getEstoque().getLote());
		setLoteEncontrado(true);
	}
	
	public double getValorTotalItens(){
		double total = 0d;
		for(NotaFiscalEstoque item : getItensNotaFiscal()){
			total += item.getTotal().doubleValue();
		}
		return total;
	}
	
	public void gravarItemNotaFiscal(){
		if(isEdicao()){
			super.atualizar();
		}else{
			cadastrarNovoItem();
		}
	}

	private void cadastrarNovoItem() {
		try {
			PadraoFluxoTemp.limparFluxo();
			getInstancia().setNotaFiscal(getNotaFiscal());
			Date dataInclusao = new Date();
			getInstancia().getEstoque().setDataInclusao(dataInclusao);
			getInstancia().setDataInsercao(dataInclusao);
			prepararLoteNovo();
			adicionarProfissional();
			if(getInstancia().getEstoque().getIdEstoque() == 0)
				PadraoFluxoTemp.getObjetoSalvar().put("Estoque-"+getInstancia().getEstoque().hashCode(), getInstancia().getEstoque());
			PadraoFluxoTemp.getObjetoSalvar().put("ItemNF-"+getInstancia().hashCode(), getInstancia());
			PadraoFluxoTemp.finalizarFluxo();
			novaInstancia();
			carregarItensNF(getNotaFiscal().getIdNotaFiscal());
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
			getInstancia().getEstoque().setMotivoBloqueio("Lote novo não liberado - NF: "+ getNotaFiscal().getIdentificacaoNotaFiscal());
		}
	}
	
	private void adicionarProfissional() {
		try {
			Profissional profissionalLogado = Autenticador.getProfissionalLogado();
			getInstancia().getEstoque().setProfissionalInclusao(profissionalLogado);
			getInstancia().setProfissionalInsercao(profissionalLogado);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	private void carregarItensNF(int id){
		 String hql = "select o from NotaFiscalEstoque o where o.notaFiscal.idNotaFiscal = " + id + 
				 		" order by to_ascii(lower(o.estoque.material.descricao)), o.estoque.lote";
		setItensNotaFiscal(super.getBusca(hql));
	}
	
	@Override
	public void novaInstancia() {
		setLote(null);
		setLoteEncontrado(null);
		super.novaInstancia();
	}
	
	public void procurarLote(){
		try {
			limparStringLote(getLote());
			verificarLoteDuplicado(getLote(), getNotaFiscal());
			carregarEstoque(getInstancia());
		} catch (ExcecaoItemLoteDuplicado e) {
			e.printStackTrace();
		}
	}

	private void verificarLoteDuplicado(String lote2, NotaFiscal notaFiscal) throws ExcecaoItemLoteDuplicado {
		String hql = "select o from NotaFiscalEstoque o where o.estoque.lote = '"+lote2+"' and o.notaFiscal.idNotaFiscal = " + notaFiscal.getIdNotaFiscal();
		NotaFiscalEstoque item = new ConsultaGeral<NotaFiscalEstoque>(new StringBuilder(hql)).consultaUnica();
		if(item != null){
			throw new ExcecaoItemLoteDuplicado();
		}
	}

	private void carregarEstoque(NotaFiscalEstoque instancia) {
		Estoque estoque = consultarLote();
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			validarLote(instancia, estoque);
		}else{
			instancia.setEstoque(new Estoque());
			instancia.getEstoque().setLote(getLote());
			loteEncontrado = false;
		}
	}

	private void validarLote(NotaFiscalEstoque instancia, Estoque estoque) {
		try {
			new ControleEstoque().validarManipulacaoLote(estoque);
			instancia.setEstoque(estoque);
			loteEncontrado = true;
		} catch (Exception e) {
			e.printStackTrace();
			loteEncontrado = null;
		}
	}

	private Estoque consultarLote() {
		String hql = "select o from Estoque o where o.lote = '"+getLote()+"'";
		Estoque estoque = new ConsultaGeral<Estoque>(new StringBuilder(hql)).consultaUnica();
		return estoque;
	}

	private void limparStringLote(String lote) {
		if(lote != null) {
			lote = lote.trim();
		}
	}
	
	public void exibirDialogItens(){
		setExibirDialogItens(true);
		carregarItensNF(getNotaFiscal().getIdNotaFiscal());
		atualizarNotaFiscal();
	}
	
	private void atualizarNotaFiscal() {
		NotaFiscal notaFiscal = new ConsultaGeral<NotaFiscal>(new StringBuilder("select o from NotaFiscal o where o.idNotaFiscal = "+getNotaFiscal().getIdNotaFiscal())).consultaUnica();
		setNotaFiscal(notaFiscal);
	}
	
	public void ocultarDialogItens(){
		setExibirDialogItens(false);
		novaInstancia();
		limparDadosDialog();
	}

	private void limparDadosDialog() {
		setNotaFiscal(null);
		setItensNotaFiscal(new ArrayList<NotaFiscalEstoque>());
	}
	
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}
	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	public boolean isExibirDialogItens() {
		return exibirDialogItens;
	}
	public void setExibirDialogItens(boolean exibirDialogItens) {
		this.exibirDialogItens = exibirDialogItens;
	}

	public Boolean getLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(Boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public List<NotaFiscalEstoque> getItensNotaFiscal() {
		return itensNotaFiscal;
	}

	public void setItensNotaFiscal(List<NotaFiscalEstoque> itensNotaFiscal) {
		this.itensNotaFiscal = itensNotaFiscal;
	}

}
