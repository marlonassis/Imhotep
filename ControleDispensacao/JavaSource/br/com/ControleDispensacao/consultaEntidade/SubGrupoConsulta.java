package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="subGrupoConsulta")
@SessionScoped
public class SubGrupoConsulta extends PadraoConsulta<SubGrupo> {
	public SubGrupoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.grupo", IGUAL);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<SubGrupo> getList() {
		setConsultaGeral(new ConsultaGeral<SubGrupo>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SubGrupo o where 1=1"));
		return super.getList();
	}
}
