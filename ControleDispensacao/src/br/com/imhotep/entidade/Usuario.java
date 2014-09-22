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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = -1554433111839681441L;
	
	private int idUsuario;
	private String login;
	private String senha;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Profissional profissionalInclusao;
	private boolean expiraSessao;
	private Profissional profissional;
	private boolean baseTeste;
	private int quantidadeErroLogin;
	
	public Usuario() {
	}

	public Usuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_usuario_id_usuario_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_usuario", unique = true, nullable = false)
	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "cv_login")
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "cv_senha")
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_inclusao")
	public Usuario getUsuarioInclusao() {
		return this.usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return this.profissionalInclusao;
	}

	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@Column(name = "bl_expira_sessao")
	public boolean getExpiraSessao() {
		return expiraSessao;
	}
	public void setExpiraSessao(boolean expiraSessao) {
		this.expiraSessao = expiraSessao;
	}
	
	@Column(name = "bl_base_teste")
	public boolean getBaseTeste() {
		return baseTeste;
	}
	public void setBaseTeste(boolean baseTeste) {
		this.baseTeste = baseTeste;
	}
	
	@OneToOne(mappedBy="usuario")  
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@Column(name="in_quantidade_erro_login")
	public int getQuantidadeErroLogin(){
		return quantidadeErroLogin;
	}
	
	public void setQuantidadeErroLogin(int quantidadeErroLogin){
		this.quantidadeErroLogin = quantidadeErroLogin;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (baseTeste ? 1231 : 1237);
		result = prime * result
				+ ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result + (expiraSessao ? 1231 : 1237);
		result = prime * result + idUsuario;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Usuario other = (Usuario) obj;
		if (baseTeste != other.baseTeste)
			return false;
		if (dataInclusao == null) {
			if (other.dataInclusao != null)
				return false;
		} else if (!dataInclusao.equals(other.dataInclusao))
			return false;
		if (expiraSessao != other.expiraSessao)
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return login;
	}

}