package br.com.imhotep.raiz;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.imhotep.consulta.raiz.PainelConsultaRaiz;
import br.com.imhotep.entidade.AutorizaPainelProfissional;
import br.com.imhotep.entidade.Painel;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class AutorizaPainelProfissionalRaiz extends PadraoHome<AutorizaPainelProfissional>{

	private TreeNode root;
	private List<Painel> paineisAutorizado;
	private TreeNode[] selectedNodes;
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		paineisAutorizado = null;
		selectedNodes = null;
		root = null;
	}
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		getInstancia().setDataInsercao(new Date());
		super.preEnvio();
	}
	
	public void atualizarAutorizacao(){
		for(TreeNode node : getSelectedNodes()){
			Painel painel = (Painel) node.getData();
			if(getPaineisAutorizado().contains(painel)){
				removePainel(painel);
			}else{
				addPainel(painel);
			}
		}
		carregarTreePainel();
	}
	
	private void addPainel(Painel painel){
		AutorizaPainelProfissionalRaiz appr = new AutorizaPainelProfissionalRaiz();
		appr.getInstancia().setProfissional(getInstancia().getProfissional());
		appr.getInstancia().setPainel(painel);
		appr.setExibeMensagemInsercao(false);
		appr.enviar();
	}
	
	private void removePainel(Painel painel){
		String hql = "delete from AutorizaPainelProfissional where profissional.idProfissional = "+ getInstancia().getProfissional().getIdProfissional()+
				" and painel.idPainel = " + painel.getIdPainel();
		super.executa(hql);
	}
	
	public void carregarTreePainel(){
		if(getInstancia().getProfissional() != null){
			List<Painel> paineis = new PainelConsultaRaiz().todosPaineis();
			setPaineisAutorizado(new PainelConsultaRaiz().todosPaineis(getInstancia().getProfissional()));
			root = new DefaultTreeNode("Root", null);
			for(Painel painel : paineis){
				new DefaultTreeNode(painel, root);
			}
		}
	}
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<Painel> getPaineisAutorizado() {
		return paineisAutorizado;
	}

	public void setPaineisAutorizado(List<Painel> paineisAutorizado) {
		this.paineisAutorizado = paineisAutorizado;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

}
