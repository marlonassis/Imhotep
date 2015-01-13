package br.com.imhotep.raiz;

import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.NotaFiscal;
import br.com.imhotep.excecoes.ExcecaoDataContabil;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class NotaFiscalRaiz extends PadraoRaiz<NotaFiscal>{	
	private boolean exibirDialogConfirmacaoValorNota;
	
	
	public void bloquearDesbloquear(){
		Boolean bloqueada = getInstancia().getBloqueada();
		getInstancia().setBloqueada(!bloqueada);
		super.atualizar();
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
	
	public void bloquearNotaFiscal(){
		getInstancia().setBloqueada(true);
		super.atualizar();
		novaInstancia();
	}

	public boolean isExibirDialogConfirmacaoValorNota() {
		return exibirDialogConfirmacaoValorNota;
	}

	public void setExibirDialogConfirmacaoValorNota(
			boolean exibirDialogConfirmacaoValorNota) {
		this.exibirDialogConfirmacaoValorNota = exibirDialogConfirmacaoValorNota;
	}

	
	
	
}
