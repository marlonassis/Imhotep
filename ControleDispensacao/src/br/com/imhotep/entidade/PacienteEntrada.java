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

import br.com.imhotep.enums.TipoEscolaridadeEnum;
import br.com.imhotep.enums.TipoEstadoCivilEnum;

@Entity
@Table(name = "tb_paciente_entrada")
public class PacienteEntrada {
	
	private int idPacienteEntrada;
	private Paciente paciente;
	private Unidade unidadeAlocacao;
	private Profissional profissionalResponsavel;
	private Convenio convenio;
	private Profissional profissionalInclusao;
	private Cidade naturalidade;
	private TipoEstadoCivilEnum tipoEstadoCivil;
	private TipoEscolaridadeEnum tipoEscolaridade;
	private Date dataInclusao;
	private String profissaoAtual;
	private String profissaoAnterior;
	private String escolaridadeSerie;
	private String religiao;
	private String leito;
	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String telefone1;
	private String telefone2;
	private Cidade cidade;
	private PacienteEntradaResponsavel pacienteEntradaResponsavel;
	private String observacao;
	
	public PacienteEntrada() {
		super();
	}
	
	public PacienteEntrada(PacienteEntrada pacienteEntrada) {
		this.paciente = pacienteEntrada.getPaciente();
		this.unidadeAlocacao = pacienteEntrada.getUnidadeAlocacao();
		this.profissionalResponsavel = pacienteEntrada.getProfissionalResponsavel();
		this.convenio = pacienteEntrada.getConvenio();
		this.profissionalInclusao = pacienteEntrada.getProfissionalInclusao();
		this.naturalidade = pacienteEntrada.getNaturalidade();
		this.tipoEstadoCivil = pacienteEntrada.getTipoEstadoCivil();
		this.tipoEscolaridade = pacienteEntrada.getTipoEscolaridade();
		this.dataInclusao = pacienteEntrada.getDataInclusao();
		this.profissaoAtual = pacienteEntrada.getProfissaoAtual();
		this.profissaoAnterior = pacienteEntrada.getProfissaoAnterior();
		this.escolaridadeSerie = pacienteEntrada.getEscolaridadeSerie();
		this.religiao = pacienteEntrada.getReligiao();
		this.leito = pacienteEntrada.getLeito();
		this.logradouro = pacienteEntrada.getLogradouro();
		this.numero = pacienteEntrada.getNumero();
		this.bairro = pacienteEntrada.getBairro();
		this.cep = pacienteEntrada.getCep();
		this.telefone1 = pacienteEntrada.getTelefone1();
		this.telefone2 = pacienteEntrada.getTelefone2();
		this.pacienteEntradaResponsavel = pacienteEntrada.getPacienteEntradaResponsavel().clone();
	}
	
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_paciente_entrada_id_paciente_entrada_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_paciente_entrada", unique = true, nullable = false)
	public int getIdPacienteEntrada() {
		return idPacienteEntrada;
	}
	public void setIdPacienteEntrada(int idPacienteEntrada) {
		this.idPacienteEntrada = idPacienteEntrada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_convenio")
	public Convenio getConvenio() {
		return convenio;
	}
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente")
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_alocacao")
	public Unidade getUnidadeAlocacao() {
		return unidadeAlocacao;
	}
	public void setUnidadeAlocacao(Unidade unidadeAlocacao) {
		this.unidadeAlocacao = unidadeAlocacao;
	}
	
	@Column(name = "tp_estado_civil")
	@Enumerated(EnumType.STRING)
	public TipoEstadoCivilEnum getTipoEstadoCivil() {
		return tipoEstadoCivil;
	}
	public void setTipoEstadoCivil(TipoEstadoCivilEnum tipoEstadoCivil) {
		this.tipoEstadoCivil = tipoEstadoCivil;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_naturalidade")
	public Cidade getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(Cidade naturalidade) {
		this.naturalidade = naturalidade;
	}
	
	@Column(name = "cv_profissao_atual")
	public String getProfissaoAtual() {
		return profissaoAtual;
	}
	public void setProfissaoAtual(String profissaoAtual) {
		this.profissaoAtual = profissaoAtual;
	}
	
	@Column(name = "cv_profissao_anterior")
	public String getProfissaoAnterior() {
		return profissaoAnterior;
	}
	public void setProfissaoAnterior(String profissaoAnterior) {
		this.profissaoAnterior = profissaoAnterior;
	}
	
	@Column(name = "cv_escolaridade_serie")
	public String getEscolaridadeSerie() {
		return escolaridadeSerie;
	}
	public void setEscolaridadeSerie(String escolaridadeSerie) {
		this.escolaridadeSerie = escolaridadeSerie;
	}
	
	@Column(name = "cv_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Column(name = "cv_logradouro")
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@Column(name = "cv_leito")
	public String getLeito() {
		return leito;
	}
	public void setLeito(String leito) {
		this.leito = leito;
	}
	
	@Column(name = "cv_numero")
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name = "cv_bairro")
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name = "cv_cep")
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Column(name = "cv_telefone_1")
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	
	@Column(name = "cv_telefone_2")
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Column(name = "tp_escolaridade")
	@Enumerated(EnumType.STRING)
	public TipoEscolaridadeEnum getTipoEscolaridade() {
		return tipoEscolaridade;
	}
	public void setTipoEscolaridade(TipoEscolaridadeEnum tipoEscolaridade) {
		this.tipoEscolaridade = tipoEscolaridade;
	}
	
	@Column(name = "cv_religiao")
	public String getReligiao() {
		return religiao;
	}
	public void setReligiao(String religiao) {
		this.religiao = religiao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return profissionalInclusao;
	}
	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_responsavel")
	public Profissional getProfissionalResponsavel() {
		return profissionalResponsavel;
	}
	public void setProfissionalResponsavel(Profissional profissionalResponsavel) {
		this.profissionalResponsavel = profissionalResponsavel;
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
	@JoinColumn(name = "id_paciente_entrada_responsavel")
	public PacienteEntradaResponsavel getPacienteEntradaResponsavel() {
		return pacienteEntradaResponsavel;
	}
	public void setPacienteEntradaResponsavel(PacienteEntradaResponsavel pacienteEntradaResponsavel) {
		this.pacienteEntradaResponsavel = pacienteEntradaResponsavel;
	}
	
	public PacienteEntrada clone(){
		return new PacienteEntrada(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof PacienteEntrada))
			return false;
		
		return ((PacienteEntrada)obj).getIdPacienteEntrada() == this.idPacienteEntrada;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + profissionalResponsavel.hashCode() + dataInclusao.hashCode();
	}

}
