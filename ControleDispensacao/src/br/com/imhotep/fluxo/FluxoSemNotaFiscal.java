package br.com.imhotep.fluxo;

import java.util.Date;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoSemNotaFiscal extends PadraoFluxoTemp{
	
	public FluxoSemNotaFiscal() {
		super("Erro ao salvar o estoque.", "Estoque salvo com sucesso.");
	}
	
	public void atualizarEstoque(Estoque estoque, MovimentoLivro movimentoLivro) throws ExcecaoPadraoFluxo, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosAtualizacaoEstoque(estoque, movimentoLivro);
		super.processarFluxo();
	}
	
	public void salvarNovoEstoque(Estoque estoque, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, ExcecaoPadraoFluxo{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosNovaEntrada(estoque, movimentoLivro);
		super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacaoEstoque(Estoque estoque, MovimentoLivro movimentoLivro) {
		super.getObjetoAtualizar().put("Estoque", estoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}
	
	private void enfileirarObjetosNovaEntrada(Estoque estoque, MovimentoLivro movimentoLivro) {
		super.getObjetoSalvar().put("Estoque", estoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado {
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
