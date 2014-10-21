package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthTipoTecnologiaEnum {
	
	AP("ASP"),
	CS("CSS"),
	FL("Flash"),
	H("HTML"),
	JS("JavaScript"),
	JA("Joomla"),
	JQ("JQuery"),
	JF("JSF"),
	JP("JSP"),
	PH("PHP"),
	PF("PrimeFaces"),
	SP("Spring"),
	RF("RichFaces"),
	SW("Shockwave"),
	WD("Wordpress");
	
	private String label;
	
	TipoEhealthTipoTecnologiaEnum(String label){
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
