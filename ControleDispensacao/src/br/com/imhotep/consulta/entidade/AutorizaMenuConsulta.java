package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AutorizaMenu;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="autorizaMenuConsulta")
@SessionScoped
public class AutorizaMenuConsulta extends PadraoConsulta<AutorizaMenu> {
	public AutorizaMenuConsulta(){
		getCamposConsulta().put("o.menu.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.especialidade.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.especialidade.descricao), to_ascii(o.menu.descricao)");
	}
	
	@Override
	public List<AutorizaMenu> getList() {
		setConsultaGeral(new ConsultaGeral<AutorizaMenu>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AutorizaMenu o where 1=1"));
		return super.getList();
	}
}
