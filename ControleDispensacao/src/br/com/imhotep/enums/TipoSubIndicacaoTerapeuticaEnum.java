package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoSubIndicacaoTerapeuticaEnum {
	IC("Infec��o Comunit�ria"),
	IN("Infec��o Nosocomial");
	
	TipoSubIndicacaoTerapeuticaEnum(String papel){
		label = papel;
	}
	
	private String label;
	
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
