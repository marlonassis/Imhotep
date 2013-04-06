package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Familia;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class FamiliaConsultaRaiz  extends ConsultaGeral<Familia>{
	
	public List<Familia> consultarFamiliasSubGrupo(int idSubGrupo) {
		StringBuilder sb = new StringBuilder("select o from Familia as o where o.subGrupo.idSubGrupo = "+idSubGrupo);
		return new ArrayList<Familia>(new ConsultaGeral<Familia>().consulta(sb, null));
	}
	
}
