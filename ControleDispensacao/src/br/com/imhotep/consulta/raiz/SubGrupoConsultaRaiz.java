package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.SubGrupo;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SubGrupoConsultaRaiz  extends ConsultaGeral<SubGrupo>{
	
	public List<SubGrupo> consultarSubGrupoGrupo(int idGrupo) {
		StringBuilder sb = new StringBuilder("select o from SubGrupo as o where o.grupo.idGrupo = "+idGrupo);
		return new ArrayList<SubGrupo>(new ConsultaGeral<SubGrupo>().consulta(sb, null));
	}
	
}
