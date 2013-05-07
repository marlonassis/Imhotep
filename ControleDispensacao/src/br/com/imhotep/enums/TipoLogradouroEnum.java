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
	PÇ("Praça"),
	QUA("Quadra"),
	ROD("Rodovia"),
	R("Rua"),
	TV("Travessa"),
	VI("Vila");
	
	private String label;
	
	TipoLogradouroEnum(String sexo){
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
