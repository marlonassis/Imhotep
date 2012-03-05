package br.com.ControleDispensacao.entidade;

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
@Table(name = "tb_prescricao_item_dose")
public class PrescricaoItemDose {
	private int idPrescricaoItemDose;
	private PrescricaoItem prescricaoItem;
	private Integer periodo;
	private Integer quantidade;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_item_dose_id_prescricao_item_dose_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao_item_dose", unique = true, nullable = false)
	public int getIdPrescricaoItemDose() {
		return idPrescricaoItemDose;
	}
	public void setIdPrescricaoItemDose(int idPrescricaoItemDose) {
		this.idPrescricaoItemDose = idPrescricaoItemDose;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao_item")
	public PrescricaoItem getPrescricaoItem() {
		return prescricaoItem;
	}
	public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
		this.prescricaoItem = prescricaoItem;
	}
	
	@Column(name = "in_periodo")
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	@Column(name = "in_quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof PrescricaoItemDose))
			return false;
		
		return ((PrescricaoItemDose)obj).getIdPrescricaoItemDose() == this.idPrescricaoItemDose;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + periodo.hashCode();
	}

	@Override
	public String toString() {
		return "Material: ".concat(prescricaoItem.getMaterial().getDescricao()).concat(" - Período: ").concat(periodo.toString());
	}
}
