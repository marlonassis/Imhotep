package br.com.ControleDispensacao.consultaEntidade;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Familia;
import br.com.ControleDispensacao.entidade.Grupo;
import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.ControleDispensacao.negocio.SubGrupoHome;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="familiaConsulta")
@SessionScoped
public class FamiliaConsulta extends PadraoConsulta<Familia> {
	
	private List<SubGrupo> sugGrupoList = new ArrayList<SubGrupo>();
	
	public void carregaSubgGrupoList(){
		if(getInstancia().getSubGrupo() != null && getInstancia().getSubGrupo().getGrupo() != null){
			setSugGrupoList((List<SubGrupo>) new SubGrupoHome().getListaSubGrupoGrupo(getInstancia().getSubGrupo().getGrupo().getIdGrupo()));
		}
	}
	
	public FamiliaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.subGrupo.descricao", IGUAL);
		getCamposConsulta().put("o.subGrupo.grupo.descricao", IGUAL);
		setOrderBy("o.descricao");
		getInstancia().setSubGrupo(new SubGrupo());
		getInstancia().getSubGrupo().setGrupo(new Grupo());
	}
	
	@Override
	public List<Familia> getList() {
		if(getInstancia().getSubGrupo() == null || getInstancia().getSubGrupo().getIdSubGrupo() == 0){
			getInstancia().setSubGrupo(new SubGrupo());
			getInstancia().getSubGrupo().setGrupo(new Grupo());
		}
		setConsultaGeral(new ConsultaGeral<Familia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Familia o where 1=1"));
		return super.getList();
	}

	public List<SubGrupo> getSugGrupoList() {
		return sugGrupoList;
	}

	public void setSugGrupoList(List<SubGrupo> sugGrupoList) {
		this.sugGrupoList = sugGrupoList;
	}
}
