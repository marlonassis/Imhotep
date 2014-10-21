package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoUsuarioLogEnum {
	E("Erro Login"),
	B("Bloqueio"),
	D("Desbloqueio"),
	A("Logout Autom�tico"),
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
