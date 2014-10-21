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
@Table(name = "tb_devolucao_material_item")
public class DevolucaoMaterialItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idDevolucaoMaterialItem;
	private DevolucaoMaterial devolucaoMaterial;
	private MaterialAlmoxarifado materialAlmoxarifado;
	private Integer quantidadeDevolvida;
	private Integer quantidadeRecebida;
	private String justificativa;
	private TipoStatusDevolucaoItemEnum status;
	private Set<DevolucaoMaterialItemMovimento> devolucoesEstoque;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_devolucao_material_item_id_devolucao_material_item_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_devolucao_material_item", unique = true, nullable = false)
	public int getIdDevolucaoMaterialItem() {
		return idDevolucaoMaterialItem;
	}

	public void setIdDevolucaoMaterialItem(int idDevolucaoMaterialTtem) {
		this.idDevolucaoMaterialItem = idDevolucaoMaterialTtem;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_devolucao_material")
	public DevolucaoMaterial getDevolucaoMaterial() {
		return devolucaoMaterial;
	}

	public void setDevolucaoMaterial(DevolucaoMaterial devolucaoMaterial) {
		this.devolucaoMaterial = devolucaoMaterial;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material_almoxarifado")
	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}

	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
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
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "devolucaoMaterialItem")
	public Set<DevolucaoMaterialItemMovimento> getDevolucoesEstoque() {
		return devolucoesEstoque;
	}
	public void setDevolucoesEstoque(Set<DevolucaoMaterialItemMovimento> devolucoesEstoque) {
		this.devolucoesEstoque = devolucoesEstoque;
	}
	
	@Transient
	public List<DevolucaoMaterialItemMovimento> getDevolucoesEstoqueList() {
		if(devolucoesEstoque != null)
			return new ArrayList<DevolucaoMaterialItemMovimento>(devolucoesEstoque);
		return new ArrayList<DevolucaoMaterialItemMovimento>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof DevolucaoMaterialItem))
			return false;
		
		return ((DevolucaoMaterialItem)obj).getIdDevolucaoMaterialItem() == this.idDevolucaoMaterialItem;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + devolucaoMaterial.hashCode() + materialAlmoxarifado.hashCode();
	}

	@Override
	public String toString() {
		return materialAlmoxarifado.getDescricao();
	}
	
}
