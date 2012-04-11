package br.com.ControleDispensacao.negocio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	
	public List<Estoque> listaEstoqueVencido(){
		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DAY_OF_MONTH, 5);
		return getBusca("select o from Estoque o where o.bloqueado = 'N' and o.dataValidade <= '"+new SimpleDateFormat("yyyy-MM-hh").format(dataFutura.getTime())+"' order by o.dataValidade"); 
	}
	
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
	
	public void bloqueioAutomáticoEstoque(){
		getInstancia().setBloqueado(TipoStatusEnum.S);
		getInstancia().setMotivoBloqueio("Bloqueio automático. Lote vencido.");
		atualizar();
	}
	
	@Override
	public boolean atualizar() {
		boolean bloqueado = getInstancia().getBloqueado().equals(TipoStatusEnum.S);
		if(bloqueado){
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
