package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_livro")
public class Livro {
	private int idLivro;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_livro_id_livro_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_livro", unique = true, nullable = false)
	public int getIdLivro() {
		return this.idLivro;
	}
	
	public void setIdLivro(int idLivro){
		this.idLivro = idLivro;
	}
	
	@Column(name = "ds_descricao", length = 40)
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
		if(!(obj instanceof Livro))
			return false;
		
		return ((Livro)obj).getIdLivro() == this.idLivro;
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
