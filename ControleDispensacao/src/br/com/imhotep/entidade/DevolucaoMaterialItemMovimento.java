package br.com.imhotep.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_devolucao_material_item_movimento")
public class DevolucaoMaterialItemMovimento {
	
	private int idDevolucaoMaterialItemMovimento;
	private DevolucaoMaterialItem devolucaoMaterialItem;
	private MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado;
	private Date dataInsercao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_devolucao_material_item_mo_id_devolucao_material_item_mo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_devolucao_material_item_movimento", unique = true, nullable = false)
	public int getIdDevolucaoMaterialItemMovimento() {
		return idDevolucaoMaterialItemMovimento;
	}

	public void setIdDevolucaoMaterialItemMovimento(int idDevolucaoMaterialItemMovimento) {
		this.idDevolucaoMaterialItemMovimento = idDevolucaoMaterialItemMovimento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_devolucao_material_item")
	public DevolucaoMaterialItem getDevolucaoMaterialItem() {
		return devolucaoMaterialItem;
	}

	public void setDevolucaoMaterialItem(DevolucaoMaterialItem devolucaoMaterialItem) {
		this.devolucaoMaterialItem = devolucaoMaterialItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro_almoxarifado")
	public MovimentoLivroAlmoxarifado getMovimentoLivroAlmoxarifado() {
		return movimentoLivroAlmoxarifado;
	}

	public void setMovimentoLivroAlmoxarifado(MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado) {
		this.movimentoLivroAlmoxarifado = movimentoLivroAlmoxarifado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataInsercao == null) ? 0 : dataInsercao.hashCode());
		result = prime
				* result
				+ ((devolucaoMaterialItem == null) ? 0 : devolucaoMaterialItem
						.hashCode());
		result = prime * result + idDevolucaoMaterialItemMovimento;
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
		DevolucaoMaterialItemMovimento other = (DevolucaoMaterialItemMovimento) obj;
		if (dataInsercao == null) {
			if (other.dataInsercao != null)
				return false;
		} else if (!dataInsercao.equals(other.dataInsercao))
			return false;
		if (devolucaoMaterialItem == null) {
			if (other.devolucaoMaterialItem != null)
				return false;
		} else if (!devolucaoMaterialItem.equals(other.devolucaoMaterialItem))
			return false;
		if (idDevolucaoMaterialItemMovimento != other.idDevolucaoMaterialItemMovimento)
			return false;
		if (movimentoLivroAlmoxarifado == null) {
			if (other.movimentoLivroAlmoxarifado != null)
				return false;
		} else if (!movimentoLivroAlmoxarifado
				.equals(other.movimentoLivroAlmoxarifado))
			return false;
		return true;
	}
	
}
