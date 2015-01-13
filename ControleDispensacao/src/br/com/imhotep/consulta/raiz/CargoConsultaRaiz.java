package br.com.imhotep.consulta.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.Cargo;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class CargoConsultaRaiz  extends ConsultaGeral<Cargo>{
	
	public String getNomeCpfCargo(int idProfissional, String nomeCpf){
		String sql = "select a.cv_nome from administrativo.tb_cargo a "+
				"inner join administrativo.tb_cargo_profissional b "+
				"	on a.id_cargo = b.id_cargo "+
				"where b.id_profissional = " + idProfissional;
		ResultSet rs = new LinhaMecanica().consultar(sql);
		try {
		if(rs.next()){
			String descricao = nomeCpf.concat(" / ").concat(rs.getString(1));
			return descricao;
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return nomeCpf;
	}
	
	public List<String> cargosProfissional(Profissional profissional){
		List<String> cargos = new ArrayList<String>();
		if(profissional != null){
			String sql = "select a.cv_nome cargo from administrativo.tb_cargo a "+
							"inner join administrativo.tb_cargo_profissional b on a.id_cargo = b.id_cargo "+
							"inner join tb_profissional c on b.id_profissional = c.id_profissional "+
							"where c.id_profissional = " + profissional.getIdProfissional() + " order by to_ascii(lower(a.cv_nome))";
			List<Object[]> listaResultado = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).getListaResultado(sql);
			for(Object[] obj : listaResultado){
				cargos.add(String.valueOf(obj[0]));
			}
		}
		return cargos;
	}
	
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
						+ "order by to_ascii(lower(cv_nome))";
		List<Object[]> listaResultado = new LinhaMecanica().getListaResultado(sql);
		for(Object[] obj : listaResultado){
			Cargo cargo = new Cargo();
			cargo.setIdCargo(Integer.valueOf(String.valueOf(obj[0])));
			cargo.setNome(String.valueOf(obj[1]));
			lista.add(cargo);
			String sqlcargosFilho = "select id_cargo, cv_nome from "
										+ "administrativo.tb_cargo "
										+ "where id_cargo_pai = " + cargo.getIdCargo()
										+ "order by to_ascii(lower(cv_nome))";
			
			List<Object[]> listaFilhos = new LinhaMecanica().getListaResultado(sqlcargosFilho);
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