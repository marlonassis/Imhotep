package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.EhealthEstado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EhealthEstadoAutoComplete extends ConsultaGeral<EhealthEstado> {
	
	public Collection<EhealthEstado> autoCompleteRegiao(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select distinct o.regiao from EhealthEstado as o where lower(to_ascii(o.regiao)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
	public Collection<EhealthEstado> autoComplete(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o from EhealthEstado as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
