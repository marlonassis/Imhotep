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
@Table(name = "tb_alteracao_estrutura_log", schema="administrativo")
public class AlteracaoEstruturaLog implements Serializable {
	private static final long serialVersionUID = 775740991820108271L;
	
	private int idAlteracaoEstruturaLog;
	private String nome;
	private String grupo;
	private Date dataCadastro;
	private Profissional profissionalCadastro;
	private EstruturaOrganizacional estruturaOrganizacional;
	private String justificativa;
	private TipoCrudEnum tipo;
	
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_alteracao_estrutura_log_id_alteracao_estrutura_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_alteracao_estrutura_log", unique = true, nullable = false)
	public int getIdAlteracaoEstruturaLog() {
		return idAlteracaoEstruturaLog;
	}
	
	public void setIdAlteracaoEstruturaLog(int idAlteracaoEstruturaLog) {
		this.idAlteracaoEstruturaLog = idAlteracaoEstruturaLog;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_grupo")
	public String getGrupo(){
		return grupo;
	}
	
	public void setGrupo(String grupo){
		this.grupo = grupo;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional")
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}
	
	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}
	
	@Column(name = "cv_justificativa")
	public String getJustificativa() {
		return justificativa;
	}
	
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	@Column(name = "tp_tipo")
	@Enumerated(EnumType.STRING)
	public TipoCrudEnum getTipo() {
		return tipo;
	}
	public void setTipo(TipoCrudEnum tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + idAlteracaoEstruturaLog;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		AlteracaoEstruturaLog other = (AlteracaoEstruturaLog) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (idAlteracaoEstruturaLog != other.idAlteracaoEstruturaLog)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
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
