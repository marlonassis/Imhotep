package br.com.imhotep.enums;

/**
 * Classe que contém todos os papéis possíveis a um usuário
 * @author marlonassis
 */

public enum TipoOperacaoEnum {
	E("Entrada"), 
	S("Saída"),
	P("Perda");
	
	private String label;
	
	TipoOperacaoEnum(String label){
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
