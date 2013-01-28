package br.com.Imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_cidade")
public class Cidade {
	private int idCidade;
	private Estado estado;
	private String nome;

	@SequenceGenerator(name = "generator", sequenceName = "public.tb_cidade_id_cidade_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_cidade", unique = true, nullable = false)
	public int getIdCidade() {
		return idCidade;
	}
	
	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estado")
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	@Column(name = "ds_nome", length = 60)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Transient
	public String getNomeCidadeEstado(){
		return nome.concat(" - ").concat(estado.getUf());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Cidade))
			return false;
		
		return ((Cidade)obj).getIdCidade() == this.idCidade;
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
