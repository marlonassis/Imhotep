package br.com.Imhotep.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.Imhotep.enums.TipoLogradouroEnum;
import br.com.Imhotep.enums.TipoSexoEnum;
import br.com.remendo.utilidades.Utilities;

@Entity
@Table(name = "tb_paciente")
public class Paciente {
	private int idPaciente;
	private Unidade unidadeCadastro;
	private Cidade cidade;
	private String nome;
	private TipoLogradouroEnum tipoLogradouro;
	private String nomeLogradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String nomeMae;
	private TipoSexoEnum sexo;
	private Date dataNascimento;
	private String telefone;
	private String cpf;
	private Date dataInclusao;
	private Profissional profissionalInclusao;
	private String numeroSus;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_paciente_id_paciente_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_paciente", unique = true, nullable = false)
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_cadastro")
	public Unidade getUnidadeCadastro() {
		return unidadeCadastro;
	}
	public void setUnidadeCadastro(Unidade unidadeCadastro) {
		this.unidadeCadastro = unidadeCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Column(name = "ds_nome", length = 70)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "tp_tipo_logradouro")
	@Enumerated(EnumType.STRING)
	public TipoLogradouroEnum getTipoLogradouro() {
		return tipoLogradouro;
	}
	public void setTipoLogradouro(TipoLogradouroEnum tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}
	
	@Column(name = "ds_logradouro", length = 50)
	public String getNomeLogradouro() {
		return nomeLogradouro;
	}
	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}
	
	@Column(name = "ds_numero", length = 7)
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name = "ds_complemento", length = 15)
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@Column(name = "ds_bairro", length = 30)
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name = "ds_nome_mae", length = 70)
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	@Column(name = "tp_sexo")
	@Enumerated(EnumType.STRING)
	public TipoSexoEnum getSexo() {
		return sexo;
	}
	public void setSexo(TipoSexoEnum sexo) {
		this.sexo = sexo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_nascimento")
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Column(name = "ds_telefone", length = 14)
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Column(name = "ds_cpf", length = 14)
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return profissionalInclusao;
	}
	
	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@Column(name = "cv_numero_sus")
	public String getNumeroSus() {
		return numeroSus;
	}
	public void setNumeroSus(String numeroSus) {
		this.numeroSus = numeroSus;
	}
	
	@Transient
	public String getNomeIdade(){
		return nome.concat(" - ").concat(Utilities.idadeAtual(dataNascimento));
	}
	
	@Transient
	public String getIdade(){
		return Utilities.idadeAtual(dataNascimento);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Paciente))
			return false;
		
		return ((Paciente)obj).getIdPaciente() == this.idPaciente;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode() + dataInclusao.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}

}