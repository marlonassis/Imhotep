package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.imhotep.comparador.TextoStringComparador;

public class EstoqueAlmoxarifadoMaterialGrupo {
	private String grupo;
	private Map<String, EstoqueAlmoxarifadoMaterialSubGrupo> mapSubGrupo = new HashMap<String, EstoqueAlmoxarifadoMaterialSubGrupo>();
	private Map<String, EstoqueAlmoxarifadoMaterial> mapMaterial = new HashMap<String, EstoqueAlmoxarifadoMaterial>();
	
	public EstoqueAlmoxarifadoMaterialGrupo(){
		super();
	}
	
	public EstoqueAlmoxarifadoMaterialGrupo(String grupo){
		super();
		this.grupo = grupo;
	}
	
	public List<EstoqueAlmoxarifadoMaterialSubGrupo> getSubGrupos(){
		List<EstoqueAlmoxarifadoMaterialSubGrupo> lista = new ArrayList<EstoqueAlmoxarifadoMaterialSubGrupo>();
		List<String> subGrupos = new ArrayList<String>(mapSubGrupo.keySet());
		Collections.sort(subGrupos, new TextoStringComparador());
		for(String subGrupo : subGrupos){
			lista.add(getMapSubGrupo().get(subGrupo));
		}
		return lista;
	}
	
	public Integer getQuantidadeTotal(){
		Integer total = 0;
		List<EstoqueAlmoxarifadoMaterial> materiais = getMateriais();
		for(EstoqueAlmoxarifadoMaterial item : materiais){
			total += item.getQuantidadeTotal();
		}
		
		List<EstoqueAlmoxarifadoMaterialSubGrupo> subGrupos = getSubGrupos();
		for(EstoqueAlmoxarifadoMaterialSubGrupo item : subGrupos){
			total += item.getQuantidadeTotal();
		}
		
		return total;
	}
	
	public List<EstoqueAlmoxarifadoMaterial> getMateriais(){
		List<EstoqueAlmoxarifadoMaterial> lista = new ArrayList<EstoqueAlmoxarifadoMaterial>();
		List<String> materiais = new ArrayList<String>(mapMaterial.keySet());
		Collections.sort(materiais, new TextoStringComparador());
		for(String material : materiais){
			lista.add(getMapMaterial().get(material));
		}
		return lista;
	}
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public Map<String, EstoqueAlmoxarifadoMaterialSubGrupo> getMapSubGrupo() {
		return mapSubGrupo;
	}
	public void setMapSubGrupo(Map<String, EstoqueAlmoxarifadoMaterialSubGrupo> mapSubGrupo) {
		this.mapSubGrupo = mapSubGrupo;
	}
	public Map<String, EstoqueAlmoxarifadoMaterial> getMapMaterial() {
		return mapMaterial;
	}
	public void setMapMaterial(Map<String, EstoqueAlmoxarifadoMaterial> mapMaterial) {
		this.mapMaterial = mapMaterial;
	}
	
}
