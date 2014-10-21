package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.imhotep.comparador.TextoStringComparador;

public class EstoqueAlmoxarifadoMaterialSubGrupo {
	private String subGrupo;
	private Map<String, EstoqueAlmoxarifadoMaterial> mapMaterial = new HashMap<String, EstoqueAlmoxarifadoMaterial>();
	
	public EstoqueAlmoxarifadoMaterialSubGrupo(){
		super();
	}
	
	public EstoqueAlmoxarifadoMaterialSubGrupo(String subGrupo){
		super();
		this.subGrupo = subGrupo;
	}
	
	public Integer getQuantidadeTotal(){
		Integer total = 0;
		List<EstoqueAlmoxarifadoMaterial> materiais = getMateriais();
		for(EstoqueAlmoxarifadoMaterial item : materiais){
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
	
	public String getSubGrupo() {
		return subGrupo;
	}
	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}
	public Map<String, EstoqueAlmoxarifadoMaterial> getMapMaterial() {
		return mapMaterial;
	}
	public void setMapMaterial(Map<String, EstoqueAlmoxarifadoMaterial> mapMaterial) {
		this.mapMaterial = mapMaterial;
	}
	
}
