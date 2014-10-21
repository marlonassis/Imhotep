package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoMotivosRejeicaoAmostraTestePezinhoEnum {
	AI("Amostra Insuficiente"), 
	AS("Amostra Sobreposta"),
	AE("Amostra Elu’da"),
	EP("Erro de preenchimento do cart‹o"),
	AC("Amostra Contaminada"),
	OT("Outros");
	
	private String label;
	
	TipoMotivosRejeicaoAmostraTestePezinhoEnum(String metodo){
		label = metodo;
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
