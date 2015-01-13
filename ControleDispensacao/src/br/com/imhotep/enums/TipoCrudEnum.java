package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoCrudEnum {
	A("Alteração"),
	C("Consulta"),
	D("Deleção"),
	I("Inserção"),
	M("Deslocamento");
	
	private String label;
	
	TipoCrudEnum(String label){
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
