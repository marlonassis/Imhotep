package br.com.Imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.NotaFiscal;
import br.com.Imhotep.entidade.NotaFiscalEstoque;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="notaFiscalRaiz")
@SessionScoped
public class NotaFiscalRaiz extends PadraoHome<NotaFiscal>{

	private Estoque estoque = new Estoque();
	
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
	
	public void limparEstoqueAtual() {
		setEstoque(new Estoque());
	}
	
	public void atualizarEstoque(){
		new EstoqueRaiz(getEstoque()).atualizar();
	}
	
	public void gravarItemNotaFiscal(){
		EntradaMaterialRaiz emr = new EntradaMaterialRaiz();
		emr.setInstancia(getEstoque());
		emr.enviar();
		
		NotaFiscalEstoque nfe = new NotaFiscalEstoque();
		nfe.setNotaFiscal(getInstancia());
		nfe.setEstoque(emr.getInstancia());
		if(new NotaFiscalEstoqueRaiz().enviar(nfe)){
			limparEstoqueAtual();
		}
	}
	
	public void bloquearNotaFiscal(){
		getInstancia().setBloqueada(true);
		super.atualizar();
		novaInstancia();
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

}
