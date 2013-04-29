package br.com.imhotep.entidade;

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

import br.com.imhotep.enums.TipoSanguineoEnum;
import br.com.imhotep.enums.TipoSexoEnum;
import br.com.remendo.utilidades.Utilities;

@Entity
@Table(name = "tb_paciente")
public class Paciente {
	private int idPaciente;
	private Unidade unidadeCadastro;
	private Cidade cidadeNaturalidade;
	private String nome;
	private String nomeMae;
	private String nomePai;
	private TipoSexoEnum sexo;
	private Date dataNascimento;
	private String cpf;
	private Date dataInclusao;
	private Profissional profissionalInclusao;
	private String numeroSus;
	private String prontuario;
	private TipoSanguineoEnum tipoSanguineo;
	private String registroGeral;
	private String orgaoRegistroGeral;
	private String nacionalidade;
	private String naturalidade;
	private String cor;
	
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
	@JoinColumn(name = "id_cidade_naturalidade")
	public Cidade getCidadeNaturalidade() {
		return cidadeNaturalidade;
	}
	public void setCidadeNaturalidade(Cidade cidade) {
		this.cidadeNaturalidade = cidade;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_nome_mae")
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	@Column(name = "cv_nome_pai")
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
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
	
	@Column(name = "cv_cpf", length = 14)
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
	
	@Column(name = "cv_prontuario")
	public String getProntuario() {
		return prontuario;
	}
	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
	
	@Column(name = "tp_sangue")
	@Enumerated(EnumType.STRING)
	public TipoSanguineoEnum getTipoSanguineo() {
		return tipoSanguineo;
	}
	public void setTipoSanguineo(TipoSanguineoEnum tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}
	
	@Column(name = "cv_registro_geral")
	public String getRegistroGeral() {
		return registroGeral;
	}
	public void setRegistroGeral(String registroGeral) {
		this.registroGeral = registroGeral;
	}
	
	
	@Column(name = "cv_orgao_registro_geral")
	public String getOrgaoRegistroGeral() {
		return orgaoRegistroGeral;
	}
	public void setOrgaoRegistroGeral(String orgaoRegistroGeral) {
		this.orgaoRegistroGeral = orgaoRegistroGeral;
	}
	
	@Column(name = "cv_nacionalidade")
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	@Column(name = "cv_cor")
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	@Column(name = "cv_naturalidade")
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	
	@Transient
	public String getNomeIdade(){
		return nome.concat(" - ").concat(getIdade());
	}
	
	@Transient
	public String getIdade(){
		return Utilities.idadeAtual(dataNascimento);
	}
	
	@Transient
	public String getNomeSus(){
		return nome.concat(" - ").concat(getNumeroSus() != null ? getNumeroSus() : "S/N");
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
