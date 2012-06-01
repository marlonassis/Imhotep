package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Grupo;
import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="subGrupoHome")
@SessionScoped
public class SubGrupoHome extends PadraoHome<SubGrupo>{
	
	/**
	 * Método que retorna uma lista de SubGrupo
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<SubGrupo> getListaSubGrupoSuggest(String sql){
		return super.getBusca("select o from SubGrupo as o where o.descricao like '%"+sql+"%' ");
	}
	
	public SubGrupo subGrupoGrupo(Grupo grupo){
		ConsultaGeral<SubGrupo> cg = new ConsultaGeral<SubGrupo>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idGrupo", grupo.getIdGrupo());
		return cg.consultaUnica(new StringBuilder("select o from SubGrupo as o where o.grupo.idGrupo = :idGrupo"), hm);
	}
	
	/**
	 * Retorna uma lista de subgrupos de acordo com o grupo
	 * @param id
	 * @return collection de SubGrupo
	 */
	public Collection<SubGrupo> getListaSubGrupoGrupo(Integer id){
		return super.getBusca("select o from SubGrupo as o where o.grupo.idGrupo = "+id+" ");
	}
	
}
