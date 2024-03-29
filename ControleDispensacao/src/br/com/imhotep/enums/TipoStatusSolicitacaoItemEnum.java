package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoStatusSolicitacaoItemEnum {
	E("Estornado"),
	P("Pendente"),
	D("Dispensado"),
	R("Recusado"),
	DP("Dispensado em parte");
	
	private String label;
	
	TipoStatusSolicitacaoItemEnum(String label){
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
