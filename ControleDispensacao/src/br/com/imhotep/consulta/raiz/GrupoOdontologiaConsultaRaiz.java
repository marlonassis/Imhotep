package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.GrupoOdontologia;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class GrupoOdontologiaConsultaRaiz  extends ConsultaGeral<GrupoOdontologia>{
	
	public List<GrupoOdontologia> consultarGrupos() {
		StringBuilder sb = new StringBuilder("select o from GrupoOdontologia as o order by to_ascii(o.descricao)");
		return new ArrayList<GrupoOdontologia>(new ConsultaGeral<GrupoOdontologia>().consulta(sb, null));
	}
	
}
