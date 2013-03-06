package br.com.Imhotep.fluxo;

import java.util.Date;

import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.entidade.Doacao;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoDoacao extends PadraoFluxoTemp{
	
	public FluxoDoacao() {
		super("Erro ao salvar a doação.", "Doação salva com sucesso.");
	}
	
	public boolean atualizarDoacao(Doacao doacao) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, doacao.getMovimentoLivro());
		enfileirarObjetosAtualizacao(doacao);
		return super.processarFluxo();
	}
	
	public boolean salvarNovaDoacao(Doacao doacao) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, doacao.getMovimentoLivro());
		enfileirarObjetosNovaEntrada(doacao);
		return super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacao(Doacao doacao) {
		super.getObjetoAtualizar().put("Estoque", doacao.getMovimentoLivro().getEstoque());
		super.getObjetoSalvar().put("MovimentoLivro", doacao.getMovimentoLivro());
		super.getObjetoSalvar().put("Doacao", doacao);
	}
	
	private void enfileirarObjetosNovaEntrada(Doacao doacao) {
		super.getObjetoSalvar().put("Estoque", doacao.getMovimentoLivro().getEstoque());
		super.getObjetoSalvar().put("MovimentoLivro", doacao.getMovimentoLivro());
		super.getObjetoSalvar().put("Doacao", doacao);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.manipularEstoque(dataAtual, movimentoLivro);
	}
	
}
