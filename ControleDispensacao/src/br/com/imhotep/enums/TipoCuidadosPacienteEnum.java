package br.com.imhotep.enums;

public enum TipoCuidadosPacienteEnum {
	AER("Aerosol"),
	CEN("Cuidados de Enfermagem"),
	MED("Medica��es"),
	MOR("Medica��es orais"),
	MSC("Medica��es SC, IM"),
	MSO("Medica��es SOS"),
	MTO("Medica��es t�picas"),
	NUT("Nutri��o (SND)"),
	SOR("Soroterapia, scalp,"),
	OUT("Outros");
	
	private String label;
	
	TipoCuidadosPacienteEnum(String label){
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
