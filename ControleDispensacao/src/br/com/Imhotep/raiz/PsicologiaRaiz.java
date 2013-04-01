package br.com.Imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Profissional;
import br.com.Imhotep.entidade.Psicologia;
import br.com.Imhotep.entidade.PsicologiaLog;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PsicologiaRaiz extends PadraoHome<Psicologia>{
	
	private PsicologiaLog log;
	
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
	public void setInstancia(Psicologia instancia) {
		log = new PsicologiaLog();
		log.setTextoOriginal(instancia.getDescricao());
		super.setInstancia(instancia);
	}
	
	@Override
	public boolean atualizar() {
		if(super.atualizar()){
			log.setDataModificacao(new Date());
			log.setProfissionalModificador(profissionalAtual());
			log.setPsicologia(getInstancia());
			log.setTextoAlterado(getInstancia().getDescricao());
			return new PsicologiaLogRaiz().enviar(log);
		}
		return false;
	}
	
}
