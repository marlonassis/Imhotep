package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoSituacaoEnum;

@Entity
@Table(name = "tb_profissional")
public class Profissional implements Serializable{
	private static final long serialVersionUID = 2823381085636103344L;
	
	private int idProfissional;
	private Estado estado; 
	private String nome;
	private TipoSituacaoEnum situacao;
	private Usuario usuarioInclusao;
	private Date dataInclusao;
	private Usuario usuario;
	private String registroConselho;
	private Integer matricula;
	private Date dataNascimento;
	private String chaveVerificacao;
	private String cpf;
	private Set<Especialidade> especialidades;
	
	
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
	
	@Transient
	public String getNomeResumido(){
		if(nome != null){
			String[] n = nome.split(" ");
			String nomeResumido = n[0];
			nomeResumido = nomeResumido.concat(" ").concat(n[n.length-1]);
			return nomeResumido;
		}
		return null;
	}
	
	@Column(name="cv_chave_verificacao")
	public String getChaveVerificacao() {
		return chaveVerificacao;
	}

	public void setChaveVerificacao(String chaveVerificacao) {
		this.chaveVerificacao = chaveVerificacao;
	}

	@Transient
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

//	@Transient
//	public Set<ProfissionalEspecialidade> getEspecialidades() {
//		if(especialidades == null || especialidades.isEmpty()) {
//			especialidades = new HashSet<ProfissionalEspecialidade>(new GerenciadorConsultaLazy<ProfissionalEspecialidade>().consultarLista(this, new ProfissionalEspecialidade(), new HashSet<ProfissionalEspecialidade>()));
//		}
//		return especialidades;
//	}
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinTable(name="tb_profissional_especialidade",
				joinColumns={@JoinColumn(name="id_profissional")}, 
				inverseJoinColumns={@JoinColumn(name="id_especialidade")})
	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	@Transient
	public List<Especialidade> getEspecialidadesList(){
		if(getEspecialidades() != null){
			return new ArrayList<Especialidade>(getEspecialidades());
		}
		return new ArrayList<Especialidade>();
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
	    return hash * 31 + ((nome == null) ? 0 : nome.hashCode()) + usuario.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
