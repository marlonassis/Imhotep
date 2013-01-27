package br.com.ControleDispensacao.enums;

/**
 * @author marlonassis
 */

public enum TipoMotivosRejeicaoAmostraTestePezinhoEnum {
	AI("Amostra Insuficiente"), 
	AS("Amostra Sobreposta"),
	AE("Amostra Eluída"),
	EP("Erro de preenchimento do cartão"),
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
