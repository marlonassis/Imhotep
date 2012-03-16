package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Familia;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="familiaConsulta")
@SessionScoped
public class FamiliaConsulta extends PadraoConsulta<Familia> {
	public FamiliaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.subGrupo", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<Familia> getList() {
		setConsultaGeral(new ConsultaGeral<Familia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Familia o where 1=1"));
		return super.getList();
	}
}
