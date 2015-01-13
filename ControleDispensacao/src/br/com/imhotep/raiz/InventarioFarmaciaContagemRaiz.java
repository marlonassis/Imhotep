package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.InventarioFarmacia;
import br.com.imhotep.entidade.InventarioFarmaciaContagem;
import br.com.imhotep.entidade.InventarioMedicamento;
import br.com.imhotep.entidade.InventarioMedicamentoEstoque;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioFarmaciaContagemRaiz extends PadraoRaiz<InventarioFarmaciaContagem>{
	private InventarioFarmacia inventarioFarmacia;
	private InventarioMedicamento inventarioMedicamento;
	private Estoque estoqueAferido = new Estoque();
	private Double quantidadeContada;
	private boolean exibirDialogContagem;
	private boolean exibirDialogContagemItem;
	private List<InventarioMedicamento> medicamentos = new ArrayList<InventarioMedicamento>();
	private List<InventarioMedicamentoEstoque> estoqueContagem = new ArrayList<InventarioMedicamentoEstoque>();
	private List<Estoque> estoquesValidos = new ArrayList<Estoque>();
	
	public void apagarItemLote(InventarioMedicamentoEstoque item){
		if(super.apagarGenerico(item)){
			carregarLotesContados();
		}
	}
	
	public void apagarAfericao(InventarioFarmaciaContagem item){
		List<InventarioFarmaciaContagem> lista = listaContagensAferidas(item.getInventarioMedicamentoEstoque());
		if(lista.size() == 1){
			super.apagarGenerico(item.getInventarioMedicamentoEstoque());
			carregarLotesContados();
		}else{
			super.apagarGenerico(item);
		}
	}
	
	public void cadastrarContagem(){
		try {
			PadraoFluxoTemp.limparFluxo();
			InventarioFarmaciaContagem ifc = new InventarioFarmaciaContagem();
			ifc.setDataContagem(new Date());
			InventarioMedicamentoEstoque estoqueAferido = verificarExistenciaContagem();
			ifc.setInventarioMedicamentoEstoque(estoqueAferido);
			ifc.setProfissionalContagem(Autenticador.getProfissionalLogado());
			ifc.setQuantidadeContada(getQuantidadeContada());
			if(estoqueAferido.getIdInventarioMedicamentoEstoque() == 0)
				PadraoFluxoTemp.getObjetoSalvar().put("InventarioMedicamentoEstoque - " + estoqueAferido.hashCode(), estoqueAferido);
			PadraoFluxoTemp.getObjetoSalvar().put("InventarioFarmaciaContagem - " + ifc.hashCode(), ifc);
			PadraoFluxoTemp.finalizarFluxo();
			carregarLotesContados();
			setEstoqueAferido(new Estoque());
			setQuantidadeContada(null);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
		PadraoFluxoTemp.limparFluxo();
	}
	
	public List<InventarioFarmaciaContagem> listaContagensAferidas(InventarioMedicamentoEstoque inventarioMedicamentoEstoque){
		int idInventarioMedicamentoEstoque = inventarioMedicamentoEstoque.getIdInventarioMedicamentoEstoque();
		String hql = "select o from InventarioFarmaciaContagem o where o.inventarioMedicamentoEstoque.idInventarioMedicamentoEstoque = " + idInventarioMedicamentoEstoque;
		Collection<InventarioFarmaciaContagem> consulta = new ConsultaGeral<InventarioFarmaciaContagem>(new StringBuilder(hql)).consulta();
		return new ArrayList<InventarioFarmaciaContagem>(consulta);
	}
	
	private InventarioMedicamentoEstoque verificarExistenciaContagem() {
		InventarioMedicamentoEstoque estoqueAferido = new InventarioMedicamentoEstoque();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from InventarioMedicamentoEstoque o where o.estoque.idEstoque = ");
		stringBuilder.append(getEstoqueAferido().getIdEstoque());
		stringBuilder.append(" and o.inventarioMedicamento.idInventarioMedicamento = ");
		stringBuilder.append(getInventarioMedicamento().getIdInventarioMedicamento());
		
		InventarioMedicamentoEstoque registro = new ConsultaGeral<InventarioMedicamentoEstoque>(stringBuilder).consultaUnica();
		
		if(registro == null){
			estoqueAferido.setInventarioMedicamento(getInventarioMedicamento());
			estoqueAferido.setEstoque(getEstoqueAferido());
			return estoqueAferido;
		}else{
			return registro;
		}
	}
	
	private void carregarLotesContados() {
		StringBuilder sb = new StringBuilder();
		int idInventarioMedicamento = getInventarioMedicamento().getIdInventarioMedicamento();
		sb.append("select o from InventarioMedicamentoEstoque o where o.inventarioMedicamento.idInventarioMedicamento = ");
		sb.append(idInventarioMedicamento);
		sb.append(" order by o.estoque.lote");
		Collection<InventarioMedicamentoEstoque> consulta = new ConsultaGeral<InventarioMedicamentoEstoque>(sb).consulta();
		setEstoqueContagem(new ArrayList<InventarioMedicamentoEstoque>(consulta));
	}

	public void exibirDialogContagemItem(){
		carregarLotesValidos();
		carregarLotesContados();
		setExibirDialogContagemItem(true);
	}

	private void carregarLotesValidos() {
		Material medicamento = getInventarioMedicamento().getMedicamento();
		setEstoquesValidos(new EstoqueConsultaRaiz().consultarEstoquesMaterial(medicamento));
	}
	
	public void ocultarDialogContagemItem(){
		setExibirDialogContagemItem(false);
	}
	
	public void exibirDialogContagem(){
		carregarMedicamentos();
		setExibirDialogContagem(true);
	}

	public void ocultarDialogContagem(){
		setExibirDialogContagem(false);
	}
	
	private void carregarMedicamentos() {
		int id = getInventarioFarmacia().getIdInventarioFarmacia();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from InventarioMedicamento o where o.inventarioFarmacia.idInventarioFarmacia = ");
		stringBuilder.append(id);
		stringBuilder.append(" order by to_ascii(lower(o.medicamento.descricao))");
		ArrayList<InventarioMedicamento> medicamentos2 = new ArrayList<InventarioMedicamento>(new ConsultaGeral<InventarioMedicamento>(stringBuilder).consulta());
		setMedicamentos(medicamentos2);
	}
	
	public InventarioMedicamento getInventarioMedicamento() {
		return inventarioMedicamento;
	}

	public void setInventarioMedicamento(InventarioMedicamento inventarioMedicamento) {
		this.inventarioMedicamento = inventarioMedicamento;
	}

	public boolean isExibirDialogContagem() {
		return exibirDialogContagem;
	}

	public void setExibirDialogContagem(boolean exibirDialogContagem) {
		this.exibirDialogContagem = exibirDialogContagem;
	}

	public List<InventarioMedicamento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<InventarioMedicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}

	public List<InventarioMedicamentoEstoque> getEstoqueContagem() {
		return estoqueContagem;
	}

	public void setEstoqueContagem(List<InventarioMedicamentoEstoque> estoqueContagem) {
		this.estoqueContagem = estoqueContagem;
	}

	public InventarioFarmacia getInventarioFarmacia() {
		return inventarioFarmacia;
	}

	public void setInventarioFarmacia(InventarioFarmacia inventarioFarmacia) {
		this.inventarioFarmacia = inventarioFarmacia;
	}

	public boolean isExibirDialogContagemItem() {
		return exibirDialogContagemItem;
	}

	public void setExibirDialogContagemItem(boolean exibirDialogContagemItem) {
		this.exibirDialogContagemItem = exibirDialogContagemItem;
	}

	public List<Estoque> getEstoquesValidos() {
		return estoquesValidos;
	}

	public void setEstoquesValidos(List<Estoque> estoquesValidos) {
		this.estoquesValidos = estoquesValidos;
	}

	public Double getQuantidadeContada() {
		return quantidadeContada;
	}

	public void setQuantidadeContada(Double quantidadeContada) {
		this.quantidadeContada = quantidadeContada;
	}

	public Estoque getEstoqueAferido() {
		return estoqueAferido;
	}

	public void setEstoqueAferido(Estoque estoqueAferido) {
		this.estoqueAferido = estoqueAferido;
	}
	
	
}
