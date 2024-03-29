package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.ErroAplicacao;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="erroAplicacaoRaiz")
@SessionScoped
public class ErroAplicacaoRaiz extends PadraoRaiz<ErroAplicacao>{
	
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
