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
@Table(name = "tb_laboratorio_setor_exame", schema="laboratorio")
public class LaboratorioSetorExame implements Serializable {
	private static final long serialVersionUID = -184827254422240796L;
	
	private int idLaboratorioSetorExame;
	private LaboratorioSetor laboratorioSetor;
	private LaboratorioExame laboratorioExame;
	private Profissional profissionalCadastro;
	private Date dataCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_setor_exame_id_laboratorio_setor_exame_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_setor_exame", unique = true, nullable = false)
	public int getIdLaboratorioSetorExame() {
		return idLaboratorioSetorExame;
	}
	public void setIdLaboratorioSetorExame(int idLaboratorioSetorExame) {
		this.idLaboratorioSetorExame = idLaboratorioSetorExame;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_setor")
	public LaboratorioSetor getLaboratorioSetor() {
		return laboratorioSetor;
	}
	public void setLaboratorioSetor(LaboratorioSetor laboratorioSetor) {
		this.laboratorioSetor = laboratorioSetor;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_exame")
	public LaboratorioExame getLaboratorioExame() {
		return laboratorioExame;
	}
	public void setLaboratorioExame(LaboratorioExame laboratorioExame) {
		this.laboratorioExame = laboratorioExame;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + idLaboratorioSetorExame;
		result = prime
				* result
				+ ((laboratorioExame == null) ? 0 : laboratorioExame
						.hashCode());
		result = prime
				* result
				+ ((laboratorioSetor == null) ? 0 : laboratorioSetor.hashCode());
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
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
		LaboratorioSetorExame other = (LaboratorioSetorExame) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (idLaboratorioSetorExame != other.idLaboratorioSetorExame)
			return false;
		if (laboratorioExame == null) {
			if (other.laboratorioExame != null)
				return false;
		} else if (!laboratorioExame.equals(other.laboratorioExame))
			return false;
		if (laboratorioSetor == null) {
			if (other.laboratorioSetor != null)
				return false;
		} else if (!laboratorioSetor.equals(other.laboratorioSetor))
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		return true;
	}
	
}
