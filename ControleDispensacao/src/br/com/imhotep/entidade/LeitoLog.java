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

import br.com.imhotep.enums.TipoStatusLeitoEnum;

@Entity
@Table(name = "tb_leito_log")
public class LeitoLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idLeitoLog;
	private Leito leito;
	private TipoStatusLeitoEnum tipoStatus;
	private Unidade unidadeAntiga;
	private Unidade unidadeAtual;
	private Unidade unidadeEmprestimo;
	private Profissional profissionalLog;
	private Date dataLog;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_leito_log_id_leito_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_leito_log", unique = true, nullable = false)
	public int getIdLeitoLog() {
		return idLeitoLog;
	}
	public void setIdLeitoLog(int idLeito) {
		this.idLeitoLog = idLeito;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_leito")
	public Leito getLeito() {
		return leito;
	}
	public void setLeito(Leito leito) {
		this.leito = leito;
	}
	
	@Column(name = "tp_tipo_status_leito")
	@Enumerated(EnumType.STRING)
	public TipoStatusLeitoEnum getTipoStatus() {
		return tipoStatus;
	}
	public void setTipoStatus(TipoStatusLeitoEnum tipoStatus) {
		this.tipoStatus = tipoStatus;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_antiga")
	public Unidade getUnidadeAntiga() {
		return unidadeAntiga;
	}
	public void setUnidadeAntiga(Unidade unidadeAntiga) {
		this.unidadeAntiga = unidadeAntiga;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_atual")
	public Unidade getUnidadeAtual() {
		return unidadeAtual;
	}
	public void setUnidadeAtual(Unidade unidadeAtual) {
		this.unidadeAtual = unidadeAtual;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_emprestimo")
	public Unidade getUnidadeEmprestimo() {
		return unidadeEmprestimo;
	}
	public void setUnidadeEmprestimo(Unidade unidadeEmprestimo) {
		this.unidadeEmprestimo = unidadeEmprestimo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_log")
	public Profissional getProfissionalLog() {
		return profissionalLog;
	}
	public void setProfissionalLog(Profissional profissionalLog) {
		this.profissionalLog = profissionalLog;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_log")
	public Date getDataLog() {
		return dataLog;
	}
	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataLog == null) ? 0 : dataLog.hashCode());
		result = prime * result + idLeitoLog;
		result = prime * result + ((leito == null) ? 0 : leito.hashCode());
		result = prime * result
				+ ((profissionalLog == null) ? 0 : profissionalLog.hashCode());
		result = prime * result
				+ ((tipoStatus == null) ? 0 : tipoStatus.hashCode());
		result = prime * result
				+ ((unidadeAntiga == null) ? 0 : unidadeAntiga.hashCode());
		result = prime * result
				+ ((unidadeAtual == null) ? 0 : unidadeAtual.hashCode());
		result = prime
				* result
				+ ((unidadeEmprestimo == null) ? 0 : unidadeEmprestimo
						.hashCode());
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
		LeitoLog other = (LeitoLog) obj;
		if (dataLog == null) {
			if (other.dataLog != null)
				return false;
		} else if (!dataLog.equals(other.dataLog))
			return false;
		if (idLeitoLog != other.idLeitoLog)
			return false;
		if (leito == null) {
			if (other.leito != null)
				return false;
		} else if (!leito.equals(other.leito))
			return false;
		if (profissionalLog == null) {
			if (other.profissionalLog != null)
				return false;
		} else if (!profissionalLog.equals(other.profissionalLog))
			return false;
		if (tipoStatus != other.tipoStatus)
			return false;
		if (unidadeAntiga == null) {
			if (other.unidadeAntiga != null)
				return false;
		} else if (!unidadeAntiga.equals(other.unidadeAntiga))
			return false;
		if (unidadeAtual == null) {
			if (other.unidadeAtual != null)
				return false;
		} else if (!unidadeAtual.equals(other.unidadeAtual))
			return false;
		if (unidadeEmprestimo == null) {
			if (other.unidadeEmprestimo != null)
				return false;
		} else if (!unidadeEmprestimo.equals(other.unidadeEmprestimo))
			return false;
		return true;
	}	
	
}
