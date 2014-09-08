package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SubGrupoAlmoxarifadoConsultaRaiz  extends ConsultaGeral<SubGrupoAlmoxarifado>{
	
	public List<SubGrupoAlmoxarifado> consultarSubGrupoGrupo(int idGrupo) {
		String string = "select o from SubGrupoAlmoxarifado as o where o.grupoAlmoxarifado.idGrupoAlmoxarifado = "+idGrupo
				+ " order by lower(to_ascii(o.descricao)) ";
		StringBuilder sb = new StringBuilder(string);
		return new ArrayList<SubGrupoAlmoxarifado>(new ConsultaGeral<SubGrupoAlmoxarifado>().consulta(sb, null));
	}
	
}
