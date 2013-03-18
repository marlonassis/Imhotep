package br.com.Imhotep.fluxo;

import java.util.Date;

import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.entidade.AjusteEstoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
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
	
	public void atualizarEstoque(AjusteEstoque ajusteEstoque) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, ajusteEstoque.getMovimentoLivro());
		enfileirarObjetosAtualizacao(ajusteEstoque);
		super.processarFluxo();
	}
	
	public void salvarNovoEstoque(AjusteEstoque ajusteEstoque) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, ajusteEstoque.getMovimentoLivro());
		enfileirarObjetosNovaEntrada(ajusteEstoque);
		super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacao(AjusteEstoque ajusteEstoque) {
		super.getObjetoAtualizar().put("Estoque", ajusteEstoque.getMovimentoLivro().getEstoque());
		super.getObjetoSalvar().put("MovimentoLivro", ajusteEstoque.getMovimentoLivro());
		super.getObjetoSalvar().put("AjusteEstoque", ajusteEstoque);
	}
	
	private void enfileirarObjetosNovaEntrada(AjusteEstoque ajusteEstoque) {
		super.getObjetoSalvar().put("Estoque", ajusteEstoque.getMovimentoLivro().getEstoque());
		super.getObjetoSalvar().put("MovimentoLivro", ajusteEstoque.getMovimentoLivro());
		super.getObjetoSalvar().put("AjusteEstoque", ajusteEstoque);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
