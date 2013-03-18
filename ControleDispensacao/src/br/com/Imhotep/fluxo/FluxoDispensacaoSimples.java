package br.com.Imhotep.fluxo;

import java.util.Date;

import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.entidade.DispensacaoSimples;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class FluxoDispensacaoSimples extends PadraoFluxoTemp{
	
	public FluxoDispensacaoSimples() {
		super("Erro ao salvar a dispensação simplificada.", "Dispensação simplificada salva com sucesso.");
	}
	
	public void atualizarEstoque(DispensacaoSimples dispensacaoSimples) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, dispensacaoSimples.getMovimentoLivro());
		enfileirarObjetosAtualizacaoEstoque(dispensacaoSimples);
		super.processarFluxo();
	}
	
	private void enfileirarObjetosAtualizacaoEstoque(DispensacaoSimples dispensacaoSimples) {
		super.getObjetoAtualizar().put("Estoque", dispensacaoSimples.getMovimentoLivro().getEstoque());
		super.getObjetoSalvar().put("MovimentoLivro", dispensacaoSimples.getMovimentoLivro());
		super.getObjetoSalvar().put("DispensacaoSimples", dispensacaoSimples);
	}
	
	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException{
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
