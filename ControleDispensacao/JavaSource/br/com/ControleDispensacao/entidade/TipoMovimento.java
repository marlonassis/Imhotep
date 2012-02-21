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

import br.com.ControleDispensacao.enums.TipoOperacaoEnum;
import br.com.ControleDispensacao.enums.TipoSituacaoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "tipo_movto")
public class TipoMovimento {
	private int idTipoMovimento;
	private String descricao;
	private TipoOperacaoEnum tipoOperacao;
	private TipoStatusEnum movimentoAdminstrativo;
	private TipoStatusEnum movimentoBloqueado;
	private TipoStatusEnum movimentoVencido;
	private TipoSituacaoEnum status;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataAlteracao;
	private Usuario usuarioAlteracao;
	
	@Id
	@GeneratedValue
	@Column(name = "id_tipo_movto")
	public int getIdTipoMovimento() {
		return idTipoMovimento;
	}
	public void setIdTipoMovimento(int idTipoMovimento) {
		this.idTipoMovimento = idTipoMovimento;
	}
	
	@Column(name = "descricao", length = 40)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "operacao")
	@Enumerated(EnumType.STRING)
	public TipoOperacaoEnum getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(TipoOperacaoEnum tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	
	@Column(name = "flg_movto")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getMovimentoAdminstrativo() {
		return movimentoAdminstrativo;
	}
	public void setMovimentoAdminstrativo(TipoStatusEnum movimentoAdminstrativo) {
		this.movimentoAdminstrativo = movimentoAdminstrativo;
	}
	
	@Column(name = "flg_movto_bloqueado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getMovimentoBloqueado() {
		return movimentoBloqueado;
	}
	public void setMovimentoBloqueado(TipoStatusEnum movimentoBloqueado) {
		this.movimentoBloqueado = movimentoBloqueado;
	}
	
	@Column(name = "flg_movto_vencido")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getMovimentoVencido() {
		return movimentoVencido;
	}
	public void setMovimentoVencido(TipoStatusEnum movimentoVencido) {
		this.movimentoVencido = movimentoVencido;
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
		if(!(obj instanceof TipoMovimento))
			return false;
		
		return ((TipoMovimento)obj).getIdTipoMovimento() == this.idTipoMovimento;
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
