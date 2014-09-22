package br.com.imhotep.entidade.extra;

import java.util.List;

import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;

public class MateriaisAlmoxarifadoGrupoSubGrupo {
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private List<MaterialAlmoxarifado> materiais;
	
	public SubGrupoAlmoxarifado getSubGrupoAlmoxarifado() {
		return subGrupoAlmoxarifado;
	}
	public void setSubGrupoAlmoxarifado(SubGrupoAlmoxarifado subGrupoAlmoxarifado) {
		this.subGrupoAlmoxarifado = subGrupoAlmoxarifado;
	}
	public List<MaterialAlmoxarifado> getMateriais() {
		return materiais;
	}
	public void setMateriais(List<MaterialAlmoxarifado> materiais) {
		this.materiais = materiais;
	}
}
