package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoViaAdministracaoMedicamentoEnum {
	
	EV("EV - Via Endovenosa"),
	IM("IM - Via Intramuscular"),
	ID("ID - Via Intrad�rmica"),
	SC("SC - Via Subcut�nea"),
	IN("Via inalat�ria"),
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
