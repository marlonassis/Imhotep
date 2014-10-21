package br.com.imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Livro;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="livroRaiz")
@SessionScoped
public class LivroRaiz extends PadraoRaiz<Livro>{
	
	/**
	 * MŽtodo que retorna uma lista de Livro
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Livro> getListaLivroSuggest(String sql){
		return super.getBusca("select o from Livro as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
