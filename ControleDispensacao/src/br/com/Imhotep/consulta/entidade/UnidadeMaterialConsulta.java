package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.UnidadeMaterial;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="unidadeMaterialConsulta")
@SessionScoped
public class UnidadeMaterialConsulta extends PadraoConsulta<UnidadeMaterial> {
	public UnidadeMaterialConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.sigla", IGUAL);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<UnidadeMaterial> getList() {
		setConsultaGeral(new ConsultaGeral<UnidadeMaterial>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from UnidadeMaterial o where 1=1"));
		return super.getList();
	}
}
