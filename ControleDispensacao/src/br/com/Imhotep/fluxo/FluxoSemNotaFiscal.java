package br.com.Imhotep.fluxo;

import java.util.Date;

import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoSemNotaFiscal extends PadraoFluxoTemp{
	
	public FluxoSemNotaFiscal() {
		super("Erro ao salvar o estoque.", "Estoque salvo com sucesso.");
	}
	
	public boolean atualizarEstoque(Estoque estoque, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosAtualizacaoEstoque(estoque, movimentoLivro);
		return super.processarFluxo();
	}
	
	public boolean salvarNovoEstoque(Estoque estoque, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosNovaEntrada(estoque, movimentoLivro);
		return super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacaoEstoque(Estoque estoque, MovimentoLivro movimentoLivro) {
		super.getObjetoAtualizar().put("Estoque", estoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}
	
	private void enfileirarObjetosNovaEntrada(Estoque estoque, MovimentoLivro movimentoLivro) {
		super.getObjetoSalvar().put("Estoque", estoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.manipularEstoque(dataAtual, movimentoLivro);
	}
	
}
