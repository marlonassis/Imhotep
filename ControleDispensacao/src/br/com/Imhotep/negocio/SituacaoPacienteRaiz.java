package br.com.Imhotep.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.SituacaoPaciente;
import br.com.remendo.PadraoHome;

@ManagedBean(name="situacaoPacienteRaiz")
@SessionScoped
public class SituacaoPacienteRaiz extends PadraoHome<SituacaoPaciente>{
	
	/**
	 * Método que retorna uma lista de SituacaoPaciente
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<SituacaoPaciente> getListaSituacaoPacienteSuggest(String sql){
		return super.getBusca("select o from SituacaoPaciente as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
