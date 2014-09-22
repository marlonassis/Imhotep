package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEstoqueLog {
	F("Fus�o"),
	G("Dele��o da Fus�o"),
	D("Dele��o"),
	A("Altera��o Origem"),
	B("Altera��o Destino"),
	O("Bloqueio"),
	P("Desbloqueio");
	
	private String label;
	
	TipoEstoqueLog(String label){
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
