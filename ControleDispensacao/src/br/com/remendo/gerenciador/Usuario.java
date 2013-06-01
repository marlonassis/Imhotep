package br.com.remendo.gerenciador;

import java.util.Date;

public class Usuario {
	
	private int idUsuario;
	private String login;
	private String senha;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	
	public Usuario() {
	}

	public Usuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Usuario getUsuarioInclusao() {
		return this.usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Usuario))
			return false;
		
		return ((Usuario)obj).getIdUsuario() == this.idUsuario;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + login.hashCode();
	}

	@Override
	public String toString() {
		return login;
	}

}