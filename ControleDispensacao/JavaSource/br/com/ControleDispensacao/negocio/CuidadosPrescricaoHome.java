package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.CuidadosPaciente;
import br.com.ControleDispensacao.entidade.CuidadosPrescricao;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.remendo.PadraoHome;

@ManagedBean(name="cuidadosPrescricaoHome")
@SessionScoped
public class CuidadosPrescricaoHome extends PadraoHome<CuidadosPrescricao>{

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