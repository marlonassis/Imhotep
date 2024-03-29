package br.com.imhotep.controle;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.entidade.ErroAplicacao;
import br.com.imhotep.entidade.PrescricaoItem;
import br.com.imhotep.enums.TipoStatusEnum;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItem;
import br.com.imhotep.raiz.ErroAplicacaoRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

public class ControlePrescricaoItem extends PadraoRaiz<PrescricaoItem>{
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao gravar a o item da prescri��o.", "Utilize o material de apoio para precrever at� o sistema voltar ao normal."));
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
			super.mensagem("Erro ao pegar o usu�rio atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControleMedicamentoRestrito");
		}
		new ErroAplicacaoRaiz(ea).enviar();
	}
	
	
	public void removePrescricaoItem(PrescricaoItem tupla){
		super.setInstancia(tupla);
		super.apagar();
	}
}
