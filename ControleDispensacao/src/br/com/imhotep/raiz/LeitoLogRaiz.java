package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LeitoLog;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class LeitoLogRaiz extends PadraoHome<LeitoLog>{
	
	public void gerarLog(LeitoLog log){
		log.setDataLog(new Date());
		try {
			log.setProfissionalLog(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		setExibeMensagemInsercao(false);
		setInstancia(log);
		super.enviar();
	}
	
}
