package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_medicamento_preco_medio_log", schema="farmacia")
public class MedicamentoPrecoMedioLog implements Serializable {
	private static final long serialVersionUID = 4260163261369231353L;
	
	private int idMedicamentoPrecoMedioLog;
	private Material medicamento;
	private Double precoMedio;
	private Profissional profissionalResponsavel;
	private Date dataCadastro;
	private String justificativa;
	
	@SequenceGenerator(name = "generator", sequenceName = "farmacia.tb_medicamento_preco_medio_lo_id_medicamento_preco_medio_lo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_medicamento_preco_medio_log", unique = true, nullable = false)
	public int getIdMedicamentoPrecoMedioLog() {
		return idMedicamentoPrecoMedioLog;
	}
	
	public void setIdMedicamentoPrecoMedioLog(int idMedicamentoPrecoMedioLog) {
		this.idMedicamentoPrecoMedioLog = idMedicamentoPrecoMedioLog;
	}
	
	@Column(name = "db_preco_medio")
	public Double getPrecoMedio() {
		return precoMedio;
	}
	
	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_responsavel")
	public Profissional getProfissionalResponsavel() {
		return profissionalResponsavel;
	}
	
	public void setProfissionalResponsavel(Profissional profissionalResponsavel) {
		this.profissionalResponsavel = profissionalResponsavel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_medicamento")
	public Material getMedicamento() {
		return medicamento;
	}
	
	public void setMedicamento(Material medicamento) {
		this.medicamento = medicamento;
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
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + idMedicamentoPrecoMedioLog;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result
				+ ((medicamento == null) ? 0 : medicamento.hashCode());
		result = prime * result
				+ ((precoMedio == null) ? 0 : precoMedio.hashCode());
		result = prime
				* result
				+ ((profissionalResponsavel == null) ? 0
						: profissionalResponsavel.hashCode());
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
		MedicamentoPrecoMedioLog other = (MedicamentoPrecoMedioLog) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (idMedicamentoPrecoMedioLog != other.idMedicamentoPrecoMedioLog)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (medicamento == null) {
			if (other.medicamento != null)
				return false;
		} else if (!medicamento.equals(other.medicamento))
			return false;
		if (precoMedio == null) {
			if (other.precoMedio != null)
				return false;
		} else if (!precoMedio.equals(other.precoMedio))
			return false;
		if (profissionalResponsavel == null) {
			if (other.profissionalResponsavel != null)
				return false;
		} else if (!profissionalResponsavel
				.equals(other.profissionalResponsavel))
			return false;
		return true;
	}

}
