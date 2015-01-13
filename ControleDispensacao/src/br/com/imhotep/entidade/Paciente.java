package br.com.imhotep.entidade;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.enums.TipoSanguineoEnum;
import br.com.imhotep.enums.TipoSexoEnum;

@Entity
@Table(name = "tb_paciente")
public class Paciente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3432678836361624292L;
	private int idPaciente;
	private Unidade unidadeCadastro;
	private Cidade cidadeNaturalidade;
	private String nome;
	private String nomeMae;
	private String nomePai;
	private TipoSexoEnum sexo;
	private Date dataNascimento;
	private String cpf;
	private Date dataInclusao;
	private Profissional profissionalInclusao;
	private String numeroSus;
	private String prontuario;
	private TipoSanguineoEnum tipoSanguineo;
	private String registroGeral;
	private String orgaoRegistroGeral;
	private String nacionalidade;
	private String naturalidade;
	private String cor;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_paciente_id_paciente_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_paciente", unique = true, nullable = false)
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_cadastro")
	public Unidade getUnidadeCadastro() {
		return unidadeCadastro;
	}
	public void setUnidadeCadastro(Unidade unidadeCadastro) {
		this.unidadeCadastro = unidadeCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade_naturalidade")
	public Cidade getCidadeNaturalidade() {
		return cidadeNaturalidade;
	}
	public void setCidadeNaturalidade(Cidade cidade) {
		this.cidadeNaturalidade = cidade;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_nome_mae")
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	@Column(name = "cv_nome_pai")
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	
	@Column(name = "tp_sexo")
	@Enumerated(EnumType.STRING)
	public TipoSexoEnum getSexo() {
		return sexo;
	}
	public void setSexo(TipoSexoEnum sexo) {
		this.sexo = sexo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_nascimento")
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Column(name = "cv_cpf")
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return profissionalInclusao;
	}
	
	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@Column(name = "cv_numero_sus")
	public String getNumeroSus() {
		return numeroSus;
	}
	public void setNumeroSus(String numeroSus) {
		this.numeroSus = numeroSus;
	}
	
	@Column(name = "cv_prontuario")
	public String getProntuario() {
		return prontuario;
	}
	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
	
	@Column(name = "tp_sangue")
	@Enumerated(EnumType.STRING)
	public TipoSanguineoEnum getTipoSanguineo() {
		return tipoSanguineo;
	}
	public void setTipoSanguineo(TipoSanguineoEnum tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}
	
	@Column(name = "cv_registro_geral")
	public String getRegistroGeral() {
		return registroGeral;
	}
	public void setRegistroGeral(String registroGeral) {
		this.registroGeral = registroGeral;
	}
	
	
	@Column(name = "cv_orgao_registro_geral")
	public String getOrgaoRegistroGeral() {
		return orgaoRegistroGeral;
	}
	public void setOrgaoRegistroGeral(String orgaoRegistroGeral) {
		this.orgaoRegistroGeral = orgaoRegistroGeral;
	}
	
	@Column(name = "cv_nacionalidade")
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	@Column(name = "cv_cor")
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	@Column(name = "cv_naturalidade")
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	
	@Transient
	public String getNomeIdade(){
		return nome.concat(" - ").concat(getIdade());
	}
	
	@Transient
	public String getIdade(){
		return Utilitarios.idadeAtual(dataNascimento);
	}
	
	@Transient
	public String getNomeSus(){
		return nome.concat(" - ").concat(getNumeroSus() != null ? getNumeroSus() : "S/N");
	}
	
	@Transient
	public String getRg(){
		if(registroGeral != null)
			return registroGeral.concat(" / ").concat(orgaoRegistroGeral == null ? "?" : orgaoRegistroGeral);
		return null;
	}
	
	@Transient
	public String getCpfFormatado(){
		return new br.com.imhotep.auxiliar.Utilitarios().formatarValorMascara(cpf, "###.###.###-##");
	}
	
	@Transient
	public String getNomeProntuarioCPF(){
		return getNome().concat(" - ").concat(getProntuario()).concat(" - ").concat(getCpfFormatado());
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cidadeNaturalidade == null) ? 0 : cidadeNaturalidade
						.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result
				+ ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + idPaciente;
		result = prime * result
				+ ((nacionalidade == null) ? 0 : nacionalidade.hashCode());
		result = prime * result
				+ ((naturalidade == null) ? 0 : naturalidade.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeMae == null) ? 0 : nomeMae.hashCode());
		result = prime * result + ((nomePai == null) ? 0 : nomePai.hashCode());
		result = prime * result
				+ ((numeroSus == null) ? 0 : numeroSus.hashCode());
		result = prime
				* result
				+ ((orgaoRegistroGeral == null) ? 0 : orgaoRegistroGeral
						.hashCode());
		result = prime
				* result
				+ ((profissionalInclusao == null) ? 0 : profissionalInclusao
						.hashCode());
		result = prime * result
				+ ((prontuario == null) ? 0 : prontuario.hashCode());
		result = prime * result
				+ ((registroGeral == null) ? 0 : registroGeral.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result
				+ ((tipoSanguineo == null) ? 0 : tipoSanguineo.hashCode());
		result = prime * result
				+ ((unidadeCadastro == null) ? 0 : unidadeCadastro.hashCode());
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
		Paciente other = (Paciente) obj;
		if (cidadeNaturalidade == null) {
			if (other.cidadeNaturalidade != null)
				return false;
		} else if (!cidadeNaturalidade.equals(other.cidadeNaturalidade))
			return false;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
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
		if (idPaciente != other.idPaciente)
			return false;
		if (nacionalidade == null) {
			if (other.nacionalidade != null)
				return false;
		} else if (!nacionalidade.equals(other.nacionalidade))
			return false;
		if (naturalidade == null) {
			if (other.naturalidade != null)
				return false;
		} else if (!naturalidade.equals(other.naturalidade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeMae == null) {
			if (other.nomeMae != null)
				return false;
		} else if (!nomeMae.equals(other.nomeMae))
			return false;
		if (nomePai == null) {
			if (other.nomePai != null)
				return false;
		} else if (!nomePai.equals(other.nomePai))
			return false;
		if (numeroSus == null) {
			if (other.numeroSus != null)
				return false;
		} else if (!numeroSus.equals(other.numeroSus))
			return false;
		if (orgaoRegistroGeral == null) {
			if (other.orgaoRegistroGeral != null)
				return false;
		} else if (!orgaoRegistroGeral.equals(other.orgaoRegistroGeral))
			return false;
		if (profissionalInclusao == null) {
			if (other.profissionalInclusao != null)
				return false;
		} else if (!profissionalInclusao.equals(other.profissionalInclusao))
			return false;
		if (prontuario == null) {
			if (other.prontuario != null)
				return false;
		} else if (!prontuario.equals(other.prontuario))
			return false;
		if (registroGeral == null) {
			if (other.registroGeral != null)
				return false;
		} else if (!registroGeral.equals(other.registroGeral))
			return false;
		if (sexo != other.sexo)
			return false;
		if (tipoSanguineo != other.tipoSanguineo)
			return false;
		if (unidadeCadastro == null) {
			if (other.unidadeCadastro != null)
				return false;
		} else if (!unidadeCadastro.equals(other.unidadeCadastro))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nome;
	}

}
