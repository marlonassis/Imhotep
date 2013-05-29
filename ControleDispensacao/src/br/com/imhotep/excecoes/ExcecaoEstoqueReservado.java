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
		super();
	}
	
	public ExcecaoEstoqueReservado(long quantidadeReservada, long sobra){
		super.mensagem("Este estoque possui "+sobra+" unidade(s), pois "+quantidadeReservada+" unidade(s) fora(m) reservada(s)", "");
	}
	
	public ExcecaoEstoqueReservado(int quantidadeReservada, int sobra){
		super.mensagem("Este estoque possui "+sobra+" unidade(s), pois "+quantidadeReservada+" unidade(s) foram reservada(s)", "");
	}
	
}
