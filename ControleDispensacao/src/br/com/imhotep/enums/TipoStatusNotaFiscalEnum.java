package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoStatusNotaFiscalEnum {
	BL("Bloqueada"),
	PE("Pendente"),
	LI("Liberada");
	
	private String label;
	
	TipoStatusNotaFiscalEnum(String label){
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
