package br.com.imhotep.entidade.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.imhotep.comparador.FinanceiroGrupoAlmoxarifadoGrupoMaterialComparador;

public class FinanceiroGrupoAlmoxarifadoGrupo {
	private String grupo;
	private List<FinanceiroGrupoAlmoxarifadoGrupoMaterial> materiais = new ArrayList<FinanceiroGrupoAlmoxarifadoGrupoMaterial>();
	
	public FinanceiroGrupoAlmoxarifadoGrupo(){
		super();
	}
	
	public FinanceiroGrupoAlmoxarifadoGrupo(String grupo){
		super();
		this.grupo = grupo;
	}
	
	public FinanceiroGrupoAlmoxarifadoGrupo(String grupo, List<FinanceiroGrupoAlmoxarifadoGrupoMaterial> materiais){
		super();
		this.grupo = grupo;
		this.materiais = materiais;
	}
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<FinanceiroGrupoAlmoxarifadoGrupoMaterial> getMateriais() {
		if(materiais != null){
			Collections.sort(materiais, new FinanceiroGrupoAlmoxarifadoGrupoMaterialComparador());
		}
		return materiais;
	}

	public void setMateriais(List<FinanceiroGrupoAlmoxarifadoGrupoMaterial> materiais) {
		this.materiais = materiais;
	}
}
