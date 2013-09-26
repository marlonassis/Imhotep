/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoMovimentoLivroNaoCadastrado extends ExcecaoPadrao {
	private static final long serialVersionUID = -5097513914364428582L;

	public ExcecaoMovimentoLivroNaoCadastrado(){
		super.mensagem("Erro ao cadastrar o movimento", "");
	}
	
}
