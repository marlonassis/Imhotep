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
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorEstoqueAlmoxarifadoLM;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

public class ControleEstoqueAlmoxarifadoTemp extends PadraoRaiz<EstoqueAlmoxarifado>{
	private static final TipoOperacaoEnum TIPO_ENTRADA = TipoOperacaoEnum.E;
	
	public MovimentoLivroAlmoxarifado getMovimentoLivroPronto(Date dataMovimento, EstoqueAlmoxarifado estoqueAlmoxarifado, TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado, 
																String justificativa, Double quantidadeMovimentada) 
				throws ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoProfissionalLogado, ExcecaoEstoqueBloqueado, 
						ExcecaoEstoqueVencido, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueAlmoxarifadoVazio {
		lockEstoque(estoqueAlmoxarifado);
		MovimentoLivroAlmoxarifado movimentoLivro = prepararMovimentoLivroAlmoxarifado(dataMovimento, estoqueAlmoxarifado, tipoMovimentoAlmoxarifado, 
																						justificativa, quantidadeMovimentada);
		prepararEstoque(estoqueAlmoxarifado, tipoMovimentoAlmoxarifado, movimentoLivro);
		return movimentoLivro;
	}

	private void prepararEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado, TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado,
			MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado) throws ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueAlmoxarifadoVazio{
		validarManipulacaoLote(estoqueAlmoxarifado);
		double saldoAtual = estoqueAlmoxarifado.getQuantidadeAtual();
		if(tipoMovimentoAlmoxarifado.getTipoOperacao().equals(TIPO_ENTRADA)){
			saldoAtual += movimentoLivroAlmoxarifado.getQuantidadeMovimentacao();
		}else{
			validarManipulacaoSaidaLote(movimentoLivroAlmoxarifado);
			saldoAtual -= movimentoLivroAlmoxarifado.getQuantidadeMovimentacao();
		}
		 estoqueAlmoxarifado.setQuantidadeAtual(saldoAtual);
	}

	private MovimentoLivroAlmoxarifado prepararMovimentoLivroAlmoxarifado(Date dataMovimento, EstoqueAlmoxarifado estoqueAlmoxarifado,
			TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado, String justificativa, Double quantidadeMovimentada)
			throws ExcecaoProfissionalLogado {
		MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado = new MovimentoLivroAlmoxarifado();
		movimentoLivroAlmoxarifado.setDataMovimento(dataMovimento);
		movimentoLivroAlmoxarifado.setEstoqueAlmoxarifado(estoqueAlmoxarifado);
		movimentoLivroAlmoxarifado.setJustificativa(justificativa);
		movimentoLivroAlmoxarifado.setProfissionalInsercao(Autenticador.getProfissionalLogado());
		movimentoLivroAlmoxarifado.setQuantidadeAtual(estoqueAlmoxarifado.getQuantidadeAtual());
		movimentoLivroAlmoxarifado.setTipoMovimentoAlmoxarifado(tipoMovimentoAlmoxarifado);
		movimentoLivroAlmoxarifado.setQuantidadeMovimentacao(quantidadeMovimentada);
		return movimentoLivroAlmoxarifado;
	}
	
	public void validarManipulacaoSaidaLote(MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado) 
			throws ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueAlmoxarifadoVazio{
		estoqueInsuficiente(movimentoLivroAlmoxarifado);
		estoqueVazio(movimentoLivroAlmoxarifado.getEstoqueAlmoxarifado());
	}
	
	public void validarManipulacaoLote(EstoqueAlmoxarifado estoqueAlmoxarifado) throws ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido{
		estoqueBloqueado(estoqueAlmoxarifado);
		estoqueVencido(estoqueAlmoxarifado);
	}
	
	
	
	
	
	
	
	
	
	
	public void entradaSemNota(MovimentoLivroAlmoxarifado movimento) 
			throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, 
				ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, 
				ExcecaoEstoqueNaoAtualizado, ExcecaoPadraoFluxo {
		PadraoFluxoTemp.limparFluxo();
		Double quantidadeAtual = new EstoqueAlmoxarifadoConsultaRaiz().consultarQuantidadeEstoque(movimento.getEstoqueAlmoxarifado());
		movimento.setQuantidadeAtual(quantidadeAtual);
		liberarAjusteEstoqueAlmoxarifado(movimento.getEstoqueAlmoxarifado(), 
										 movimento.getQuantidadeMovimentacao(), 
										 movimento.getTipoMovimentoAlmoxarifado());
		PadraoFluxoTemp.getObjetoSalvar().put("Movimento", movimento);
		PadraoFluxoTemp.finalizarFluxo();
	}
	
	public void liberarSolicitacao(Double quantidadeMovimentada, MaterialAlmoxarifado materialAlmoxarifado, SolicitacaoMaterialAlmoxarifadoUnidade sma) 
			throws ExcecaoQuantidadeZero, ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado {
		validaQuantidadeMovimentada(quantidadeMovimentada);
		Double quantidadeTotal = new EstoqueAlmoxarifadoConsultaRaiz().totalEmEstoqueValido(materialAlmoxarifado);
		estoqueVazio(quantidadeTotal == null ? 0 : quantidadeTotal);
		Double quantidadeReservada = new SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz().totalReservardo(materialAlmoxarifado, sma);
		estoqueInsuficiente(quantidadeTotal == null ? 0 : quantidadeTotal, quantidadeMovimentada, quantidadeReservada == null ? 0 : quantidadeReservada);
	}
	
	public void liberarDispensacao(MovimentoLivroAlmoxarifado mla, Double quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) 
			throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, 
					ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		validaQuantidadeMovimentada(quantidadeMovimentada);
		lockEstoque(mla.getEstoqueAlmoxarifado());
		estoqueVencido(mla.getEstoqueAlmoxarifado());
		estoqueBloqueado(mla.getEstoqueAlmoxarifado());
		estoqueVazio(mla.getEstoqueAlmoxarifado());
		estoqueInsuficiente(mla);
		atuaizarMovimento(mla, quantidadeMovimentada, tipoMovimento);
		atualizarEstoque(mla.getEstoqueAlmoxarifado(), quantidadeMovimentada, tipoMovimento);
	}

	private void atuaizarMovimento(MovimentoLivroAlmoxarifado mla, Double quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoProfissionalLogado {
		mla.setProfissionalInsercao(Autenticador.getProfissionalLogado());
		mla.setQuantidadeAtual(mla.getEstoqueAlmoxarifado().getQuantidadeAtual());
		mla.setQuantidadeMovimentacao(quantidadeMovimentada);
		mla.setTipoMovimentoAlmoxarifado(tipoMovimento);
	}

	public void liberarAjusteEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado, Double quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) 
			throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, 
			ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado{
		validaQuantidadeMovimentada(quantidadeMovimentada);
		lockEstoque(estoqueAlmoxarifado);
		estoqueVencido(estoqueAlmoxarifado);
		estoqueBloqueado(estoqueAlmoxarifado);
		
		if(!tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E)){
			estoqueVazio(estoqueAlmoxarifado);
			Double quantidadeReservada = new SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz().totalReservardo(estoqueAlmoxarifado.getMaterialAlmoxarifado());
			estoqueInsuficiente(estoqueAlmoxarifado.getQuantidadeAtual(), quantidadeMovimentada, quantidadeReservada == null ? 0 : quantidadeReservada);
		}
		
		atualizarEstoque(estoqueAlmoxarifado, quantidadeMovimentada, tipoMovimento);
	}
	
	private void atualizarEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado, Double quantidadeMovimentada, TipoMovimentoAlmoxarifado tipoMovimento) throws ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado {
		if(estoqueAlmoxarifado.getIdEstoqueAlmoxarifado() == 0){
			estoqueAlmoxarifado.setDataInclusao(new Date());
			estoqueAlmoxarifado.setProfissionalInclusao(Autenticador.getProfissionalLogado());
			estoqueAlmoxarifado.setQuantidadeAtual(quantidadeMovimentada);
		}else{
			Double quantidadeAtual = new EstoqueAlmoxarifadoConsultaRaiz().consultarQuantidadeEstoque(estoqueAlmoxarifado);
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
	
	private void validaQuantidadeMovimentada(Double quantidadeMovimentada) throws ExcecaoQuantidadeZero {
		if(quantidadeMovimentada == 0)
			throw new ExcecaoQuantidadeZero();
	}

	private void estoqueInsuficiente(Double quantidadeAtual, Double quantidadeMovimentada, Double quantidadeReservada) throws ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado {
//		if(quantidadeAtual < quantidadeMovimentada)
//			throw new ExcecaoEstoqueSaldoInsuficiente(quantidadeAtual);
//		else{
			Double quantidadeVirtual = quantidadeAtual - quantidadeReservada;
			if(quantidadeVirtual < quantidadeMovimentada)
				throw new ExcecaoEstoqueReservado(quantidadeReservada, quantidadeVirtual);
//		}
			
	}

	private void estoqueInsuficiente(MovimentoLivroAlmoxarifado movimentoLivro) throws ExcecaoEstoqueSaldoInsuficiente {
		double saldoAtual = movimentoLivro.getEstoqueAlmoxarifado().getQuantidadeAtual();
		if(movimentoLivro.getQuantidadeMovimentacao() > saldoAtual)
			throw new ExcecaoEstoqueSaldoInsuficiente(saldoAtual);
	}

	private void estoqueVazio(EstoqueAlmoxarifado estoque) throws ExcecaoEstoqueAlmoxarifadoVazio {
		estoqueVazio(estoque.getQuantidadeAtual().doubleValue());
	}
	
	private void estoqueVazio(double saldoAtual) throws ExcecaoEstoqueAlmoxarifadoVazio {
		if(saldoAtual == 0d)
			throw new ExcecaoEstoqueAlmoxarifadoVazio();
	}
	
	private void estoqueBloqueado(EstoqueAlmoxarifado estoqueAlmoxarifado) throws ExcecaoEstoqueBloqueado {
		if(estoqueAlmoxarifado.getBloqueado())
			throw new ExcecaoEstoqueBloqueado();
	}

	private void estoqueVencido(EstoqueAlmoxarifado estoqueAlmoxarifado) throws ExcecaoEstoqueVencido {
		Date dataValidade = estoqueAlmoxarifado.getDataValidade();
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
