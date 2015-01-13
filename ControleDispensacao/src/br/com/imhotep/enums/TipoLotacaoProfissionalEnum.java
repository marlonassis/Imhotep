package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoLotacaoProfissionalEnum {
	C("Convidado"),
	E("Efetivo"); 
	
	private String label;
	
	TipoLotacaoProfissionalEnum(String label){
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
