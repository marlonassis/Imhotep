package br.com.Imhotep.entidade;

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
@Table(name = "tb_psicologia")
public class Psicologia {
	
	private int idPsicologia;
	private Paciente paciente;
	private String descricao;
	private Date dataAvaliacao;
	private Date dataCriacao;
	private Profissional profissionalCriacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_psicologia_id_psicologia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_psicologia", unique = true, nullable = false)
	public int getIdPsicologia() {
		return this.idPsicologia;
	}
	
	public void setIdPsicologia(int idPsicologia){
		this.idPsicologia = idPsicologia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente")
	public Paciente getPaciente(){
		return paciente;
	}
	
	public void setPaciente(Paciente paciente){
		this.paciente = paciente;
	}
	
	@Column(name = "tx_descricao")
	public String getDescricao(){
		return descricao;
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_avaliacao")
	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}
	
	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_criacao")
	public Profissional getProfissionalCriacao(){
		return profissionalCriacao;
	}
	
	public void setProfissionalCriacao(Profissional profissionalCriacao){
		this.profissionalCriacao = profissionalCriacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Psicologia))
			return false;
		
		return ((Psicologia)obj).getIdPsicologia() == this.idPsicologia;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataAvaliacao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
