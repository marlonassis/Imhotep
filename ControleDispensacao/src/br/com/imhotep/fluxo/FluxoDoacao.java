package br.com.imhotep.fluxo;

import java.util.Date;

import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.entidade.Doacao;
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

public class FluxoDoacao extends PadraoFluxoTemp{
	
	public FluxoDoacao() {
		super("Erro ao salvar a doação.", "Doação salva com sucesso.");
	}
	
	public void atualizarDoacao(Doacao doacao) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, doacao.getMovimentoLivro());
		enfileirarObjetosAtualizacao(doacao);
		super.processarFluxo();
	}
	
	public void salvarNovaDoacao(Doacao doacao) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, doacao.getMovimentoLivro());
		enfileirarObjetosNovaEntrada(doacao);
		super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacao(Doacao doacao) {
		super.getObjetoSalvar().put("MovimentoLivro", doacao.getMovimentoLivro());
		super.getObjetoSalvar().put("Doacao", doacao);
	}
	
	private void enfileirarObjetosNovaEntrada(Doacao doacao) {
		super.getObjetoSalvar().put("MovimentoLivro", doacao.getMovimentoLivro());
		super.getObjetoSalvar().put("Doacao", doacao);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock{
		ControleEstoqueTemp controleEstoque = new ControleEstoqueTemp();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
