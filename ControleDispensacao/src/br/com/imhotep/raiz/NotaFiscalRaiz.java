package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.imhotep.consulta.raiz.LoteExistenteNotaFiscalConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.excecoes.ExcecaoDataContabil;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorEstoqueLM;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class NotaFiscalRaiz extends PadraoRaiz<NotaFiscal>{	
	
	private NotaFiscalEstoque notaFiscalEstoque = new NotaFiscalEstoque();
	private Boolean loteEncontrado;
	private boolean exibirDialogConfirmacaoValorNota;
	
	
	public NotaFiscalRaiz() {
		super();
		limpar();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		limpar();
	}
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
			getInstancia().setDataInsercao(new Date());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	public void finalizarCadastro(){
		try {
			ocultarDialogConfirmacaoValorNota();
			validarDataContabil();
			super.enviar();
		} catch (ExcecaoDataContabil e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean enviar() {
		exibirDialogConfirmacaoValorNota();
		return false;
	}
	
	public void exibirDialogConfirmacaoValorNota(){
		setExibirDialogConfirmacaoValorNota(true);
	}
	
	public void ocultarDialogConfirmacaoValorNota(){
		setExibirDialogConfirmacaoValorNota(false);
	}
	
	private void validarDataContabil() throws ExcecaoDataContabil {
		Calendar dataContabil = Calendar.getInstance();
		dataContabil.setTime(getInstancia().getDataContabil());
		int mes = dataContabil.get(Calendar.MONTH);
		int ano = dataContabil.get(Calendar.YEAR);
		
		dataContabil = Calendar.getInstance();
		dataContabil.set(Calendar.HOUR_OF_DAY, 0);
		dataContabil.set(Calendar.MINUTE, 0);
		dataContabil.set(Calendar.SECOND, 0);
		dataContabil.set(Calendar.DAY_OF_MONTH, 01);
		dataContabil.set(Calendar.MONTH, mes);
		dataContabil.set(Calendar.YEAR, ano);
		
		Calendar mesAtual = Calendar.getInstance();
		mesAtual.set(Calendar.HOUR_OF_DAY, 0);
		mesAtual.set(Calendar.MINUTE, 0);
		mesAtual.set(Calendar.SECOND, 0);
		mesAtual.set(Calendar.DAY_OF_MONTH, 01);
		
		if(dataContabil.getTime().before(mesAtual.getTime())){
			throw new ExcecaoDataContabil();
		}
	}
	
	public void procurarLote() throws IOException{
		String lote = getNotaFiscalEstoque().getEstoque().getLote();
		Estoque estoque = new LoteExistenteNotaFiscalConsultaRaiz().consultar(lote, getInstancia());
		if(estoque == null){
			estoque = new EstoqueLoteConsultaRaiz().consultar(lote);
			setLoteEncontrado(estoque != null);
			if(getLoteEncontrado()){
				getNotaFiscalEstoque().setEstoque(estoque);
			}
		}else{
			mensagem("Este lote j� est� cadastrado para essa nota fiscal.", lote, Constantes.INFO);
			limpar();
		}
	}
	
	public void limpar() {
		setLoteEncontrado(null);
		setNotaFiscalEstoque(new NotaFiscalEstoque());
		getNotaFiscalEstoque().setEstoque(new Estoque());
		getNotaFiscalEstoque().setNotaFiscal(new NotaFiscal());
	}
	
	public void gravarItemNotaFiscal(){
		PadraoFluxoTemp.limparFluxo();
		int idEstoque = getNotaFiscalEstoque().getEstoque().getIdEstoque();
		try {
			getNotaFiscalEstoque().setNotaFiscal(getInstancia());
			MovimentoLivro movimentoLivro = prepararMovimentoLivro(getNotaFiscalEstoque());
			Date dataAtual = new Date();
			new ControleEstoqueTemp().liberarAjuste(dataAtual, movimentoLivro);
			movimentoLivro.setJustificativa("NF: "+getInstancia().getIdentificacaoNotaFiscal());
			PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro-"+movimentoLivro.hashCode(), movimentoLivro);
			preparaItem(movimentoLivro, dataAtual);
			PadraoFluxoTemp.getObjetoSalvar().put("NotaFiscalEstoque"+getNotaFiscalEstoque().hashCode(), getNotaFiscalEstoque());
			PadraoFluxoTemp.finalizarFluxo();
			getInstancia().getItens().add(getNotaFiscalEstoque());
			limpar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PadraoFluxoTemp.limparFluxo();
		try {
			new AtualizadorEstoqueLM().unLockEstoque(idEstoque);
		} catch (ExcecaoEstoqueUnLock e) {
			e.printStackTrace();
		}
	}

	private void preparaItem(MovimentoLivro movimentoLivro, Date dataAtual) throws ExcecaoProfissionalLogado {
		getNotaFiscalEstoque().setDataInsercao(dataAtual);
		getNotaFiscalEstoque().setProfissionalInsercao(Autenticador.getProfissionalLogado());
		getNotaFiscalEstoque().setMovimentoLivro(movimentoLivro);
	}

	private MovimentoLivro prepararMovimentoLivro(NotaFiscalEstoque notaFiscalEstoque) {
		MovimentoLivro movimentoLivro = new MovimentoLivro();
		movimentoLivro.setEstoque(notaFiscalEstoque.getEstoque());
		movimentoLivro.setQuantidadeMovimentacao(notaFiscalEstoque.getQuantidadeEntrada());
		movimentoLivro.setTipoMovimento(Parametro.tipoMovimentoNotaFiscalEntrada());
		return movimentoLivro;
	}
	
	public void bloquearNotaFiscal(){
		getInstancia().setBloqueada(true);
		super.atualizar();
		novaInstancia();
	}

	public NotaFiscalEstoque getNotaFiscalEstoque() {
		return notaFiscalEstoque;
	}

	public void setNotaFiscalEstoque(NotaFiscalEstoque notaFiscalEstoque) {
		this.notaFiscalEstoque = notaFiscalEstoque;
	}

	public Boolean getLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(Boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}

	public boolean isExibirDialogConfirmacaoValorNota() {
		return exibirDialogConfirmacaoValorNota;
	}

	public void setExibirDialogConfirmacaoValorNota(
			boolean exibirDialogConfirmacaoValorNota) {
		this.exibirDialogConfirmacaoValorNota = exibirDialogConfirmacaoValorNota;
	}

	
	
	
}
