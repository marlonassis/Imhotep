package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.TipoConselho;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class TipoConselhoConsultaRaiz  extends ConsultaGeral<TipoConselho>{
	
	public List<TipoConselho> consultarTiposConselho() {
		StringBuilder sb = new StringBuilder("select o from TipoConselho as o order by to_ascii(o.descricao)");
		return new ArrayList<TipoConselho>(new ConsultaGeral<TipoConselho>().consulta(sb, null));
	}
	
}
