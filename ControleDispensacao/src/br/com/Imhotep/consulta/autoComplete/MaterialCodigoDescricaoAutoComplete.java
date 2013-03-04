package br.com.Imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.auxiliar.Utilities;
import br.com.Imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="materialCodigoDescricaoAutoComplete")
@RequestScoped
public class MaterialCodigoDescricaoAutoComplete extends ConsultaGeral<Material> {
	
	public Collection<Material> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Material o where ");
		
		if(Utilities.isNumero(string)){
			stringB.append("o.codigoMaterial = "+string);
		}else{
			stringB.append("lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		}
		return super.consulta(stringB, null);
	}
	
}
