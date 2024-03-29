package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthUnidadeSaudeEnum {
	
	CR("CENTRAL DE REGULACAO"),
	CRS("CENTRAL DE REGULACAO DE SERVICOS DE SAUDE"),
	CRMU("CENTRAL DE REGULACAO MEDICA DAS URGENCIAS"),
	CASF("CENTRO DE APOIO A SAUDE DA FAMILIA"),
	CAHH("CENTRO DE ATENCAO HEMOTERAPIA E OU HEMATOLOGICA"),
	CAP("CENTRO DE ATENCAO PSICOSSOCIAL"),
	CPNI("CENTRO DE PARTO NORMAL - ISOLADO"),
	CSUB("CENTRO DE SAUDE/UNIDADE BASICA"),
	CCE("CLINICA/CENTRO DE ESPECIALIDADE"),
	CI("CONSULTORIO ISOLADO"),
	CO("COOPERATIVA"),
	FA("FARMACIA"),
	HE("HOSPITAL ESPECIALIZADO"),
	HG("HOSPITAL GERAL"),
	HP("HOSPITAL"),
	HDI("HOSPITAL/DIA - ISOLADO"),
	LCSP("LABORATORIO CENTRAL DE SAUDE PUBLICA LACEN"),
	OO("OFICINA ORTOPEDICA"),
	PO("POLICLINICA"),
	PAS("POLO ACADEMIA DA SAUDE"),
	PS("POSTO DE SAUDE"),
	PA("PRONTO ATENDIMENTO"),
	PSE("PRONTO SOCORRO ESPECIALIZADO"),
	PSG("PRONTO SOCORRO GERAL"),
	SS("SECRETARIA DE SAUDE"),
	SADI("SERVICO DE ATENCAO DOMICILIAR ISOLADO(HOME CARE)"),
	TE("TELESSAUDE"),
	UADT("UNIDADE DE APOIO DIAGNOSE E TERAPIA (SADT ISOLADO)"),
	UASI("UNIDADE DE ATENCAO A SAUDE INDIGENA"),
	UVS("UNIDADE DE VIGILANCIA EM SAUDE"),
	UM("UNIDADE MISTA"),
	UMPH("UNIDADE MOVEL DE NIVEL PRE-HOSPITALAR NA AREA DE URGENCIA"),
	UMF("UNIDADE MOVEL FLUVIAL"),
	UMT("UNIDADE MOVEL TERRESTRE");

	private String label;
	
	TipoEhealthUnidadeSaudeEnum(String label){
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
