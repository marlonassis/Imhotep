package br.com.imhotep.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
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

import br.com.imhotep.enums.TipoSituacaoEnum;

@Entity
@Table(name = "tb_profissional")
public class Profissional {
	private int idProfissional;
	private Estado estado; 
	private Especialidade especialidade;
	private String nome;
	private TipoSituacaoEnum situacao;
	private Usuario usuarioInclusao;
	private Date dataInclusao;
	private Usuario usuario;
	private String registroConselho;
	private Integer matricula;
	private Date dataNascimento;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_profissional_id_profissional_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_profissional", unique = true, nullable = false)
	public int getIdProfissional() {
		return idProfissional;
	}
	
	public void setIdProfissional(int idProfissional) {
		this.idProfissional = idProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estado")
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_registro_conselho")
	public String getRegistroConselho() {
		return registroConselho;
	}
	
	public void setRegistroConselho(String registroConselho) {
		this.registroConselho = registroConselho;
	}
	
	@Column(name = "tp_status")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getSituacao() {
		return this.situacao;
	}

	public void setSituacao(TipoSituacaoEnum situacao) {
		this.situacao = situacao;
	}

	@Column(name = "in_matricula")
	public Integer getMatricula() {
		return matricula;
	}
	
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
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

	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_nascimento")
	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Profissional))
			return false;
		
		return ((Profissional)obj).getIdProfissional() == this.idProfissional;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + especialidade.hashCode() + nome.hashCode() + usuario.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
