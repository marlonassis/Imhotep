package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoUnidadeSaudeEnum {
	PS("Posto de Saúde"), 
	CS("Centro de Saúde/Unidade Básica de Saúde"), 
	PC("Policlínica"), 
	HG("Hospital Geral"), 
	HE("Hospital Especializado"), 
	UM("Unidade Mista"), 
	PG("Pronto Socorro Geral"), 
	PE("Pronto Socorro Especializado"), 
	CI("Consultório Isolado"), 
	UMF("Unidade Móvel Fluvial"), 
	CAE("Clínica Especializada/Amb. Especializado"), 
	US("Unidade de Serviço de Apoio de Diagnose e Terapia"), 
	UMT("Unidade Móvel Terrestre"), 
	UMP("Unidade Móvel de Nível Pré-hospitalar na Área de Urgência e Emergência"), 
	FA("Farmácia"), 
	UVS("Unidade de Vigilância em Saúde"), 
	CO("Cooperativa"), 
	CPN("Centro de Parto Normal Isolado"), 
	HI("Hospital /Dia- Isolado"), 
	CRS("Central de Regulação de Serviços de Saúde"), 
	LCS("Laboratório Central de Saúde Pública - LACEN"), 
	SS("Secretaria de Saúde");
	
	private String label;
	
	TipoUnidadeSaudeEnum(String sexo){
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
