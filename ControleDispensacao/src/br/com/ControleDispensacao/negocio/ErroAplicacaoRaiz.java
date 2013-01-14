package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.remendo.PadraoHome;

@ManagedBean(name="erroAplicacaoRaiz")
@SessionScoped
public class ErroAplicacaoRaiz extends PadraoHome<ErroAplicacao>{
	
	public ErroAplicacaoRaiz(ErroAplicacao ea) {
		setInstancia(ea);
	}
	
	public ErroAplicacaoRaiz(Date dataOcorrencia) {
		getInstancia().setDataOcorrencia(dataOcorrencia);
	}
	
	@Override
	public boolean enviar() {
		return super.enviar();
	}
	
}