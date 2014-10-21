package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.LaboratorioExame;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class LaboratorioExameConsultaRaiz  extends ConsultaGeral<LaboratorioExame>{
	
	public List<LaboratorioExame> consultarExamesValidos() {
		String string = "select o from LaboratorioExame as o where o.bloqueado is false ";
		StringBuilder sb = new StringBuilder(string);
		return new ArrayList<LaboratorioExame>(new ConsultaGeral<LaboratorioExame>().consulta(sb, null));
	}
	
}
