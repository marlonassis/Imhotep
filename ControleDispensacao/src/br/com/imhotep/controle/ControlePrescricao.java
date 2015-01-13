package br.com.imhotep.controle;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.entidade.ErroAplicacao;
import br.com.imhotep.entidade.Prescricao;
import br.com.imhotep.enums.TipoStatusEnum;
import br.com.imhotep.raiz.ErroAplicacaoRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoControle;

public class ControlePrescricao extends PadraoControle{
	
	public boolean atualizaPrescricao(Prescricao prescricao) {
		super.setInstancia(prescricao);
		return super.atualizar();
	}
	
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao iniciar a prescri��o.", "Utilize o material de apoio para precrever at� o sistema voltar ao normal."));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaPrescricao()");
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
			super.mensagem("Erro ao pegar o usu�rio atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControlePrescricao");
		}
		new ErroAplicacaoRaiz(ea).enviar();
	}
	
	private void completarPrescricao(Prescricao prescricao) {
		prescricao.setDataInclusao(new Date());
		try {
//			prescricao.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		}  catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usu�rio atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControlePrescricao");
		}
		prescricao.setDispensavel(false);
		prescricao.setDispensado(false);
		try {
			prescricao.setProfissionalInclusao(Autenticador.getInstancia().getProfissionalAtual());
		}  catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usu�rio atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControlePrescricao");
		}
	}	
}
