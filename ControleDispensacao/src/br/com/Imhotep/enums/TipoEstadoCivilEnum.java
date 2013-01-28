package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEstadoCivilEnum {
	SO("Solteiro(a)"),
	CA("Casado(a)"),
	SE("Separado(a)"),
	DI("Divorciado(a)"),
	VI("Viúvo(a)");
	
	TipoEstadoCivilEnum(String papel){
		label = papel;
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
