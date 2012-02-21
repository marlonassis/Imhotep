package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.SubGrupoOrigemPrescricao;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="subGrupoOrigemPrescricaoConsulta")
@SessionScoped
public class SubGrupoOrigemPrescricaoConsulta extends PadraoConsulta<SubGrupoOrigemPrescricao> {
	public SubGrupoOrigemPrescricaoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.grupoOrigemPrescricao", IGUAL);
		setOrderBy("o.descricao, o.grupoOrigemPrescricao.descricao");
	}
	
	@Override
	public List<SubGrupoOrigemPrescricao> getList() {
		setConsultaGeral(new ConsultaGeral<SubGrupoOrigemPrescricao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SubGrupoOrigemPrescricao o where 1=1"));
		return super.getList();
	}
}
