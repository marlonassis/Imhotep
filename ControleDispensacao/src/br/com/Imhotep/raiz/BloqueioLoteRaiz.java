package br.com.Imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.enums.TipoStatusEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="bloqueioLoteRaiz")
@SessionScoped
public class BloqueioLoteRaiz extends PadraoHome<Estoque>{
	
	public List<Estoque> listaEstoqueVencido(){
		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DAY_OF_MONTH, 5);
		return getBusca("select o from Estoque o where o.bloqueado = false and o.dataValidade <= '"+new SimpleDateFormat("yyyy-MM-hh").format(dataFutura.getTime())+"' order by o.dataValidade"); 
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
	
	public void bloqueioAutomaticoEstoque(){
		getInstancia().setBloqueado(true);
		getInstancia().setMotivoBloqueio("Lote vencido.");
		atualizar();
	}
	
	@Override
	public boolean atualizar() {
		boolean bloqueado = getInstancia().getBloqueado();
		if(bloqueado){
			if(!getInstancia().getMotivoBloqueio().isEmpty()){
				getInstancia().setDataBloqueio(new Date());
				try {
					getInstancia().setUsuarioBloqueio(Autenticador.getInstancia().getUsuarioAtual());
				} catch (Exception e) {
					e.printStackTrace();
					super.mensagem("Erro ao pegar o usuário atual.", null, FacesMessage.SEVERITY_ERROR);
					System.out.print("Erro em BloqueioLoteHome");
				}
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
