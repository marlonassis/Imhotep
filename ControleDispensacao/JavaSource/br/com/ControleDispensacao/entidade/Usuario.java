package br.com.ControleDispensacao.entidade;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	private static final long serialVersionUID = 1L;
	private int idUsuario;
	private String matricula;
	private String login;
	private String senha;
	private TipoSituacaoEnum situacao;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataAlteracao;
	private Usuario usuarioAlteracao;
//	private Set<Papel> papeis = new HashSet<Papel>(0);
//	private Set<Titulo> titulos = new HashSet<Titulo>(0);

	public Usuario() {
	}

	public Usuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	@Id
	@GeneratedValue
	@Column(name = "id_usuario")
	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "matricula", length = 10)
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Column(name = "login", length = 200)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "senha", length = 200)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	@Column(name = "situacao")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getSituacao() {
		return this.situacao;
	}

	public void setSituacao(TipoSituacaoEnum situacao) {
		this.situacao = situacao;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_incl", length = 13)
	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_incl")
	public Usuario getUsuarioInclusao() {
		return this.usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_alt", length = 13)
	public Date getDataAlteracao() {
		return this.dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_alt")
	public Usuario getUsuarioAlteracao() {
		return this.usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
//	public Set<Papel> getPapeis() {
//		return this.papeis;
//	}
//
//	public void setPapeis(Set<Papel> papeis) {
//		this.papeis = papeis;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
//	public Set<Titulo> getTitulos() {
//		return this.titulos;
//	}
//
//	public void setTitulos(Set<Titulo> titulos) {
//		this.titulos = titulos;
//	}

	
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