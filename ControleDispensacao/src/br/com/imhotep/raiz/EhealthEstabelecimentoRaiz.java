package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.EhealthEstabelecimento;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EhealthEstabelecimentoRaiz extends PadraoHome<EhealthEstabelecimento>{
	
	public static EhealthEstabelecimentoRaiz getInstanciaAtual(){
		try {
			return (EhealthEstabelecimentoRaiz) Utilitarios.procuraInstancia(EhealthEstabelecimentoRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void pegarEstabelecimento(){
		try {
			getInstancia().setProfissionalPesquisador(Autenticador.getProfissionalLogado());
			super.atualizar();
			super.novaInstancia();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	public void rejeitarEstabelecimento(){
		getInstancia().setProfissionalPesquisador(null);
		super.atualizar();
		super.novaInstancia();
	}
}
