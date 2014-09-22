package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthNaturezaEnum {
	
	ADS("ADMINISTRACAO DIRETA DA SAUDE (MS,SES e SMS)"),
	ADOO("ADMINISTRACO DIRETA DE OUTROS ORGAOS (MEC,MEx,Marinha,etc)"),
	AIA("ADMINISTRACAO INDIRETA - AUTARQUIAS"),
	AIEP("ADMINISTRACAO INDIRETA - EMPRESA PUBLICA"),
	AIFP("ADMINISTRACAO INDIRETA - FUNDA��O PUBLICA"),
	AIOS("ADMINISTRACAO INDIRETA - ORGANIZACAO SOCIAL PUBLICA"),
	COO("COOPERATIVA"),
	EM("ECONOMIA MISTA"),
	EP("EMPRESA PRIVADA"),
	EBFL("ENTIDADE BENEFICENTE SEM FINS LUCRATIVOS"),
	FP("FUNDACAO PRIVADA"),
	ONG("ORGANIZA��O N�O GOVERNAMENTAL"),
	PU("P�BLICO"),
	SIN("SINDICATO"),
	SSA("SERVI�O SOCIAL AUTONOMO");
	
	private String label;
	
	TipoEhealthNaturezaEnum(String label){
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
