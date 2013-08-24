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
	private Integer quantidadeErroLogin;
	
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
	public Integer getQuantidadeErroLogin(){
		return quantidadeErroLogin;
	}
	
	public void setQuantidadeErroLogin(Integer quantidadeErroLogin){
		this.quantidadeErroLogin = quantidadeErroLogin;
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