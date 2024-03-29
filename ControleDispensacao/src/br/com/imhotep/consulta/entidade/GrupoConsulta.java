package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Grupo;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class GrupoConsulta extends PadraoConsulta<Grupo> {
	public GrupoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<Grupo> getList() {
		setConsultaGeral(new ConsultaGeral<Grupo>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Grupo o where 1=1"));
		return super.getList();
	}
}
