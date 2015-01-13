package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioFarmacia;
import br.com.imhotep.entidade.InventarioMedicamento;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioMedicamentoRaiz extends PadraoRaiz<InventarioMedicamento>{
	private InventarioFarmacia inventarioFarmacia;
	private boolean exibirDialogAddItens;
	private List<InventarioMedicamento> medicamentos = new ArrayList<InventarioMedicamento>();
	private List<Material> medicamentosNaoEscolhidos = new ArrayList<Material>();
	private String letra;
	
	public void remTodosMedicamentos(){
		String hql = "delete from InventarioMedicamento where inventarioFarmacia.idInventarioFarmacia = " + getInventarioFarmacia().getIdInventarioFarmacia();
		super.executa(hql);
		carregarMedicamentos();
		setMedicamentos(new ArrayList<InventarioMedicamento>());
	}
	
	public void exibirDialogAddItens(){
		atualizarMedicamentos();
		setExibirDialogAddItens(true);
	}
	
	private void carregarMedicamentoInventario() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select a from InventarioMedicamento a where a.inventarioFarmacia.idInventarioFarmacia = ");
		stringBuilder.append(getInventarioFarmacia().getIdInventarioFarmacia());
		stringBuilder.append(" order by to_ascii(lower(a.medicamento.descricao))");
		ArrayList<InventarioMedicamento> arrayList = new ArrayList<InventarioMedicamento>(new ConsultaGeral<InventarioMedicamento>(stringBuilder).consulta());
		setMedicamentos(arrayList);
	}

	private void carregarMedicamentos() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from Material o where o.bloqueado is false and o.idMaterial not in (");
		stringBuilder.append("select a.medicamento.idMaterial from InventarioMedicamento a where a.inventarioFarmacia.idInventarioFarmacia = ");
		stringBuilder.append(getInventarioFarmacia().getIdInventarioFarmacia());
		stringBuilder.append(") ");
		stringBuilder.append("order by to_ascii(lower(o.descricao))");
		ArrayList<Material> arrayList = new ArrayList<Material>(new ConsultaGeral<Material>(stringBuilder).consulta());
		setMedicamentosNaoEscolhidos(arrayList);
	}
	
	public void ocultarDialogAddItens(){
		setExibirDialogAddItens(false);
	}
	
	public void addMedicamentoInventario(Material medicamento){
		cadastrarMedicamentoInventario(medicamento, getInventarioFarmacia());
	}
	
	private void cadastrarMedicamentoInventario(Material medicamento, InventarioFarmacia inventarioFarmacia) {
		InventarioMedicamento im = new InventarioMedicamento();
		im.setInventarioFarmacia(inventarioFarmacia);
		im.setMedicamento(medicamento);
		if(super.enviarGenerico(im)){
			getMedicamentos().add(im);
		}
	}
	
	public void remMedicamentoInventario(InventarioMedicamento medicamento){
		if(super.apagarGenerico(medicamento)){
			getMedicamentos().remove(medicamento);
		}
	}
	
	public void addMedicamentosControlados(){
		for(Material med : getMedicamentosNaoEscolhidos()){
			if(med.getListaEspecial() != null){
				cadastrarMedicamentoInventario(med, getInventarioFarmacia());
			}
		}
		atualizarMedicamentos();
	}
	
	public void addTodosMedicamentos(){
		for(Material med : getMedicamentosNaoEscolhidos()){
			cadastrarMedicamentoInventario(med, getInventarioFarmacia());
		}
		atualizarMedicamentos();
	}

	private void atualizarMedicamentos() {
		carregarMedicamentos();
		carregarMedicamentoInventario();
	}
	
	public void addMedicamentosLetra(){
		for(Material med : getMedicamentosNaoEscolhidos()){
			String primeiraLetraMinuscula = med.getDescricao().substring(0, 1).toLowerCase();
			String letraMinuscula = getLetra().toLowerCase();
			if(letraMinuscula.contains(primeiraLetraMinuscula)){
				cadastrarMedicamentoInventario(med, getInventarioFarmacia());
			}
		}
		setLetra(null);
		atualizarMedicamentos();
	}
	
	public InventarioFarmacia getInventarioFarmacia() {
		return inventarioFarmacia;
	}

	public void setInventarioFarmacia(InventarioFarmacia inventarioFarmacia) {
		this.inventarioFarmacia = inventarioFarmacia;
	}

	public List<InventarioMedicamento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<InventarioMedicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}

	public List<Material> getMedicamentosNaoEscolhidos() {
		return medicamentosNaoEscolhidos;
	}

	public void setMedicamentosNaoEscolhidos(
			List<Material> medicamentosNaoEscolhidos) {
		this.medicamentosNaoEscolhidos = medicamentosNaoEscolhidos;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public boolean isExibirDialogAddItens() {
		return exibirDialogAddItens;
	}

	public void setExibirDialogAddItens(boolean exibirDialogAddItens) {
		this.exibirDialogAddItens = exibirDialogAddItens;
	}
	
}
