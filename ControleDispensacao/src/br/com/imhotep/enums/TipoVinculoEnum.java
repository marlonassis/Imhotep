package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoVinculoEnum {
	EB("EBSERH"),
	MS("Ministério da Saúde"),
	SM("Secretária Municipal da Saúde"),
	TE("Terceirizado"),
	ES("UFS");
	
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
