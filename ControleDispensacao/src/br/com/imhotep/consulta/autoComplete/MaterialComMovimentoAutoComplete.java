package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MaterialComMovimentoAutoComplete extends ConsultaGeral<Material> {
	
	public Collection<Material> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select distinct o.estoque.material from MovimentoLivro o where ");
		stringB.append("o.estoque.material.codigoMaterial = '"+string+"' or ");
		stringB.append("lower(to_ascii(o.estoque.material.descricao)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
