package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Cargo;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class CargoConsultaRaiz  extends ConsultaGeral<Cargo>{
	public List<Cargo> getCargosLista() {
		String hql = "select o from Cargo o order by to_ascii(lower(o.nome))";
		List<Cargo> list = new ArrayList<Cargo>(new ConsultaGeral<Cargo>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Cargo> cargosProfissionalLista(Profissional prof) {
		String hql = "select o.cargo from CargoProfissional o where o.profissional.idProfissional = "+prof.getIdProfissional()+
				" order by to_ascii(lower(o.cargo.nome))";
		List<Cargo> list = new ArrayList<Cargo>(new ConsultaGeral<Cargo>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Cargo> getCargosGroup() {
		List<Cargo> lista = new ArrayList<Cargo>();
		String sql = "select id_cargo, cv_nome from "
						+ "administrativo.tb_cargo "
						+ "where id_cargo_pai is null "
						+ "order by cv_nome";
		List<Object[]> listaResultado = new LinhaMecanica("db_imhotep").getListaResultado(sql);
		for(Object[] obj : listaResultado){
			Cargo cargo = new Cargo();
			cargo.setIdCargo(Integer.valueOf(String.valueOf(obj[0])));
			cargo.setNome(String.valueOf(obj[1]));
			lista.add(cargo);
			String sqlcargosFilho = "select id_cargo, cv_nome from "
										+ "administrativo.tb_cargo "
										+ "where id_cargo_pai = " + cargo.getIdCargo()
										+ "order by cv_nome";
			
			List<Object[]> listaFilhos = new LinhaMecanica("db_imhotep").getListaResultado(sqlcargosFilho);
			for(Object[] obj2 : listaFilhos){
				Cargo cargoFilho = new Cargo();
				cargoFilho.setIdCargo(Integer.valueOf(String.valueOf(obj2[0])));
				cargoFilho.setNome(String.valueOf(obj2[1]));
				cargoFilho.setCargoPai(cargo);
				lista.add(cargoFilho);
			}
		}
		return lista;
	}
}