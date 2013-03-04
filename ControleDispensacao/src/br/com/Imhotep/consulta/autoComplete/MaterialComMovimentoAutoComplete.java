package br.com.Imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.auxiliar.Utilities;
import br.com.Imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="materialComMovimentoAutoComplete")
@RequestScoped
public class MaterialComMovimentoAutoComplete extends ConsultaGeral<Material> {
	
	public Collection<Material> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select distinct o.estoque.material from MovimentoLivro o where ");
		
		if(Utilities.isNumero(string)){
			stringB.append("o.estoque.material.codigoMaterial = "+string);
		}else{
			stringB.append("lower(to_ascii(o.estoque.material.descricao)) like lower(to_ascii('%"+string+"%'))");
		}
		return super.consulta(stringB, null);
	}
	
}
