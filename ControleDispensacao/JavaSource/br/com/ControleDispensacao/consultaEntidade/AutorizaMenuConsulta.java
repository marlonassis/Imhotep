package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.AutorizaMenu;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="autorizaMenuConsulta")
@SessionScoped
public class AutorizaMenuConsulta extends PadraoConsulta<AutorizaMenu> {
	public AutorizaMenuConsulta(){
		getCamposConsulta().put("o.menu.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.especialidade.descricao", INCLUINDO_TUDO);
		setOrderBy("o.especialidade.descricao, o.menu.descricao");
	}
	
	@Override
	public List<AutorizaMenu> getList() {
		setConsultaGeral(new ConsultaGeral<AutorizaMenu>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AutorizaMenu o where 1=1"));
		return super.getList();
	}
}
