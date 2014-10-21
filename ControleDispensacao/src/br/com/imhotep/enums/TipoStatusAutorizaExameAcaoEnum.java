package br.com.imhotep.enums;

public enum TipoStatusAutorizaExameAcaoEnum {
	RE("Recusar"),
	RA("Reabrir"),
	CO("Coletar"),
	RC("Re-Coletar"),
	AN("Analisar"),
	RT("Lançar Resultados"),
	LI("Liberar"),
	IM("Imprimir");
	
	private String label;
	
	TipoStatusAutorizaExameAcaoEnum(String label){
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
