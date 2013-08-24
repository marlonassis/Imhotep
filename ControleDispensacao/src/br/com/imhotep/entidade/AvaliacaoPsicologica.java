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
@Table(name = "tb_avaliacao_psicologica")
public class AvaliacaoPsicologica implements Serializable {
	private static final long serialVersionUID = -8216269846830472606L;
	
	private int idAvaliacaoPsicologica;
	private Paciente paciente;
	private String descricao;
	private Date dataAvaliacao;
	private Date dataCriacao;
	private Profissional profissionalCriacao;
	private Unidade unidade;
	private String leito;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_avaliacao_psicologica_id_avaliacao_psicologica_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_avaliacao_psicologica", unique = true, nullable = false)
	public int getIdAvaliacaoPsicologica() {
		return this.idAvaliacaoPsicologica;
	}
	
	public void setIdAvaliacaoPsicologica(int idAvaliacaoPsicologica){
		this.idAvaliacaoPsicologica = idAvaliacaoPsicologica;
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
	
	@Column(name = "cv_leito")
	public String getLeito() {
		return leito;
	}
	
	public void setLeito(String leito) {
		this.leito = leito;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade(){
		return unidade;
	}
	
	public void setUnidade(Unidade unidade){
		this.unidade = unidade;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AvaliacaoPsicologica))
			return false;
		
		return ((AvaliacaoPsicologica)obj).getIdAvaliacaoPsicologica() == this.idAvaliacaoPsicologica;
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
