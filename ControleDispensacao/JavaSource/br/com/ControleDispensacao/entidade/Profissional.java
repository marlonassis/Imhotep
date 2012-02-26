package br.com.ControleDispensacao.entidade;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;

@Entity
@Table(name = "tb_profissional")
public class Profissional {
	private int idProfissional;
	private Estado estado; 
	private Especialidade especialidade;
	private String nome;
	private TipoSituacaoEnum status;
	private Long inscricao;
	private Usuario usuarioInclusao;
	private Date dataInclusao;
	private Usuario usuario;
	
	
	@Id
	@GeneratedValue
	@Column(name = "id_profissional")
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
	
	@Column(name = "ds_nome", length = 60)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "tp_status")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return this.status;
	}

	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	
	@Column(name = "bg_inscricao")
	public Long getInscricao() {
		return inscricao;
	}
	
	public void setInscricao(Long inscricao) {
		this.inscricao = inscricao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_data_inclusao", length = 13)
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
	    return hash * 31 + nome.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
