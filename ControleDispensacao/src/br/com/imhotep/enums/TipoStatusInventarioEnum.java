package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoStatusInventarioEnum {
	A("Aberto"),
	F("Fechado"), 
	N("Cancelado");
	
	private String label;
	
	TipoStatusInventarioEnum(String label){
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
