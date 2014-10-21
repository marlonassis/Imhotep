package br.com.imhotep.consulta.autoComplete;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstoqueAutoComplete extends ConsultaGeral<Estoque> {
	
	public Collection<Estoque> autoComplete(String string){
		if(string != null){
			string = string.trim();
			String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			String hql = "select o from Estoque o where o.bloqueado = false and "
					+ "o.dataValidade >= '"+dataS+"' and to_ascii(lower(o.lote)) like to_ascii(lower('%"+string+"%'))";
			List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(new StringBuilder(hql), null));
			return list;
		}
		return new ArrayList<Estoque>();
	}
	
}
