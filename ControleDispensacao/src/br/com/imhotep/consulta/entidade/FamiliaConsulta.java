package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Familia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="familiaConsulta")
@SessionScoped
public class FamiliaConsulta extends PadraoConsulta<Familia> {
	
	public FamiliaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.subGrupo", IGUAL);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<Familia> getList() {
		setConsultaGeral(new ConsultaGeral<Familia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Familia o where 1=1"));
		return super.getList();
	}

}
