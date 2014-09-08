package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.GrupoOdontologia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class GrupoOdontologiaConsulta extends PadraoConsulta<GrupoOdontologia> {
	public GrupoOdontologiaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<GrupoOdontologia> getList() {
		setConsultaGeral(new ConsultaGeral<GrupoOdontologia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from GrupoOdontologia o where 1=1"));
		return super.getList();
	}
}
