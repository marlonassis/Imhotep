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

import br.com.ControleDispensacao.enums.TipoLogradouroEnum;
import br.com.ControleDispensacao.enums.TipoSexoEnum;
import br.com.ControleDispensacao.enums.TipoSituacaoEnum;

@Entity
@Table(name = "paciente")
public class Paciente {
	private int idPaciente;
	private SituacaoPaciente situacaoPaciente;
	private Unidade unidadeCadastro;
	private Unidade unidadeReferida;
	private Cidade cidade;
	private String nome;
	private TipoLogradouroEnum tipoLogradouro;
	private String nomeLogradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String nomeMae;
	private TipoSexoEnum sexo;
	private Date dataNascimento;
	private TipoSituacaoEnum status;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataAlteracao;
	private Usuario usuarioAlteracao;
	private String telefone;
	private String cpf;
	
	@Id
	@GeneratedValue
	@Column(name = "id_paciente")
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_status_paciente")
	public SituacaoPaciente getSituacaoPaciente() {
		return situacaoPaciente;
	}
	public void setSituacaoPaciente(SituacaoPaciente situacaoPaciente) {
		this.situacaoPaciente = situacaoPaciente;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unidade_cadastro")
	public Unidade getUnidadeCadastro() {
		return unidadeCadastro;
	}
	public void setUnidadeCadastro(Unidade unidadeCadastro) {
		this.unidadeCadastro = unidadeCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unidade_referida")
	public Unidade getUnidadeReferida() {
		return unidadeReferida;
	}
	public void setUnidadeReferida(Unidade unidadeReferida) {
		this.unidadeReferida = unidadeReferida;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cidade_id_cidade")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Column(name = "nome", length = 70)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "tipo_logradouro")
	@Enumerated(EnumType.STRING)
	public TipoLogradouroEnum getTipoLogradouro() {
		return tipoLogradouro;
	}
	public void setTipoLogradouro(TipoLogradouroEnum tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}
	
	@Column(name = "nome_logradouro", length = 50)
	public String getNomeLogradouro() {
		return nomeLogradouro;
	}
	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}
	
	@Column(name = "numero", length = 7)
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name = "complemento", length = 15)
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@Column(name = "bairro", length = 30)
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name = "nome_mae", length = 70)
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	@Column(name = "sexo")
	@Enumerated(EnumType.STRING)
	public TipoSexoEnum getSexo() {
		return sexo;
	}
	public void setSexo(TipoSexoEnum sexo) {
		this.sexo = sexo;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nasc")
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Column(name = "status_2")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return status;
	}
	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_incl")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_incl")
	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}
	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_alt")
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_alt")
	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}
	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
	
	@Column(name = "telefone", length = 14)
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Column(name = "cpf", length = 14)
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Paciente))
			return false;
		
		return ((Paciente)obj).getIdPaciente() == this.idPaciente;
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
