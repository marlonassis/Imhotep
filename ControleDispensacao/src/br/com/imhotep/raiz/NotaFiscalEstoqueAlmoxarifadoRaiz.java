package br.com.imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.excecoes.ExcecaoEstoqueRepetidoNotaFiscal;
import br.com.imhotep.excecoes.ExcecaoItemLoteDuplicado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class NotaFiscalEstoqueAlmoxarifadoRaiz extends PadraoRaiz<NotaFiscalEstoqueAlmoxarifado>{
	private NotaFiscalAlmoxarifado notaFiscal;
	private String lote;
	private boolean exibirDialogItens;
	private Boolean loteEncontrado;
	private List<NotaFiscalEstoqueAlmoxarifado> itensNotaFiscal = new ArrayList<NotaFiscalEstoqueAlmoxarifado>();
	
	public void cadastrarLoteDuplicado(){
		setLoteEncontrado(false);
		String lote = getInstancia().getEstoqueAlmoxarifado().getLote();
		getInstancia().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		getInstancia().getEstoqueAlmoxarifado().setLote(lote);
	}
	
	@Override
	public boolean apagarInstancia() {
		String lote = super.getInstanciaDelecao().getEstoqueAlmoxarifado().getLote();
		if(super.apagarInstancia()){
			super.executa("delete from EstoqueAlmoxarifado where lote = '"+lote+"'");
			carregarItensNF(getNotaFiscal().getIdNotaFiscalAlmoxarifado());
			return true;
		}
		return false;
	}
	
	public void setEditarLinha(NotaFiscalEstoqueAlmoxarifado linha){
		setInstancia(linha);
		setLote(linha.getEstoqueAlmoxarifado().getLote());
		setLoteEncontrado(true);
	}
	
	public double getValorTotalItens(){
		double total = 0d;
		for(NotaFiscalEstoqueAlmoxarifado item : getItensNotaFiscal()){
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
			varificaItemSemLote();
			PadraoFluxoTemp.limparFluxo();
			getInstancia().setNotaFiscalAlmoxarifado(getNotaFiscal());
			Date dataInclusao = new Date();
			getInstancia().getEstoqueAlmoxarifado().setDataInclusao(dataInclusao);
			getInstancia().setDataInsercao(dataInclusao);
			prepararLoteNovo();
			adicionarProfissional();
			if(getInstancia().getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado() == 0)
				PadraoFluxoTemp.getObjetoSalvar().put("EstoqueAlmoxarifado-"+getInstancia().getEstoqueAlmoxarifado().hashCode(), getInstancia().getEstoqueAlmoxarifado());
			PadraoFluxoTemp.getObjetoSalvar().put("ItemNFA-"+getInstancia().hashCode(), getInstancia());
			PadraoFluxoTemp.finalizarFluxo();
			novaInstancia();
			carregarItensNF(getNotaFiscal().getIdNotaFiscalAlmoxarifado());
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueRepetidoNotaFiscal e) {
			e.printStackTrace();
		}finally{
			PadraoFluxoTemp.limparFluxo();
		}
	}
	
	private void prepararLoteNovo() {
		if(getInstancia().getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado() == 0){
			getInstancia().getEstoqueAlmoxarifado().setQuantidadeAtual(0d);
			getInstancia().getEstoqueAlmoxarifado().setBloqueado(true);
			getInstancia().getEstoqueAlmoxarifado().setMotivoBloqueio("Lote novo não liberado - NF: " + getNotaFiscal().getIdentificacao());
		}
	}

	private void varificaItemSemLote() throws ExcecaoEstoqueRepetidoNotaFiscal {
		String lote = getInstancia().getEstoqueAlmoxarifado().getLote();
		if(lote == null || lote.trim().equals("")){
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idMaterial", getInstancia().getEstoqueAlmoxarifado().getMaterialAlmoxarifado().getIdMaterialAlmoxarifado());
			hm.put("idFabricante", getInstancia().getEstoqueAlmoxarifado().getFabricanteAlmoxarifado().getIdFabricanteAlmoxarifado());
			hm.put("dataValidade", new SimpleDateFormat("yyyy-MM-dd").format(getInstancia().getEstoqueAlmoxarifado().getDataValidade()));
			hm.put("codigoBarras", getInstancia().getEstoqueAlmoxarifado().getCodigoBarras());
			String hql = "select o from EstoqueAlmoxarifado o where o.fabricanteAlmoxarifado.idFabricanteAlmoxarifado = :idFabricante and "
					+ "o.materialAlmoxarifado.idMaterialAlmoxarifado = :idMaterial and "
					+ "to_char(o.dataValidade, 'yyyy-MM-dd') = :dataValidade and "
					+ "o.codigoBarras = :codigoBarras";
			EstoqueAlmoxarifado ea = new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder(hql), hm).consultaUnica();
			if(ea != null && ea.getIdEstoqueAlmoxarifado() != 0){
				int idEA = ea.getIdEstoqueAlmoxarifado();
				int idNF = getInstancia().getNotaFiscalAlmoxarifado().getIdNotaFiscalAlmoxarifado();
				String sql2 = "select a from NotaFiscalEstoqueAlmoxarifado a where a.estoqueAlmoxarifado.idEstoqueAlmoxarifado = " + idEA + 
								" and a.notaFiscalAlmoxarifado.idNotaFiscalAlmoxarifado = "+idNF;
				List<EstoqueAlmoxarifado> busca = new EstoqueAlmoxarifadoRaiz().getBusca(sql2);
				if(busca != null && busca.size() > 0){
					throw new ExcecaoEstoqueRepetidoNotaFiscal();
				}else					
					getInstancia().setEstoqueAlmoxarifado(ea);
			}
		}
	}
	
	private void adicionarProfissional() {
		try {
			Profissional profissionalLogado = Autenticador.getProfissionalLogado();
			getInstancia().getEstoqueAlmoxarifado().setProfissionalInclusao(profissionalLogado);
			getInstancia().setProfissionalInsercao(profissionalLogado);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	private void carregarItensNF(int id){
		 String hql = "select o from NotaFiscalEstoqueAlmoxarifado o where o.notaFiscalAlmoxarifado.idNotaFiscalAlmoxarifado = " + id + 
				 		" order by to_ascii(lower(o.estoqueAlmoxarifado.materialAlmoxarifado.descricao)), o.estoqueAlmoxarifado.lote";
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
			carregarEstoque(getInstancia(), getLote());
		} catch (ExcecaoItemLoteDuplicado e) {
			e.printStackTrace();
		}
	}

	private void verificarLoteDuplicado(String lote2, NotaFiscalAlmoxarifado notaFiscal) throws ExcecaoItemLoteDuplicado {
		if(lote2 != null && !lote2.isEmpty()){
			int id = notaFiscal.getIdNotaFiscalAlmoxarifado();
			String hql = "select o from NotaFiscalEstoqueAlmoxarifado o where o.estoqueAlmoxarifado.lote = '"+lote2+"' and o.notaFiscalAlmoxarifado.idNotaFiscalAlmoxarifado = " + id;
			NotaFiscalEstoqueAlmoxarifado item = new ConsultaGeral<NotaFiscalEstoqueAlmoxarifado>(new StringBuilder(hql)).consultaUnica();
			if(item != null){
				throw new ExcecaoItemLoteDuplicado();
			}
		}
	}

	private void carregarEstoque(NotaFiscalEstoqueAlmoxarifado instancia, String lote) {
		EstoqueAlmoxarifado estoque = null;
		if(lote != null && !lote.isEmpty()){
			estoque = consultarLote(lote);
			loteEncontrado = estoque != null;
			if(loteEncontrado){
				validarLote(instancia, estoque);
			}else{
				instancia.setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
				instancia.getEstoqueAlmoxarifado().setLote(getLote());
				loteEncontrado = false;
			}
		}else{
			instancia.setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
			loteEncontrado = false;
		}
	}

	private void validarLote(NotaFiscalEstoqueAlmoxarifado instancia, EstoqueAlmoxarifado estoque) {
		try {
			new ControleEstoqueAlmoxarifadoTemp().validarManipulacaoLote(estoque);
			instancia.setEstoqueAlmoxarifado(estoque);
			loteEncontrado = true;
		} catch (Exception e) {
			e.printStackTrace();
			loteEncontrado = null;
		}
	}

	private EstoqueAlmoxarifado consultarLote(String lote) {
		String hql = "select o from EstoqueAlmoxarifado o where o.lote = '"+lote+"'";
		EstoqueAlmoxarifado estoqueAlmoxarifado = new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder(hql)).consultaUnica();
		return estoqueAlmoxarifado;
	}

	private void limparStringLote(String lote) {
		if(lote != null) {
			lote = lote.trim();
		}
	}
	
	public void exibirDialogItens(){
		setExibirDialogItens(true);
		carregarItensNF(getNotaFiscal().getIdNotaFiscalAlmoxarifado());
		atualizarNotaFiscal();
	}
	
	private void atualizarNotaFiscal() {
		int id = getNotaFiscal().getIdNotaFiscalAlmoxarifado();
		String hql = "select o from NotaFiscalAlmoxarifado o where o.idNotaFiscalAlmoxarifado = " + id;
		NotaFiscalAlmoxarifado notaFiscal = new ConsultaGeral<NotaFiscalAlmoxarifado>(new StringBuilder(hql)).consultaUnica();
		setNotaFiscal(notaFiscal);
	}
	
	public void ocultarDialogItens(){
		setExibirDialogItens(false);
		novaInstancia();
		limparDadosDialog();
	}

	private void limparDadosDialog() {
		setNotaFiscal(null);
		setItensNotaFiscal(new ArrayList<NotaFiscalEstoqueAlmoxarifado>());
	}
	
	public NotaFiscalAlmoxarifado getNotaFiscal() {
		return notaFiscal;
	}
	public void setNotaFiscal(NotaFiscalAlmoxarifado notaFiscal) {
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

	public List<NotaFiscalEstoqueAlmoxarifado> getItensNotaFiscal() {
		return itensNotaFiscal;
	}

	public void setItensNotaFiscal(List<NotaFiscalEstoqueAlmoxarifado> itensNotaFiscal) {
		this.itensNotaFiscal = itensNotaFiscal;
	}
}
