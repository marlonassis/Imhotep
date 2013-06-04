/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDispensacaoSolicitacaoItemPendente extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoDispensacaoSolicitacaoItemPendente(){
		super.mensagem("Existem itens que não foram dispensados", "Verifique novamente se falta algum item");
	}
	
}
