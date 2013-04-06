package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AvaliacaoPsicologica;
import br.com.imhotep.entidade.AvaliacaoPsicologicaLog;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class AvaliacaoPsicologicaRaiz extends PadraoHome<AvaliacaoPsicologica>{
	
	private AvaliacaoPsicologicaLog log;
	
	@Override
	public boolean enviar() {
		getInstancia().setDataCriacao(new Date());
		getInstancia().setProfissionalCriacao(profissionalAtual());
		return super.enviar();
	}

	private Profissional profissionalAtual(){
		try {
			return Autenticador.getInstancia().getProfissionalAtual();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setInstancia(AvaliacaoPsicologica instancia) {
		log = new AvaliacaoPsicologicaLog();
		log.setTextoOriginal(instancia.getDescricao());
		super.setInstancia(instancia);
	}
	
	@Override
	public boolean atualizar() {
		if(super.atualizar()){
			log.setDataModificacao(new Date());
			log.setProfissionalModificador(profissionalAtual());
			log.setAvaliacaoPsicologica(getInstancia());
			log.setTextoAlterado(getInstancia().getDescricao());
			return new AvaliacaoPsicologicaLogRaiz().enviar(log);
		}
		return false;
	}
	
}
