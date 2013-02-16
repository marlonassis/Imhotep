package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="papelPaginaRaiz")
@SessionScoped
public class PapelPaginaRaiz {

//	private static final long serialVersionUID = 1L;
//	@In(create = true)
//	PapelHome papelHome;
//	@In(create = true)
//	PaginaHome paginaHome;
//
//	public void setPapelPaginaIdPapelPagina(Integer id) {
//		setId(id);
//	}
//
//	public Integer getPapelPaginaIdPapelPagina() {
//		return (Integer) getId();
//	}
//
//	@Override
//	protected PapelPagina createInstance() {
//		PapelPagina papelPagina = new PapelPagina();
//		return papelPagina;
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
//		Papel papel = papelHome.getDefinedInstance();
//		if (papel != null) {
//			getInstance().setPapel(papel);
//		}
//		Pagina pagina = paginaHome.getDefinedInstance();
//		if (pagina != null) {
//			getInstance().setPagina(pagina);
//		}
//	}
//
//	public boolean isWired() {
//		return true;
//	}
//
//	public PapelPagina getDefinedInstance() {
//		return isIdDefined() ? getInstance() : null;
//	}

}
