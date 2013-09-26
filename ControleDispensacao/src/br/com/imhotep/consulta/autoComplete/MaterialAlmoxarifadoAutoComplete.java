package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MaterialAlmoxarifadoAutoComplete extends ConsultaGeral<MaterialAlmoxarifado> {
	
	public Collection<MaterialAlmoxarifado> autoComplete(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o from MaterialAlmoxarifado as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%')) and o.bloqueado = false");
		return super.consulta(stringB, null);
	}
	
	public Collection<String> autoCompleteApenasDescricao(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o.descricao from MaterialAlmoxarifado as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%')) and o.bloqueado = false");
		return new ConsultaGeral<String>(stringB).consulta();
	}
	
	public Collection<MaterialAlmoxarifado> autoCompleteDescricaoCodigo(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o from MaterialAlmoxarifado o where ");
		
		if(Utilitarios.isNumero(string)){
			stringB.append(" o.codigoMaterial = "+string);
		}else{
			stringB.append("lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		}
		
		stringB.append(" and o.bloqueado = false order by o.descricao");
		
		return super.consulta(stringB, null);
	}
	
}
