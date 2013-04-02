/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoAcessoNaoAutorizado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoAcessoNaoAutorizado(){
	}
	
	public ExcecaoAcessoNaoAutorizado(String pagina){
		super.mensagem("Acesso não autorizado.", "O usuário tentou acessar "+pagina);
	}
	
}
