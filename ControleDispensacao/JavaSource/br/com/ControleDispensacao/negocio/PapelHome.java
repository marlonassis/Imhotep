package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="papelHome")
@SessionScoped
public class PapelHome {

//	private static final long serialVersionUID = 1L;
//	@In(create = true)
//	UsuarioHome usuarioHome;
//
//	public void setPapelIdPapel(Integer id) {
//		setId(id);
//	}
//
//	public Integer getPapelIdPapel() {
//		return (Integer) getId();
//	}
//
//	@Override
//	protected Papel createInstance() {
//		Papel papel = new Papel();
//		return papel;
//	}
//
//	public void load() {
//		if (isIdDefined()) {
//			wire();
//		}
//	}
//
//	public void wire() {
//		getInstance();
//		Usuario usuario = usuarioHome.getDefinedInstance();
//		if (usuario != null) {
//			getInstance().setUsuario(usuario);
//		}
//	}
//
//	public boolean isWired() {
//		return true;
//	}
//
//	public Papel getDefinedInstance() {
//		return isIdDefined() ? getInstance() : null;
//	}
//
//	public List<PapelPagina> getPapelPaginas() {
//		return getInstance() == null ? null : new ArrayList<PapelPagina>(
//				getInstance().getPapelPaginas());
//	}

}
