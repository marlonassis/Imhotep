package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoAutorizacaoSolicitacaoExameEnum {
	AA("Liberar"), 
	AB("Recusar"),
	AC("Lançar Resultados"),
	AD("Confirmar Resultados"),
	AE("Imprimir"),
	AF("Reabrir");
	
	private String label;
	
	TipoAutorizacaoSolicitacaoExameEnum(String label){
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
