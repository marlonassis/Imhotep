package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.imhotep.comparador.LogSolicitacaoExameItemComparador;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameItemEnum;


@Entity
@Table(name = "tb_laboratorio_solicitacao_item", schema="laboratorio")
public class LaboratorioSolicitacaoItem implements Serializable {
	private static final long serialVersionUID = 424448658498860465L;
	
	private int idLaboratorioSolicitacaoItem;
	private TipoStatusSolicitacaoExameItemEnum statusItem;
	private String justificativaSolicitacao;
	private String resultado;
	private Date dataColetaPrevista;
	private Profissional profissionalSolicitacao;
	private LaboratorioExame laboratorioExame;
	private LaboratorioSolicitacao laboratorioSolicitacao;
	private Date dataColeta;
	private Set<LaboratorioSolicitacaoItemLog> log;

	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_solicitacao_it_id_laboratorio_solicitacao_it_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_solicitacao_item", unique = true, nullable = false)	
	public int getIdLaboratorioSolicitacaoItem() {
		return idLaboratorioSolicitacaoItem;
	}

	public void setIdLaboratorioSolicitacaoItem(int idLaboratorioSolicitacaoItem) {
		this.idLaboratorioSolicitacaoItem = idLaboratorioSolicitacaoItem;
	}

	@Column(name = "tp_status_item")
	@Enumerated(EnumType.STRING)	
	public TipoStatusSolicitacaoExameItemEnum getStatusItem() {
		return statusItem;
	}

	public void setStatusItem(TipoStatusSolicitacaoExameItemEnum statusItem) {
		this.statusItem = statusItem;
	}

	@Column(name = "cv_justificativa_solicitacao")	
	public String getJustificativaSolicitacao() {
		return justificativaSolicitacao;
	}

	public void setJustificativaSolicitacao(String justificativaSolicitacao) {
		this.justificativaSolicitacao = justificativaSolicitacao;
	}

	@Column(name = "cv_resultado")	
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_coleta_prevista")	
	public Date getDataColetaPrevista() {
		return dataColetaPrevista;
	}

	public void setDataColetaPrevista(Date dataColetaPrevista) {
		this.dataColetaPrevista = dataColetaPrevista;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_solicitacao")	
	public Profissional getProfissionalSolicitacao() {
		return profissionalSolicitacao;
	}

	public void setProfissionalSolicitacao(Profissional profissionalSolicitacao) {
		this.profissionalSolicitacao = profissionalSolicitacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_exame")	
	public LaboratorioExame getLaboratorioExame() {
		return laboratorioExame;
	}

	public void setLaboratorioExame(LaboratorioExame laboratorioExame) {
		this.laboratorioExame = laboratorioExame;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_coleta")	
	public Date getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_solicitacao")	
	public LaboratorioSolicitacao getLaboratorioSolicitacao() {
		return laboratorioSolicitacao;
	}

	public void setLaboratorioSolicitacao(LaboratorioSolicitacao laboratorioSolicitacao) {
		this.laboratorioSolicitacao = laboratorioSolicitacao;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "laboratorioSolicitacaoItem")
	@Fetch(FetchMode.SUBSELECT)
	public Set<LaboratorioSolicitacaoItemLog> getLog() {
		return log;
	}
	
	public void setLog(Set<LaboratorioSolicitacaoItemLog> log) {
		this.log = log;
	}
	
	@Transient
	public List<LaboratorioSolicitacaoItemLog> getLogLista(){
		if(getLog() != null){
			List<LaboratorioSolicitacaoItemLog> list = new ArrayList<LaboratorioSolicitacaoItemLog>(getLog());
			Collections.sort(list, new LogSolicitacaoExameItemComparador());
			return list;
		}
		return new ArrayList<LaboratorioSolicitacaoItemLog>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataColeta == null) ? 0 : dataColeta.hashCode());
		result = prime
				* result
				+ ((dataColetaPrevista == null) ? 0 : dataColetaPrevista
						.hashCode());
		result = prime * result + idLaboratorioSolicitacaoItem;
		result = prime
				* result
				+ ((justificativaSolicitacao == null) ? 0
						: justificativaSolicitacao.hashCode());
		result = prime
				* result
				+ ((laboratorioExame == null) ? 0 : laboratorioExame
						.hashCode());
		result = prime
				* result
				+ ((laboratorioSolicitacao == null) ? 0
						: laboratorioSolicitacao.hashCode());
		result = prime
				* result
				+ ((profissionalSolicitacao == null) ? 0
						: profissionalSolicitacao.hashCode());
		result = prime * result
				+ ((resultado == null) ? 0 : resultado.hashCode());
		result = prime
				* result
				+ ((statusItem == null) ? 0 : statusItem
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
		LaboratorioSolicitacaoItem other = (LaboratorioSolicitacaoItem) obj;
		if (dataColeta == null) {
			if (other.dataColeta != null)
				return false;
		} else if (!dataColeta.equals(other.dataColeta))
			return false;
		if (dataColetaPrevista == null) {
			if (other.dataColetaPrevista != null)
				return false;
		} else if (!dataColetaPrevista.equals(other.dataColetaPrevista))
			return false;
		if (idLaboratorioSolicitacaoItem != other.idLaboratorioSolicitacaoItem)
			return false;
		if (justificativaSolicitacao == null) {
			if (other.justificativaSolicitacao != null)
				return false;
		} else if (!justificativaSolicitacao
				.equals(other.justificativaSolicitacao))
			return false;
		if (laboratorioExame == null) {
			if (other.laboratorioExame != null)
				return false;
		} else if (!laboratorioExame.equals(other.laboratorioExame))
			return false;
		if (laboratorioSolicitacao == null) {
			if (other.laboratorioSolicitacao != null)
				return false;
		} else if (!laboratorioSolicitacao.equals(other.laboratorioSolicitacao))
			return false;
		if (profissionalSolicitacao == null) {
			if (other.profissionalSolicitacao != null)
				return false;
		} else if (!profissionalSolicitacao
				.equals(other.profissionalSolicitacao))
			return false;
		if (resultado == null) {
			if (other.resultado != null)
				return false;
		} else if (!resultado.equals(other.resultado))
			return false;
		if (statusItem != other.statusItem)
			return false;
		return true;
	}
	
}
