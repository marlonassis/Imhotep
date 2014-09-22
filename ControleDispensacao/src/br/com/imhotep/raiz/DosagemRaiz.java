package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Prescricao;
import br.com.imhotep.entidade.PrescricaoItem;
import br.com.imhotep.entidade.PrescricaoItemDose;
import br.com.imhotep.entidade.PrescricaoItemEstoqueSaida;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="dosagemRaiz")
@SessionScoped
public class DosagemRaiz extends PadraoRaiz<Prescricao>{
	
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
