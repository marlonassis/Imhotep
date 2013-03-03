package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.SubGrupo;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="subGrupoConsulta")
@SessionScoped
public class SubGrupoConsulta extends PadraoConsulta<SubGrupo> {
	public SubGrupoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.grupo", IGUAL);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<SubGrupo> getList() {
		setConsultaGeral(new ConsultaGeral<SubGrupo>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SubGrupo o where 1=1"));
		return super.getList();
	}
}
