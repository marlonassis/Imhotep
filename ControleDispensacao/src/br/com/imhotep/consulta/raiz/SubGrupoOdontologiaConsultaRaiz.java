package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.SubGrupoOdontologia;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SubGrupoOdontologiaConsultaRaiz  extends ConsultaGeral<SubGrupoOdontologia>{
	
	public List<SubGrupoOdontologia> consultarSubGrupoGrupo(int idGrupo) {
		String string = "select o from SubGrupoOdontologia as o where o.grupoOdontologia.idGrupoOdontologia = "+idGrupo
				+ " order by lower(to_ascii(o.descricao)) ";
		StringBuilder sb = new StringBuilder(string);
		return new ArrayList<SubGrupoOdontologia>(new ConsultaGeral<SubGrupoOdontologia>().consulta(sb, null));
	}
	
}
