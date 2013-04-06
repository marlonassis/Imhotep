package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="notaFiscalEstoqueRaiz")
@SessionScoped
public class NotaFiscalEstoqueRaiz extends PadraoHome<NotaFiscalEstoque>{

	public boolean enviar(NotaFiscalEstoque notaFiscalEstoque) {
		try {
			notaFiscalEstoque.setDataInsercao(new Date());
			notaFiscalEstoque.setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
			setInstancia(notaFiscalEstoque);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return super.enviar();
	}
	
}
