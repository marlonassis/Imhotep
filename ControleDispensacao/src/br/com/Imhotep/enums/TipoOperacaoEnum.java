package br.com.Imhotep.enums;

/**
 * Classe que contém todos os papéis possíveis a um usuário
 * @author marlonassis
 */

public enum TipoOperacaoEnum {
	Entrada("Entrada"), 
	Saida("Saída"),
	Perda("Perda");
	
	private String label;
	
	TipoOperacaoEnum(String sexo){
		label = sexo;
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
