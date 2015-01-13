package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioAlmoxarifado;
import br.com.imhotep.entidade.InventarioMaterial;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioMaterialRaiz extends PadraoRaiz<InventarioMaterial>{
	private InventarioAlmoxarifado inventarioAlmoxarifado;
	private boolean exibirDialogAddItens;
	private List<InventarioMaterial> materiais = new ArrayList<InventarioMaterial>();
	private List<MaterialAlmoxarifado> materiaisNaoEscolhidos = new ArrayList<MaterialAlmoxarifado>();
	private String letra;
	
	public void remTodosMaterialAlmoxarifados(){
		String hql = "delete from InventarioMaterial where inventarioAlmoxarifado.idInventarioAlmoxarifado = " + getInventarioAlmoxarifado().getIdInventarioAlmoxarifado();
		super.executa(hql);
		carregarMaterialAlmoxarifados();
		setMaterialAlmoxarifados(new ArrayList<InventarioMaterial>());
	}
	
	public void exibirDialogAddItens(){
		atualizarMaterialAlmoxarifados();
		setExibirDialogAddItens(true);
	}
	
	private void carregarMaterialAlmoxarifadoInventario() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select a from InventarioMaterial a where a.inventarioAlmoxarifado.idInventarioAlmoxarifado = ");
		stringBuilder.append(getInventarioAlmoxarifado().getIdInventarioAlmoxarifado());
		stringBuilder.append(" order by to_ascii(lower(a.material.descricao))");
		ArrayList<InventarioMaterial> arrayList = new ArrayList<InventarioMaterial>(new ConsultaGeral<InventarioMaterial>(stringBuilder).consulta());
		setMaterialAlmoxarifados(arrayList);
	}

	private void carregarMaterialAlmoxarifados() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from MaterialAlmoxarifado o where o.bloqueado is false and o.idMaterialAlmoxarifado not in (");
		stringBuilder.append("select a.material.idMaterialAlmoxarifado from InventarioMaterial a where a.inventarioAlmoxarifado.idInventarioAlmoxarifado = ");
		stringBuilder.append(getInventarioAlmoxarifado().getIdInventarioAlmoxarifado());
		stringBuilder.append(") ");
		stringBuilder.append("order by to_ascii(lower(o.descricao))");
		ArrayList<MaterialAlmoxarifado> arrayList = new ArrayList<MaterialAlmoxarifado>(new ConsultaGeral<MaterialAlmoxarifado>(stringBuilder).consulta());
		setMaterialAlmoxarifadosNaoEscolhidos(arrayList);
	}
	
	public void ocultarDialogAddItens(){
		setExibirDialogAddItens(false);
	}
	
	public void addMaterialAlmoxarifadoInventario(MaterialAlmoxarifado material){
		cadastrarMaterialAlmoxarifadoInventario(material, getInventarioAlmoxarifado());
	}
	
	private void cadastrarMaterialAlmoxarifadoInventario(MaterialAlmoxarifado material, InventarioAlmoxarifado inventarioAlmoxarifado) {
		InventarioMaterial im = new InventarioMaterial();
		im.setInventarioAlmoxarifado(inventarioAlmoxarifado);
		im.setMaterial(material);
		if(super.enviarGenerico(im)){
			getMaterialAlmoxarifados().add(im);
		}
	}
	
	public void remMaterialAlmoxarifadoInventario(InventarioMaterial material){
		if(super.apagarGenerico(material)){
			getMaterialAlmoxarifados().remove(material);
		}
	}
	
	public void addTodosMaterialAlmoxarifados(){
		for(MaterialAlmoxarifado med : getMaterialAlmoxarifadosNaoEscolhidos()){
			cadastrarMaterialAlmoxarifadoInventario(med, getInventarioAlmoxarifado());
		}
		atualizarMaterialAlmoxarifados();
	}

	private void atualizarMaterialAlmoxarifados() {
		carregarMaterialAlmoxarifados();
		carregarMaterialAlmoxarifadoInventario();
	}
	
	public void addMaterialAlmoxarifadosLetra(){
		for(MaterialAlmoxarifado med : getMaterialAlmoxarifadosNaoEscolhidos()){
			String primeiraLetraMinuscula = med.getDescricao().substring(0, 1).toLowerCase();
			String letraMinuscula = getLetra().toLowerCase();
			if(letraMinuscula.contains(primeiraLetraMinuscula)){
				cadastrarMaterialAlmoxarifadoInventario(med, getInventarioAlmoxarifado());
			}
		}
		setLetra(null);
		atualizarMaterialAlmoxarifados();
	}
	
	public InventarioAlmoxarifado getInventarioAlmoxarifado() {
		return inventarioAlmoxarifado;
	}

	public void setInventarioAlmoxarifado(InventarioAlmoxarifado inventarioAlmoxarifado) {
		this.inventarioAlmoxarifado = inventarioAlmoxarifado;
	}

	public List<InventarioMaterial> getMaterialAlmoxarifados() {
		return materiais;
	}

	public void setMaterialAlmoxarifados(List<InventarioMaterial> materiais) {
		this.materiais = materiais;
	}

	public List<MaterialAlmoxarifado> getMaterialAlmoxarifadosNaoEscolhidos() {
		return materiaisNaoEscolhidos;
	}

	public void setMaterialAlmoxarifadosNaoEscolhidos(
			List<MaterialAlmoxarifado> materiaisNaoEscolhidos) {
		this.materiaisNaoEscolhidos = materiaisNaoEscolhidos;
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
