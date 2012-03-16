package br.com.nucleo.gerenciador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.enums.UsuarioPapeisEnum;

@ManagedBean(name="gerenciadorAcesso")
@RequestScoped
public class GerenciadorAcesso {
	Usuario usuarioAtual = null;//UsuarioHome.getUsuarioAtual();
	
	public boolean isAdministrador(){
		return procuraPapel(UsuarioPapeisEnum.ADMINISTRADOR);
	}
	
	public boolean isUsuario(){
		return procuraPapel(UsuarioPapeisEnum.USUARIO);
	}
	
	public boolean isUsuarioParticipante(){
		return procuraPapel(UsuarioPapeisEnum.USUARIO_PARTICIPANTE);
	}
	
	public boolean isVisitante(){
		return procuraPapel(UsuarioPapeisEnum.VISITANTE);
	}
	
	private boolean procuraPapel(UsuarioPapeisEnum upe){
		if(usuarioAtual != null){
//			for(Papel p : usuarioAtual.getPapeis()){
//				if(p.getNome().equalsIgnoreCase(upe.getLabel())){
//					return true;
//				}
//			}
		}
		return false;
	}
}
