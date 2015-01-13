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

import br.com.imhotep.enums.TipoCrudEnum;

@Entity
@Table(name = "tb_alteracao_locacao_log", schema="administrativo")
public class AlteracaoLocacaoLog implements Serializable {
	private static final long serialVersionUID = 7816640519449718856L;
	
	private int idAlteracaoLocacaoLog;
	private String estrutura;
	private String justificativa;
	private Profissional profissional;
	private TipoCrudEnum tipo;
	private Date dataCadastro;
	private Profissional profissionalCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_alteracao_locacao_log_id_alteracao_locacao_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_alteracao_locacao_log", unique = true, nullable = false)
	public int getIdAlteracaoLocacaoLog() {
		return idAlteracaoLocacaoLog;
	}
	
	public void setIdAlteracaoLocacaoLog(int idAlteracaoLocacaoLog) {
		this.idAlteracaoLocacaoLog = idAlteracaoLocacaoLog;
	}
	
	@Column(name = "cv_estrutura")
	public String getEstrutura() {
		return estrutura;
	}
	
	public void setEstrutura(String estrutura) {
		this.estrutura = estrutura;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Column(name = "tp_tipo")
	@Enumerated(EnumType.STRING)
	public TipoCrudEnum getTipo() {
		return tipo;
	}
	public void setTipo(TipoCrudEnum tipo) {
		this.tipo = tipo;
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
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional() {
		return profissional;
	}
	
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
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
		result = prime * result
				+ ((estrutura == null) ? 0 : estrutura.hashCode());
		result = prime * result + idAlteracaoLocacaoLog;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result
				+ ((profissional == null) ? 0 : profissional.hashCode());
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
						.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		AlteracaoLocacaoLog other = (AlteracaoLocacaoLog) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (estrutura == null) {
			if (other.estrutura != null)
				return false;
		} else if (!estrutura.equals(other.estrutura))
			return false;
		if (idAlteracaoLocacaoLog != other.idAlteracaoLocacaoLog)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (profissional == null) {
			if (other.profissional != null)
				return false;
		} else if (!profissional.equals(other.profissional))
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
}
