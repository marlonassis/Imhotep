package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEscolaridadeEnum {
	EI("Educa��o Infantil"),
	EF("Ensino Fundamental"),
	EM("Ensino M�dio"),
	ES("Ensino Superior"),
	ME("Mestrado"),
	DO("Doutorado"),
	PD("P�s-Doutorado");
	
	TipoEscolaridadeEnum(String papel){
		label = papel;
	}
	
	private String label;
	
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
