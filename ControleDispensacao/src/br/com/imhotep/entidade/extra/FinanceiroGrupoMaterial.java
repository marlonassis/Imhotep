package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.imhotep.comparador.FinanceiroGrupoMaterialDataComparador;

public class FinanceiroGrupoMaterial {
	private String material;
	private String materialCodigo;
	private Map<String, FinanceiroGrupoMaterialData> mapPeriodo = new HashMap<String, FinanceiroGrupoMaterialData>();
	
	public FinanceiroGrupoMaterial(){
		super();
	}
	
	public FinanceiroGrupoMaterial(String material, String materialCodigo){
		super();
		this.materialCodigo = materialCodigo;
		this.material = material;
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public List<FinanceiroGrupoMaterialData> getListaPeriodo() {
		List<FinanceiroGrupoMaterialData> listaPeriodo = new ArrayList<FinanceiroGrupoMaterialData>();
		List<String> periodos = new ArrayList<String>(mapPeriodo.keySet());
		for(String periodo : periodos){
			listaPeriodo.add(mapPeriodo.get(periodo));
		}
		Collections.sort(listaPeriodo, new FinanceiroGrupoMaterialDataComparador());
		return listaPeriodo;
	}
	
	public Map<String, FinanceiroGrupoMaterialData> getMapPeriodo() {
		return mapPeriodo;
	}
	public void setMapPeriodo(Map<String, FinanceiroGrupoMaterialData> mapPeriodo) {
		this.mapPeriodo = mapPeriodo;
	}

	public String getMaterialCodigo() {
		return materialCodigo;
	}

	public void setMaterialCodigo(String materialCodigo) {
		this.materialCodigo = materialCodigo;
	}
	
}
