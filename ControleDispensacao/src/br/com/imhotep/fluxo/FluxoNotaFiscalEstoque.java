package br.com.imhotep.fluxo;

import java.util.Date;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoNotaFiscalEstoque extends PadraoFluxoTemp{
	
	public FluxoNotaFiscalEstoque() {
		super("Erro ao salvar a nota fiscal.", "Nota fiscal salva com sucesso.");
	}

	private void prepararNotaFiscalEstoque(NotaFiscalEstoque notaFiscalEstoque, Date dataAtual) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		notaFiscalEstoque.setProfissionalInsercao(autenticador.getProfissionalAtual());
		notaFiscalEstoque.setDataInsercao(dataAtual);
	}
	
	public void atualizarNotaFiscalEstoque(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoPadraoFluxo, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		Date dataAtual = new Date();
		prepararNotaFiscalEstoque(notaFiscalEstoque, dataAtual);
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosAtualizacaoEstoque(notaFiscalEstoque, movimentoLivro);
		super.processarFluxo();
	}
	
	public void salvarNovaNotaFiscalEstoque(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		Date dataAtual = new Date();
		prepararNotaFiscalEstoque(notaFiscalEstoque, dataAtual);
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosNovaEntrada(notaFiscalEstoque, movimentoLivro);
		super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacaoEstoque(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) {
		super.getObjetoSalvar().put("NotaFiscalEstoque", notaFiscalEstoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}
	
	private void enfileirarObjetosNovaEntrada(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) {
		super.getObjetoSalvar().put("NotaFiscalEstoque", notaFiscalEstoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
