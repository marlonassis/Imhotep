package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoMetodoExameEnum {
	IM("Imunofluorimetria"), 
	FI("Focalização Isoelétrica");
	
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
