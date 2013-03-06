package br.com.Imhotep.fluxo;

import java.util.Date;

import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.entidade.DispensacaoSimples;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoDispensacaoSimples extends PadraoFluxoTemp{
	
	public FluxoDispensacaoSimples() {
		super("Erro ao salvar a dispensação simplificada.", "Dispensação simplificada salva com sucesso.");
	}
	
	public boolean atualizarEstoque(DispensacaoSimples dispensacaoSimples) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		if(ativarControladoraEstoque(dataAtual, dispensacaoSimples.getMovimentoLivro())){
			enfileirarObjetosAtualizacaoEstoque(dispensacaoSimples);
			return super.processarFluxo();
		}
		return false;
	}
	
	private void enfileirarObjetosAtualizacaoEstoque(DispensacaoSimples dispensacaoSimples) {
		super.getObjetoAtualizar().put("Estoque", dispensacaoSimples.getMovimentoLivro().getEstoque());
		super.getObjetoSalvar().put("MovimentoLivro", dispensacaoSimples.getMovimentoLivro());
		super.getObjetoSalvar().put("DispensacaoSimples", dispensacaoSimples);
	}
	
	private boolean ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		ControleEstoque controleEstoque = new ControleEstoque();
		return controleEstoque.manipularEstoque(dataAtual, movimentoLivro);
	}
	
}
