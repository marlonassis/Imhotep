package br.com.imhotep.fluxo;

import java.util.Date;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.AjusteEstoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoAjusteEstoque extends PadraoFluxoTemp{
	
	public FluxoAjusteEstoque() {
		super("Erro ao salvar o ajuste.", "Ajuste realizado com sucesso.");
	}
	
	public void atualizarEstoque(AjusteEstoque ajusteEstoque) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, ajusteEstoque.getMovimentoLivro());
		enfileirarObjetosAtualizacao(ajusteEstoque);
		super.processarFluxo();
	}
	
	public void salvarNovoEstoque(AjusteEstoque ajusteEstoque) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, ajusteEstoque.getMovimentoLivro());
		enfileirarObjetosNovaEntrada(ajusteEstoque);
		super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacao(AjusteEstoque ajusteEstoque) {
		super.getObjetoSalvar().put("MovimentoLivro", ajusteEstoque.getMovimentoLivro());
		super.getObjetoSalvar().put("AjusteEstoque", ajusteEstoque);
	}
	
	private void enfileirarObjetosNovaEntrada(AjusteEstoque ajusteEstoque) {
		super.getObjetoSalvar().put("MovimentoLivro", ajusteEstoque.getMovimentoLivro());
		super.getObjetoSalvar().put("AjusteEstoque", ajusteEstoque);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
