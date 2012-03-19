package br.com.ControleDispensacao.entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_prescricao_item")
public class PrescricaoItem {
	private int idPrescricaoItem;
	private Material material;
	private String referenciaUnica;
	private Prescricao prescricao;
	private String observacao;
	private Set<PrescricaoItemDose> prescricaoItemDoses;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_item_id_prescricao_item_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao_item", unique = true, nullable = false)
	public int getIdPrescricaoItem() {
		return idPrescricaoItem;
	}
	public void setIdPrescricaoItem(int idPrescricaoItem) {
		this.idPrescricaoItem = idPrescricaoItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao")
	public Prescricao getPrescricao() {
		return prescricao;
	}
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	@Column(name = "ds_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Column(name = "ds_referencia_unica")
	public String getReferenciaUnica() {
		return referenciaUnica;
	}
	public void setReferenciaUnica(String referenciaUnica) {
		this.referenciaUnica = referenciaUnica;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "prescricaoItem")
	public Set<PrescricaoItemDose> getPrescricaoItemDoses() {
		return prescricaoItemDoses;
	}
	public void setPrescricaoItemDoses(Set<PrescricaoItemDose> prescricaoItemDoses) {
		this.prescricaoItemDoses = prescricaoItemDoses;
	}

	@Override
	public String toString() {
		return "Material: ".concat(material.getDescricao()).concat(" - Prescrição: ").concat(prescricao.getNumero().toString());
	}
}
