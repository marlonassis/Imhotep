package br.com.Imhotep.enums;

/**
 * Classe que contém todos os papéis possíveis a um usuário
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
