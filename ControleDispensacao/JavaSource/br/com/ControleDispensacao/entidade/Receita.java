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
@Table(name = "receita")
public class Receita {
	private int idReceita;
	private Unidade unidade;
	private Cidade cidade;
	private Profissional profissional;
	private Paciente paciente;
	private SubGrupoOrigemPrescricao subGrupoOrigemPrescricao;
	private Integer ano;
	private Integer numero;
	private Date dataEmissao;
	private Usuario usuarioInclusao;
	private Date dataInclusao;
	private Usuario usuarioAlteracao;
	private String leito;
	private Double peso;
	private Date dataAlteracao;
	private TipoSituacaoEnum status;
	private Date dataUltimaDispensacao;
	private MotivoFimReceita motivoFimReceita;
	
	@Id
	@GeneratedValue
	@Column(name = "id_receita")
	public int getIdReceita() {
		return idReceita;
	}
	public void setIdReceita(int idReceita) {
		this.idReceita = idReceita;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unidade_id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cidade_id_cidade")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profissional_id_profissional")
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paciente_id_paciente")
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subgrupo_origem_id_subgrupo_origem")
	public SubGrupoOrigemPrescricao getSubGrupoOrigemPrescricao() {
		return subGrupoOrigemPrescricao;
	}
	public void setSubGrupoOrigemPrescricao(SubGrupoOrigemPrescricao subGrupoOrigemPrescricao) {
		this.subGrupoOrigemPrescricao = subGrupoOrigemPrescricao;
	}
	
	@Column(name = "ano")
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	@Column(name = "numero")
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_emissao")
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
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
	@Column(name = "data_incl")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_alt")
	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}
	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
	
	@Column(name = "leito")
	public String getLeito() {
		return leito;
	}
	public void setLeito(String leito) {
		this.leito = leito;
	}
	
	@Column(name = "peso")
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_alt")
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
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
	@Column(name = "data_ult_disp")
	public Date getDataUltimaDispensacao() {
		return dataUltimaDispensacao;
	}
	public void setDataUltimaDispensacao(Date dataUltimaDispensacao) {
		this.dataUltimaDispensacao = dataUltimaDispensacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "motivo_fim_receita_id_motivo_fim_receita")
	public MotivoFimReceita getMotivoFimReceita() {
		return motivoFimReceita;
	}
	public void setMotivoFimReceita(MotivoFimReceita motivoFimReceita) {
		this.motivoFimReceita = motivoFimReceita;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Receita))
			return false;
		
		return ((Receita)obj).getIdReceita() == this.idReceita;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + unidade.getNome().hashCode();
	}

	@Override
	public String toString() {
		return "Receita";
	}
}
