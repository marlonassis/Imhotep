package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.imhotep.consulta.raiz.LoteExistenteNotaFiscalConsultaRaiz;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.fluxo.FluxoNotaFiscalEstoque;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="notaFiscalRaiz")
@SessionScoped
public class NotaFiscalRaiz extends PadraoHome<NotaFiscal>{

	private NotaFiscalEstoque notaFiscalEstoque = new NotaFiscalEstoque();
	private Boolean loteEncontrado;
	
	public NotaFiscalRaiz() {
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
			mensagem("Este lote já está cadastrado para essa nota fiscal.", lote, Constantes.INFO);
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
		try {
			getNotaFiscalEstoque().setNotaFiscal(getInstancia());
			MovimentoLivro movimentoLivro = prepararMovimentoLivro(getNotaFiscalEstoque());
			getNotaFiscalEstoque().setMovimentoLivro(movimentoLivro);
			if(getLoteEncontrado()){
				FluxoNotaFiscalEstoque fluxoNotaFiscalEstoque = new FluxoNotaFiscalEstoque();
				fluxoNotaFiscalEstoque.atualizarNotaFiscalEstoque(getNotaFiscalEstoque(), movimentoLivro);
				limpar();
			}else{
				FluxoNotaFiscalEstoque fluxoNotaFiscalEstoque = new FluxoNotaFiscalEstoque();
				fluxoNotaFiscalEstoque.salvarNovaNotaFiscalEstoque(getNotaFiscalEstoque(), movimentoLivro);
				limpar();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	
	
	
}
