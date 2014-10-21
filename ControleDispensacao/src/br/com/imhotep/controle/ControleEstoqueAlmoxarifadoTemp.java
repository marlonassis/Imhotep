package br.com.imhotep.controle;

import java.util.Calendar;
import java.util.Date;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.raiz.SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidade;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueAlmoxarifadoVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoMovimentoLivroNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorEstoqueAlmoxarifadoLM;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

public class ControleEstoqueAlmoxarifadoTemp extends PadraoRaiz<EstoqueAlmoxarifado>{
	
	public void entradaSemNota(MovimentoLivroAlmoxarifado movimento) throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueUnLock, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoMovimentoLivroNaoCadastrado, ExcecaoPadraoFluxo{
		PadraoFluxoTemp.limparFluxo();
		int quantidadeAtual = new EstoqueAlmoxarifadoConsultaRaiz().consultarQuantidadeEstoque(movimento.getEstoqueAlmoxarifado());
		movimento.setQuantidadeAtual(quantidadeAtual);
		liberarAjusteEstoqueAlmoxarifado(movimento.getEstoqueAlmoxarifado(), 
										 movimento.getQuantidadeMovimentacao(), 
										 movimento.getTipoMovimentoAlmoxarifado());
		PadraoFluxoTemp.getObjetoSalvar().put("Movimento", movimento);
		PadraoFluxoTemp.finalizarFluxo();
	}
	
	public void liberarSolicitacao(int quantidadeMovimentada, MaterialAlmoxarifado materialAlmoxarifado, SolicitacaoMaterialAlmoxarifadoUnidade sma) throws ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoQuantidadeZero, ExcecaoEstoqueAlmoxarifadoVazio{
		validaQuantidadeMovimentada(quantidadeMovimentada);
		Long quantidadeTotal = new EstoqueAlmoxarifadoConsultaRaiz().totalEmEstoqueValido(materialAlmoxarifado);
		estoqueVazio(quantidadeTotal == null ? 0 : quantidadeTotal.intValue());
		Long quantidadeReservada = new SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz().totalReservardo(materialAlmoxarifado, sma);
		estoqueInsuficiente(quantidadeTotal == null ? 0 : quantidadeTotal.intValue(), quantidadeMovimentada, quantidadeReservada == null ? 0 : quantidadeReservada.intValue());
	}
	
	public void liberarDispensacao(MovimentoLivroAlmoxarifado mla, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueUnLock, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoMovimentoLivroNaoCadastrado{
		validaQuantidadeMovimentada(quantidadeMovimentada);
		lockEstoque(mla.getEstoqueAlmoxarifado());
		estoqueVencido(mla.getEstoqueAlmoxarifado().getDataValidade());
		estoqueBloqueado(mla.getEstoqueAlmoxarifado().getBloqueado());
		estoqueVazio(mla.getEstoqueAlmoxarifado().getQuantidadeAtual());
		estoqueInsuficiente(mla.getEstoqueAlmoxarifado().getQuantidadeAtual(), quantidadeMovimentada);
		atuaizarMovimento(mla, quantidadeMovimentada, tipoMovimento);
		atualizarEstoque(mla.getEstoqueAlmoxarifado(), quantidadeMovimentada, tipoMovimento);
	}

	private void atuaizarMovimento(MovimentoLivroAlmoxarifado mla, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoProfissionalLogado {
		mla.setProfissionalInsercao(Autenticador.getProfissionalLogado());
		mla.setQuantidadeAtual(mla.getEstoqueAlmoxarifado().getQuantidadeAtual());
		mla.setQuantidadeMovimentacao(quantidadeMovimentada);
		mla.setTipoMovimentoAlmoxarifado(tipoMovimento);
	}

	public void liberarAjusteEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueUnLock, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoMovimentoLivroNaoCadastrado{
		validaQuantidadeMovimentada(quantidadeMovimentada);
		lockEstoque(estoqueAlmoxarifado);
		estoqueVencido(estoqueAlmoxarifado.getDataValidade());
		estoqueBloqueado(estoqueAlmoxarifado.getBloqueado());
		
		if(!tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E)){
			estoqueVazio(estoqueAlmoxarifado.getQuantidadeAtual());
			Long quantidadeReservada = new SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz().totalReservardo(estoqueAlmoxarifado.getMaterialAlmoxarifado());
			estoqueInsuficiente(estoqueAlmoxarifado.getQuantidadeAtual(), quantidadeMovimentada, quantidadeReservada == null ? 0 : quantidadeReservada.intValue());
		}
		
		atualizarEstoque(estoqueAlmoxarifado, quantidadeMovimentada, tipoMovimento);
	}
	
	private void atualizarEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado, int quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado {
		if(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado() == 0){
			estoqueAlmoxarifado.setDataInclusao(new Date());
			estoqueAlmoxarifado.setProfissionalInclusao(Autenticador.getProfissionalLogado());
			estoqueAlmoxarifado.setQuantidadeAtual(quantidadeMovimentada);
		}else{
			int quantidadeAtual = new EstoqueAlmoxarifadoConsultaRaiz().consultarQuantidadeEstoque(estoqueAlmoxarifado);
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
			PadraoFluxoTemp.getObjetoAtualizar().put("estoqueAlmoxarifado"+estoqueAlmoxarifado.hashCode(), estoqueAlmoxarifado);
		else
			PadraoFluxoTemp.getObjetoSalvar().put("estoqueAlmoxarifado"+estoqueAlmoxarifado.hashCode(), estoqueAlmoxarifado);
	}
	
	private void validaQuantidadeMovimentada(int quantidadeMovimentada) throws ExcecaoQuantidadeZero {
		if(quantidadeMovimentada == 0)
			throw new ExcecaoQuantidadeZero();
	}

	private void estoqueInsuficiente(int quantidadeAtual, int quantidadeMovimentada, int quantidadeReservada) throws ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado {
//		if(quantidadeAtual < quantidadeMovimentada)
//			throw new ExcecaoEstoqueSaldoInsuficiente(quantidadeAtual);
//		else{
			long quantidadeVirtual = quantidadeAtual - quantidadeReservada;
			if(quantidadeVirtual < quantidadeMovimentada)
				throw new ExcecaoEstoqueReservado(quantidadeReservada, quantidadeVirtual);
//		}
			
	}

	private void estoqueInsuficiente(int quantidadeAtual, int quantidadeMovimentada) throws ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado {
		if(quantidadeAtual < quantidadeMovimentada)
			throw new ExcecaoEstoqueSaldoInsuficiente(quantidadeAtual);			
	}
	
	private void estoqueVazio(int quantidadeAtual) throws ExcecaoEstoqueAlmoxarifadoVazio {
		if(quantidadeAtual == 0)
			throw new ExcecaoEstoqueAlmoxarifadoVazio();
	}

	private void estoqueBloqueado(boolean bloqueado) throws ExcecaoEstoqueBloqueado {
		if(bloqueado)
			throw new ExcecaoEstoqueBloqueado();
	}

	private void estoqueVencido(Date dataValidade) throws ExcecaoEstoqueVencido {
		if(dataValidade == null){
			return;
		}
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
		if(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado() > 0)
			new AtualizadorEstoqueAlmoxarifadoLM().lockEstoque(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado(), estoqueAlmoxarifado.getLote(), 0);
	}
	
}
