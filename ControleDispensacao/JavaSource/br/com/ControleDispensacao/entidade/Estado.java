package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estado")
public class Estado {
	private int idEstado;
	private String uf;
	private String nome;
	
	@Id
	@GeneratedValue
	@Column(name = "id_estado")
	public int getIdEstado() {
		return idEstado;
	}
	
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	
	@Column(name = "uf", length = 2)
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	@Column(name = "nome", length = 40)
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
