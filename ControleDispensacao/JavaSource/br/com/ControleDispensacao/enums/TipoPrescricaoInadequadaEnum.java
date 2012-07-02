package br.com.ControleDispensacao.enums;

/**
 * @author marlonassis
 */

public enum TipoPrescricaoInadequadaEnum {
	SI("Sem evidência de infecção"),
	TX("Toxidade"),
	EI("Espectro inadequado"),
	OT("Outros");
	
	private String label;
	
	TipoPrescricaoInadequadaEnum(String sexo){
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
