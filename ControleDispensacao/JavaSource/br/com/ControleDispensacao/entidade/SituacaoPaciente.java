package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_situacao_paciente")
public class SituacaoPaciente {
	private int idSituacaoPaciente;
	private String descricao;
	
	@Id
	@GeneratedValue
	@Column(name = "id_status_paciente")
	public int getIdSituacaoPaciente() {
		return this.idSituacaoPaciente;
	}
	
	public void setIdSituacaoPaciente(int idSituacaoPaciente){
		this.idSituacaoPaciente = idSituacaoPaciente;
	}

	@Column(name = "ds_descricao", length = 120)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof SituacaoPaciente))
			return false;
		
		return ((SituacaoPaciente)obj).getIdSituacaoPaciente() == this.idSituacaoPaciente;
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
