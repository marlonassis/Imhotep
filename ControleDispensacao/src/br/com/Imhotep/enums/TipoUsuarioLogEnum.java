package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoUsuarioLogEnum {
	A("Logout Automático"),
	O("Logout"), 
	I("Login");
	
	private String label;
	
	TipoUsuarioLogEnum(String label){
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
