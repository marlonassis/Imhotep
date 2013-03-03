package br.com.Imhotep.controle;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.Imhotep.entidade.ErroAplicacao;
import br.com.Imhotep.entidade.PrescricaoItemDose;
import br.com.Imhotep.entidade.extra.Dose;
import br.com.Imhotep.enums.TipoStatusEnum;
import br.com.Imhotep.raiz.ErroAplicacaoRaiz;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoControle;

public class ControlePrescricaoItemDose extends PadraoControle{
	
	public boolean gravaPrescricaoItemDose(Dose dose) {
		boolean ret = false;
		try{
			iniciarTransacao();
			Calendar dataReferencia = Calendar.getInstance();
			dataReferencia.setTime(dose.getDataInicio());
			for(int i = 0; i < dose.getQuantidadeDoses(); i++){
				PrescricaoItemDose temp = new PrescricaoItemDose();
				temp.setDataDose(dataReferencia.getTime());
				temp.setPeriodo(dose.getIntervaloEntreDoses());
				temp.setQuantidade(dose.getQuantidadePorDose());
				dataReferencia.add(Calendar.HOUR, dose.getIntervaloEntreDoses());
				temp.setPrescricaoItem(dose.getPrescricaoItem());
				session.save(temp);
			}
			session.flush();  
			tx.commit();
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao gravar a o item da prescrição.", "Utilize o material de apoio para precrever até o sistema voltar ao normal."));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaPrescricaoItemDose(Dose dose)");
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
	
	public void removeDose(PrescricaoItemDose tupla){
		super.setInstancia(tupla);
		super.apagar();
	}
}
