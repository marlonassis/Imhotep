package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Especialidade;
import br.com.Imhotep.entidade.Menu;
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
	
}
