package br.com.ControleDispensacao.enums;

public enum TipoCuidadosPacienteEnum {
	NUT("Nutrição (SND)"),
	SOR("Soroterapia, scalp,"),
	MED("Medicações"),
	MSC("Medicações SC, IM"),
	MOR("Medicações orais"),
	AER("Aerosol"),
	MTO("Medicações tópicas"),
	MSO("Medicações SOS"),
	CEN("Cuidados de Enfermagem");
	
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
