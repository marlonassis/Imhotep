package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Variavel;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class VariavelRaiz extends PadraoRaiz<Variavel>{
	@Override
	protected void aposEnviar() {
		super.novaInstancia();
		super.aposEnviar();
	}
}
