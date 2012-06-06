package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.MotivoFimReceita;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="motivoFimReceitaHome")
@SessionScoped
public class MotivoFimReceitaHome extends PadraoHome<MotivoFimReceita>{
	
	/**
	 * Método que retorna uma lista de MotivoFimReceita
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<MotivoFimReceita> getListaFabricanteAutoComplete(String sql){
		return super.getBusca("select o from MotivoFimReceita as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
