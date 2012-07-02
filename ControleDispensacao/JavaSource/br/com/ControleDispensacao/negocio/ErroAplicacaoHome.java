package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="erroAplicacaoHome")
@SessionScoped
public class ErroAplicacaoHome extends PadraoHome<ErroAplicacao>{
	
	public ErroAplicacaoHome(ErroAplicacao ea) {
		setInstancia(ea);
	}
	
	public ErroAplicacaoHome(Date dataOcorrencia) {
		getInstancia().setDataOcorrencia(dataOcorrencia);
	}
	
	@Override
	public boolean enviar() {
		return super.enviar();
	}
	
}
