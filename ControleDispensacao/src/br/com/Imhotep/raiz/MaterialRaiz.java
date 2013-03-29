package br.com.Imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Familia;
import br.com.Imhotep.entidade.Grupo;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.SubGrupo;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.consulta.raiz.FamiliaConsultaRaiz;
import br.com.imhotep.consulta.raiz.SubGrupoConsultaRaiz;
import br.com.remendo.PadraoHome;

@ManagedBean
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
			setSugGrupoList(new SubGrupoConsultaRaiz().consultarSubGrupoGrupo(getInstancia().getFamilia().getSubGrupo().getGrupo().getIdGrupo()));
			setFamiliaList(new ArrayList<Familia>());
		}
	}
	
	/**
	 * Método usando para carregar as familias do subgrupo informado pelo usuário
	 */
	public void carregaFamiliaList(){
		if(getInstancia().getFamilia().getSubGrupo() != null){
			setFamiliaList(new FamiliaConsultaRaiz().consultarFamiliasSubGrupo(getInstancia().getFamilia().getSubGrupo().getIdSubGrupo()));
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
