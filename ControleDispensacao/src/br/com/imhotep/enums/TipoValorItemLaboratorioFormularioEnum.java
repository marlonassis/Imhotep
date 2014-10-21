package br.com.imhotep.enums;

public enum TipoValorItemLaboratorioFormularioEnum {
	IN("Inteiro"),
	AL("Alfanum�rico"),
	TX("Texto"),
	LI("Lista"),
	SN("Sim/N�o");
	
	private String label;
	
	TipoValorItemLaboratorioFormularioEnum(String label){
		this.label = label;
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
