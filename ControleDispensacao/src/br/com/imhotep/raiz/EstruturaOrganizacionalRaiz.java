package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.AlteracaoEstruturaLog;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.GrupoAdm;
import br.com.imhotep.entidade.GrupoEstrutura;
import br.com.imhotep.enums.TipoCrudEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstruturaOrganizacionalRaiz extends PadraoRaiz<EstruturaOrganizacional>{
	
	private TreeNode root;
	private TreeNode selectedNode;
	private boolean exibirDialogEstrutura;
	private boolean exibirDialogDeslocarNo;
	private EstruturaOrganizacional origemDeslocar;
	private EstruturaOrganizacional destinoDeslocar;
	private GrupoAdm grupoAdm;
    
	public EstruturaOrganizacionalRaiz(){
		super();
		carrregarEstrutura();
	}
	
	public List<GrupoAdm> getGrupos(){
		String hql = "select o from GrupoAdm o order by to_ascii(lower(o.nome))";
		Collection<GrupoAdm> consulta = new ConsultaGeral<GrupoAdm>(new StringBuilder(hql)).consulta();
		return new ArrayList<GrupoAdm>(consulta);
	}
	
	public void configurarNode(){
		if(getSelectedNode() != null){
			EstruturaOrganizacional estruturaOrganizacional = (EstruturaOrganizacional) getSelectedNode().getData();
			carregarInformacoesBasicas(estruturaOrganizacional);
			carregarLotados();
			carregarFuncoesLotadas(estruturaOrganizacional);
			carregarProfissionaisFuncoesLotadas(estruturaOrganizacional);
			carregarFuncoesMenu(estruturaOrganizacional);
			carregarPaineis(estruturaOrganizacional);
			carregarLogAlteracoes(estruturaOrganizacional);
		}
	}
	
	private void carregarLogAlteracoes(EstruturaOrganizacional estruturaOrganizacional) {
		try {
			AlteracaoEstruturaLogRaiz logs = 
					(AlteracaoEstruturaLogRaiz) new ControleInstancia().procuraInstancia(AlteracaoEstruturaLogRaiz.class);
			logs.carregarDados(estruturaOrganizacional);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void carregarPaineis(EstruturaOrganizacional estruturaOrganizacional) {
		try {
			EstruturaOrganizacionalPainelRaiz paineis = 
					(EstruturaOrganizacionalPainelRaiz) new ControleInstancia().procuraInstancia(EstruturaOrganizacionalPainelRaiz.class);
			paineis.carregarDados(estruturaOrganizacional);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void carregarFuncoesLotadas(EstruturaOrganizacional estruturaOrganizacional) {
		try {
			EstruturaOrganizacionalFuncaoRaiz funcoes = 
					(EstruturaOrganizacionalFuncaoRaiz) new ControleInstancia().procuraInstancia(EstruturaOrganizacionalFuncaoRaiz.class);
			funcoes.carregarDados(estruturaOrganizacional);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void carregarInformacoesBasicas(EstruturaOrganizacional estruturaOrganizacional) {
		try {
			EstruturaOrganizacionalInformacoesBasicas informacoesBasicas = 
					(EstruturaOrganizacionalInformacoesBasicas) new ControleInstancia().procuraInstancia(EstruturaOrganizacionalInformacoesBasicas.class);
			informacoesBasicas.carregarEstruturaBasica(estruturaOrganizacional);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void carregarFuncoesMenu(EstruturaOrganizacional eo) {
		try {
			EstruturaOrganizacionalMenuRaiz lotacaoMenu = 
					(EstruturaOrganizacionalMenuRaiz) new ControleInstancia().procuraInstancia(EstruturaOrganizacionalMenuRaiz.class);
			lotacaoMenu.iniciarEdicao(eo);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void carregarProfissionaisFuncoesLotadas(EstruturaOrganizacional estruturaOrganizacional) {
		try {
			LotacaoProfissionalFuncaoRaiz lotacaoFuncao = 
					(LotacaoProfissionalFuncaoRaiz) new ControleInstancia().procuraInstancia(LotacaoProfissionalFuncaoRaiz.class);
			lotacaoFuncao.carregarDadosLotacaoFuncao(estruturaOrganizacional);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void carregarLotados() {
		try {
			LotacaoProfissionalRaiz lotacao = 
					(LotacaoProfissionalRaiz) new ControleInstancia().procuraInstancia(LotacaoProfissionalRaiz.class);
			lotacao.setEstruturaOrganizacional((EstruturaOrganizacional) getSelectedNode().getData());
			lotacao.exibirDialogLotacao();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void desmarcarNo(){
		getSelectedNode().setSelected(false);
		setSelectedNode(null);
	}
	
	public void exibirDialogDeslocarNo(){
		setExibirDialogDeslocarNo(true);
		if(getSelectedNode() != null){
			setOrigemDeslocar((EstruturaOrganizacional) getSelectedNode().getData());
		}
	}
	
	public void ocultarDialogDeslocarNo(){
		setExibirDialogDeslocarNo(false);
		setOrigemDeslocar(null);
		setDestinoDeslocar(null);
		carrregarEstrutura();
	}
	
	public void deslocarNo(){
		String justificativa = "Migrado de " + getOrigemDeslocar().getEstruturaPai().getNome() + " para " + getDestinoDeslocar().getNome();
		getOrigemDeslocar().setEstruturaPai(getDestinoDeslocar());
		if(super.atualizarGenerico(getOrigemDeslocar()) != null){
			carrregarEstrutura();
			try {
				gerarLog(TipoCrudEnum.M, grupoAdm, getOrigemDeslocar(), justificativa);
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onDragDrop(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        EstruturaOrganizacional pai = (EstruturaOrganizacional) dropNode.getData();
        EstruturaOrganizacional filho = (EstruturaOrganizacional) dragNode.getData();
        filho.setEstruturaPai(pai);
        if(super.atualizarGenerico(filho) != null){
        	dragNode.setParent(dropNode);
        	dropNode.setExpanded(false);
        }
    }
	
	public void exibirDialogAtualizarEstrutura(){
		if(getSelectedNode() != null){
			EstruturaOrganizacional no = (EstruturaOrganizacional) getSelectedNode().getData();
			setInstancia(no);
			setExibirDialogEstrutura(true);
			carregarGrupo();
		}else{
			super.mensagem("Selecione algum nível da estrutura para poder atualizá-la", null, Constantes.WARN);
		}
	}
	
	public void exibirDialogAddEstrutura(){
		super.novaInstancia();
		setExibirDialogEstrutura(true);
	}

	private void carregarGrupo() {
		if(getSelectedNode() != null){
			int id = getInstancia().getIdEstruturaOrganizacional();
			String hql = "select o.grupoAdm from GrupoEstrutura o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id;
			GrupoAdm obj = new ConsultaGeral<GrupoAdm>(new StringBuilder(hql)).consultaUnica();
			setGrupoAdm(obj);
		}
	}
	
	public void ocultarDialogAddEstrutura(){
		setExibirDialogEstrutura(false);
		novaInstancia();
		setSelectedNode(null);
	}
	
	public void enviarAtualizar(){
		try {
			super.desativarMensagensCrud();
			atualizarGravarNoEstrutura();
			atualizarGravarGrupo();
			super.mensagem("Atualização realizada com sucesso", null, Constantes.INFO);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
			super.mensagem("Ocorreu uma falha", null, Constantes.ERROR);
		}finally{
			super.ativarMensagensCrud();
		}
	}

	private void atualizarGravarNoEstrutura() throws ExcecaoProfissionalLogado {
		TreeNode noSelecionado = getSelectedNode();
		if(super.isEdicao()){
			atualizarEstrutura(noSelecionado);
		}else{
			cadastrarEstrutura(noSelecionado);
		}
	}

	private void cadastrarEstrutura(TreeNode noSelecionado) throws ExcecaoProfissionalLogado {
		if(noSelecionado != null){
			EstruturaOrganizacional objSelecionado = (EstruturaOrganizacional) noSelecionado.getData();
			getInstancia().setEstruturaPai(objSelecionado);
			if(super.enviar()){
				noSelecionado.setExpanded(true);
				DefaultTreeNode node = new DefaultTreeNode(getInstancia(), noSelecionado);
				noSelecionado.getChildren().add(node);
				gerarLog(TipoCrudEnum.I, getGrupoAdm(), getInstancia());
			}
		}else{
			if(super.enviar()){
				new DefaultTreeNode(getInstancia(), root);
				gerarLog(TipoCrudEnum.I, getGrupoAdm(), getInstancia());
			}
		}
	}

	private void atualizarEstrutura(TreeNode noSelecionado)
			throws ExcecaoProfissionalLogado {
		if(super.atualizar()){
			gerarLog(TipoCrudEnum.A, getGrupoAdm(), getInstancia());
			if(noSelecionado != null){
				noSelecionado.setExpanded(true);
			}
		}
	}

	private void gerarLog(TipoCrudEnum tipo, GrupoAdm grupoAdm, EstruturaOrganizacional estruturaOrganizacional, String justificativa) throws ExcecaoProfissionalLogado {
		AteracaoEstruturaLogRaiz aelr = new AteracaoEstruturaLogRaiz();
		AlteracaoEstruturaLog log = aelr.montarLog(justificativa, grupoAdm, tipo, estruturaOrganizacional);
		aelr.setInstancia(log);
		aelr.enviar();
	}
	
	private void gerarLog(TipoCrudEnum tipo, GrupoAdm grupoAdm, EstruturaOrganizacional estruturaOrganizacional) throws ExcecaoProfissionalLogado {
		gerarLog(tipo, grupoAdm, estruturaOrganizacional, null);
	}

	private void atualizarGravarGrupo() {
		GrupoEstrutura ge = getGrupoAtual();
		ge = ge == null ? new GrupoEstrutura() : ge;
		
		if(getGrupoAdm() == null && ge != null){
			super.apagarGenerico(ge);
		}else{
			ge.setGrupoAdm(getGrupoAdm());
			ge.setEstruturaOrganizacional(getInstancia());
			if(ge.getIdGrupoEstrutura() != 0)
				super.atualizarGenerico(ge);
			else
				super.enviarGenerico(ge);
		}
	}

	private GrupoEstrutura getGrupoAtual() {
		int id = getInstancia().getIdEstruturaOrganizacional();
		String hql = "select o from GrupoEstrutura o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id;
		GrupoEstrutura obj = new ConsultaGeral<GrupoEstrutura>(new StringBuilder(hql)).consultaUnica();
		return obj;
	}
	
	public void deleteNode(){
		EstruturaOrganizacional no = (EstruturaOrganizacional) getSelectedNode().getData();
		if(super.apagarGenerico(no)){
			try {
				carrregarEstrutura();
				gerarLog(TipoCrudEnum.D, null, no);
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			} 
		}
	}
	
    public void carrregarEstrutura() {
        root = new DefaultTreeNode(Constantes.NOME_ROOT_ORGANIZACAO, null);
        root.setExpanded(true);
        root.setSelectable(false);
        List<EstruturaOrganizacional> estruturaList = super.getBusca("select o from EstruturaOrganizacional o");
        for(EstruturaOrganizacional obj : estruturaList){
        	DefaultTreeNode node = null;
        	if(obj.getEstruturaPai() == null){
        		node = new DefaultTreeNode(obj, root);
        		montarArvore(obj, node, estruturaList);
        	}
        }
    }
    
    private void montarArvore(EstruturaOrganizacional objPai, DefaultTreeNode noTreePai, List<EstruturaOrganizacional> nosTotais){
    	noTreePai.setExpanded(true);
    	List<EstruturaOrganizacional> nosFilhos = filhosObj(objPai, nosTotais);
    	for(EstruturaOrganizacional filho : nosFilhos){
    		DefaultTreeNode node = new DefaultTreeNode(filho, noTreePai);
    		List<EstruturaOrganizacional> filhos = filhosObj(filho, nosTotais);
    		if(filhos != null && !filhos.isEmpty()){
    			montarArvore(filho, node, nosTotais);
    		}
    	}
    }
    
    private List<EstruturaOrganizacional> filhosObj(EstruturaOrganizacional obj, List<EstruturaOrganizacional> nosLista){
    	List<EstruturaOrganizacional> retList = new ArrayList<EstruturaOrganizacional>();
    	for(EstruturaOrganizacional item : nosLista){
    		if(item.getEstruturaPai() != null && item.getEstruturaPai().equals(obj)){
    			retList.add(item);
    		}
    	}
    	return retList;
    }
    
    public TreeNode getRoot() {
        return root;
    }

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public boolean isExibirDialogEstrutura() {
		return exibirDialogEstrutura;
	}

	public void setExibirDialogEstrutura(boolean exibirDialogEstrutura) {
		this.exibirDialogEstrutura = exibirDialogEstrutura;
	}

	public boolean isExibirDialogDeslocarNo() {
		return exibirDialogDeslocarNo;
	}

	public void setExibirDialogDeslocarNo(boolean exibirDialogDeslocarNo) {
		this.exibirDialogDeslocarNo = exibirDialogDeslocarNo;
	}

	public EstruturaOrganizacional getOrigemDeslocar() {
		return origemDeslocar;
	}

	public void setOrigemDeslocar(EstruturaOrganizacional origemDeslocar) {
		this.origemDeslocar = origemDeslocar;
	}

	public EstruturaOrganizacional getDestinoDeslocar() {
		return destinoDeslocar;
	}

	public void setDestinoDeslocar(EstruturaOrganizacional destinoDeslocar) {
		this.destinoDeslocar = destinoDeslocar;
	}

	public GrupoAdm getGrupoAdm() {
		return grupoAdm;
	}

	public void setGrupoAdm(GrupoAdm grupoAdm) {
		this.grupoAdm = grupoAdm;
	}

}
