package br.com.Imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Hospital;
import br.com.remendo.PadraoHome;

@ManagedBean(name="hospitalRaiz")
@SessionScoped
public class HospitalRaiz extends PadraoHome<Hospital>{
	
	/**
	 * Método que retorna uma lista de Hospital
	 * @param String sql
	 * @return Collection Hospital
	 */
	public Collection<Hospital> getListaHospitalAutoComplete(String expressaoConsulta){
		return super.getBusca("select o from Hospital as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+expressaoConsulta+"%')) ");
	}
	
}
