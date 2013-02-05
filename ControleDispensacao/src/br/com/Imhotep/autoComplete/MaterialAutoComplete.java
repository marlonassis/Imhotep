package br.com.Imhotep.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="materialAutoComplete")
@RequestScoped
public class MaterialAutoComplete extends ConsultaGeral<Material> {
	
	public Collection<Material> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Material as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
