package br.com.imhotep.mensageiro.entidade;

import java.util.Date;

import br.com.imhotep.enums.TipoSituacaoEnum;

public class MensagemDestinatario {
	
	private int idMensagemDestinatario;
	private Usuario usuarioDestinatario;
	private Date dataEnvio;
	private TipoSituacaoEnum status;
	private Mensagem mensagem;
	
	
	public int getIdMensagemDestinatario() {
		return idMensagemDestinatario;
	}
	public void setIdMensagemDestinatario(int idMensagemDestinatario) {
		this.idMensagemDestinatario = idMensagemDestinatario;
	}
	public Usuario getUsuarioDestinatario() {
		return usuarioDestinatario;
	}
	public void setUsuarioDestinatario(Usuario usuarioDestinatario) {
		this.usuarioDestinatario = usuarioDestinatario;
	}
	public Date getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public TipoSituacaoEnum getStatus() {
		return status;
	}
	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	public Mensagem getMensagem() {
		return mensagem;
	}
	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
	
}
