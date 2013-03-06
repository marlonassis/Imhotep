package br.com.Imhotep.controle;

import java.util.Date;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.enums.TipoOperacaoEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.consulta.raiz.MovimentoLivroConsultaRaiz;

public class ControleEstoque {
	
	public void manipularEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		prepararEstoque(dataAtual, movimentoLivro.getEstoque(), movimentoLivro.getQuantidadeMovimentacao(), movimentoLivro.getTipoMovimento());
		prepararMovimentoLivro(dataAtual, movimentoLivro);
	}
	
	private void prepararMovimentoLivro(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		int saldoAtualMaterial = new MovimentoLivroConsultaRaiz().saldoAtualMaterial(movimentoLivro.getEstoque().getMaterial());
		movimentoLivro.setSaldoAnterior(saldoAtualMaterial);
		movimentoLivro.setDataMovimento(dataAtual);
		movimentoLivro.setUnidadeCadastrante(autenticador.getUnidadeAtual());
		movimentoLivro.setUsuarioMovimentacao(autenticador.getUsuarioAtual());
	}

	private void prepararEstoque(Date dataAtual, Estoque estoque, int quantidadeMovimentada, TipoMovimento tipoMovimento) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		estoque.setDataInclusao(dataAtual);
		estoque.setUnidade(autenticador.getUnidadeAtual());
		estoque.setUsuarioInclusao(autenticador.getUsuarioAtual());
		int saldoAtualizado = 0;
		int saldoAtual = estoque.getQuantidadeAtual();
		if(tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.Entrada)){
			saldoAtualizado = saldoAtual + quantidadeMovimentada;
		}else{
			saldoAtualizado = saldoAtual - quantidadeMovimentada;
		}
		estoque.setQuantidadeAtual(saldoAtualizado);
	}
	
}
