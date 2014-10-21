/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoCPFCNPJInvalido extends ExcecaoPadrao {

	private static final long serialVersionUID = 1942547442524982786L;

	public ExcecaoCPFCNPJInvalido(){
		super.mensagem("O CPF ou CNPJ informado n‹o Ž v‡lido", "");
	}
	
}
