package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.GrupoAdm;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class GrupoAdmConsulta extends PadraoConsulta<GrupoAdm> {
	
	public GrupoAdmConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<GrupoAdm> getList() {
		setConsultaGeral(new ConsultaGeral<GrupoAdm>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from GrupoAdm o where 1=1"));
		return super.getList();
	}
}
