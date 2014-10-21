package br.com.imhotep.mensageiro.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.mensageiro.entidade.Usuario;

@ManagedBean
@SessionScoped
public class MensageiroRaiz {
	private List<Usuario> destinatarios = new ArrayList<Usuario>();
	private List<Usuario> cc = new ArrayList<Usuario>();
	private String assunto;
	private String mensagem;
	
	
	
	public List<Usuario> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<Usuario> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public List<Usuario> getCc() {
		return cc;
	}

	public void setCc(List<Usuario> cc) {
		this.cc = cc;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
