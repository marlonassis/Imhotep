package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;

@Entity
@Table(name = "item_solicita_remanej")
public class ItemSolicitaRemanejamento {
	private int idItemSolicitaRemanejamento;
	private SolicitaRemanejamento solicitaRemanejamento;
	private Material material;
	private Integer quantidadeSolicitada;
	private Integer quantidadeAtendida;
	private TipoSituacaoEnum status;
	
	@Id
	@GeneratedValue
	@Column(name = "id_item_solicita_remanej")
	public int getIdItemSolicitaRemanejamento() {
		return idItemSolicitaRemanejamento;
	}
	public void setIdItemSolicitaRemanejamento(int idItemSolicitaRemanejamento) {
		this.idItemSolicitaRemanejamento = idItemSolicitaRemanejamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_solicita_remanej")
	public SolicitaRemanejamento getSolicitaRemanejamento() {
		return solicitaRemanejamento;
	}
	public void setSolicitaRemanejamento(SolicitaRemanejamento solicitaRemanejamento) {
		this.solicitaRemanejamento = solicitaRemanejamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "material_id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Column(name = "qtde_solicita")
	public Integer getQuantidadeSolicitada() {
		return quantidadeSolicitada;
	}
	public void setQuantidadeSolicitada(Integer quantidadeSolicitada) {
		this.quantidadeSolicitada = quantidadeSolicitada;
	}
	
	@Column(name = "qtde_atendida")
	public Integer getQuantidadeAtendida() {
		return quantidadeAtendida;
	}
	public void setQuantidadeAtendida(Integer quantidadeAtendida) {
		this.quantidadeAtendida = quantidadeAtendida;
	}
	
	@Column(name = "status_2")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return status;
	}
	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ItemSolicitaRemanejamento))
			return false;
		
		return ((ItemSolicitaRemanejamento)obj).getIdItemSolicitaRemanejamento() == this.idItemSolicitaRemanejamento;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + material.getDescricao().hashCode();
	}

	@Override
	public String toString() {
		return material.getDescricao().concat(" - ").concat(solicitaRemanejamento.getUnidadeSolicitada().getNome());
	}
	
}
