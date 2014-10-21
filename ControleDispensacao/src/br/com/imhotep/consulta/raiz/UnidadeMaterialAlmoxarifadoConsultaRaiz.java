package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.UnidadeMaterialAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class UnidadeMaterialAlmoxarifadoConsultaRaiz  extends ConsultaGeral<UnidadeMaterialAlmoxarifado>{
	
	public List<UnidadeMaterialAlmoxarifado> getTodasUnidadesMaterialAlmoxarifado() {
		StringBuilder sb = new StringBuilder("select o from UnidadeMaterialAlmoxarifado as o order by to_ascii(o.descricao)");
		return new ArrayList<UnidadeMaterialAlmoxarifado>(new ConsultaGeral<UnidadeMaterialAlmoxarifado>().consulta(sb, null));
	}
	
}
