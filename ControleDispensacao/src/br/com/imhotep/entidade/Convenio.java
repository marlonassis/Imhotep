package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_convenio")
public class Convenio {
	
	private int idConvenio;
	private String sigla;
	private String nome;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_convenio_id_convenio_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_convenio", unique = true, nullable = false)
	public int getIdConvenio() {
		return idConvenio;
	}
	public void setIdConvenio(int idConvenio) {
		this.idConvenio = idConvenio;
	}
	
	@Column(name = "cv_sigla")
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
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
		if(!(obj instanceof Convenio))
			return false;
		
		return ((Convenio)obj).getIdConvenio() == this.idConvenio;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode() + sigla.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
