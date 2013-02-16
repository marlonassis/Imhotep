package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.CuidadosPaciente;
import br.com.Imhotep.entidade.CuidadosPrescricao;
import br.com.Imhotep.entidade.Prescricao;
import br.com.remendo.PadraoHome;

@ManagedBean(name="cuidadosPrescricaoRaiz")
@SessionScoped
public class CuidadosPrescricaoRaiz extends PadraoHome<CuidadosPrescricao>{

	public CuidadosPrescricaoRaiz(){
		super();
	}
	
	public CuidadosPrescricaoRaiz(CuidadosPrescricao cuidadosPrescricao){
		setInstancia(cuidadosPrescricao);
	}
	
	public boolean enviar(CuidadosPaciente cuidadosPaciente, Prescricao prescricao) {
		getInstancia().setCuidadosPaciente(cuidadosPaciente);
		getInstancia().setPrescricao(prescricao);
		return super.enviar();
	}
	
	public boolean apagar(CuidadosPrescricao cuidadosPrescricao) {
		setInstancia(cuidadosPrescricao);
		return super.apagar();
	}
	
}