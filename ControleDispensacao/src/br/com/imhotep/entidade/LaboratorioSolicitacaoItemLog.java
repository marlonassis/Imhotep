package br.com.imhotep.entidade;

import java.io.Serializable;
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

import br.com.imhotep.enums.TipoStatusSolicitacaoExameItemEnum;


@Entity
@Table(name = "tb_laboratorio_solicitacao_item_log", schema="laboratorio")
public class LaboratorioSolicitacaoItemLog implements Serializable {
	private static final long serialVersionUID = 868996053964199701L;
	
	private int idLaboratorioSolicitacaoItemLog;
	private TipoStatusSolicitacaoExameItemEnum tipoLog;
	private Date dataLog;
	private Profissional profissionalLog;
	private LaboratorioSolicitacaoItem laboratorioSolicitacaoItem;
	private String justificativa;

	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_solicitacao_it_id_laboratorio_solicitacao_i_seq2")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_solicitacao_item_log", unique = true, nullable = false)	
	public int getIdLaboratorioSolicitacaoItemLog() {
		return idLaboratorioSolicitacaoItemLog;
	}

	public void setIdLaboratorioSolicitacaoItemLog(int idLaboratorioSolicitacaoItemLog) {
		this.idLaboratorioSolicitacaoItemLog = idLaboratorioSolicitacaoItemLog;
	}

	@Column(name = "tp_log")
	@Enumerated(EnumType.STRING)	
	public TipoStatusSolicitacaoExameItemEnum getTipoLog() {
		return tipoLog;
	}

	public void setTipoLog(TipoStatusSolicitacaoExameItemEnum tipoLog) {
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
	@JoinColumn(name = "id_laboratorio_solicitacao_item")	
	public LaboratorioSolicitacaoItem getLaboratorioSolicitacaoItem() {
		return laboratorioSolicitacaoItem;
	}

	public void setLaboratorioSolicitacaoItem(LaboratorioSolicitacaoItem laboratorioSolicitacaoItem) {
		this.laboratorioSolicitacaoItem = laboratorioSolicitacaoItem;
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
		result = prime * result + idLaboratorioSolicitacaoItemLog;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime
				* result
				+ ((laboratorioSolicitacaoItem == null) ? 0
						: laboratorioSolicitacaoItem.hashCode());
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
		LaboratorioSolicitacaoItemLog other = (LaboratorioSolicitacaoItemLog) obj;
		if (dataLog == null) {
			if (other.dataLog != null)
				return false;
		} else if (!dataLog.equals(other.dataLog))
			return false;
		if (idLaboratorioSolicitacaoItemLog != other.idLaboratorioSolicitacaoItemLog)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (laboratorioSolicitacaoItem == null) {
			if (other.laboratorioSolicitacaoItem != null)
				return false;
		} else if (!laboratorioSolicitacaoItem
				.equals(other.laboratorioSolicitacaoItem))
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
