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
@Table(name = "tb_tipo_movimento")
public class TipoMovimento {
	private int idTipoMovimento;
	private String descricao;
	private TipoOperacaoEnum tipoOperacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_tipo_movimento_id_tipo_movimento_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_tipo_movimento", unique = true, nullable = false)
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
