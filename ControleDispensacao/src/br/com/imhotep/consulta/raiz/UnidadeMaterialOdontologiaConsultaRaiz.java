package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.UnidadeMaterialOdontologia;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class UnidadeMaterialOdontologiaConsultaRaiz  extends ConsultaGeral<UnidadeMaterialOdontologia>{
	
	public List<UnidadeMaterialOdontologia> getTodasUnidadesMaterialOdontologia() {
		StringBuilder sb = new StringBuilder("select o from UnidadeMaterialOdontologia as o order by to_ascii(o.descricao)");
		return new ArrayList<UnidadeMaterialOdontologia>(new ConsultaGeral<UnidadeMaterialOdontologia>().consulta(sb, null));
	}
	
}
