package br.com.imhotep.entidade;

import java.io.Serializable;
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
import javax.persistence.Transient;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.CargoConsultaRaiz;
import br.com.imhotep.enums.TipoQualidadeDigitalEnum;
import br.com.imhotep.enums.TipoSexoEnum;
import br.com.imhotep.enums.TipoSituacaoEnum;
import br.com.imhotep.enums.TipoVinculoEnum;

@Entity
@Table(name = "tb_profissional")
public class Profissional implements Serializable{
	private static final long serialVersionUID = -8680432489992482031L;
	
	private int idProfissional;
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
	private String email;
	private String senhaDigital;
	private TipoSexoEnum sexo;
	private TipoQualidadeDigitalEnum qualidadeDigital;
	private TipoVinculoEnum vinculo;
	
	
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

	@Column(name = "cv_email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "tp_sexo")
	@Enumerated(EnumType.STRING)
	public TipoSexoEnum getSexo() {
		return this.sexo;
	}

	public void setSexo(TipoSexoEnum sexo) {
		this.sexo = sexo;
	}
	
	@Column(name = "tp_qualidade_digital")
	@Enumerated(EnumType.STRING)
	public TipoQualidadeDigitalEnum getQualidadeDigital() {
		return this.qualidadeDigital;
	}

	public void setQualidadeDigital(TipoQualidadeDigitalEnum qualidadeDigital) {
		this.qualidadeDigital = qualidadeDigital;
	}
	
	@Column(name = "tp_tipo_vinculo")
	@Enumerated(EnumType.STRING)
	public TipoVinculoEnum getVinculo() {
		return this.vinculo;
	}

	public void setVinculo(TipoVinculoEnum vinculo) {
		this.vinculo = vinculo;
	}
	
	@Column(name = "cv_senha_digital")
	public String getSenhaDigital() {
		return senhaDigital;
	}
	
	public void setSenhaDigital(String senhaDigital) {
		this.senhaDigital = senhaDigital;
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
		return new Utilitarios().nomeResumido(getNome());
	}
	
	@Column(name="cv_chave_verificacao")
	public String getChaveVerificacao() {
		return chaveVerificacao;
	}

	public void setChaveVerificacao(String chaveVerificacao) {
		this.chaveVerificacao = chaveVerificacao;
	}

	@Column(name="cv_cpf")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Transient
	public String getCpfFormatado(){
		if(getCpf() != null && !getCpf().isEmpty())
			return new Utilitarios().formatarValorMascara(getCpf(), "###.###.###-##");
		return "";
	}
	
	@Transient
	public String getNomeCpf(){
		return getNome().concat(" / ").concat(getCpfFormatado());
	}
	
	@Transient
	public String getNomeCpfCargo(){
		return new CargoConsultaRaiz().getNomeCpfCargo(getIdProfissional(), getNomeCpf());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((chaveVerificacao == null) ? 0 : chaveVerificacao.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result
				+ ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + idProfissional;
		result = prime * result
				+ ((matricula == null) ? 0 : matricula.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime
				* result
				+ ((qualidadeDigital == null) ? 0 : qualidadeDigital.hashCode());
		result = prime
				* result
				+ ((registroConselho == null) ? 0 : registroConselho.hashCode());
		result = prime * result
				+ ((senhaDigital == null) ? 0 : senhaDigital.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result
				+ ((usuarioInclusao == null) ? 0 : usuarioInclusao.hashCode());
		result = prime * result + ((vinculo == null) ? 0 : vinculo.hashCode());
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
		Profissional other = (Profissional) obj;
		if (chaveVerificacao == null) {
			if (other.chaveVerificacao != null)
				return false;
		} else if (!chaveVerificacao.equals(other.chaveVerificacao))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataInclusao == null) {
			if (other.dataInclusao != null)
				return false;
		} else if (!dataInclusao.equals(other.dataInclusao))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idProfissional != other.idProfissional)
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (qualidadeDigital != other.qualidadeDigital)
			return false;
		if (registroConselho == null) {
			if (other.registroConselho != null)
				return false;
		} else if (!registroConselho.equals(other.registroConselho))
			return false;
		if (senhaDigital == null) {
			if (other.senhaDigital != null)
				return false;
		} else if (!senhaDigital.equals(other.senhaDigital))
			return false;
		if (sexo != other.sexo)
			return false;
		if (situacao != other.situacao)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (usuarioInclusao == null) {
			if (other.usuarioInclusao != null)
				return false;
		} else if (!usuarioInclusao.equals(other.usuarioInclusao))
			return false;
		if (vinculo != other.vinculo)
			return false;
		return true;
	}
	
}
