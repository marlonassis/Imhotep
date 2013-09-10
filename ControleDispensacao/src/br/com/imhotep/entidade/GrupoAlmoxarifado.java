package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grupo_almoxarifado")
public class GrupoAlmoxarifado implements Serializable {
	private static final long serialVersionUID = 2729621542707300366L;
	
	private int idGrupoAlmoxarifado;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_grupo_almoxarifado_id_grupo_almoxarifado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_grupo_almoxarifado", unique = true, nullable = false)
	public int getIdGrupoAlmoxarifado() {
		return this.idGrupoAlmoxarifado;
	}
	
	public void setIdGrupoAlmoxarifado(int idGrupoAlmoxarifado){
		this.idGrupoAlmoxarifado = idGrupoAlmoxarifado;
	}

	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof GrupoAlmoxarifado))
			return false;
		
		return ((GrupoAlmoxarifado)obj).getIdGrupoAlmoxarifado() == this.idGrupoAlmoxarifado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
