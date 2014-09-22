package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoVinculoEnum {
	EB("EBSERH"),
	ES("Estatutario"),
	TE("Terceirizado");
	
	TipoVinculoEnum(String label){
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
