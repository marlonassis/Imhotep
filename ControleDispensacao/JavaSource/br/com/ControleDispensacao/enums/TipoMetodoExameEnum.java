package br.com.ControleDispensacao.enums;

/**
 * Classe que contém todos os papéis possíveis a um usuário
 * @author marlonassis
 */

public enum TipoMetodoExameEnum {
	T("Sim"), 
	F("Não");
	
	private String label;
	
	TipoMetodoExameEnum(String sexo){
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
