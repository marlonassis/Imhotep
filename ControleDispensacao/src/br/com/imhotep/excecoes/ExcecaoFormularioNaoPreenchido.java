/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoFormularioNaoPreenchido extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoFormularioNaoPreenchido(){
		
	}
	
	public ExcecaoFormularioNaoPreenchido(String mensagem){
		super.mensagem(mensagem, "");
	}
	
}
