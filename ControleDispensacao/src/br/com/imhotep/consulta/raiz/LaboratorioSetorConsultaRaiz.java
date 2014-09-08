package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.LaboratorioSetor;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class LaboratorioSetorConsultaRaiz  extends ConsultaGeral<LaboratorioSetor>{

	public List<LaboratorioSetor> getListaSetores(){
		String hql = "select o from LaboratorioSetor o ";
		StringBuilder sb = new StringBuilder(hql);
		ConsultaGeral<LaboratorioSetor> cg = new ConsultaGeral<LaboratorioSetor>();
		List<LaboratorioSetor> res = new ArrayList<LaboratorioSetor>(cg.consulta(sb, null));
		return res;
	}
	
}