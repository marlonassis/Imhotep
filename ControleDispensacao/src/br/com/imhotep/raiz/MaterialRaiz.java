package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.FamiliaConsultaRaiz;
import br.com.imhotep.consulta.raiz.SubGrupoConsultaRaiz;
import br.com.imhotep.entidade.Familia;
import br.com.imhotep.entidade.Grupo;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MaterialLog;
import br.com.imhotep.entidade.SubGrupo;
import br.com.imhotep.entidade.extra.MaterialFaltaEstoque;
import br.com.imhotep.enums.TipoEstoqueLog;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MaterialRaiz extends PadraoRaiz<Material>{
	
	private List<SubGrupo> sugGrupoList = new ArrayList<SubGrupo>();
	private List<Familia> familiaList = new ArrayList<Familia>();
	private List<MaterialFaltaEstoque> materiaisAbaixoQuantidadeMinima = new ArrayList<MaterialFaltaEstoque>();
	private MaterialLog materialAntigoLog = new MaterialLog();
	private MaterialLog materialNovoLog = new MaterialLog();
	private boolean exibirDialogConsultaQuantidadeMaterial;

	public MaterialRaiz() {
		inicializaVariaveis();
	}
	
	public void entrou(){
		super.mensagem("entrou", "entrou", Constantes.ERROR);
	}
	
	public void alterarStatusDialogConsultaQuantidadeMaterial(){
		if(isExibirDialogConsultaQuantidadeMaterial()){
			setExibirDialogConsultaQuantidadeMaterial(false);
		}else{
			setExibirDialogConsultaQuantidadeMaterial(true);
		}
	}
	
	public void ocultarDialogConsultaQuantidadeMaterial(){
		setExibirDialogConsultaQuantidadeMaterial(false);
	}
	
	public static MaterialRaiz getInstanciaAtual(){
		try {
			return (MaterialRaiz) Utilitarios.procuraInstancia(MaterialRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
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
		materialAntigoLog = new MaterialLog();
	}
	
	@Override
	public void setInstancia(Material instancia) {
		//carregando os valores ao entrar em edição
		inicializaVariaveis();
		super.setInstancia(instancia);
		carregaSubGrupoList();
		familiaList.add(getInstancia().getFamilia());
		gerarLogInicial();
	}
	
	private void gerarLogInicial() {
		getMaterialAntigoLog().setCodigo(String.valueOf(getInstancia().getCodigoMaterial()));
		getMaterialAntigoLog().setNome(getInstancia().getDescricao());
		getMaterialAntigoLog().setUnidade(getInstancia().getUnidadeMaterial().getDescricao());
		getMaterialAntigoLog().setBloqueado(getInstancia().getBloqueado());
		getMaterialAntigoLog().setMaterial(getInstancia());
	}

	private void gerarLogFinal() {
		getMaterialNovoLog().setCodigo(String.valueOf(getInstancia().getCodigoMaterial()));
		getMaterialNovoLog().setNome(getInstancia().getDescricao());
		getMaterialNovoLog().setUnidade(getInstancia().getUnidadeMaterial().getDescricao());
		getMaterialNovoLog().setBloqueado(getInstancia().getBloqueado());
		getMaterialNovoLog().setMaterial(getInstancia());
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

	@Override
	public boolean atualizar() {
		gerarLogFinal();
		if(super.atualizar()){
			Date dataLog = new Date();
			gerarLog(getMaterialAntigoLog(), dataLog, TipoEstoqueLog.A);
			gerarLog(getMaterialNovoLog(), dataLog, TipoEstoqueLog.B);
			gerarLogInicial();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean apagar() {
		if(super.apagar()){
			gerarLog(getMaterialAntigoLog(), new Date(), TipoEstoqueLog.D);
			return true;
		}
		return false;
	}
	
	private void gerarLog(MaterialLog materialLog, Date dataLog, TipoEstoqueLog tipoEstoqueLog) {
		MaterialLogRaiz mlr = new MaterialLogRaiz();
		mlr.setInstancia(materialLog);
		mlr.setDadosBasicos(dataLog);
		mlr.getInstancia().setTipoLog(tipoEstoqueLog);
		mlr.setExibeMensagemInsercao(false);
		mlr.enviar();
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

	public List<MaterialFaltaEstoque> getMateriaisAbaixoQuantidadeMinima() {
		return materiaisAbaixoQuantidadeMinima;
	}

	public void setMateriaisAbaixoQuantidadeMinima(
			List<MaterialFaltaEstoque> materiaisAbaixoQuantidadeMinima) {
		this.materiaisAbaixoQuantidadeMinima = materiaisAbaixoQuantidadeMinima;
	}

	public MaterialLog getMaterialAntigoLog() {
		return materialAntigoLog;
	}

	public void setMateriaAntigolLog(MaterialLog materialLog) {
		this.materialAntigoLog = materialLog;
	}

	public MaterialLog getMaterialNovoLog() {
		return materialNovoLog;
	}

	public void setMaterialNovoLog(MaterialLog materialNovoLog) {
		this.materialNovoLog = materialNovoLog;
	}

	public boolean isExibirDialogConsultaQuantidadeMaterial() {
		return exibirDialogConsultaQuantidadeMaterial;
	}

	public void setExibirDialogConsultaQuantidadeMaterial(
			boolean exibirDialogConsultaQuantidadeMaterial) {
		this.exibirDialogConsultaQuantidadeMaterial = exibirDialogConsultaQuantidadeMaterial;
	}
	
}
