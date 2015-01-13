package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grupo", schema="administrativo")
public class GrupoAdm implements Serializable {
	private static final long serialVersionUID = 2776132916167685967L;
	
	private int idGrupoAdm;
	private String nome;

	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_grupo_id_grupo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_grupo", unique = true, nullable = false)
	public int getIdGrupoAdm() {
		return idGrupoAdm;
	}
	
	public void setIdGrupoAdm(int idGrupoAdm) {
		this.idGrupoAdm = idGrupoAdm;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGrupoAdm;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoAdm other = (GrupoAdm) obj;
		if (idGrupoAdm != other.idGrupoAdm)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
