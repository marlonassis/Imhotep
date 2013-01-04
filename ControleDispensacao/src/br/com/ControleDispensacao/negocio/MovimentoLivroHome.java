package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.MovimentoLivro;
import br.com.remendo.PadraoHome;

@ManagedBean(name="movimentoLivroHome")
@SessionScoped
public class MovimentoLivroHome extends PadraoHome<MovimentoLivro>{
	
}
