package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEscolaridadeEnum {
	EI("Educação Infantil"),
	EF("Ensino Fundamental"),
	EM("Ensino Médio"),
	ES("Ensino Superior"),
	ME("Mestrado"),
	DO("Doutorado"),
	PD("Pós-Doutorado");
	
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
