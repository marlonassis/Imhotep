package br.com.imhotep.entidade;

import java.io.Serializable;

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
public class TipoMovimento implements Serializable {
	private static final long serialVersionUID = -7019219864542935870L;
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idTipoMovimento;
		result = prime * result
				+ ((tipoOperacao == null) ? 0 : tipoOperacao.hashCode());
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
		TipoMovimento other = (TipoMovimento) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idTipoMovimento != other.idTipoMovimento)
			return false;
		if (tipoOperacao != other.tipoOperacao)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
	

}
