package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.AlmoxarifadoUnidadeCota;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class AlmoxarifadoUnidadeCotaConsultaRaiz  extends ConsultaGeral<AlmoxarifadoUnidadeCota>{

	public Integer cotaMaterialUnidade(MaterialAlmoxarifado ma, Unidade un) {
		String sql = "select coalesce(o.cota, 0) from AlmoxarifadoUnidadeCota o where o.materialAlmoxarifado.idMaterialAlmoxarifado = "
						+ma.getIdMaterialAlmoxarifado()
						+" and o.unidade.idUnidade = "+un.getIdUnidade();
		StringBuilder sb = new StringBuilder(sql);
		ConsultaGeral<Integer> cg = new ConsultaGeral<Integer>();
		return cg.consultaUnica(sb, null);
	}
	
}