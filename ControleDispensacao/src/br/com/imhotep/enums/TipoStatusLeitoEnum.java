package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoStatusLeitoEnum {
	A("Ativo"),
	I("Inativo"),
	E("Exclu’do");
	
	private String label;
	
	TipoStatusLeitoEnum(String label){
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
