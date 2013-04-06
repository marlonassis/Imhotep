package br.com.imhotep.fluxo;

import java.util.Date;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.Doacao;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoDoacao extends PadraoFluxoTemp{
	
	public FluxoDoacao() {
		super("Erro ao salvar a doação.", "Doação salva com sucesso.");
	}
	
	public void atualizarDoacao(Doacao doacao) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, doacao.getMovimentoLivro());
		enfileirarObjetosAtualizacao(doacao);
		super.processarFluxo();
	}
	
	public void salvarNovaDoacao(Doacao doacao) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, doacao.getMovimentoLivro());
		enfileirarObjetosNovaEntrada(doacao);
		super.processarFluxo();
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

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
