package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="especialidadeHome")
@SessionScoped
public class EspecialidadeHome extends PadraoHome<Especialidade>{

	private Collection<Especialidade> getListaEspecialidadeSemConselho(){
		return getBusca("select o from Especialidade o where o.tipoConselho.idTipoConselho is null");
	}
	
	private Collection<Especialidade> getListaEspecialidadePai(){
		return getBusca("select o from Especialidade o where o.especialidadePai is null");
	}
	
	private Collection<Especialidade> getListaEspecialidadeTipoConselho(int idTipoConselho){
		return getBusca("select o from Especialidade o where o.especialidadePai is null and o.tipoConselho.idTipoConselho = " + idTipoConselho);
	}
	
	private Collection<Especialidade> getListaEspecialidadeFilho(Especialidade pai){
		return getBusca("select o from Especialidade o where o.especialidadePai.idEspecialidade = "+pai.getIdEspecialidade());
	}
	
	private void montaTree(TreeNode root, Especialidade pai){
		TreeNode no = new DefaultTreeNode(pai, root);
		for(Especialidade filho : getListaEspecialidadeFilho(pai)){
			montaTree(no, filho);
		}
	}
 	
	private Collection<Especialidade> getListaEspecialidade(Integer idTipoConselho){
		Collection<Especialidade> listaEspecialidadeTipoConselho;
		if(idTipoConselho != null){
    		listaEspecialidadeTipoConselho = getListaEspecialidadeTipoConselho(idTipoConselho);
    	}else{
    		listaEspecialidadeTipoConselho = getListaEspecialidadePai();
    	}
		return listaEspecialidadeTipoConselho;
	}
	
	private TreeNode montarTree(Integer idTipoConselho) {
		TreeNode root = new DefaultTreeNode("root", null);  
    	Collection<Especialidade> listaEspecialidadeTipoConselho = getListaEspecialidade(idTipoConselho);
		for(Especialidade tu : listaEspecialidadeTipoConselho){
    		montaTree(root, tu);
    	}
    	
        return root;
	}
	
	private TreeNode montarTreeSemTipoConselho() {
		TreeNode root = new DefaultTreeNode("root", null);  
    	Collection<Especialidade> listaEspecialidadeTipoConselho = getListaEspecialidadeSemConselho();
		for(Especialidade tu : listaEspecialidadeTipoConselho){
    		montaTree(root, tu);
    	}
    	
        return root;
	}
	
    public TreeNode montarTreeEspecialidadeTipoConselho(int idTipoConselho) {
    	return montarTree(idTipoConselho);
    }  
	
    public TreeNode montarTreeEspecialidadeSemTipoConselho() {
    	return montarTreeSemTipoConselho();
    }  
  
    public TreeNode montarTreeEspecialidade() {
    	return montarTree(null);
    }  
    
	/**
	 * Método que retorna uma lista de Especialidade
	 * @param String sql
	 * @return Collection Especialidade
	 */
	public Collection<Especialidade> getListaEspecialidadeAutoComplete(String sql){
		return super.getBusca("select o from Especialidade as o where lower(o.descricao) like lower('%"+sql+"%') ");
	}
	
	@Override
	public boolean enviar() {
		if(getInstancia().getTipoConselho() == null && getInstancia().getEspecialidadePai() != null){
			getInstancia().setTipoConselho(getInstancia().getEspecialidadePai().getTipoConselho());
		}
		return super.enviar();
	}
}
