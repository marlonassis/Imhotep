/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueSaldoInsuficiente extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoEstoqueSaldoInsuficiente(){
		
	}
	
	public ExcecaoEstoqueSaldoInsuficiente(long quantidadeAtual){
		super.mensagem("N‹o h‡ quantidade suficiente no estoque. O m‡ximo dispon’vel Ž de " + quantidadeAtual + " unidade(s)", "");
	}
	
	public ExcecaoEstoqueSaldoInsuficiente(int quantidadeAtual){
		super.mensagem("N‹o h‡ quantidade suficiente no estoque. O m‡ximo dispon’vel Ž de " + quantidadeAtual + " unidade(s)", "");
	}
	
}
