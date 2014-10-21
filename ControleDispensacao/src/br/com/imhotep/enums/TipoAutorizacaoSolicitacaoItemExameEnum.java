package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoAutorizacaoSolicitacaoItemExameEnum {
	BA("Coletar"), 
	BB("Recusar"),
	BC("Lan�ar Resultados"),
	BD("Avaliar Amostra"),
	BE("Reabrir");
	
	private String label;
	
	TipoAutorizacaoSolicitacaoItemExameEnum(String label){
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
