package br.com.Imhotep.entidade;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.Imhotep.entidade.Cidade;
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
public class Fornecedor {
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
	public String getNome(){
		return nomeFantasia == null || nomeFantasia.isEmpty() ? razaoSocial : nomeFantasia;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Fornecedor))
			return false;
		
		return ((Fornecedor)obj).getIdFornecedor() == this.idFornecedor;
	}
}