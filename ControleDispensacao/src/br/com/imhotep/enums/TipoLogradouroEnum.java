package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoLogradouroEnum {
	AV("Avenida"),
	B("Beco"),
	CAM("Caminho"),
	EST("Estrada"),
	LAD("Ladeira"),
	LG("Largo"),
	LT("Lote"),
	OT("Outro"),
	PC("Praça"),
	QUA("Quadra"),
	ROD("Rodovia"),
	R("Rua"),
	TV("Travessa"),
	VI("Vila");
	
	private String label;
	
	TipoLogradouroEnum(String label){
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
