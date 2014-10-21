package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoPrescricaoInadequadaEnum {
	SI("Sem evid�ncia de infec��o"),
	TX("Toxidade"),
	EI("Espectro inadequado"),
	OT("Outros");
	
	private String label;
	
	TipoPrescricaoInadequadaEnum(String label){
		this.label = label;
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
