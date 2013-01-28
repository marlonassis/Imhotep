package br.com.Imhotep.entidade;

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
@Table(name = "tb_prescricao_item_estoque_saida")
public class PrescricaoItemEstoqueSaida {
	private int idPrescricaoItemEstoqueSaida;
	private PrescricaoItem prescricaoItem;
	private Estoque estoque;
	private Integer quantidadeSaida;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_item_estoque_sa_id_prescricao_item_estoque_sa_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao_item_estoque_saida", unique = true, nullable = false)
	public int getIdPrescricaoItemEstoqueSaida() {
		return idPrescricaoItemEstoqueSaida;
	}
	public void setIdPrescricaoItemEstoqueSaida(int idPrescricaoItemEstoqueSaida) {
		this.idPrescricaoItemEstoqueSaida = idPrescricaoItemEstoqueSaida;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao_item")
	public PrescricaoItem getPrescricaoItem() {
		return prescricaoItem;
	}
	public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
		this.prescricaoItem = prescricaoItem;
	}
	
	@Column(name = "in_quantidade_saida")
	public Integer getQuantidadeSaida() {
		return quantidadeSaida;
	}
	public void setQuantidadeSaida(Integer quantidadeSaida) {
		this.quantidadeSaida = quantidadeSaida;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque")
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	@Override
	public String toString() {
		return prescricaoItem.getMaterial().getDescricao().concat(": ").concat(String.valueOf(quantidadeSaida));
	}
}
