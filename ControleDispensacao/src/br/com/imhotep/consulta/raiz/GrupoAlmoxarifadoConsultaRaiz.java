package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class GrupoAlmoxarifadoConsultaRaiz  extends ConsultaGeral<GrupoAlmoxarifado>{
	
	public List<GrupoAlmoxarifado> consultarGrupos() {
		StringBuilder sb = new StringBuilder("select o from GrupoAlmoxarifado as o order by to_ascii(o.descricao)");
		return new ArrayList<GrupoAlmoxarifado>(new ConsultaGeral<GrupoAlmoxarifado>().consulta(sb, null));
	}
	
}
