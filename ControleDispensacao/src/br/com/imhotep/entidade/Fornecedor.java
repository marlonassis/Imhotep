package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.imhotep.entidade.Cidade;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;


@Entity
@Table(name = "tb_fornecedor")
public class Fornecedor implements Serializable {
	private static final long serialVersionUID = -1270604461836538304L;
	
	private int idFornecedor;
	private String cadastroPessoaFisicaJuridica;
	private String endereco;
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private Cidade cidade;
	private String nomeFantasia;
	private String razaoSocial;
	private Integer cep;
	private String telefone;
	private String telefone2;
	private String email;

	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_fornecedor_id_fornecedor_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_fornecedor", unique = true, nullable = false)	
	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	@Column(name = "cv_cadastro")	
	public String getCadastroPessoaFisicaJuridica() {
		return cadastroPessoaFisicaJuridica;
	}

	public void setCadastroPessoaFisicaJuridica(String cadastroPessoaFisicaJuridica) {
		this.cadastroPessoaFisicaJuridica = cadastroPessoaFisicaJuridica;
	}

	@Column(name = "cv_endereco")	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "cv_inscricao_estadual")	
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@Column(name = "cv_inscricao_municipal")	
	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade")	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Column(name = "cv_nome_fantasia")	
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Column(name = "cv_razao_social")	
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "in_cep")	
	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	@Column(name = "cv_telefone")	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "cv_telefone_2")	
	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	
	@Column(name = "cv_email")	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	public String getCadastroPessoaFisicaJuridicaFormatado() {
		return cadastroPessoaFisicaJuridica;
	}
	
	@Transient
	public String getNome(){
		return nomeFantasia == null || nomeFantasia.isEmpty() ? razaoSocial : nomeFantasia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cadastroPessoaFisicaJuridica == null) ? 0
						: cadastroPessoaFisicaJuridica.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + idFornecedor;
		result = prime
				* result
				+ ((inscricaoEstadual == null) ? 0 : inscricaoEstadual
						.hashCode());
		result = prime
				* result
				+ ((inscricaoMunicipal == null) ? 0 : inscricaoMunicipal
						.hashCode());
		result = prime * result
				+ ((nomeFantasia == null) ? 0 : nomeFantasia.hashCode());
		result = prime * result
				+ ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result
				+ ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result
				+ ((telefone2 == null) ? 0 : telefone2.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (cadastroPessoaFisicaJuridica == null) {
			if (other.cadastroPessoaFisicaJuridica != null)
				return false;
		} else if (!cadastroPessoaFisicaJuridica
				.equals(other.cadastroPessoaFisicaJuridica))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (idFornecedor != other.idFornecedor)
			return false;
		if (inscricaoEstadual == null) {
			if (other.inscricaoEstadual != null)
				return false;
		} else if (!inscricaoEstadual.equals(other.inscricaoEstadual))
			return false;
		if (inscricaoMunicipal == null) {
			if (other.inscricaoMunicipal != null)
				return false;
		} else if (!inscricaoMunicipal.equals(other.inscricaoMunicipal))
			return false;
		if (nomeFantasia == null) {
			if (other.nomeFantasia != null)
				return false;
		} else if (!nomeFantasia.equals(other.nomeFantasia))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (telefone2 == null) {
			if (other.telefone2 != null)
				return false;
		} else if (!telefone2.equals(other.telefone2))
			return false;
		return true;
	}
	
	
}