package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoComplexidadeEnum {
	T0("Não se Aplica"), 
	T1("Atenção Básica Complexidade"),
	T2("Média Complexidade"),
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
