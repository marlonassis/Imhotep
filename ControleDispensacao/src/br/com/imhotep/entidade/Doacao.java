package br.com.imhotep.entidade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "tb_doacao", schema="farmacia")
public class Doacao  implements Serializable {
	private static final long serialVersionUID = -920930199567785029L;
	
	private int idDoacao;
	private String observacao;
	private boolean liberado;
	private Hospital hospital;
	private Date dataDoacao;
	private String cpf;
	private Profissional profissionalCadastro;
	private Profissional profissionalAutorizacao;
	private TipoMovimento tipoMovimento;
	
	@SequenceGenerator(name = "generator", sequenceName = "farmacia.tb_doacao_id_doacao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_doacao", unique = true, nullable = false)
	public int getIdDoacao() {
		return idDoacao;
	}
	public void setIdDoacao(int idDoacao) {
		this.idDoacao = idDoacao;
	}
	
	@Column(name = "cv_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Column(name = "bl_liberado")
	public boolean getLiberado() {
		return liberado;
	}
	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hospital")
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_doacao")
	public Date getDataDoacao() {
		return dataDoacao;
	}
	public void setDataDoacao(Date dataDoacao) {
		this.dataDoacao = dataDoacao;
	}
	
	@Column(name = "cv_cpf")
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_autorizacao")
	public Profissional getProfissionalAutorizacao() {
		return profissionalAutorizacao;
	}
	public void setProfissionalAutorizacao(Profissional profissionalAutorizacao) {
		this.profissionalAutorizacao = profissionalAutorizacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_movimento")
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((dataDoacao == null) ? 0 : dataDoacao.hashCode());
		result = prime * result
				+ ((hospital == null) ? 0 : hospital.hashCode());
		result = prime * result + idDoacao;
		result = prime * result + (liberado ? 1231 : 1237);
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime
				* result
				+ ((profissionalAutorizacao == null) ? 0
						: profissionalAutorizacao.hashCode());
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
						.hashCode());
		result = prime * result
				+ ((tipoMovimento == null) ? 0 : tipoMovimento.hashCode());
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
		Doacao other = (Doacao) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataDoacao == null) {
			if (other.dataDoacao != null)
				return false;
		} else if (!dataDoacao.equals(other.dataDoacao))
			return false;
		if (hospital == null) {
			if (other.hospital != null)
				return false;
		} else if (!hospital.equals(other.hospital))
			return false;
		if (idDoacao != other.idDoacao)
			return false;
		if (liberado != other.liberado)
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (profissionalAutorizacao == null) {
			if (other.profissionalAutorizacao != null)
				return false;
		} else if (!profissionalAutorizacao
				.equals(other.profissionalAutorizacao))
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		if (tipoMovimento == null) {
			if (other.tipoMovimento != null)
				return false;
		} else if (!tipoMovimento.equals(other.tipoMovimento))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Hospital: " + hospital.getNome() + ", Data: " + new SimpleDateFormat("dd/MM/yyyy").format(dataDoacao);
	}
}
