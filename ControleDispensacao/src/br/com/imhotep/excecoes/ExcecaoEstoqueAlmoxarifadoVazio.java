/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueAlmoxarifadoVazio extends ExcecaoPadrao {

	private static final long serialVersionUID = -8821325484438887186L;

	public ExcecaoEstoqueAlmoxarifadoVazio(){
		super.mensagem("Este material est‡ em falta", "");
	}
	
}
