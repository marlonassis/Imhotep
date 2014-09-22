package br.com.imhotep.mensageiro.entidade;

import java.util.Date;

import br.com.imhotep.enums.TipoSituacaoEnum;

public class Mensagem {
	
	private int idMensagem;
	private String assunto;
	private String conteudo;
	private Date dataEnvio;
	private Date dataCriacao;
	private Usuario usuario;
	private TipoSituacaoEnum status;
	private Mensagem mensagemPai;
	private boolean mensagemLida;
	
	
	public int getIdMensagem() {
		return idMensagem;
	}
	public void setIdMensagem(int idMensagem) {
		this.idMensagem = idMensagem;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Date getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public TipoSituacaoEnum getStatus() {
		return status;
	}
	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	public Mensagem getMensagemPai() {
		return mensagemPai;
	}
	public void setMensagemPai(Mensagem mensagemPai) {
		this.mensagemPai = mensagemPai;
	}
	public boolean isMensagemLida() {
		return mensagemLida;
	}
	public void setMensagemLida(boolean mensagemLida) {
		this.mensagemLida = mensagemLida;
	}
	
	
}
