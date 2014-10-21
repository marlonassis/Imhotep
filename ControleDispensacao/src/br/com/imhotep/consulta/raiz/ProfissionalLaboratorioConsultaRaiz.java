package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class ProfissionalLaboratorioConsultaRaiz  extends ConsultaGeral<Profissional>{

	public List<Profissional> consultar() {
		StringBuilder sb = new StringBuilder("select o.profissional from AutorizaUnidadeProfissional o where o.unidade.idUnidade = " + Constantes.ID_UNIDADE_LABORATORIO);
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		ConsultaGeral<Profissional> cg = new ConsultaGeral<Profissional>();
		return new ArrayList<Profissional>(cg.consulta(sb, map));
	}
	
}
