/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSaldoInsuficienteEstoque extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoSaldoInsuficienteEstoque(){
		
	}
	
	public ExcecaoSaldoInsuficienteEstoque(int quantidadeAtual){
		super.mensagem("Não há quantidade suficiente no estoque. O máximo disponível é de " + 
				String.valueOf(quantidadeAtual) + " unidade(s)", "");
	}
	
}
