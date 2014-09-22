package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoUnidadeSaudeEnum {
	PS("Posto de Sa�de"), 
	CS("Centro de Sa�de/Unidade B�sica de Sa�de"), 
	PC("Policl�nica"), 
	HG("Hospital Geral"), 
	HE("Hospital Especializado"), 
	UM("Unidade Mista"), 
	PG("Pronto Socorro Geral"), 
	PE("Pronto Socorro Especializado"), 
	CI("Consult�rio Isolado"), 
	UMF("Unidade M�vel Fluvial"), 
	CAE("Cl�nica Especializada/Amb. Especializado"), 
	US("Unidade de Servi�o de Apoio de Diagnose e Terapia"), 
	UMT("Unidade M�vel Terrestre"), 
	UMP("Unidade M�vel de N�vel Pr�-hospitalar na �rea de Urg�ncia e Emerg�ncia"), 
	FA("Farm�cia"), 
	UVS("Unidade de Vigil�ncia em Sa�de"), 
	CO("Cooperativa"), 
	CPN("Centro de Parto Normal Isolado"), 
	HI("Hospital /Dia- Isolado"), 
	CRS("Central de Regula��o de Servi�os de Sa�de"), 
	LCS("Laborat�rio Central de Sa�de P�blica - LACEN"), 
	SS("Secretaria de Sa�de");
	
	private String label;
	
	TipoUnidadeSaudeEnum(String label){
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
