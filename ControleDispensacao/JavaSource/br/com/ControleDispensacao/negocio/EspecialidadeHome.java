package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="especialidadeHome")
@SessionScoped
public class EspecialidadeHome extends PadraoHome<Especialidade>{
	
	/**
	 * Método que retorna uma lista de Especialidade de acordo com o TipoProfissional
	 * @param String sql
	 * @return Collection Especialidade
	 */
	public Collection<Especialidade> getListaEspecialidadeTipoConselho(Integer id){
		return super.getBusca("select o from Especialidade as o where o.tipoProfissional.idTipoProfissional = "+id);
	}
	
}
