package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEstoqueLog {
	F("Fusão"),
	G("Deleção da Fusão"),
	D("Deleção"),
	A("Alteração Origem"),
	B("Alteração Destino"),
	O("Bloqueio"),
	P("Desbloqueio");
	
	private String label;
	
	TipoEstoqueLog(String sexo){
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
