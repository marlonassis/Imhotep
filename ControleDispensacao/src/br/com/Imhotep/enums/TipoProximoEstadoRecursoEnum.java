package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoProximoEstadoRecursoEnum {
	A("Aberto"),
	F("Fechado"), 
	N("Não Resolvido");
	
	private String label;
	
	TipoProximoEstadoRecursoEnum(String sexo){
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
