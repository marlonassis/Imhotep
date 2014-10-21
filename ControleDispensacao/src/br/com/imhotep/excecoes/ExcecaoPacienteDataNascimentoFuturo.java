/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoPacienteDataNascimentoFuturo extends ExcecaoPadrao {
	private static final long serialVersionUID = 6433409188688170983L;

	public ExcecaoPacienteDataNascimentoFuturo(){
		super();
		super.mensagem("A data de nascimento n‹o pode ser maior que a data de hoje", "");
	}
	
}
