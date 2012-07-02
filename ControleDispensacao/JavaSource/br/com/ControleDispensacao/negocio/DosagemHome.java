package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.entidade.PrescricaoItemEstoqueSaida;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="dosagemHome")
@SessionScoped
public class DosagemHome extends PadraoHome<Prescricao>{
	
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
