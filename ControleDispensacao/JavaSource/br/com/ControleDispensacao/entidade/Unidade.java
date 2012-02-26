package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade")
public class Unidade {
	private int idUnidade;
	private Unidade unidadePai;
	private String sigla;
	private String nome;
	private Profissional coordenador;
	private String telefone;
	private String email;
	
	@Id
	@GeneratedValue
	@Column(name = "id_unidade")
	public int getIdUnidade() {
		return idUnidade;
	}
	public void setIdUnidade(int idUnidade) {
		this.idUnidade = idUnidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_pai")
	public Unidade getUnidadePai() {
		return unidadePai;
	}
	public void setUnidadePai(Unidade unidadePai) {
		this.unidadePai = unidadePai;
	}
	
	@Column(name = "ds_sigla")
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(name = "ds_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_coordenador")
	public Profissional getCoordenador() {
		return coordenador;
	}
	public void setCoordenador(Profissional coordenador) {
		this.coordenador = coordenador;
	}
	
	@Column(name = "ds_telefone", unique = true, length = 13)
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Column(name = "ds_email", unique = true, length = 100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Unidade))
			return false;
		
		return ((Unidade)obj).getIdUnidade() == this.idUnidade;
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
