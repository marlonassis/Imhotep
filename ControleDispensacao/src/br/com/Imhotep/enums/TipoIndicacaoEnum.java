package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoIndicacaoEnum {
	T("Terapêutica"),
	P("Profilática");
	
	TipoIndicacaoEnum(String papel){
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
