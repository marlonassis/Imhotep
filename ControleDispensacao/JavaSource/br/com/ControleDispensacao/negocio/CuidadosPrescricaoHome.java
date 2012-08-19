package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.CuidadosPaciente;
import br.com.ControleDispensacao.entidade.CuidadosPrescricao;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="cuidadosPrescricaoHome")
@SessionScoped
public class CuidadosPrescricaoHome extends PadraoHome<CuidadosPrescricao>{

	public CuidadosPrescricaoHome(){
		
	}
	
	public boolean apagarCuidadosPrescricaoHome(CuidadosPaciente cuidadosPaciente){
		return false;
	}
	
	public CuidadosPrescricaoHome(CuidadosPaciente cuidadosPaciente, Prescricao prescricao){
		getInstancia().setCuidadosPaciente(cuidadosPaciente);
		getInstancia().setPrescricao(prescricao);
	}
	
}