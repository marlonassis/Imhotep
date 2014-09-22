package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.imhotep.comparador.TextoStringComparador;

public class MedicamentoControladoLista {
	private String lista;
	private Map<String, MedicamentoControladoListaMedicamento> mapMaterial = new LinkedHashMap<String, MedicamentoControladoListaMedicamento>();
	
	public MedicamentoControladoLista(){
		super();
	}
	
	public MedicamentoControladoLista(String lista){
		super();
		this.lista = lista;
	}
	
	public List<MedicamentoControladoListaMedicamento> getMateriais(){
		List<MedicamentoControladoListaMedicamento> lista = new ArrayList<MedicamentoControladoListaMedicamento>();
		List<String> materiais = new ArrayList<String>(getMapMaterial().keySet());
		Collections.sort(materiais, new TextoStringComparador());
		for(String material : materiais){
			lista.add(getMapMaterial().get(material));
		}
		return lista;
	}
	
	public String getLista() {
		return lista;
	}
	public void setLista(String lista) {
		this.lista = lista;
	}

	public Map<String, MedicamentoControladoListaMedicamento> getMapMaterial() {
		return mapMaterial;
	}

	public void setMapMaterial(Map<String, MedicamentoControladoListaMedicamento> mapMaterial) {
		this.mapMaterial = mapMaterial;
	}
	
}
