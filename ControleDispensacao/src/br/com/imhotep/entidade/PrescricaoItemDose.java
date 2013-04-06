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
import javax.persistence.Transient;

@Entity
@Table(name = "tb_prescricao_item_dose")
public class PrescricaoItemDose {
	private int idPrescricaoItemDose;
	private PrescricaoItem prescricaoItem;
	private Integer periodo;
	private Integer quantidade;
	private Date dataDose;
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_dose")
	public Date getDataDose() {
		return dataDose;
	}
	
	public void setDataDose(Date dataDose) {
		this.dataDose = dataDose;
	}
	
	@Column(name = "in_quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Transient
	public String getNumeroReferencia(){
		String referencia = String.valueOf(periodo).concat("-");
		referencia.concat(String.valueOf(dataDose.getTime()));
		return referencia;
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
	    return hash * 31 + (dataDose == null ? 0 : dataDose.hashCode());
	}

	@Override
	public String toString() {
		return "Material: ".concat(prescricaoItem.getMaterial().getDescricao()).concat(" - Período: ").concat(periodo.toString());
	}
}
