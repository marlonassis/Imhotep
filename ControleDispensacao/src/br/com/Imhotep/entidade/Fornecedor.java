package br.com.Imhotep.entidade;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import br.com.Imhotep.entidade.Cidade;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "tb_fornecedor")
public class Fornecedor {
	private int idFornecedor;
	private String nome;
	private String cadastroPessoaFisicaJuridica;
	private String endereco;
	private Cidade cidade;
	private String inscricaoEstadual;
	private String inscricaoMunicipal;

	
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

	@Column(name = "cv_nome")	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade")
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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


	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Fornecedor))
			return false;
		
		return ((Fornecedor)obj).getIdFornecedor() == this.idFornecedor;
	}
}
