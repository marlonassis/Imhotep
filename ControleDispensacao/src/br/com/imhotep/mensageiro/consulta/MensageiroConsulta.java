package br.com.imhotep.mensageiro.consulta;

import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.mensageiro.LinhaMecanica;
import br.com.imhotep.mensageiro.entidade.Mensagem;
import br.com.imhotep.mensageiro.entidade.Usuario;

@ManagedBean
@SessionScoped
public class MensageiroConsulta {
	private String criterio;
	private Mensagem[] mensagensSelecionadas;
	
//	public List<Usuario> getUsuarios(){
//		try {
//			return new LinhaMecanica().getUsuarios();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public List<Usuario> autoCompleteUsuario(String nome){
//		try {
//			return new LinhaMecanica().getUsuarios(nome);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public List<Mensagem> getListaMensagensRecebidas(){
//		try {
//			return new LinhaMecanica().getMensagensUsuario();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public String getCriterio() {
//		return criterio;
//	}
//
//	public void setCriterio(String criterio) {
//		this.criterio = criterio;
//	}
//
//	public Mensagem[] getMensagensSelecionadas() {
//		return mensagensSelecionadas;
//	}
//
//	public void setMensagensSelecionadas(Mensagem[] mensagensSelecionadas) {
//		this.mensagensSelecionadas = mensagensSelecionadas;
//	}

}
