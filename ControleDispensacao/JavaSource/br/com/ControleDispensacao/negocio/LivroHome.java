package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Livro;
import br.com.remendo.PadraoHome;

@ManagedBean(name="livroHome")
@SessionScoped
public class LivroHome extends PadraoHome<Livro>{
	
	/**
	 * Método que retorna uma lista de Livro
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Livro> getListaLivroSuggest(String sql){
		return super.getBusca("select o from Livro as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
