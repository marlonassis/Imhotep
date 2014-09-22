package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEstadoFisicoMedicamentoEnum {
	LI("L’quido"), 
	SL("Semi L’quido"),
	SO("Solido"),
	GA("Gasoso");
	
	private String label;
	
	TipoEstadoFisicoMedicamentoEnum(String label){
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
