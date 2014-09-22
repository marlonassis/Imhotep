package br.com.imhotep.entidade.relatorio;

import java.util.ArrayList;
import java.util.List;

public class MovimentacoesGrupoAlmoxarifadoGrupo {
	private String grupo;
	private String subGrupo;
	private List<MovimentacoesGrupoAlmoxarifadoGrupoMaterial> materiais = new ArrayList<MovimentacoesGrupoAlmoxarifadoGrupoMaterial>();
	
	public MovimentacoesGrupoAlmoxarifadoGrupo(){
		super();
	}
	
	public MovimentacoesGrupoAlmoxarifadoGrupo(String grupo){
		super();
		this.grupo = grupo;
	}
	
	public MovimentacoesGrupoAlmoxarifadoGrupo(String grupo, String subGrupo, List<MovimentacoesGrupoAlmoxarifadoGrupoMaterial> materiais){
		super();
		this.grupo = grupo;
		this.subGrupo = subGrupo;
		this.setMateriais(materiais);
	}
	
	public String getDescricaoCompleta(){
		if(getSubGrupo() != null){
			return getGrupo().concat("/").concat(getSubGrupo());
		}
		return getGrupo();
	}
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<MovimentacoesGrupoAlmoxarifadoGrupoMaterial> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<MovimentacoesGrupoAlmoxarifadoGrupoMaterial> materiais) {
		this.materiais = materiais;
	}

	public String getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}

}
