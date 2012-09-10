package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.ControleDispensacao.entidade.MovimentoLivro;
import br.com.remendo.PadraoHome;

@ManagedBean(name="movimentoLivroHome")
@SessionScoped
public class MovimentoLivroHome extends PadraoHome<MovimentoLivro>{
	
	public static MovimentoLivroHome getInstanciaMovimentoLivro(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		return (MovimentoLivroHome) session.getAttribute("movimentoLivroHome");
	}
	
}
