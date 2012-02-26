package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ControleDispensacao.enums.TipoOperacaoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_tipo_movimento")
public class TipoMovimento {
	private int idTipoMovimento;
	private String descricao;
	private TipoOperacaoEnum tipoOperacao;
	private TipoStatusEnum movimentoAdminstrativo;
	private TipoStatusEnum movimentoBloqueado;
	private TipoStatusEnum movimentoVencido;
	
	@Id
	@GeneratedValue
	@Column(name = "id_tipo_movimento")
	public int getIdTipoMovimento() {
		return idTipoMovimento;
	}
	public void setIdTipoMovimento(int idTipoMovimento) {
		this.idTipoMovimento = idTipoMovimento;
	}
	
	@Column(name = "ds_descricao", length = 40)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "tp_operacao")
	@Enumerated(EnumType.STRING)
	public TipoOperacaoEnum getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(TipoOperacaoEnum tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	
	@Column(name = "tp_movimento_administrativo")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getMovimentoAdminstrativo() {
		return movimentoAdminstrativo;
	}
	public void setMovimentoAdminstrativo(TipoStatusEnum movimentoAdminstrativo) {
		this.movimentoAdminstrativo = movimentoAdminstrativo;
	}
	
	@Column(name = "tp_movimento_bloqueado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getMovimentoBloqueado() {
		return movimentoBloqueado;
	}
	public void setMovimentoBloqueado(TipoStatusEnum movimentoBloqueado) {
		this.movimentoBloqueado = movimentoBloqueado;
	}
	
	@Column(name = "tp_movimento_vencido")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getMovimentoVencido() {
		return movimentoVencido;
	}
	public void setMovimentoVencido(TipoStatusEnum movimentoVencido) {
		this.movimentoVencido = movimentoVencido;
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
