package br.com.imhotep.entidade;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.imhotep.auxiliar.Utilitarios;

@Entity
@Table(name = "tb_prescricao")
public class Prescricao {
	private int idPrescricao;
	private Unidade unidade;
	private Profissional profissionalInclusao;
	private Paciente paciente;
	private Date dataInclusao;
	private Date dataConclusao;
	private String leito;
	private Double massa;
	private boolean dispensavel;
	private boolean dispensado;
	private List<PrescricaoItem> prescricaoItens;
	private Set<CuidadosPrescricao> cuidadosPrescricao;
	private Date dataDipensacao;
	private Profissional profissionalDispensante;
	private Profissional profissionalConclusao;
	private String observacao;
	private Date dataBloqueio;
	private String motivoBloqueio;
	private Profissional profissionalBloqueio;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_id_prescricao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao", unique = true, nullable = false)
	public int getIdPrescricao() {
		return idPrescricao;
	}
	public void setIdPrescricao(int idPrescricao) {
		this.idPrescricao = idPrescricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return profissionalInclusao;
	}
	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "id_paciente")
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_conclusao")
	public Date getDataConclusao() {
		return dataConclusao;
	}
	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
	
	@Column(name = "ds_leito")
	public String getLeito() {
		return leito;
	}
	public void setLeito(String leito) {
		this.leito = leito;
	}
	
	@Column(name = "db_massa")
	public Double getMassa() {
		return massa;
	}
	public void setMassa(Double massa) {
		this.massa = massa;
	}
	
	@Column(name = "bl_dispensavel")
	public boolean getDispensavel() {
		return dispensavel;
	}
	public void setDispensavel(boolean dispensavel) {
		this.dispensavel = dispensavel;
	}
	
	@Column(name = "bl_dispensado")
	public boolean getDispensado() {
		return dispensado;
	}
	public void setDispensado(boolean dispensado) {
		this.dispensado = dispensado;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "prescricao")
	public List<PrescricaoItem> getPrescricaoItens() {
		return prescricaoItens;
	}
	public void setPrescricaoItens(List<PrescricaoItem> prescricaoItens) {
		this.prescricaoItens = prescricaoItens;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "prescricao")
	public Set<CuidadosPrescricao> getCuidadosPrescricao() {
		return cuidadosPrescricao;
	}
	public void setCuidadosPrescricao(Set<CuidadosPrescricao> cuidadosPrescricao) {
		this.cuidadosPrescricao = cuidadosPrescricao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_dispensacao")
	public Date getDataDipensacao() {
		return dataDipensacao;
	}
	
	public void setDataDipensacao(Date dataDipensacao) {
		this.dataDipensacao = dataDipensacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_dispensante")
	public Profissional getProfissionalDispensante() {
		return profissionalDispensante;
	}
	public void setProfissionalDispensante(Profissional profissionalDispensante) {
		this.profissionalDispensante = profissionalDispensante;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_conclusao")
	public Profissional getProfissionalConclusao() {
		return profissionalConclusao;
	}
	public void setProfissionalConclusao(Profissional profissionalConclusao) {
		this.profissionalConclusao = profissionalConclusao;
	}
	
	@Column(name = "tx_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_bloqueio")
	public Date getDataBloqueio() {
		return dataBloqueio;
	}
	
	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}
	
	@Column(name = "cv_motivo_bloqueio")
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_bloqueio")
	public Profissional getProfissionalBloqueio() {
		return profissionalBloqueio;
	}
	public void setProfissionalBloqueio(Profissional profissionalBloqueio) {
		this.profissionalBloqueio = profissionalBloqueio;
	}
	
	@Transient
	public String getMassaFormatada(){
		if(getMassa() != null)
			return Utilitarios.doubleFormatadoBr(getMassa()).concat(" Kg");
		return "";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Prescricao))
			return false;
		
		return ((Prescricao)obj).getIdPrescricao() == this.idPrescricao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataInclusao.hashCode();
	}

	@Override
	public String toString() {
		return "Leito: ".concat(leito).concat(" - Paciente: ").concat(paciente.getNome());
	}
}
