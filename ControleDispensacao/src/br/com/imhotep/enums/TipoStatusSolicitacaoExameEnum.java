package br.com.imhotep.enums;

public enum TipoStatusSolicitacaoExameEnum {
	AA("Aberto"), 
	AB("Liberado"),
	AC("Recusado"),
	AD("Resultados Lançados"),
	AE("Verificado"),
	AF("Impresso");
	
	private String label;
	
	TipoStatusSolicitacaoExameEnum(String label){
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
