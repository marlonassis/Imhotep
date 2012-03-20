package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Hospital;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="hospitalHome")
@SessionScoped
public class HospitalHome extends PadraoHome<Hospital>{
	
	/**
	 * Método que retorna uma lista de Hospital
	 * @param String sql
	 * @return Collection Hospital
	 */
	public Collection<Hospital> getListaHospitalAutoComplete(String expressaoConsulta){
		return super.getBusca("select o from Hospital as o where lower(o.nome) like lower('%"+expressaoConsulta+"%') ");
	}
	
}
