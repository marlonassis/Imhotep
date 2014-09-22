package br.com.imhotep.enums;

public enum TipoValorItemLaboratorioFormularioEnum {
	IN("Inteiro"),
	AL("Alfanumérico"),
	TX("Texto"),
	LI("Lista"),
	SN("Sim/Não");
	
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
