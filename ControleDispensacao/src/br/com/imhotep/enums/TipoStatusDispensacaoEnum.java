package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoStatusDispensacaoEnum {
	A("Aberta"),
	P("Pendente"),
	D("Dispensado"),  
	DP("Dispensado em parte"),
	R("Recusado");
	
	private String label;
	
	TipoStatusDispensacaoEnum(String label){
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
