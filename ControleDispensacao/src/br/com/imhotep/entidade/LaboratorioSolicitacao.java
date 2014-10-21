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

import br.com.imhotep.comparador.LogSolicitacaoExameComparador;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameEnum;


@Entity
@Table(name = "tb_laboratorio_solicitacao", schema="laboratorio")
public class LaboratorioSolicitacao implements Serializable {
	private static final long serialVersionUID = 2647716188613916354L;
	
	private int idLaboratorioSolicitacao;
	private Profissional profissionalSolicitacao;
	private Paciente paciente;
	private Date dataSolicitacao;
	private String justificativa;
	private TipoStatusSolicitacaoExameEnum statusSolicitacao;
	private Unidade unidade;
	private Set<LaboratorioSolicitacaoItem> itens;
	private Set<LaboratorioSolicitacaoLog> log;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_solicitacao_id_laboratorio_solicitacao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_solicitacao", unique = true, nullable = false)	
	public int getIdLaboratorioSolicitacao() {
		return idLaboratorioSolicitacao;
	}

	public void setIdLaboratorioSolicitacao(int idLaboratorioSolicitacao) {
		this.idLaboratorioSolicitacao = idLaboratorioSolicitacao;
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
	@JoinColumn(name = "id_unidade_paciente")
	public Unidade getUnidade() {
		return unidade;
	}
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente")	
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_solicitacao")	
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	@Column(name = "cv_justificativa")	
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@Column(name = "tp_status_solicitacao")
	@Enumerated(EnumType.STRING)	
	public TipoStatusSolicitacaoExameEnum getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(TipoStatusSolicitacaoExameEnum statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "laboratorioSolicitacao")
	@Fetch(FetchMode.SUBSELECT)
	public Set<LaboratorioSolicitacaoItem> getItens() {
		return itens;
	}
	
	public void setItens(Set<LaboratorioSolicitacaoItem> itens) {
		this.itens = itens;
	}
	
	@Transient
	public List<LaboratorioSolicitacaoItem> getItensLista(){
		if(getItens() != null)
			return new ArrayList<LaboratorioSolicitacaoItem>(getItens());
		return new ArrayList<LaboratorioSolicitacaoItem>();
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "laboratorioSolicitacao")
	@Fetch(FetchMode.SUBSELECT)
	public Set<LaboratorioSolicitacaoLog> getLog() {
		return log;
	}
	
	public void setLog(Set<LaboratorioSolicitacaoLog> log) {
		this.log = log;
	}
	
	@Transient
	public List<LaboratorioSolicitacaoLog> getLogLista(){
		if(getLog() != null){
			List<LaboratorioSolicitacaoLog> list = new ArrayList<LaboratorioSolicitacaoLog>(getLog());
			Collections.sort(list, new LogSolicitacaoExameComparador());
			return list;
		}
		return new ArrayList<LaboratorioSolicitacaoLog>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataSolicitacao == null) ? 0 : dataSolicitacao.hashCode());
		result = prime * result + idLaboratorioSolicitacao;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result
				+ ((paciente == null) ? 0 : paciente.hashCode());
		result = prime
				* result
				+ ((profissionalSolicitacao == null) ? 0
						: profissionalSolicitacao.hashCode());
		result = prime
				* result
				+ ((statusSolicitacao == null) ? 0 : statusSolicitacao
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
		LaboratorioSolicitacao other = (LaboratorioSolicitacao) obj;
		if (dataSolicitacao == null) {
			if (other.dataSolicitacao != null)
				return false;
		} else if (!dataSolicitacao.equals(other.dataSolicitacao))
			return false;
		if (idLaboratorioSolicitacao != other.idLaboratorioSolicitacao)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (paciente == null) {
			if (other.paciente != null)
				return false;
		} else if (!paciente.equals(other.paciente))
			return false;
		if (profissionalSolicitacao == null) {
			if (other.profissionalSolicitacao != null)
				return false;
		} else if (!profissionalSolicitacao
				.equals(other.profissionalSolicitacao))
			return false;
		if (statusSolicitacao != other.statusSolicitacao)
			return false;
		return true;
	}

}
