package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Familia;
import br.com.ControleDispensacao.entidade.Grupo;
import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="familiaHome")
@SessionScoped
public class FamiliaHome extends PadraoHome<Familia>{
	
	private Grupo grupo;
	private List<SubGrupo> sugGrupoList = new ArrayList<SubGrupo>();
	
	public void carregaSubgGrupoList(){
		SubGrupoHome sgh = new SubGrupoHome();
		if(grupo != null){
			sugGrupoList = (List<SubGrupo>) sgh.getListaSubGrupoGrupoSuggest(grupo.getIdGrupo());
		}
	}
	
	/**
	 * Método que retorna uma lista de Aplicacao
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Familia> getListaFabricanteSuggest(String sql){
		return super.getBusca("select o from Familia as o where o.descricao like '%"+sql+"%' ");
	}
	
	@Override
	public void novaInstancia() {
		grupo = null;
		super.novaInstancia();
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

	@Override
	public void setInstancia(Familia instancia) {
		//ao editar um registro o grupo deve ser setado e os subgrupos devem ser carregados
		setGrupo(instancia.getSubGrupo().getGrupo());
		carregaSubgGrupoList();
		super.setInstancia(instancia);
	}
	
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<SubGrupo> getSugGrupoList() {
		return sugGrupoList;
	}

	public void setSugGrupoList(List<SubGrupo> sugGrupoList) {
		this.sugGrupoList = sugGrupoList;
	}
}