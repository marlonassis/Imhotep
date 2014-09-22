package br.com.imhotep.enums;

/**
 * Classe que cont�m todos os pap�is poss�veis a um usu�rio
 * @author marlonassis
 */

public enum TipoOperacaoEnum {
	E("Entrada"), 
	S("Sa�da"),
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
