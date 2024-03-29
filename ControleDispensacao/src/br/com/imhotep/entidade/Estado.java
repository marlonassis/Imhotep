package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_estado")
public class Estado implements Serializable {
	private static final long serialVersionUID = 5879254265304964043L;
	
	private int idEstado;
	private String unidadeFederativa;
	private String nome;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estado_id_estado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estado", unique = true, nullable = false)
	public int getIdEstado() {
		return idEstado;
	}
	
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	
	@Column(name = "cv_unidade_federativa", length = 2)
	public String getUnidadeFederativa() {
		return unidadeFederativa;
	}
	
	public void setUnidadeFederativa(String unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}
	
	@Column(name = "cv_nome")
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
		if(!(obj instanceof Estado))
			return false;
		
		return ((Estado)obj).getIdEstado() == this.idEstado;
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
