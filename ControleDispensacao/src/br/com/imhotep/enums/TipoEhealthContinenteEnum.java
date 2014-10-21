package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthContinenteEnum {
	
	AS("Am�rica do Sul"),
	AC("Am�rica Central"),
	AN("Am�rica do Norte"),
	AA("�sia"),
	OC("Oceania"),
	EU("Europa"),
	AF("�frica");
	
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
