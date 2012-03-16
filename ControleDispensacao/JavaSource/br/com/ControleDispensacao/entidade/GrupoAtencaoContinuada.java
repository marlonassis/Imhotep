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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;

@Entity
@Table(name = "tb_atencao_continuada")
public class GrupoAtencaoContinuada {
	private int idGrupoAtencaoContinuada;
	private String descricao;
	private TipoSituacaoEnum status;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataAlteracao;
	private Usuario usuarioAlteracao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_atencao_continuada_id_atencao_continuada_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_atencao_continuada", unique = true, nullable = false)
	public int getIdGrupoAtencaoContinuada() {
		return this.idGrupoAtencaoContinuada;
	}
	
	public void setIdGrupoAtencaoContinuada(int idGrupoAtencaoContinuada){
		this.idGrupoAtencaoContinuada = idGrupoAtencaoContinuada;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_incl")
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alt")
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
		if(!(obj instanceof GrupoAtencaoContinuada))
			return false;
		
		return ((GrupoAtencaoContinuada)obj).getIdGrupoAtencaoContinuada() == this.idGrupoAtencaoContinuada;
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
