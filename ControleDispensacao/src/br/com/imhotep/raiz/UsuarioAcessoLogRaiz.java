package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.entidade.UsuarioAcessoLog;
import br.com.imhotep.enums.TipoUsuarioLogEnum;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class UsuarioAcessoLogRaiz extends PadraoHome<UsuarioAcessoLog>{
	
	private UsuarioAcessoLog usuarioAcessoLog;
	
	public boolean gerarLogLogin(){
		return gerarLog(TipoUsuarioLogEnum.I);
	}
	
	public boolean gerarLogLogout(){
		return gerarLog(TipoUsuarioLogEnum.O);
	}
	
	public boolean gerarLog(TipoUsuarioLogEnum logEnum){
		try {
			Usuario usuario = Autenticador.getInstancia().getUsuarioAtual();
			UsuarioAcessoLog usuarioAcessoLog = new UsuarioAcessoLog();
			usuarioAcessoLog.setUsuario(usuario);
			usuarioAcessoLog.setDataLog(new Date());
			usuarioAcessoLog.setTipoLog(logEnum);
			usuarioAcessoLog.setSessao(ControleInstancia.getIdSessao());
			usuarioAcessoLog.setTempoSessao(ControleInstancia.getTempoInativacaoSessao());
			setInstancia(usuarioAcessoLog);
			setExibeMensagemInsercao(false);
			return super.enviar();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public UsuarioAcessoLog getUsuarioAcessoLog() {
		return usuarioAcessoLog;
	}

	public void setUsuarioAcessoLog(UsuarioAcessoLog usuarioAcessoLog) {
		this.usuarioAcessoLog = usuarioAcessoLog;
	}
	
}
