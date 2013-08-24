package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEhealthRedeSocialEnum {
	
	BS("BlogSpot"),
	CM("Classmates"),
	FA("Facebook"),
	FX("Flixster"),
	FS("Foursquare"),
	GP("Google+"),
	IE("Imeem"),
	LI("Linkedin"),
	LJ("Live Journal"),
	MS("MySpace"),
	MB("My Year Book"),
	OK("Orkut"),
	TG("Tagged"),
	TW("Twitter"),
	YT("YouTube");
	
	private String label;
	
	TipoEhealthRedeSocialEnum(String label){
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
