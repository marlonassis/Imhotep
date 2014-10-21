package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoSexoEnum {
	M("Masculino"), 
	F("Feminino"),
	I("Indiferente"),
	N("N‹o se aplica");
	
	private String label;
	
	TipoSexoEnum(String label){
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
