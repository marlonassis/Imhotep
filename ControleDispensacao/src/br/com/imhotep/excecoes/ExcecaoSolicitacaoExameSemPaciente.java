/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoExameSemPaciente extends ExcecaoPadrao {


	/**
	 * 
	 */
	private static final long serialVersionUID = 675077992874789053L;

	public ExcecaoSolicitacaoExameSemPaciente(){
		super();
		super.mensagem("Voc� precisa informar um ou mais pacientes para fazer a solicita��o", null);
	}
	
}
