package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoMetodoExameEnum {
	IM("Imunofluorimetria"), 
	FI("Focaliza��o Isoel�trica");
	
	private String label;
	
	TipoMetodoExameEnum(String metodo){
		label = metodo;
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
