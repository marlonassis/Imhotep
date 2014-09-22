package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.imhotep.comparador.TextoStringComparador;

public class FinanceiroGrupo {
	private String grupo;
	private Map<String, FinanceiroGrupoMaterial> mapMaterial = new HashMap<String, FinanceiroGrupoMaterial>();
	
	public FinanceiroGrupo(){
		super();
	}
	
	public FinanceiroGrupo(String grupo){
		super();
		this.grupo = grupo;
	}
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public List<FinanceiroGrupoMaterial> getListaMaterial() {
		List<FinanceiroGrupoMaterial> listaMaterial = new ArrayList<FinanceiroGrupoMaterial>();
		List<String> materiais = new ArrayList<String>(getMapMaterial().keySet());
		Collections.sort(materiais, new TextoStringComparador());
		for(String m : materiais){
			listaMaterial.add(getMapMaterial().get(m));
		}
		return listaMaterial;
	}
	public Map<String, FinanceiroGrupoMaterial> getMapMaterial() {
		return mapMaterial;
	}
	public void setMapMaterial(Map<String, FinanceiroGrupoMaterial> mapMaterial) {
		this.mapMaterial = mapMaterial;
	}
}
