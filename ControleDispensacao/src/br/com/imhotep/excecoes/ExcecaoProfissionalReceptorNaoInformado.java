/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoProfissionalReceptorNaoInformado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoProfissionalReceptorNaoInformado(){
		super.mensagem("Informe o profissional que recebeu a dispensação", null);
	}
	
}
