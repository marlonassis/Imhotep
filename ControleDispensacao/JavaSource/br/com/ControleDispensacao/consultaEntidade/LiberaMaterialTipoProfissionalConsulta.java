package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.LiberaMaterialTipoProfissional;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="liberaMaterialTipoProfissionalConsulta")
@SessionScoped
public class LiberaMaterialTipoProfissionalConsulta extends PadraoConsulta<LiberaMaterialTipoProfissional> {
	public LiberaMaterialTipoProfissionalConsulta(){
		getCamposConsulta().put("o.material", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoProfissional", INCLUINDO_TUDO);
		setOrderBy("o.tipoProfissional.descricao, o.material.descricao");
	}
	
	@Override
	public List<LiberaMaterialTipoProfissional> getList() {
		setConsultaGeral(new ConsultaGeral<LiberaMaterialTipoProfissional>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from LiberaMaterialTipoProfissional o where 1=1"));
		return super.getList();
	}
}
