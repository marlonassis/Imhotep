package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.UnidadeMaterial;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="unidadeMaterialConsulta")
@SessionScoped
public class UnidadeMaterialConsulta extends PadraoConsulta<UnidadeMaterial> {
	public UnidadeMaterialConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<UnidadeMaterial> getList() {
		setConsultaGeral(new ConsultaGeral<UnidadeMaterial>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from UnidadeMaterial o where 1=1"));
		return super.getList();
	}
}
