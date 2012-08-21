package br.com.ControleDispensacao.auxiliar;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.negocio.ErroAplicacaoHome;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoControle;

public class ControleMedicacaoRestrito extends PadraoControle{
	
	public ControleMedicacaoRestrito() {
		super();
	}
	
	public boolean gravaRestricao(ControleMedicacaoRestritoSCHI medicacaoRestritoSCHI) {
		boolean ret = false;
		if(medicacaoRestritoSCHI.getIdControleMedicacaoRestritoSCHI() != 0){
			return true;
		}
		try{
			iniciarTransacao();
			session.save(medicacaoRestritoSCHI);
			session.flush();  
			tx.commit();
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro ao gravar a autorização.", ""));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaRestricao(ControleMedicacaoRestritoSCHI medicacaoRestritoSCHI)");
			session.getTransaction().rollback();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}
	
	protected void gravaErroAplicacao(Date date, String message, StackTraceElement[] stackTrace, String metodo) {
		ErroAplicacao ea = new ErroAplicacao();
		ea.setAtendido(TipoStatusEnum.N);
		ea.setDataOcorrencia(date);
		ea.setMessage(message);
		ea.setMetodo(metodo);
		String pagina = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
		ea.setPagina(pagina);
		ea.setStackTrace(stackTrace.toString());
		ea.setUsuario(Autenticador.getInstancia().getUsuarioAtual());
		new ErroAplicacaoHome(ea).enviar();
	}
	
}
