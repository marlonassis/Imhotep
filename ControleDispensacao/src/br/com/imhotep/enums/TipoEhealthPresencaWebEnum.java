package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthPresencaWebEnum {
	
	P("Próprio"),
	H("Hospedado");
	
	private String label;
	
	TipoEhealthPresencaWebEnum(String label){
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
