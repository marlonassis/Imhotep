package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade_solicitacao")
public class UnidadeSolicitacao implements Serializable {
	private static final long serialVersionUID = -6296123297957539785L;
	
	private int idUnidadeSolicitacao;
	private Unidade unidade;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_unidade_solicitacao_id_unidade_solicitacao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_unidade_solicitacao", unique = true, nullable = false)
	public int getIdUnidadeSolicitacao() {
		return this.idUnidadeSolicitacao;
	}
	
	public void setIdUnidadeSolicitacao(int idUnidadeSolicitacao){
		this.idUnidadeSolicitacao = idUnidadeSolicitacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade(){
		return unidade;
	}
	
	public void setUnidade(Unidade unidade){
		this.unidade = unidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idUnidadeSolicitacao;
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
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
		UnidadeSolicitacao other = (UnidadeSolicitacao) obj;
		if (idUnidadeSolicitacao != other.idUnidadeSolicitacao)
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		return true;
	}
	
}
