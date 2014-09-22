package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoCategoriaRecursoEnum {
	A("Aberto"),
	F("Fechado"), 
	N("N�o Resolvido");
	
	private String label;
	
	TipoCategoriaRecursoEnum(String label){
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
