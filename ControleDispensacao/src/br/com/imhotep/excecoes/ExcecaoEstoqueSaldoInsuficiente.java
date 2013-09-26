/**
 * Criado por Márlon Assis
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
		super.mensagem("Não há quantidade suficiente no estoque. O máximo disponível é de " + quantidadeAtual + " unidade(s)", "");
	}
	
	public ExcecaoEstoqueSaldoInsuficiente(int quantidadeAtual){
		super.mensagem("Não há quantidade suficiente no estoque. O máximo disponível é de " + quantidadeAtual + " unidade(s)", "");
	}
	
}
