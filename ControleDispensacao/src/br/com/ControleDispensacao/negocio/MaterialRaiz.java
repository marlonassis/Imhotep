package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Familia;
import br.com.ControleDispensacao.entidade.Grupo;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="materialRaiz")
@SessionScoped
public class MaterialRaiz extends PadraoHome<Material>{

	
	private List<SubGrupo> sugGrupoList = new ArrayList<SubGrupo>();
	private List<Familia> familiaList = new ArrayList<Familia>();

	public MaterialRaiz() {
		inicializaVariaveis();
	}
	
	@Override
	public void novaInstancia() {
		setSugGrupoList(new ArrayList<SubGrupo>());
		setFamiliaList(new ArrayList<Familia>());
		super.novaInstancia();
		getInstancia().setFamilia(new Familia());
		getInstancia().getFamilia().setSubGrupo(new SubGrupo());
		getInstancia().getFamilia().getSubGrupo().setGrupo(new Grupo());
	}
	
	/**
	 * Método usando para carregar o subgrupo do grupo informado pelo usuário
	 */
	public void carregaSubGrupoList(){
		if(getInstancia().getFamilia().getSubGrupo().getGrupo() != null){
			setSugGrupoList((List<SubGrupo>) new SubGrupoRaiz().getListaSubGrupoGrupo(getInstancia().getFamilia().getSubGrupo().getGrupo().getIdGrupo()));
			setFamiliaList(new ArrayList<Familia>());
		}
	}
	
	/**
	 * Método usando para carregar as familias do subgrupo informado pelo usuário
	 */
	public void carregaFamiliaList(){
		if(getInstancia().getFamilia().getSubGrupo() != null){
			setFamiliaList((List<Familia>) new FamiliaRaiz().getListaFamiliaSubGrupo(getInstancia().getFamilia().getSubGrupo().getIdSubGrupo()));
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
	
	public Collection<Material> getListaMaterialAutoComplete(String sql){
		return super.getBusca("select o from Material as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
	public Collection<Material> getListaMaterialAntibioticoAutoComplete(String sql){
		String sql2 = "select o from Material as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) and ";
		sql2 = sql2.concat("lower(o.familia.subGrupo.grupo.descricao) = lower('ANTIBIÓTICO')");
		return super.getBusca(sql2);
	}
	
	public Collection<String> getListaMaterialDescricaoAutoComplete(String sql){
		ConsultaGeral<String> cg = new ConsultaGeral<String>();
		return cg.consulta(new StringBuilder("select o.descricao from Material as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql.toLowerCase()+"%')) "), null);
	}
	
	public Collection<Material> getListaMaterialEstoque(){
		return super.getBusca("select o from Material o where o.idMaterial in (select e.material.idMaterial from Estoque e) order by to_ascii(o.descricao)");
	}
	
	public Collection<Material> getListaMaterialEstoqueAutoComplete(String sql){
		return super.getBusca("select distinct o.material from Estoque o where o.quantidade > 0 and lower(to_ascii(o.material.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
	public Collection<Material> getListaMaterialPesquisaCentroCirurgicoAutoComplete(String sql){
		Set<Material> buscaList = new HashSet<Material>(super.getBusca("select o.material from EstoqueCentroCirurgico as o where o.dataValidade >= now() and o.bloqueado = 'N' and lower(to_ascii(o.material.descricao)) like lower(to_ascii('%"+sql+"%')) order by o.material.descricao, o.lote "));
		return new ArrayList<Material>(buscaList);
	}
	
	@Override
	public boolean enviar() {
		try {
			getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usuário atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em MaterialHome");
		}
		getInstancia().setDataInclusao(new Date());
		getInstancia().setDescricao(getInstancia().getDescricao().toUpperCase());
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
