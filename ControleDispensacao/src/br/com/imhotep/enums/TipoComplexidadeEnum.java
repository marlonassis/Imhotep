package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoComplexidadeEnum {
	T0("N�o se Aplica"), 
	T1("Aten��o B�sica Complexidade"),
	T2("M�dia Complexidade"),
	T3("Alta Complexidade");
	
	private String label;
	
	TipoComplexidadeEnum(String label){
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
