/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoFusaoNaoRealizada extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoFusaoNaoRealizada(){
		super.mensagem("Não foi possível realizar a fusão", "Tente novamente");
	}
	
}
