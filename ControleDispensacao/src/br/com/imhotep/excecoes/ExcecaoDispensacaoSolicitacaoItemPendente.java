/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDispensacaoSolicitacaoItemPendente extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoDispensacaoSolicitacaoItemPendente(){
		super.mensagem("Existem itens que n‹o foram dispensados", "Verifique novamente se falta algum item");
	}
	
}
