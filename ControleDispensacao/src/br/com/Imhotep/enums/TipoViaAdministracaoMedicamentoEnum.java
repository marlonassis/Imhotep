package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoViaAdministracaoMedicamentoEnum {
	
	EV("EV - Via Endovenosa"),
	IM("IM - Via Intramuscular"),
	ID("ID - Via Intradérmica"),
	SC("SC - Via Subcutânea"),
	IN("Via inalatória"),
	VR("Via Retal"),
	VO("VO - Via Oral"),
	VS("VS - Via sublingual"),
	OT("Outros...");
	
	private String label;
	
	TipoViaAdministracaoMedicamentoEnum(String via){
		label = via;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}
}
