package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoImpressaoComprovanteConsultaEnum {
	C("Consulta MŽdica"),
	E("Exame"); 
	
	private String label;
	
	TipoImpressaoComprovanteConsultaEnum(String label){
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
