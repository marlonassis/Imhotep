package br.com.imhotep.enums;

/**
 * Classe que cont�m todos os pap�is poss�veis a um usu�rio
 * @author marlonassis
 */

public enum UsuarioPapeisEnum {
	ENGENHARIA ("engenharia"), 
	USUARIO("usuario"), 
	USUARIO_PARTICIPANTE("usuarioparticipante"),
	VISITANTE("visitante");
	
	UsuarioPapeisEnum(String papel){
		label = papel;
	}
	
	private String label;
	
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
}
