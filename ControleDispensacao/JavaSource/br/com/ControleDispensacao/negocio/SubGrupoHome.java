package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;
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
	 * Método que retorna uma lista de Aplicacao
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
	
	public Collection<SubGrupo> getListaSubGrupoGrupoSuggest(Integer id){
		return super.getBusca("select o from SubGrupo as o where o.grupo.idGrupo = "+id+" ");
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setUsuarioAlteracao(UsuarioHome.getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(UsuarioHome.getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
