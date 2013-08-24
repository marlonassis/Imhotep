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
@Table(name = "tb_ajuste_estoque")
public class AjusteEstoque  implements Serializable{
	private static final long serialVersionUID = 8038451118277498809L;
	
	private int idAjusteEstoque;
	private MovimentoLivro movimentoLivro;
	private String justificativa;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ajuste_estoque_id_ajuste_estoque_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ajuste_estoque", unique = true, nullable = false)
	public int getIdAjusteEstoque() {
		return this.idAjusteEstoque;
	}
	
	public void setIdAjusteEstoque(int idAjusteEstoque){
		this.idAjusteEstoque = idAjusteEstoque;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")
	public MovimentoLivro getMovimentoLivro(){
		return movimentoLivro;
	}
	
	public void setMovimentoLivro(MovimentoLivro movimentoLivro){
		this.movimentoLivro = movimentoLivro;
	}
	
	@Column(name = "cv_justificativa")
	public String getJustificativa(){
		return justificativa;
	}
	
	public void setJustificativa(String justificativa){
		this.justificativa = justificativa;
	}
		
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AjusteEstoque))
			return false;
		
		return ((AjusteEstoque)obj).getIdAjusteEstoque() == this.idAjusteEstoque;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + movimentoLivro.hashCode();
	}

	@Override
	public String toString() {
		return justificativa;
	}
	
}
