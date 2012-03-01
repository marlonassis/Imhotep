package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Chamado;
import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="chamadoHome")
@SessionScoped
public class ChamadoHome extends PadraoHome<Chamado>{
	@Override
	public boolean enviar() {
		getInstancia().setUsuario(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataChamado(new Date());
		getInstancia().setAtendido(TipoBooleanEnum.F);
		return super.enviar();
	}
}
