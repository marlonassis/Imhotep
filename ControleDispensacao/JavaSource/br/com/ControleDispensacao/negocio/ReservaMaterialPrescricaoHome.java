package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ReservaMaterialPrescricao;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="reservaMaterialPrescricaoHome")
@SessionScoped
public class ReservaMaterialPrescricaoHome extends PadraoHome<ReservaMaterialPrescricao>{
	
	@Override
	public boolean enviar() {
		getInstancia().setDataReserva(new Date());
		return super.enviar();
	}
	
}
