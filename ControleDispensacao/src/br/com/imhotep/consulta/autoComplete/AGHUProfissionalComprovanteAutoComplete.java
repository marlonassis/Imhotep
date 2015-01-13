package br.com.imhotep.consulta.autoComplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.linhaMecanica.LinhaMecanicaAGHU;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class AGHUProfissionalComprovanteAutoComplete  extends ConsultaGeral<String>{

	public Collection<String> autoComplete(String nome) {
		nome = nome.trim();
		String sql = "select profissional, especialidade, crm from agh.temp_compro_consul "+
				"where lower(profissional) ilike lower('%" + nome + "%') "+
				"group by profissional, especialidade, crm "
				+ "order by profissional, especialidade, crm";
		LinhaMecanicaAGHU lma = new LinhaMecanicaAGHU();
		List<Object[]> listaResultado = lma.getListaResultado(sql);
		Collection<String> res = new ArrayList<String>();
		for(Object[] o : listaResultado){
			String item = String.valueOf(o[0]).concat(" / ").concat(String.valueOf(o[1]));
			String crm = String.valueOf(o[2]);
			if(crm != null && !crm.isEmpty() && !crm.equals("null"))
				item = crm + "-" + item;
			res.add(item);
		}
		
		if(res.size() > 0)
			return res;
		else{
			List<String> l = new ArrayList<String>();
			l.add("Não encontrado");
			return l;
		}
	}
	
}