/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueReservado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoEstoqueReservado(){
		
	}
	
	public ExcecaoEstoqueReservado(int quantidadeReservada){
		super.mensagem("Este estoque possui "+quantidadeReservada+" unidade(s) reservada(s)", "");
	}
	
}
