package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LivroOcorrencia;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LivroOcorrenciaRaiz extends PadraoRaiz<LivroOcorrencia>{
	@Override
	public boolean enviar() {
		getInstancia().setDataCadastro(new Date());
		try {
			getInstancia().setProfissionalOcorrencia(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		if(super.enviar()){
			super.novaInstancia();
			return true;
		}
		return false;
	}
}
