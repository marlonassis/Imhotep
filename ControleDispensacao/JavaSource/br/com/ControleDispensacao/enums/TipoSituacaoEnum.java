package br.com.ControleDispensacao.enums;

/**
 * @author marlonassis
 */

public enum TipoSituacaoEnum {
	A("Ativo"), 
	I("Inativo"); 
	
	TipoSituacaoEnum(String papel){
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
