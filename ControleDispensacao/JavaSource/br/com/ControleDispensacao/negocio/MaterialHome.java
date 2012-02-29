package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Familia;
import br.com.ControleDispensacao.entidade.Grupo;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="materialHome")
@SessionScoped
public class MaterialHome extends PadraoHome<Material>{

	
	private List<SubGrupo> sugGrupoList = new ArrayList<SubGrupo>();
	private List<Familia> familiaList = new ArrayList<Familia>();

	public MaterialHome() {
		inicializaVariaveis();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		inicializaVariaveis();
	}
	
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
	
	public void inicializaVariaveis(){
		getInstancia().setFamilia(new Familia());
		getInstancia().getFamilia().setSubGrupo(new SubGrupo());
		getInstancia().getFamilia().getSubGrupo().setGrupo(new Grupo());
		sugGrupoList = new ArrayList<SubGrupo>();
		familiaList = new ArrayList<Familia>();
	}
	
	@Override
	public void setInstancia(Material instancia) {
		//carregando os valores ao entrar em edição
		inicializaVariaveis();
		super.setInstancia(instancia);
		carregaSubGrupoList();
		familiaList.add(getInstancia().getFamilia());
	}
	
	/**
	 * Método que retorna uma lista de Material
	 * @param String sql
	 * @return Collection Material
	 */
	public Collection<Material> getListaMaterialAutoComplete(String sql){
		return super.getBusca("select o from Material as o where lower(o.descricao) like lower('%"+sql+"%') ");
	}
	
	public Collection<Material> getListaMaterialEstoque(){
		return super.getBusca("select o.material from Estoque o inner join o.material order by o.material.descricao");
	}
	
	public Collection<Material> getListaMaterialEstoqueAutoComplete(String sql){
		return super.getBusca("select distinct o.material from Estoque o where lower(o.material.descricao) like lower('%"+sql+"%') ");
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
