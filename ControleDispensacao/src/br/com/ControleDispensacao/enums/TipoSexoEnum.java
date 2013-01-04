package br.com.ControleDispensacao.enums;

/**
 * Classe que contém todos os papéis possíveis a um usuário
 * @author marlonassis
 */

public enum TipoSexoEnum {
	M("Masculino"), 
	F("Feminino");
	
	private String label;
	
	TipoSexoEnum(String sexo){
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
