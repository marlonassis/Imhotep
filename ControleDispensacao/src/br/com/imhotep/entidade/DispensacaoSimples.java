package br.com.imhotep.entidade;

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
@Table(name = "tb_dispensacao_simples")
public class DispensacaoSimples {
	
	private int idDispensacaoSimples;
	private Unidade unidadeDispensada;
	private MovimentoLivro movimentoLivro;

	  
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_dispensacao_simples_id_dispensacao_simples_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_dispensacao_simples", unique = true, nullable = false)
	public int getIdDispensacaoSimples() {
		return idDispensacaoSimples;
	}
	
	public void setIdDispensacaoSimples(int idDispensacaoSimples) {
		this.idDispensacaoSimples = idDispensacaoSimples;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_dispensada")
	public Unidade getUnidadeDispensada() {
		return unidadeDispensada;
	}

	public void setUnidadeDispensada(Unidade unidadeDispensada) {
		this.unidadeDispensada = unidadeDispensada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")
	public MovimentoLivro getMovimentoLivro() {
		return movimentoLivro;
	}

	public void setMovimentoLivro(MovimentoLivro movimentoLivro) {
		this.movimentoLivro = movimentoLivro;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof DispensacaoSimples))
			return false;
		
		return ((DispensacaoSimples)obj).getIdDispensacaoSimples() == this.idDispensacaoSimples;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + unidadeDispensada.hashCode() + movimentoLivro.hashCode();
	}

	@Override
	public String toString() {
		return unidadeDispensada.toString();
	}
}
