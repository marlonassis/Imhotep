package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthNaturezaEnum {
	
	AIEP("ADMINISTRACAO INDIRETA - EMPRESA PUBLICA"),
	ADOO("ADMINISTRACO DIRETA DE OUTROS ORGAOS (MEC,MEx,Marinha,etc)"),
	EM("ECONOMIA MISTA"),
	AIOS("ADMINISTRACAO INDIRETA - ORGANIZACAO SOCIAL PUBLICA"),
	FP("FUNDACAO PRIVADA"),
	AIA("ADMINISTRACAO INDIRETA - AUTARQUIAS"),
	AIFP("ADMINISTRACAO INDIRETA - FUNDAÇÃO PUBLICA"),
	SIN("SINDICATO"),
	SSA("SERVIÇO SOCIAL AUTONOMO"),
	ADS("ADMINISTRACAO DIRETA DA SAUDE (MS,SES e SMS)"),
	COO("COOPERATIVA"),
	EP("EMPRESA PRIVADA"),
	EBFL("ENTIDADE BENEFICENTE SEM FINS LUCRATIVOS");
	
	private String label;
	
	TipoEhealthNaturezaEnum(String sexo){
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
