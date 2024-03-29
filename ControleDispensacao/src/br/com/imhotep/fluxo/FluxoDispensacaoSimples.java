package br.com.imhotep.fluxo;

import java.util.Date;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.DispensacaoSimples;
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

public class FluxoDispensacaoSimples extends PadraoFluxoTemp{
	
	public FluxoDispensacaoSimples() {
		super("Erro ao salvar a dispensa��o simplificada.", "Dispensa��o simplificada salva com sucesso.");
	}
	
	public FluxoDispensacaoSimples(boolean exibirMensagem) {
		super("Erro ao salvar a dispensa��o simplificada.", "Dispensa��o simplificada salva com sucesso.");
		if(!exibirMensagem){
			setExibeMensagemAtualizacao(false);
			setExibeMensagemDelecao(false);
			setExibeMensagemInsercao(false);
		}
	}
	
	public void atualizarEstoque(DispensacaoSimples dispensacaoSimples) throws ExcecaoPadraoFluxo, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock{
		Date dataAtual = new Date();
		ativarControladoraEstoque(dataAtual, dispensacaoSimples.getMovimentoLivro());
		enfileirarObjetosAtualizacaoEstoque(dispensacaoSimples);
		super.processarFluxo();
	}
	
	private void enfileirarObjetosAtualizacaoEstoque(DispensacaoSimples dispensacaoSimples) {
		super.getObjetoSalvar().put("MovimentoLivro", dispensacaoSimples.getMovimentoLivro());
		super.getObjetoSalvar().put("DispensacaoSimples", dispensacaoSimples);
	}
	
	private void ativarControladoraEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock{
		ControleEstoque controleEstoque = new ControleEstoque();
		controleEstoque.liberarAjuste(dataAtual, movimentoLivro);
	}
	
}
