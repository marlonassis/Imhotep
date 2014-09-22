package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoStatusLaboratorioResultadoEnum {
	LI("Liberado"),
	RE("Recusado");
	
	private String label;
	
	TipoStatusLaboratorioResultadoEnum(String label){
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
