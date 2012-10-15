package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.MovimentoGeral;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="movimentoGeralHome")
@SessionScoped
public class MovimentoGeralHome extends PadraoHome<MovimentoGeral>{
	
	@Override
	public boolean enviar() {
		try {
			getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usuário Atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em MovimentoGeralHome");
		}
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
