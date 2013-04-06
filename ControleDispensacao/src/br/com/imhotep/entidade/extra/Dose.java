package br.com.imhotep.entidade.extra;

import java.util.Date;

import br.com.imhotep.entidade.PrescricaoItem;

public class Dose {
	private Date dataInicio;
	private Integer quantidadeDoses;
	private Integer intervaloEntreDoses;
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	private Integer quantidadePorDose;
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Integer getQuantidadeDoses() {
		return quantidadeDoses;
	}
	public void setQuantidadeDoses(Integer quantidadeDoses) {
		this.quantidadeDoses = quantidadeDoses;
	}
	public Integer getIntervaloEntreDoses() {
		return intervaloEntreDoses;
	}
	public void setIntervaloEntreDoses(Integer intervaloEntreDoses) {
		this.intervaloEntreDoses = intervaloEntreDoses;
	}
	public Integer getQuantidadePorDose() {
		return quantidadePorDose;
	}
	public void setQuantidadePorDose(Integer quantidadePorDose) {
		this.quantidadePorDose = quantidadePorDose;
	}
	public PrescricaoItem getPrescricaoItem() {
		return prescricaoItem;
	}
	public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
		this.prescricaoItem = prescricaoItem;
	}
}
