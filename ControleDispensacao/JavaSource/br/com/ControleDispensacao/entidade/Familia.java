package br.com.ControleDispensacao.entidade;

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
@Table(name = "tb_familia")
public class Familia {
	private int idFamilia;
	private SubGrupo subGrupo;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_familia_id_familia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_familia", unique = true, nullable = false)
	public int getIdFamilia() {
		return this.idFamilia;
	}
	
	public void setIdFamilia(int idFamilia){
		this.idFamilia = idFamilia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sub_grupo")
	public SubGrupo getSubGrupo(){
		return subGrupo;
	}
	
	public void setSubGrupo(SubGrupo subGrupo){
		this.subGrupo = subGrupo;
	}
	
	@Column(name = "ds_descricao", length = 120)
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
		if(!(obj instanceof Familia))
			return false;
		
		return ((Familia)obj).getIdFamilia() == this.idFamilia;
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
