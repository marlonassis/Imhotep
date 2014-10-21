package br.com.imhotep.entidade;

import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "tb_prescricao_antiga")
public class PrescricaoAntiga {

	private int idPrescricaoAntiga;
	private Date dataPrescricao;
	private Paciente paciente;
	private Double massa;
	private Integer idade;
	private Unidade unidade;
	private String leito;
	private Date dataInsercao;
	private Profissional profissionalInsercao;
	private List<PrescricaoAntigaArquivo> prescricoesAntigasArquivo;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_antiga_id_prescricao_antiga_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao_antiga", unique = true, nullable = false)
	public int getIdPrescricaoAntiga() {
		return idPrescricaoAntiga;
	}
	public void setIdPrescricaoAntiga(int idPrescricaoAntiga) {
		this.idPrescricaoAntiga = idPrescricaoAntiga;
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
	@Column(name = "dt_data_prescricao")
	public Date getDataPrescricao() {
		return dataPrescricao;
	}
	public void setDataPrescricao(Date dataPrescricao) {
		this.dataPrescricao = dataPrescricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@Column(name = "cv_leito")
	public String getLeito() {
		return leito;
	}
	public void setLeito(String leito) {
		this.leito = leito;
	}
	
	@Column(name = "in_idade")
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	@Column(name = "db_massa")
	public Double getMassa() {
		return massa;
	}
	public void setMassa(Double massa) {
		this.massa = massa;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}
	public void setProfissionalInsercao(Profissional profissionalInsercao) {
		this.profissionalInsercao = profissionalInsercao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "prescricaoAntiga")
	public List<PrescricaoAntigaArquivo> getPrescricoesAntigasArquivo() {
		return prescricoesAntigasArquivo;
	}
	public void setPrescricoesAntigasArquivo(List<PrescricaoAntigaArquivo> prescricoesAntigasArquivo) {
		this.prescricoesAntigasArquivo = prescricoesAntigasArquivo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof PrescricaoAntiga))
			return false;
		
		return ((PrescricaoAntiga)obj).getIdPrescricaoAntiga() == this.idPrescricaoAntiga;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + paciente.hashCode() + dataPrescricao.hashCode() + leito.hashCode() + dataInsercao.hashCode();
	}

	@Override
	public String toString() {
		return paciente.getNome();
	}
}
