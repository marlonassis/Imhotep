package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MenuConsultaRaiz  extends ConsultaGeral<Menu>{

	public List<Menu> consultarMenuPai() {
		String hql = "select o from Menu o where o.menuPai is null order by o.descricao";
		List<Menu> list = new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Menu> consultarMenuAutorizado(Especialidade especialidade) {
		int idEspecialidade = especialidade.getIdEspecialidade();
		String hql = "select o.menu from AutorizaMenu o where o.especialidade.idEspecialidade = "+idEspecialidade+" order by o.menu.descricao";
		List<Menu> list = new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Menu> consultarMenuAutorizado(Profissional profissional) {
		int idProfissional = profissional.getIdProfissional();
		String hql = "select o.menu from AutorizaMenuProfissional o where o.profissional.idProfissional = "+idProfissional+" order by o.menu.descricao";
		List<Menu> list = new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
}
