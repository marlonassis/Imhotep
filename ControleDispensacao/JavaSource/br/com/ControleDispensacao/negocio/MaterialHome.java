package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Familia;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="materialHome")
@SessionScoped
public class MaterialHome extends PadraoHome<Material>{

	
	private List<SubGrupo> sugGrupoList = new ArrayList<SubGrupo>();
	private List<Familia> familiaList = new ArrayList<Familia>();

	/**
	 * Método usando para carregar o subgrupo do grupo informado pelo usuário
	 */
	public void carregaSubGrupoList(){
		if(getInstancia().getFamilia().getSubGrupo().getGrupo() != null){
			setSugGrupoList((List<SubGrupo>) new SubGrupoHome().getListaSubGrupoGrupo(getInstancia().getFamilia().getSubGrupo().getGrupo().getIdGrupo()));
		}
	}
	
	/**
	 * Método usando para carregar as familias do subgrupo informado pelo usuário
	 */
	public void carregaFamiliaList(){
		if(getInstancia().getFamilia().getSubGrupo() != null){
			setFamiliaList((List<Familia>) new FamiliaHome().getListaFamiliaSubGrupo(getInstancia().getFamilia().getSubGrupo().getGrupo().getIdGrupo()));
		}
	}
	
	@Override
	public void setInstancia(Material instancia) {
		//carregando os valores ao entrar em edição
		super.setInstancia(instancia);
		carregaSubGrupoList();
		carregaFamiliaList();
	}
	
	/**
	 * Método que retorna uma lista de Material
	 * @param String sql
	 * @return Collection Material
	 */
	public Collection<Material> getListaMaterialAutoComplete(String sql){
		return super.getBusca("select o from Material as o where o.descricao like '%"+sql+"%' ");
	}
	
	public Collection<Material> getListaMaterialEstoque(){
		return super.getBusca("select distinct o.material from Estoque o order by o.material.descricao ");
	}
	
	public Collection<Material> getListaMaterialEstoqueAutoComplete(String sql){
		return super.getBusca("select distinct o.material from Estoque o where o.material.descricao like '%"+sql+"%' ");
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}


	public List<SubGrupo> getSugGrupoList() {
		return sugGrupoList;
	}


	public void setSugGrupoList(List<SubGrupo> sugGrupoList) {
		this.sugGrupoList = sugGrupoList;
	}


	public List<Familia> getFamiliaList() {
		return familiaList;
	}


	public void setFamiliaList(List<Familia> familiaList) {
		this.familiaList = familiaList;
	}
	
}
