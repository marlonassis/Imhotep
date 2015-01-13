package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoChamadoStatusEnum {
	A("Aberto"),
	F("Fechado"), 
	N("Não Resolvido");
	
	private String label;
	
	TipoChamadoStatusEnum(String label){
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
