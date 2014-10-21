package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoStatusDevolucaoItemEnum {
	A("Aberto"),
	E("Estornado"),
	P("Pendente"),
	R("Recebido"),
	RP("Recebido em Parte"),
	RE("Recusado");
	
	private String label;
	
	TipoStatusDevolucaoItemEnum(String label){
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
