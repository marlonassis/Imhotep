package br.com.imhotep.enums;

public enum TipoStatusSolicitacaoExameItemEnum {
	BA("Pendente"),
	BB("Recusado"),
	BC("Coletado");
	
	private String label;
	
	TipoStatusSolicitacaoExameItemEnum(String label){
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
