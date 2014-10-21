/**
 * Criado por M‡rlon Assis
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
		super.mensagem("N‹o h‡ quantidade suficiente no estoque. O m‡ximo dispon’vel Ž de " + 
				String.valueOf(quantidadeAtual) + " unidade(s)", "");
	}
	
}
