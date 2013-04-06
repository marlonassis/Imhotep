package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoBloqueioLoteEnum {
	V("Lote Vencido."), 
	A("Lote Bloqueado Pela Anvisa."),
	O("Outro");
	
	private String label;
	
	TipoBloqueioLoteEnum(String sexo){
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
