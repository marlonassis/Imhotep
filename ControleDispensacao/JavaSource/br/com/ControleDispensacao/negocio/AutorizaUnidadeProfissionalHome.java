package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.AutorizaUnidadeProfissional;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="autorizaUnidadeProfissionalHome")
@SessionScoped
public class AutorizaUnidadeProfissionalHome extends PadraoHome<AutorizaUnidadeProfissional>{
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
