package br.com.imhotep.entidade.extra;

import java.util.List;

import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;

public class MateriaisAlmoxarifadoGrupo {
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private List<MateriaisAlmoxarifadoGrupoSubGrupo> subGrupos;
	private List<MaterialAlmoxarifado> materiais;
	
	
	public GrupoAlmoxarifado getGrupoAlmoxarifado() {
		return grupoAlmoxarifado;
	}
	public void setGrupoAlmoxarifado(GrupoAlmoxarifado grupoAlmoxarifado) {
		this.grupoAlmoxarifado = grupoAlmoxarifado;
	}
	public List<MateriaisAlmoxarifadoGrupoSubGrupo> getSubGrupos() {
		return subGrupos;
	}
	public void setSubGrupos(List<MateriaisAlmoxarifadoGrupoSubGrupo> subGrupos) {
		this.subGrupos = subGrupos;
	}
	public List<MaterialAlmoxarifado> getMateriais() {
		return materiais;
	}
	public void setMateriais(List<MaterialAlmoxarifado> materiais) {
		this.materiais = materiais;
	}
}
