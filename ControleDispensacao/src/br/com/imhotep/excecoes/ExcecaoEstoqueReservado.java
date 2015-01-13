/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueReservado extends ExcecaoPadrao {

	private static final long serialVersionUID = -7864856216170986066L;

	public ExcecaoEstoqueReservado(){
		super();
	}
	
	public ExcecaoEstoqueReservado(Double quantidadeReservada, Double sobra){
		super.mensagem("Este estoque possui "+sobra+" unidade(s), pois "+quantidadeReservada+" unidade(s) fora(m) reservada(s)", "");
	}
	
	public ExcecaoEstoqueReservado(int quantidadeReservada, int sobra){
		super.mensagem("Este estoque possui "+sobra+" unidade(s), pois "+quantidadeReservada+" unidade(s) foram reservada(s)", "");
	}
	
}
