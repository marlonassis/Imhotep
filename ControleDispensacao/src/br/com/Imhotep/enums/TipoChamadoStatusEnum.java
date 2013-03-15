package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoChamadoStatusEnum {
	A("Aberto"),
	F("Fechado"), 
	N("Não Resolvido");
	
	private String label;
	
	TipoChamadoStatusEnum(String sexo){
		label = sexo;
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