package br.com.Imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Especialidade;
import br.com.Imhotep.entidade.LiberaMaterialEspecialidade;
import br.com.remendo.PadraoHome;

@ManagedBean(name="liberaMaterialEspecialidadeRaiz")
@SessionScoped
public class LiberaMaterialEspecialidadeRaiz extends PadraoHome<LiberaMaterialEspecialidade>{
	
	/**
	 * Método que retorna uma lista de LiberaMaterialTipoPrescritor de acordo com a string informada pelo usuário
	 * @param id
	 * @return Collection LiberaMaterialTipoPrescritor
	 */
	public Collection<LiberaMaterialEspecialidade> getListaLiberaMaterialTipoPrescritorAutoComplete(Integer id){
		return super.getBusca("select o from LiberaMaterialTipoProfissional as o where o.idLiberaMaterialTipoProfissional = "+id+" ");
	}
	
	@Override
	public boolean enviar() {
		Especialidade especialidade = getInstancia().getEspecialidade();
		if(super.enviar()){
			super.novaInstancia();
			getInstancia().setEspecialidade(especialidade);
			return true;
		}
		return false;
	}

}
