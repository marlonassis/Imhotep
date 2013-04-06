package br.com.imhotep.controle;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.entidade.ControleMedicacaoRestritoSCHI;
import br.com.imhotep.entidade.ErroAplicacao;
import br.com.imhotep.enums.TipoStatusEnum;
import br.com.imhotep.raiz.ErroAplicacaoRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoControle;

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
			super.finallyTransacao();
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
		try {
			ea.setUsuario(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usuário atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControleMedicamentoRestrito");
		}
		new ErroAplicacaoRaiz(ea).enviar();
	}
	
}
