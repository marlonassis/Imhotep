package br.com.Imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEstadoAtualRecursoEnum {
	A("Aberto"),
	F("Fechado"), 
	N("Não Resolvido");
	
	private String label;
	
	TipoEstadoAtualRecursoEnum(String sexo){
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
