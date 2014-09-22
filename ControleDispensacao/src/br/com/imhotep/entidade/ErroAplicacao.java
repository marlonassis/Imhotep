package br.com.imhotep.entidade;

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

import br.com.imhotep.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_erro_aplicacao")
public class ErroAplicacao {
	
	private int idErroAplicacao;
	private String message;
	private String stackTrace;
	private String metodo;
	private String pagina;
	private Usuario usuario;
	private Date dataOcorrencia;
	private TipoStatusEnum atendido;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_erro_aplicacao_id_erro_aplicacao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_erro_aplicacao", unique = true, nullable = false)
	public int getIdErroAplicacao() {
		return idErroAplicacao;
	}
	public void setIdErroAplicacao(int idErroAplicacao) {
		this.idErroAplicacao = idErroAplicacao;
	}
	
	@Column(name = "ds_message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name = "ds_stack_trace")
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	@Column(name = "ds_metodo")
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	
	@Column(name = "ds_pagina")
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_ocorrencia")
	public Date getDataOcorrencia() {
		return dataOcorrencia;
	}
	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}
	
	@Column(name = "tp_atendido")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getAtendido() {
		return atendido;
	}
	public void setAtendido(TipoStatusEnum atendido) {
		this.atendido = atendido;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ErroAplicacao))
			return false;
		
		return ((ErroAplicacao)obj).getIdErroAplicacao() == this.idErroAplicacao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + metodo.hashCode() + dataOcorrencia.hashCode() + pagina.hashCode();
	}

	@Override
	public String toString() {
		return "MŽtodo: " + metodo + " - P‡gina: "+pagina;
	}
}
