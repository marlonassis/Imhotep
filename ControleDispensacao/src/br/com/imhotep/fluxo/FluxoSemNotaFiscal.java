package br.com.imhotep.fluxo;

import java.util.Date;

import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoSemNotaFiscal extends PadraoFluxoTemp{
	
	public FluxoSemNotaFiscal() {
		super("Erro ao salvar o estoque.", "Estoque salvo com sucesso.");
	}
	
	public void atualizarEstoque(Estoque estoque, MovimentoLivro movimentoLivro) throws ExcecaoPadraoFluxo, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosAtualizacaoEstoque(estoque, movimentoLivro);
		super.processarFluxo();
	}
	
	public void salvarNovoEstoque(Estoque estoque, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoPadraoFluxo, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosNovaEntrada(estoque, movimentoLivro);
		super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacaoEstoque(Estoque estoque, MovimentoLivro movimentoLivro) {
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}
	
	private void enfileirarObjetosNovaEntrada(Estoque estoque, MovimentoLivro movimentoLivro) {
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock {
		ControleEstoqueTemp controleEstoque = new ControleEstoqueTemp();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
