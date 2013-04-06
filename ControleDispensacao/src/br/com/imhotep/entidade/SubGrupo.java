package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_sub_grupo")
public class SubGrupo {
	private int idSubGrupo;
	private Grupo grupo;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_sub_grupo_id_sub_grupo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_sub_grupo", unique = true, nullable = false)
	public int getIdSubGrupo() {
		return this.idSubGrupo;
	}
	
	public void setIdSubGrupo(int idSubGrupo){
		this.idSubGrupo = idSubGrupo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo")
	public Grupo getGrupo(){
		return grupo;
	}
	
	public void setGrupo(Grupo grupo){
		this.grupo = grupo;
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
		if(!(obj instanceof SubGrupo))
			return false;
		
		return ((SubGrupo)obj).getIdSubGrupo() == this.idSubGrupo;
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
