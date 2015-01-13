package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_variavel", schema="controle")
public class Variavel implements Serializable {
	private static final long serialVersionUID = -6501095349452941123L;
	
	private int idVariavel;
	private String nome;
	private Boolean apagada;

	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_variavel_id_variavel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_variavel", unique = true, nullable = false)
	public int getIdVariavel() {
		return idVariavel;
	}
	
	public void setIdVariavel(int idVariavel) {
		this.idVariavel = idVariavel;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "bl_apagada")
	public Boolean getApagada() {
		return apagada;
	}

	public void setApagada(Boolean apagada) {
		this.apagada = apagada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apagada == null) ? 0 : apagada.hashCode());
		result = prime * result + idVariavel;
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
		Variavel other = (Variavel) obj;
		if (apagada == null) {
			if (other.apagada != null)
				return false;
		} else if (!apagada.equals(other.apagada))
			return false;
		if (idVariavel != other.idVariavel)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
	
}
