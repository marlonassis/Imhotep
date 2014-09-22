package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthContinenteEnum {
	
	AS("América do Sul"),
	AC("América Central"),
	AN("América do Norte"),
	AA("Ásia"),
	OC("Oceania"),
	EU("Europa"),
	AF("África");
	
	private String label;
	
	TipoEhealthContinenteEnum(String label){
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
