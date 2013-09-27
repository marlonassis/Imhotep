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
@Table(name = "tb_ajuste_estoque_almoxarifado")
public class AjusteEstoqueAlmoxarifado  implements Serializable{
	private static final long serialVersionUID = -1169595414593048201L;
	
	private int idAjusteEstoqueAlmoxarifado;
	private MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado;
	private String justificativa;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ajuste_estoque_almoxarifad_id_ajuste_estoque_almoxarifad_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ajuste_estoque_almoxarifado", unique = true, nullable = false)
	public int getIdAjusteEstoqueAlmoxarifado() {
		return this.idAjusteEstoqueAlmoxarifado;
	}
	
	public void setIdAjusteEstoqueAlmoxarifado(int idAjusteEstoqueAlmoxarifado){
		this.idAjusteEstoqueAlmoxarifado = idAjusteEstoqueAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro_almoxarifado")
	public MovimentoLivroAlmoxarifado getMovimentoLivroAlmoxarifado(){
		return movimentoLivroAlmoxarifado;
	}
	
	public void setMovimentoLivroAlmoxarifado(MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado){
		this.movimentoLivroAlmoxarifado = movimentoLivroAlmoxarifado;
	}
	
	@Column(name = "cv_justificativa")
	public String getJustificativa(){
		return justificativa;
	}
	
	public void setJustificativa(String justificativa){
		this.justificativa = justificativa;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idAjusteEstoqueAlmoxarifado;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime
				* result
				+ ((movimentoLivroAlmoxarifado == null) ? 0
						: movimentoLivroAlmoxarifado.hashCode());
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
		AjusteEstoqueAlmoxarifado other = (AjusteEstoqueAlmoxarifado) obj;
		if (idAjusteEstoqueAlmoxarifado != other.idAjusteEstoqueAlmoxarifado)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (movimentoLivroAlmoxarifado == null) {
			if (other.movimentoLivroAlmoxarifado != null)
				return false;
		} else if (!movimentoLivroAlmoxarifado
				.equals(other.movimentoLivroAlmoxarifado))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return justificativa;
	}
	
}
