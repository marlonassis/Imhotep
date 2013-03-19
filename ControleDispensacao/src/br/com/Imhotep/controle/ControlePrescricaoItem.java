package br.com.Imhotep.controle;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.Imhotep.entidade.ErroAplicacao;
import br.com.Imhotep.entidade.PrescricaoItem;
import br.com.Imhotep.enums.TipoStatusEnum;
import br.com.Imhotep.raiz.ErroAplicacaoRaiz;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItem;
import br.com.remendo.PadraoHome;

public class ControlePrescricaoItem extends PadraoHome<PrescricaoItem>{
	public boolean gravaPrescricaoItem(PrescricaoItem prescricaoItem) throws ExcecaoControlePrescricaoItem {
		boolean ret = false;
		
		try{
			iniciarTransacao();
			prescricaoItem.setDispensado(TipoStatusEnum.N);
			prescricaoItem.setStatus(TipoStatusEnum.S);
			session.save(prescricaoItem);
			session.flush();  
			tx.commit();
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao gravar a o item da prescrição.", "Utilize o material de apoio para precrever até o sistema voltar ao normal."));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaPrescricaoItem()");
			session.getTransaction().rollback();
		}finally{
			super.finallyTransacao();
		}
		
		if(!ret)
			throw new ExcecaoControlePrescricaoItem();
		
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
		}  catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usuário atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControleMedicamentoRestrito");
		}
		new ErroAplicacaoRaiz(ea).enviar();
	}
	
	
	public void removePrescricaoItem(PrescricaoItem tupla){
		super.setInstancia(tupla);
		super.apagar();
	}
}
