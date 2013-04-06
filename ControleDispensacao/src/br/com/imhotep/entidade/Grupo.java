package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grupo")
public class Grupo {
	private int idGrupo;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_grupo_id_grupo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_grupo", unique = true, nullable = false)
	public int getIdGrupo() {
		return this.idGrupo;
	}
	
	public void setIdGrupo(int idGrupo){
		this.idGrupo = idGrupo;
	}

	@Column(name = "cv_descricao", length = 120)
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
		if(!(obj instanceof Grupo))
			return false;
		
		return ((Grupo)obj).getIdGrupo() == this.idGrupo;
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
