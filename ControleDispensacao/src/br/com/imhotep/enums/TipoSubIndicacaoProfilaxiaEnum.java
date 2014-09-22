package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoSubIndicacaoProfilaxiaEnum {
	CI("Cirœrgica"),
	CL("Cl’nica");
	
	TipoSubIndicacaoProfilaxiaEnum(String papel){
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
