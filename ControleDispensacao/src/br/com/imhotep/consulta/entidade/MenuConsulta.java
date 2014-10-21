package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Menu;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class MenuConsulta extends PadraoConsulta<Menu> {
	public MenuConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.menuPai", IGUAL);
		getCamposConsulta().put("o.url", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<Menu> getList() {
		setConsultaGeral(new ConsultaGeral<Menu>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Menu o where 1=1"));
		return super.getList();
	}
}
