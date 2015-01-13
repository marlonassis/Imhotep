package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Doacao;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class DoacaoRaiz extends PadraoRaiz<Doacao>{
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalCadastro(Autenticador.getProfissionalLogado());
			getInstancia().setDataDoacao(new Date());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
}
