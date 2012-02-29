package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="bloqueioLoteHome")
@SessionScoped
public class BloqueioLoteHome extends PadraoHome<Estoque>{
	
	@Override
	public boolean enviar() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não é permitido inserir um estoque", "Inserção não autorizada."));
		return false;
	}
	
	@Override
	public boolean apagar() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não é permitido apagar um estoque", "Deleção não autorizada."));
		return false;
	}
	
	@Override
	public boolean atualizar() {
		if(getInstancia().getBloqueado().equals(TipoStatusEnum.S)){
			if(!getInstancia().getMotivoBloqueio().isEmpty()){
				getInstancia().setDataBloqueio(new Date());
				getInstancia().setUsuarioBloqueio(Autenticador.getInstancia().getUsuarioAtual());
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe o motivo do bloqueio", "Atualização não autorizada."));
				return false;
			}
		}else{
			getInstancia().setDataBloqueio(null);
			getInstancia().setUsuarioBloqueio(null);
			getInstancia().setMotivoBloqueio(null);
		}
		return super.atualizar();
	}
}