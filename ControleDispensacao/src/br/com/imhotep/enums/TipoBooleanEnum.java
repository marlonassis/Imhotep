package br.com.imhotep.enums;

/**
 * Classe que contém todos os papéis possíveis a um usuário
 * @author marlonassis
 */

public enum TipoBooleanEnum {
	T("Sim"), 
	F("Não");
	
	private String label;
	
	TipoBooleanEnum(String label){
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
