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
@Table(name = "tb_laboratorio_setor", schema="laboratorio")
public class LaboratorioSetor implements Serializable{
	private static final long serialVersionUID = 5259849439705357915L;
	
	private int idLaboratorioSetor;
	private String descricao;
	private Date dataCadastro;
	private Profissional profissionalCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_setor_id_laboratorio_setor_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_setor", unique = true, nullable = false)
	public int getIdLaboratorioSetor() {
		return idLaboratorioSetor;
	}
	public void setIdLaboratorioSetor(int idLaboratorioSetor) {
		this.idLaboratorioSetor = idLaboratorioSetor;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLaboratorioSetor;
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
		LaboratorioSetor other = (LaboratorioSetor) obj;
		if (idLaboratorioSetor != other.idLaboratorioSetor)
			return false;
		return true;
	}
	
}
