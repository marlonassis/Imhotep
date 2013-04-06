package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.SubGrupoConsultaRaiz;
import br.com.imhotep.entidade.Familia;
import br.com.imhotep.entidade.Grupo;
import br.com.imhotep.entidade.SubGrupo;
import br.com.remendo.PadraoHome;

@ManagedBean(name="familiaRaiz")
@SessionScoped
public class FamiliaRaiz extends PadraoHome<Familia>{
	
	private Grupo grupo;
	private List<SubGrupo> sugGrupoList = new ArrayList<SubGrupo>();
	
	public void carregaSubgGrupoList(){
		if(grupo != null){
			sugGrupoList = new SubGrupoConsultaRaiz().consultarSubGrupoGrupo(grupo.getIdGrupo());
		}
	}
	
	@Override
	public void novaInstancia() {
		grupo = null;
		super.novaInstancia();
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
