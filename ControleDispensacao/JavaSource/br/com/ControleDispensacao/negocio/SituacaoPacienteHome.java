package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.SituacaoPaciente;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="situacaoPacienteHome")
@SessionScoped
public class SituacaoPacienteHome extends PadraoHome<SituacaoPaciente>{
	
	/**
	 * Método que retorna uma lista de Aplicacao
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<SituacaoPaciente> getListaSituacaoPacienteSuggest(String sql){
		return super.getBusca("select o from SituacaoPaciente as o where o.descricao like '%"+sql+"%' ");
	}
	
}
