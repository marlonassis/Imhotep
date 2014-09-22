package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EstoqueAlmoxarifadoMaterial {
	private String material;
	private String materialId;
	private Map<Integer, EstoqueAlmoxarifadoMaterialLote> mapLote = new LinkedHashMap<Integer, EstoqueAlmoxarifadoMaterialLote>();
	
	public EstoqueAlmoxarifadoMaterial(){
		super();
	}
	
	public EstoqueAlmoxarifadoMaterial(String material, String materialId){
		super();
		this.material = material;
		this.materialId = materialId;
	}
	
	public Integer getQuantidadeTotal(){
		Integer total = 0;
		List<EstoqueAlmoxarifadoMaterialLote> lotes = getLotes();
		for(EstoqueAlmoxarifadoMaterialLote item : lotes){
			total += item.getQuantidadeAtual();
		}
		return total;
	}
	
	public List<EstoqueAlmoxarifadoMaterialLote> getLotes(){
		List<EstoqueAlmoxarifadoMaterialLote> lista = new ArrayList<EstoqueAlmoxarifadoMaterialLote>();
		List<Integer> lotes = new ArrayList<Integer>(mapLote.keySet());
		for(Integer lote : lotes){
			lista.add(getMapLote().get(lote));
		}
		return lista;
	}
	
	public Map<Integer, EstoqueAlmoxarifadoMaterialLote> getMapLote() {
		return mapLote;
	}
	public void setMapLote(Map<Integer, EstoqueAlmoxarifadoMaterialLote> mapLote) {
		this.mapLote = mapLote;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
}
