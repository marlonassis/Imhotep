/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueVazio extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoEstoqueVazio(){
		super.mensagem("Este medicamento est‡ em falta", "");
	}
	
}
