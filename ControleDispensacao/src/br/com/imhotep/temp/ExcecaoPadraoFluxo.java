/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.temp;

import br.com.imhotep.excecoes.ExcecaoPadrao;


/**
 * @author marlonassis
 *
 */
public class ExcecaoPadraoFluxo extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoPadraoFluxo(){
		
	}
	
	public ExcecaoPadraoFluxo(int quantidadeAtual){
		super.mensagem("Não há quantidade suficiente no estoque. O máximo disponível é de " + 
				String.valueOf(quantidadeAtual) + " unidade(s)", "");
	}
	
}
