package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.imhotep.comparador.TextoStringComparador;

public class MedicamentoControladoListaMedicamento {
	private String material;
	private Map<String, MedicamentoControladoListaMedicamentoEstoque> mapEstoque = new LinkedHashMap<String, MedicamentoControladoListaMedicamentoEstoque>();
	
	public MedicamentoControladoListaMedicamento(){
		super();
	}
	
	public MedicamentoControladoListaMedicamento(String material){
		super();
		this.setMaterial(material);
	}

	public List<MedicamentoControladoListaMedicamentoEstoque> getEstoques(){
		List<MedicamentoControladoListaMedicamentoEstoque> lista = new ArrayList<MedicamentoControladoListaMedicamentoEstoque>();
		List<String> estoques = new ArrayList<String>(getMapEstoque().keySet());
		Collections.sort(estoques, new TextoStringComparador());
		for(String estoque : estoques){
			lista.add(getMapEstoque().get(estoque));
		}
		return lista;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Map<String, MedicamentoControladoListaMedicamentoEstoque> getMapEstoque() {
		return mapEstoque;
	}

	public void setMapEstoque(Map<String, MedicamentoControladoListaMedicamentoEstoque> mapEstoque) {
		this.mapEstoque = mapEstoque;
	}
	
}
