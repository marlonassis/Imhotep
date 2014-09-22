package br.com.imhotep.raiz;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PrecoMedioTransportadoMedLynx;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PrecoMedioTransportadoMedLynxRaiz extends PadraoRaiz<PrecoMedioTransportadoMedLynx>{
	
	@Override
	public boolean enviar() {
		boolean ret = false;
		List<PrecoMedioTransportadoMedLynx> busca = super.getBusca("select o from PrecoMedioTransportadoMedLynx o where o.material.idMaterial = "+getInstancia().getMaterial().getIdMaterial());
		if(busca != null && busca.size() > 0){
			getInstancia().setIdPrecoMedioTransportadoMedLynx(busca.get(0).getIdPrecoMedioTransportadoMedLynx());
			ret = super.atualizar();
		}else{
			ret = super.enviar();
		}
		super.novaInstancia();
		return ret;
	}
	
}
