package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoHistoricoChefiaEnum {
	EX("Exoneração"),
	PO("Posse");
	
	TipoHistoricoChefiaEnum(String label){
		this.label = label;
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
