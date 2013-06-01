package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MaterialAutoComplete extends ConsultaGeral<Material> {
	
	public Collection<Material> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Material as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
	public Collection<String> autoCompleteApenasDescricao(String string){
		StringBuilder stringB = new StringBuilder("select o.descricao from Material as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		return new ConsultaGeral<String>(stringB).consulta();
	}
	
	public Collection<Material> autoCompleteDescricaoCodigo(String string){
		StringBuilder stringB = new StringBuilder("select o from Material o where ");
		
		if(Utilitarios.isNumero(string)){
			stringB.append("o.codigoMaterial = "+string);
		}else{
			stringB.append("lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		}
		return super.consulta(stringB, null);
	}
	
}
