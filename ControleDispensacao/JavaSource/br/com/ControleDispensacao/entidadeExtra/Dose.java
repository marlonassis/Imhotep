package br.com.ControleDispensacao.entidadeExtra;

import java.util.Date;

import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.enums.TipoViaAdministracaoMedicamentoEnum;

public class Dose {
	private Date dataInicio;
	private Integer quantidadeDoses;
	private Integer intervaloEntreDoses;
	private Integer quantidadePorDose;
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	
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
