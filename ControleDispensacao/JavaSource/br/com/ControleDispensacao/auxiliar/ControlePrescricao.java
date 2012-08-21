package br.com.ControleDispensacao.auxiliar;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.negocio.ErroAplicacaoHome;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoControle;

public class ControlePrescricao extends PadraoControle{
	
	public boolean gravaPrescricao(Prescricao prescricao) {
		if(prescricao.getIdPrescricao() != 0){
			return true;
		}else{
			return persistePrescricao(prescricao);
		}
	}

	private boolean persistePrescricao(Prescricao prescricao) {
		boolean ret = false;
		try{
			completarPrescricao(prescricao);
			setInstancia(prescricao);
			iniciarTransacao();
			session.save(getInstancia());
			session.flush();
			tx.commit();
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao iniciar a prescrição.", "Utilize o material de apoio para precrever até o sistema voltar ao normal."));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaPrescricao()");
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
	
	private void completarPrescricao(Prescricao prescricao) {
		prescricao.setAno(Calendar.getInstance().get(Calendar.YEAR));
		prescricao.setDataInclusao(new Date());
		prescricao.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		prescricao.setDispensavel(TipoStatusEnum.N);
		prescricao.setDispensado(TipoStatusEnum.N);
		prescricao.setProfissionalInclusao(Autenticador.getInstancia().getProfissionalAtual());
	}	
}
