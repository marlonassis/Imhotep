package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name = "tb_dispensacao_simples_almoxarifado")
public class DispensacaoSimplesAlmoxarifado  implements Serializable {
	private static final long serialVersionUID = -7669021399201857758L;
	
	private int idDispensacaoSimplesAlmoxarifado;
	private Unidade unidadeDispensada;
	private MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado;
	private SolicitacaoMaterialAlmoxarifadoUnidadeItem solicitacaoMaterialAlmoxarifadoUnidadeItem;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_dispensacao_simples_almoxa_id_dispensacao_simples_almoxa_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_dispensacao_simples_almoxarifado", unique = true, nullable = false)
	public int getIdDispensacaoSimplesAlmoxarifado() {
		return idDispensacaoSimplesAlmoxarifado;
	}
	
	public void setIdDispensacaoSimplesAlmoxarifado(int idDispensacaoSimplesAlmoxarifado) {
		this.idDispensacaoSimplesAlmoxarifado = idDispensacaoSimplesAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_dispensada")
	public Unidade getUnidadeDispensada() {
		return unidadeDispensada;
	}

	public void setUnidadeDispensada(Unidade unidadeDispensada) {
		this.unidadeDispensada = unidadeDispensada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_movimento_livro_almoxarifado")
	public MovimentoLivroAlmoxarifado getMovimentoLivroAlmoxarifado() {
		return movimentoLivroAlmoxarifado;
	}

	public void setMovimentoLivroAlmoxarifado(MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado) {
		this.movimentoLivroAlmoxarifado = movimentoLivroAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_solicitacao_material_almoxarifado_unidade_item")
	public SolicitacaoMaterialAlmoxarifadoUnidadeItem getSolicitacaoMaterialAlmoxarifadoUnidadeItem() {
		return solicitacaoMaterialAlmoxarifadoUnidadeItem;
	}
	
	public void setSolicitacaoMaterialAlmoxarifadoUnidadeItem(
			SolicitacaoMaterialAlmoxarifadoUnidadeItem solicitacaoMaterialAlmoxarifadoUnidadeItem) {
		this.solicitacaoMaterialAlmoxarifadoUnidadeItem = solicitacaoMaterialAlmoxarifadoUnidadeItem;
	}

	@Override
	public String toString() {
		if(unidadeDispensada != null)
			return unidadeDispensada.toString();
		return super.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDispensacaoSimplesAlmoxarifado;
		result = prime
				* result
				+ ((movimentoLivroAlmoxarifado == null) ? 0
						: movimentoLivroAlmoxarifado.hashCode());
		result = prime
				* result
				+ ((solicitacaoMaterialAlmoxarifadoUnidadeItem == null) ? 0
						: solicitacaoMaterialAlmoxarifadoUnidadeItem.hashCode());
		result = prime
				* result
				+ ((unidadeDispensada == null) ? 0 : unidadeDispensada
						.hashCode());
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
		DispensacaoSimplesAlmoxarifado other = (DispensacaoSimplesAlmoxarifado) obj;
		if (idDispensacaoSimplesAlmoxarifado != other.idDispensacaoSimplesAlmoxarifado)
			return false;
		if (movimentoLivroAlmoxarifado == null) {
			if (other.movimentoLivroAlmoxarifado != null)
				return false;
		} else if (!movimentoLivroAlmoxarifado
				.equals(other.movimentoLivroAlmoxarifado))
			return false;
		if (solicitacaoMaterialAlmoxarifadoUnidadeItem == null) {
			if (other.solicitacaoMaterialAlmoxarifadoUnidadeItem != null)
				return false;
		} else if (!solicitacaoMaterialAlmoxarifadoUnidadeItem
				.equals(other.solicitacaoMaterialAlmoxarifadoUnidadeItem))
			return false;
		if (unidadeDispensada == null) {
			if (other.unidadeDispensada != null)
				return false;
		} else if (!unidadeDispensada.equals(other.unidadeDispensada))
			return false;
		return true;
	}

	

	

}
