package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_aplicacao")
public class Aplicacao {
	private int idAplicacao;
	private String executavel;
	private String descricao;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_aplicacao_id_aplicacao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_aplicacao", unique = true, nullable = false)
	public int getIdAplicacao() {
		return this.idAplicacao;
	}
	
	public void setIdAplicacao(int idAplicacao){
		this.idAplicacao = idAplicacao;
	}
	
	@Column(name = "ds_executavel", length = 120)
	public String getExecutavel() {
		return this.executavel;
	}

	public void setExecutavel(String executavel) {
		this.executavel = executavel;
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
		if(!(obj instanceof Aplicacao))
			return false;
		
		return ((Aplicacao)obj).getIdAplicacao() == this.idAplicacao;
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
