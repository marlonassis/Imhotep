package br.com.Imhotep.fluxo;

import java.util.Date;

import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.NotaFiscalEstoque;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoNotaFiscalEstoque extends PadraoFluxoTemp{
	
	public FluxoNotaFiscalEstoque() {
		super("Erro ao salvar a nota fiscal.", "Nota fiscal salva com sucesso.");
	}

	private void prepararNotaFiscalEstoque(NotaFiscalEstoque notaFiscalEstoque, Date dataAtual) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Autenticador autenticador = Autenticador.getInstancia();
		notaFiscalEstoque.setProfissionalInsercao(autenticador.getProfissionalAtual());
		notaFiscalEstoque.setDataInsercao(dataAtual);
	}
	
	public boolean atualizarNotaFiscalEstoque(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		prepararNotaFiscalEstoque(notaFiscalEstoque, dataAtual);
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosAtualizacaoEstoque(notaFiscalEstoque, movimentoLivro);
		return super.processarFluxo();
	}
	
	public boolean salvarNovaNotaFiscalEstoque(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		prepararNotaFiscalEstoque(notaFiscalEstoque, dataAtual);
		ativarControladoraEstoque(dataAtual, movimentoLivro);
		enfileirarObjetosNovaEntrada(notaFiscalEstoque, movimentoLivro);
		return super.processarFluxo();
	}

	private void enfileirarObjetosAtualizacaoEstoque(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) {
		super.getObjetoAtualizar().put("Estoque", notaFiscalEstoque.getEstoque());
		super.getObjetoSalvar().put("NotaFiscalEstoque", notaFiscalEstoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}
	
	private void enfileirarObjetosNovaEntrada(NotaFiscalEstoque notaFiscalEstoque, MovimentoLivro movimentoLivro) {
		super.getObjetoSalvar().put("Estoque", notaFiscalEstoque.getEstoque());
		super.getObjetoSalvar().put("NotaFiscalEstoque", notaFiscalEstoque);
		super.getObjetoSalvar().put("MovimentoLivro", movimentoLivro);
	}

	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.manipularEstoque(dataAtual, movimentoLivro);
	}
	
}
