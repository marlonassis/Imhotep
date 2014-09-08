package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.SubGrupoOdontologia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class SubGrupoOdontologiaConsulta extends PadraoConsulta<SubGrupoOdontologia> {
	public SubGrupoOdontologiaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.grupoOdontologia", IGUAL);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<SubGrupoOdontologia> getList() {
		setConsultaGeral(new ConsultaGeral<SubGrupoOdontologia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SubGrupoOdontologia o where 1=1"));
		return super.getList();
	}
}
