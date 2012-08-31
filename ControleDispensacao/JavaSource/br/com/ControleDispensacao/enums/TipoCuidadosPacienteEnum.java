package br.com.ControleDispensacao.enums;

public enum TipoCuidadosPacienteEnum {
	AER("Aerosol"),
	CEN("Cuidados de Enfermagem"),
	MED("Medicações"),
	MOR("Medicações orais"),
	MSC("Medicações SC, IM"),
	MSO("Medicações SOS"),
	MTO("Medicações tópicas"),
	NUT("Nutrição (SND)"),
	SOR("Soroterapia, scalp,");
	
	private String label;
	
	TipoCuidadosPacienteEnum(String sexo){
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
