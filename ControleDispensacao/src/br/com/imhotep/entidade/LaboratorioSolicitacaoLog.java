package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;

import br.com.imhotep.entidade.LaboratorioSolicitacao;

import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;

import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameEnum;

import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;


@Entity
@Table(name = "tb_laboratorio_solicitacao_log", schema="laboratorio")
public class LaboratorioSolicitacaoLog implements Serializable {
	private static final long serialVersionUID = 2231805785353864620L;
	
	private int idLaboratorioSolicitacaoLog;
	private TipoStatusSolicitacaoExameEnum tipoLog;
	private Date dataLog;
	private Profissional profissionalLog;
	private LaboratorioSolicitacao laboratorioSolicitacao;
	private String justificativa;

	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_solicitacao_lo_id_laboratorio_solicitacao_lo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_solicitacao_log", unique = true, nullable = false)	
	public int getIdLaboratorioSolicitacaoLog() {
		return idLaboratorioSolicitacaoLog;
	}

	public void setIdLaboratorioSolicitacaoLog(int idLaboratorioSolicitacaoLog) {
		this.idLaboratorioSolicitacaoLog = idLaboratorioSolicitacaoLog;
	}

	@Column(name = "tp_log")
	@Enumerated(EnumType.STRING)	
	public TipoStatusSolicitacaoExameEnum getTipoLog() {
		return tipoLog;
	}

	public void setTipoLog(TipoStatusSolicitacaoExameEnum tipoLog) {
		this.tipoLog = tipoLog;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_log")	
	public Date getDataLog() {
		return dataLog;
	}

	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_log")	
	public Profissional getProfissionalLog() {
		return profissionalLog;
	}

	public void setProfissionalLog(Profissional profissionalLog) {
		this.profissionalLog = profissionalLog;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_solicitacao")	
	public LaboratorioSolicitacao getLaboratorioSolicitacao() {
		return laboratorioSolicitacao;
	}

	public void setLaboratorioSolicitacao(LaboratorioSolicitacao laboratorioSolicitacao) {
		this.laboratorioSolicitacao = laboratorioSolicitacao;
	}

	@Column(name = "cv_justificativa")	
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataLog == null) ? 0 : dataLog.hashCode());
		result = prime * result + idLaboratorioSolicitacaoLog;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime
				* result
				+ ((laboratorioSolicitacao == null) ? 0
						: laboratorioSolicitacao.hashCode());
		result = prime * result
				+ ((profissionalLog == null) ? 0 : profissionalLog.hashCode());
		result = prime * result + ((tipoLog == null) ? 0 : tipoLog.hashCode());
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
		LaboratorioSolicitacaoLog other = (LaboratorioSolicitacaoLog) obj;
		if (dataLog == null) {
			if (other.dataLog != null)
				return false;
		} else if (!dataLog.equals(other.dataLog))
			return false;
		if (idLaboratorioSolicitacaoLog != other.idLaboratorioSolicitacaoLog)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (laboratorioSolicitacao == null) {
			if (other.laboratorioSolicitacao != null)
				return false;
		} else if (!laboratorioSolicitacao.equals(other.laboratorioSolicitacao))
			return false;
		if (profissionalLog == null) {
			if (other.profissionalLog != null)
				return false;
		} else if (!profissionalLog.equals(other.profissionalLog))
			return false;
		if (tipoLog != other.tipoLog)
			return false;
		return true;
	}

}
