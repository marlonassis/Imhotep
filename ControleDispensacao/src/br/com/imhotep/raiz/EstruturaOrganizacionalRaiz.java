package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.EstruturaOrganizacional;
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
    
	public EstruturaOrganizacionalRaiz(){
		super();
		carrregarEstrutura();
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
		getOrigemDeslocar().setEstruturaPai(getDestinoDeslocar());
		if(super.atualizarGenerico(getOrigemDeslocar()) != null){
			carrregarEstrutura();
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
		}else{
			super.mensagem("Selecione algum n’vel da estrutura para poder atualiz‡-la", null, Constantes.WARN);
		}
	}
	
	public void exibirDialogAddEstrutura(){
		super.novaInstancia();
		setExibirDialogEstrutura(true);
	}
	
	public void ocultarDialogAddEstrutura(){
		setExibirDialogEstrutura(false);
		novaInstancia();
		setSelectedNode(null);
	}
	
	public void enviarAtualizar(){
		TreeNode noSelecionado = getSelectedNode();
		if(super.isEdicao()){
			if(super.atualizar()){
				if(noSelecionado != null){
					noSelecionado.setExpanded(true);
				}
			}
		}else{
			if(noSelecionado != null){
				EstruturaOrganizacional objSelecionado = (EstruturaOrganizacional) noSelecionado.getData();
				getInstancia().setEstruturaPai(objSelecionado);
				if(super.enviar()){
					noSelecionado.setExpanded(true);
					DefaultTreeNode node = new DefaultTreeNode(getInstancia(), noSelecionado);
					noSelecionado.getChildren().add(node);
				}
			}else{
				if(super.enviar()){
					new DefaultTreeNode(getInstancia(), root);
				}
			}
		}
	}
	
	public void deleteNode(){
		EstruturaOrganizacional no = (EstruturaOrganizacional) getSelectedNode().getData();
		super.apagarGenerico(no);
		carrregarEstrutura();
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

}
