/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoRecusadaSemJustificativa extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;

	public ExcecaoSolicitacaoRecusadaSemJustificativa(){
		super.mensagem("Informe o motivo da recusa", null);
	}
	
}
