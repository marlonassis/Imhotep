package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoQualidadeDigitalEnum {
	B("Boa"), 
	R("Ruim"),
	I("Inexistente");
	
	private String label;
	
	TipoQualidadeDigitalEnum(String label){
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
