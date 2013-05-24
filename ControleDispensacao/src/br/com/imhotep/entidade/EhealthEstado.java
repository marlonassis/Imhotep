package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_ehealth_estado")
public class EhealthEstado {
	private int idEhealthEstado;
	private String nome;
	private String link;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ehealth_estado_id_ehealth_estado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_estado", unique = true, nullable = false)
	public int getIdEhealthEstado() {
		return idEhealthEstado;
	}
	public void setIdEhealthEstado(int idEhealthEstado) {
		this.idEhealthEstado = idEhealthEstado;
	}
	
	@Column(name="cv_nome")
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	
	@Column(name="cv_link")
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EhealthEstado))
			return false;
		
		return ((EhealthEstado)obj).getIdEhealthEstado() == this.idEhealthEstado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
