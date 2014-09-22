package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoSanguineoEnum {
	ON("O−"),
	OP("O+"),
	AN("A−"),
	AP("A+"),
	BN("B−"),
	BP("B+"),
	ABN("AB−"),
	ABP("AB+"),
	NI("N�o Informado");
	
	TipoSanguineoEnum(String papel){
		label = papel;
	}
	
	private String label;
	
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
