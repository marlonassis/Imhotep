package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MaterialCompletoConsultaRaiz  extends ConsultaGeral<Material>{

	public List<Material> consultar() {
		List<Material> list = new ArrayList<Material>(new ConsultaGeral<Material>().consulta(new StringBuilder("select o from Material o order by to_ascii(lower(o.descricao))"), null));
		return list;
	}
	
	public List<Material> materialPadronizado() {
		StringBuilder stringB = new StringBuilder("select o from Material o where o.padronizado = true order by to_ascii(lower(o.descricao))");
		List<Material> list = new ArrayList<Material>(new ConsultaGeral<Material>().consulta(stringB, null));
		return list;
	}
}
