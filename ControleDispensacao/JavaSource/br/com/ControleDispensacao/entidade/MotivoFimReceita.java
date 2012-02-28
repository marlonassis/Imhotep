package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_motivo_fim_receita")
public class MotivoFimReceita {
	private int idMotivoFimReceita;
	private String descricao;

	@SequenceGenerator(name = "generator", sequenceName = "public.tb_motivo_fim_receita_id_motivo_fim_receita_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_motivo_fim_receita", unique = true, nullable = false)
	public int getIdMotivoFimReceita() {
		return this.idMotivoFimReceita;
	}
	
	public void setIdMotivoFimReceita(int idMotivoFimReceita){
		this.idMotivoFimReceita = idMotivoFimReceita;
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
		if(!(obj instanceof MotivoFimReceita))
			return false;
		
		return ((MotivoFimReceita)obj).getIdMotivoFimReceita() == this.idMotivoFimReceita;
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
