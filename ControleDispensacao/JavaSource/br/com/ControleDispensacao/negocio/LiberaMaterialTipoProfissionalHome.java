package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.LiberaMaterialTipoProfissional;
import br.com.ControleDispensacao.entidade.TipoProfissional;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="liberaMaterialTipoProfissionalHome")
@SessionScoped
public class LiberaMaterialTipoProfissionalHome extends PadraoHome<LiberaMaterialTipoProfissional>{
	
	/**
	 * Método que retorna uma lista de LiberaMaterialTipoPrescritor de acordo com a string informada pelo usuário
	 * @param id
	 * @return Collection LiberaMaterialTipoPrescritor
	 */
	public Collection<LiberaMaterialTipoProfissional> getListaLiberaMaterialTipoPrescritorAutoComplete(Integer id){
		return super.getBusca("select o from LiberaMaterialTipoProfissional as o where o.idLiberaMaterialTipoProfissional = "+id+" ");
	}
	
	@Override
	public boolean enviar() {
		TipoProfissional tipoProfissional = getInstancia().getTipoProfissional();
		if(super.enviar()){
			super.novaInstancia();
			getInstancia().setTipoProfissional(tipoProfissional);
			return true;
		}
		return false;
	}

}
