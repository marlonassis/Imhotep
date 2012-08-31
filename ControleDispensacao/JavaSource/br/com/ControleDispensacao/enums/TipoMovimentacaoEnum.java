package br.com.ControleDispensacao.enums;

/**
 * @author marlonassis
 */

public enum TipoMovimentacaoEnum {
	E("Entrada"),
	P("Perda"),
	S("Saída");
	
	TipoMovimentacaoEnum(String papel){
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
