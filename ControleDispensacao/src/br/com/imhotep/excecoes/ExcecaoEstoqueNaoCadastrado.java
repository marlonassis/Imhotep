/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueNaoCadastrado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoEstoqueNaoCadastrado(){
		super.mensagem("Não foi possível cadastrar este estoque", "Tente novamente ou verifique se ele não está cadastrado");
	}
	
}
