package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="materialCodigoDescricaoAutoComplete")
@RequestScoped
public class MaterialCodigoDescricaoAutoComplete extends ConsultaGeral<Material> {
	
	public Collection<Material> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Material o where ");
		stringB.append("o.codigoMaterial = '"+string+"' or ");
		stringB.append("lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
