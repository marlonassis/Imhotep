package br.com.Imhotep.fluxo;

import java.util.Date;

import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.entidade.AjusteEstoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoAjusteEstoque extends PadraoFluxoTemp{
	
	public FluxoAjusteEstoque() {
		super("Erro ao salvar o ajuste.", "Ajuste realizado com sucesso.");
	}
	
	public boolean atualizarEstoque(AjusteEstoque ajusteEstoque) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		if(ativarControladoraEstoque(dataAtual, ajusteEstoque.getMovimentoLivro())){
			enfileirarObjetosAtualizacao(ajusteEstoque);
			return super.processarFluxo();
		}
		return false;
	}
	
	public boolean salvarNovaEstoque(AjusteEstoque ajusteEstoque) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		if(ativarControladoraEstoque(dataAtual, ajusteEstoque.getMovimentoLivro())){
			enfileirarObjetosNovaEntrada(ajusteEstoque);
			return super.processarFluxo();
		}
		return false;
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

	private boolean ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		ControleEstoque controleEstoque = new ControleEstoque();
		return controleEstoque.manipularEstoque(dataAtual, movimentoLivro);
	}
	
}
