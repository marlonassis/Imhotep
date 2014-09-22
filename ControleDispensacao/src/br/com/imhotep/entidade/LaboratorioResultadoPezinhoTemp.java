package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_laboratorio_resultado_pezinho_temp")
public class LaboratorioResultadoPezinhoTemp implements Serializable {
	
	private static final long serialVersionUID = -2315381195994341700L;
	
	private int idLaboratorioResultadoPezinhoTemp;
	private Double resultadoExame;
	private Integer exameIdentificacao;
	private Integer prontuario;
	private byte[] paginaWebByte;
	private Profissional profissionalCadastro;
	private Date dataCadastro;

	private Date dataResultado;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_laboratorio_resultado_pezi_id_laboratorio_resultado_pezi_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_resultado_pezinho_temp", unique = true, nullable = false)
	public int getIdLaboratorioResultadoPezinhoTemp() {
		return idLaboratorioResultadoPezinhoTemp;
	}
	
	public void setIdLaboratorioResultadoPezinhoTemp(int idLaboratorioResultadoPezinhoTemp) {
		this.idLaboratorioResultadoPezinhoTemp = idLaboratorioResultadoPezinhoTemp;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_resultado")
	public Date getDataResultado() {
		return dataResultado;
	}
	public void setDataResultado(Date dataResultado) {
		this.dataResultado = dataResultado;
	}
	
	@Column(name = "db_resultado_exame")
	public Double getResultadoExame() {
		return resultadoExame;
	}
	
	public void setResultadoExame(Double resultadoExame) {
		this.resultadoExame = resultadoExame;
	}
	
	@Column(name = "in_exame_identificacao")
	public Integer getExameIdentificacao() {
		return exameIdentificacao;
	}
	
	public void setExameIdentificacao(Integer exameIdentificacao) {
		this.exameIdentificacao = exameIdentificacao;
	}
	
	@Column(name = "in_prontuario")
	public Integer getProntuario() {
		return prontuario;
	}
	
	public void setProntuario(Integer prontuario) {
		this.prontuario = prontuario;
	}
	
	@Lob
	@Basic( optional=false )
	@Column(name = "by_pagina_web")
	public byte[] getPaginaWebByte() {
		return paginaWebByte;
	}
	public void setPaginaWebByte(byte[] paginaWebByte) {
		this.paginaWebByte = paginaWebByte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((exameIdentificacao == null) ? 0 : exameIdentificacao
						.hashCode());
		result = prime * result + idLaboratorioResultadoPezinhoTemp;
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
		LaboratorioResultadoPezinhoTemp other = (LaboratorioResultadoPezinhoTemp) obj;
		if (exameIdentificacao == null) {
			if (other.exameIdentificacao != null)
				return false;
		} else if (!exameIdentificacao.equals(other.exameIdentificacao))
			return false;
		if (idLaboratorioResultadoPezinhoTemp != other.idLaboratorioResultadoPezinhoTemp)
			return false;
		return true;
	}
	
}
