package br.com.Imhotep.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.entidade.PrescricaoItem;
import br.com.Imhotep.entidade.PrescricaoItemDose;
import br.com.Imhotep.entidade.PrescricaoItemEstoqueSaida;
import br.com.remendo.PadraoHome;

@ManagedBean(name="dosagemRaiz")
@SessionScoped
public class DosagemRaiz extends PadraoHome<Prescricao>{
	
	public List<PrescricaoItemEstoqueSaida> doses(){
		
//		for(PrescricaoItemEstoqueSaida pies : getInstancia().)
		return null;
	}
	
	public List<PrescricaoItemDose> listaPrescricaoItemDose(PrescricaoItem item){
		if(item != null){
			return new ArrayList<PrescricaoItemDose>(item.getPrescricaoItemDoses());
		}
		return null;
	}
	
}
