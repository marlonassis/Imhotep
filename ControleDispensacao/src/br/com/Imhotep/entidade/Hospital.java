package br.com.Imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_hospital")
public class Hospital {
	
	private int idHospital;
	private String nome;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_hospital_id_hospital_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_hospital", unique = true, nullable = false)
	public int getIdHospital() {
		return idHospital;
	}
	public void setIdHospital(int idHospital) {
		this.idHospital = idHospital;
	}
	
	@Column(name = "ds_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "ds_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Hospital))
			return false;
		
		return ((Hospital)obj).getIdHospital() == this.idHospital;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode() + descricao.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
