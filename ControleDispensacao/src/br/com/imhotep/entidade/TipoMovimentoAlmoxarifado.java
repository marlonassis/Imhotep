package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.imhotep.enums.TipoOperacaoEnum;

@Entity
@Table(name = "tb_tipo_movimento_almoxarifado")
public class TipoMovimentoAlmoxarifado {
	private int idTipoMovimentoAlmoxarifado;
	private String descricao;
	private TipoOperacaoEnum tipoOperacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_tipo_movimento_almoxarifad_id_tipo_movimento_almoxarifad_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_tipo_movimento_almoxarifado", unique = true, nullable = false)
	public int getIdTipoMovimentoAlmoxarifado() {
		return idTipoMovimentoAlmoxarifado;
	}
	public void setIdTipoMovimentoAlmoxarifado(int idTipoMovimentoAlmoxarifado) {
		this.idTipoMovimentoAlmoxarifado = idTipoMovimentoAlmoxarifado;
	}
	
	@Column(name = "cv_descricao")
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof TipoMovimentoAlmoxarifado))
			return false;
		
		return ((TipoMovimentoAlmoxarifado)obj).getIdTipoMovimentoAlmoxarifado() == this.idTipoMovimentoAlmoxarifado;
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
