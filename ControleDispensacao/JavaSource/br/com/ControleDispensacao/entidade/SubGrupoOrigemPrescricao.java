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
import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "subgrupo_origem")
public class SubGrupoOrigemPrescricao {
	private int idSubGrupoOrigemPrescricao;
	private GrupoOrigemPrescricao grupoOrigemPrescricao;
	private String descricao;
	private TipoSituacaoEnum status;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataAlteracao;
	private Usuario usuarioAlteracao;
	private TipoStatusEnum exibirCidade;
	
	@Id
	@GeneratedValue
	@Column(name = "id_subgrupo_origem")
	public int getIdSubGrupoOrigemPrescricao() {
		return this.idSubGrupoOrigemPrescricao;
	}
	
	public void setIdSubGrupoOrigemPrescricao(int idSubGrupoOrigemPrescricao){
		this.idSubGrupoOrigemPrescricao = idSubGrupoOrigemPrescricao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grupo_origem_id_grupo_origem")
	public GrupoOrigemPrescricao getGrupoOrigemPrescricao(){
		return grupoOrigemPrescricao;
	}
	
	public void setGrupoOrigemPrescricao(GrupoOrigemPrescricao grupoOrigemPrescricao){
		this.grupoOrigemPrescricao = grupoOrigemPrescricao;
	}
	
	@Column(name = "descricao", length = 120)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "status_2")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return this.status;
	}

	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	
	@Column(name = "st_exibir_cidade")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getExibirCidade() {
		return this.exibirCidade;
	}

	public void setExibirCidade(TipoStatusEnum exibirCidade) {
		this.exibirCidade = exibirCidade;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof SubGrupoOrigemPrescricao))
			return false;
		
		return ((SubGrupoOrigemPrescricao)obj).getIdSubGrupoOrigemPrescricao() == this.idSubGrupoOrigemPrescricao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
