package br.com.Imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.controle.ControleInstancia;
import br.com.Imhotep.entidade.Usuario;
import br.com.Imhotep.entidade.UsuarioAcessoLog;
import br.com.Imhotep.enums.TipoUsuarioLogEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="usuarioAcessoRaiz")
@SessionScoped
public class UsuarioAcessoLogRaiz extends PadraoHome<UsuarioAcessoLog>{
	
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
			usuarioAcessoLog.setBloqueado(logEnum);
			usuarioAcessoLog.setSessao(ControleInstancia.getIdSessao());
			usuarioAcessoLog.setTempoSessao(ControleInstancia.getTempoInativacaoSessao());
			setInstancia(usuarioAcessoLog);
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
	
}
