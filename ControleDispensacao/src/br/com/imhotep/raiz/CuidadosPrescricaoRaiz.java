package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.CuidadosPaciente;
import br.com.imhotep.entidade.CuidadosPrescricao;
import br.com.imhotep.entidade.Prescricao;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="cuidadosPrescricaoRaiz")
@SessionScoped
public class CuidadosPrescricaoRaiz extends PadraoRaiz<CuidadosPrescricao>{

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