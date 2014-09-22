package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoStatusDevolucaoItemEnum;

@Entity
@Table(name = "tb_devolucao_medicamento_item")
public class DevolucaoMedicamentoItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idDevolucaoMedicamentoItem;
	private DevolucaoMedicamento devolucaoMedicamento;
	private Material material;
	private Integer quantidadeDevolvida;
	private Integer quantidadeRecebida;
	private String justificativa;
	private TipoStatusDevolucaoItemEnum status;
	private Set<DevolucaoMedicamentoItemMovimento> devolucoesEstoque;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_devolucao_medicamento_item_id_devolucao_medicamento_item_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_devolucao_medicamento_item", unique = true, nullable = false)
	public int getIdDevolucaoMedicamentoItem() {
		return idDevolucaoMedicamentoItem;
	}

	public void setIdDevolucaoMedicamentoItem(int idDevolucaoMedicamentoTtem) {
		this.idDevolucaoMedicamentoItem = idDevolucaoMedicamentoTtem;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_devolucao_medicamento")
	public DevolucaoMedicamento getDevolucaoMedicamento() {
		return devolucaoMedicamento;
	}

	public void setDevolucaoMedicamento(DevolucaoMedicamento devolucaoMedicamento) {
		this.devolucaoMedicamento = devolucaoMedicamento;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Column(name = "in_quantidade_devolvida")
	public Integer getQuantidadeDevolvida() {
		return quantidadeDevolvida;
	}

	public void setQuantidadeDevolvida(Integer quantidadeDevolvida) {
		this.quantidadeDevolvida = quantidadeDevolvida;
	}

	@Column(name = "in_quantidade_recebida")
	public Integer getQuantidadeRecebida() {
		return quantidadeRecebida;
	}

	public void setQuantidadeRecebida(Integer quantidadeRecebida) {
		this.quantidadeRecebida = quantidadeRecebida;
	}

	@Column(name="cv_jutificativa")
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@Column(name="tp_status")
	@Enumerated(EnumType.STRING)
	public TipoStatusDevolucaoItemEnum getStatus() {
		return status;
	}

	public void setStatus(TipoStatusDevolucaoItemEnum status) {
		this.status = status;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "devolucaoMedicamentoItem")
	public Set<DevolucaoMedicamentoItemMovimento> getDevolucoesEstoque() {
		return devolucoesEstoque;
	}
	public void setDevolucoesEstoque(Set<DevolucaoMedicamentoItemMovimento> devolucoesEstoque) {
		this.devolucoesEstoque = devolucoesEstoque;
	}
	
	@Transient
	public List<DevolucaoMedicamentoItemMovimento> getDevolucoesEstoqueList() {
		if(devolucoesEstoque != null)
			return new ArrayList<DevolucaoMedicamentoItemMovimento>(devolucoesEstoque);
		return new ArrayList<DevolucaoMedicamentoItemMovimento>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof DevolucaoMedicamentoItem))
			return false;
		
		return ((DevolucaoMedicamentoItem)obj).getIdDevolucaoMedicamentoItem() == this.idDevolucaoMedicamentoItem;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + devolucaoMedicamento.hashCode() + material.hashCode();
	}

	@Override
	public String toString() {
		return material.getDescricao();
	}
	
}
