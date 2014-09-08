package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.UnidadeMaterialOdontologia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class UnidadeMaterialOdontologiaConsulta extends PadraoConsulta<UnidadeMaterialOdontologia> {
	public UnidadeMaterialOdontologiaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.sigla", IGUAL);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<UnidadeMaterialOdontologia> getList() {
		setConsultaGeral(new ConsultaGeral<UnidadeMaterialOdontologia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from UnidadeMaterialOdontologia o where 1=1"));
		return super.getList();
	}
}
