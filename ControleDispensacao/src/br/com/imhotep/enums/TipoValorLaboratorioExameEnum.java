package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoValorLaboratorioExameEnum {
	
    IN("Inteiro"), 
    FR("Fracionado"), 
    TE("Texto"), 
    AL("Alfanumérico"); 
	
	private String label;
	
	TipoValorLaboratorioExameEnum(String label){
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
