package br.com.imhotep.controle;

import java.util.Calendar;
import java.util.Date;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoMovimentoLivroNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.linhaMecanica.AtualizadorEstoqueAlmoxarifadoLM;
import br.com.imhotep.raiz.MovimentoLivroAlmoxarifadoRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;

public class ControleEstoqueAlmoxarifado {

	public MovimentoLivroAlmoxarifado validarEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueUnLock, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoMovimentoLivroNaoCadastrado{
		validaQuantidadeMovimentada(quantidadeMovimentada);
		if(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado() > 0)
			lockEstoque(estoqueAlmoxarifado);
		int quantidadeAtual = estoqueAlmoxarifado.getQuantidadeAtual();
		validaEstoque(estoqueAlmoxarifado, quantidadeMovimentada, tipoMovimento);
		atualizarEstoque(estoqueAlmoxarifado, quantidadeMovimentada, tipoMovimento);
		return gerarMovimento(estoqueAlmoxarifado, quantidadeMovimentada, tipoMovimento, quantidadeAtual);
	}

	private MovimentoLivroAlmoxarifado gerarMovimento(EstoqueAlmoxarifado estoqueAlmoxarifado, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento, int quantidadeAtual) throws ExcecaoMovimentoLivroNaoCadastrado, ExcecaoProfissionalLogado {
		MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado = new MovimentoLivroAlmoxarifadoRaiz().criarNovoMovimento(estoqueAlmoxarifado, quantidadeMovimentada, quantidadeAtual, tipoMovimento);
		if(movimentoLivroAlmoxarifado == null)
			throw new ExcecaoMovimentoLivroNaoCadastrado();
		else
			return movimentoLivroAlmoxarifado;
	}

	private void atualizarEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado {
		estoqueAlmoxarifado.setDataInclusao(new Date());
		estoqueAlmoxarifado.setProfissionalInclusao(Autenticador.getProfissionalLogado());
		if(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado() == 0){
			estoqueAlmoxarifado.setLock(true);
			estoqueAlmoxarifado.setDataLock(new Date());
			estoqueAlmoxarifado.setQuantidadeAtual(quantidadeMovimentada);
		}else{
			int quantidadeAtual = estoqueAlmoxarifado.getQuantidadeAtual();
			if(tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E)){
				estoqueAlmoxarifado.setQuantidadeAtual(quantidadeAtual + quantidadeMovimentada);
			}else{
				estoqueAlmoxarifado.setQuantidadeAtual(quantidadeAtual - quantidadeMovimentada);
			}
		}
		addEstoqueFluxo(estoqueAlmoxarifado);
	}

	private void addEstoqueFluxo(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		if(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado() > 0)
			PadraoFluxoTemp.getObjetoAtualizar().put("estoqueAlmoxarifado", estoqueAlmoxarifado);
		else
			PadraoFluxoTemp.getObjetoSalvar().put("estoqueAlmoxarifado", estoqueAlmoxarifado);
	}
	
	private void validaQuantidadeMovimentada(int quantidadeMovimentada) throws ExcecaoQuantidadeZero {
		if(quantidadeMovimentada == 0)
			throw new ExcecaoQuantidadeZero();
	}

	private void validaEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido {
		estoqueVencido(estoqueAlmoxarifado.getDataValidade());
		estoqueBloqueado(estoqueAlmoxarifado.getBloqueado());
		if(!tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E)){
			estoqueVazio(estoqueAlmoxarifado.getQuantidadeAtual());
			estoqueInsuficiente(estoqueAlmoxarifado.getQuantidadeAtual(), quantidadeMovimentada);
		}
	}

	private void estoqueInsuficiente(int quantidadeAtual, int quantidadeMovimentada) throws ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado {
		if(quantidadeAtual < quantidadeMovimentada)
			throw new ExcecaoEstoqueSaldoInsuficiente(quantidadeAtual);
		else{
			long quantidadeReservada = 0;
			long quantidadeVirtual = quantidadeAtual - quantidadeReservada;
			if(quantidadeVirtual < quantidadeMovimentada)
				throw new ExcecaoEstoqueReservado(quantidadeReservada, quantidadeVirtual);
		}
			
	}

	private void estoqueVazio(int quantidadeAtual) throws ExcecaoEstoqueVazio {
		if(quantidadeAtual == 0)
			throw new ExcecaoEstoqueVazio();
	}

	private void estoqueBloqueado(boolean bloqueado) throws ExcecaoEstoqueBloqueado {
		if(bloqueado)
			throw new ExcecaoEstoqueBloqueado();
	}

	private void estoqueVencido(Date dataValidade) throws ExcecaoEstoqueVencido {
		Calendar dataAtual = new Utilitarios().zerarHoraDataAtual();
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.setTime(dataValidade);
		if(dataAtual.after(dataVencimento))
			throw new ExcecaoEstoqueVencido();
	}

	public void unLockEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado) throws ExcecaoEstoqueUnLock {
		new AtualizadorEstoqueAlmoxarifadoLM().unLockEstoque(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado());
	}

	private void lockEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado) throws ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock {
		new AtualizadorEstoqueAlmoxarifadoLM().lockEstoque(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado(), 0);
	}
	
}
