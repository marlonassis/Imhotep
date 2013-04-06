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
	private TipoEstadoCivilEnum tipoEstadoCivil;
	private Cidade naturalidade;
	private String profissaoAtual;
	private String profissaoAnterior;
	private TipoEscolaridadeEnum tipoEscolaridade;
	private String religiao;
	private String informante;
	private String grauParentescoInformante;
	private Profissional profissionalInclusao;
	private Date dataEntrada;
	private Date dataInclusao;
	
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
	
	@Column(name = "cv_informante")
	public String getInformante() {
		return informante;
	}
	public void setInformante(String informante) {
		this.informante = informante;
	}
	
	@Column(name = "cv_grau_parentesco_informante")
	public String getGrauParentescoInformante() {
		return grauParentescoInformante;
	}
	public void setGrauParentescoInformante(String grauParentescoInformante) {
		this.grauParentescoInformante = grauParentescoInformante;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return profissionalInclusao;
	}
	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_entrada")
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
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
	    return hash * 31 + dataEntrada.hashCode() + dataInclusao.hashCode();
	}

}
