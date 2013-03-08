package br.com.Imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.enums.TipoBloqueioLoteEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.remendo.PadraoHome;

@ManagedBean(name="bloqueioLoteRaiz")
@SessionScoped
public class BloqueioLoteRaiz extends PadraoHome<Estoque>{
	
	private boolean loteEncontrado;
	
	public List<Estoque> listaEstoqueVencido(){
		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DAY_OF_MONTH, 5);
		return getBusca("select o from Estoque o where o.bloqueado = false and (to_char(o.dataValidade, 'yyyy-MM') <= '"+new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime())+"' or to_char(o.dataValidade, 'yyyy-MM') <= '"+new SimpleDateFormat("yyyy-MM").format(dataFutura.getTime())+"') order by o.dataValidade"); 
	}
	
	public boolean medicamentoVencido(Date validade){
		Calendar atual = Calendar.getInstance();
		Calendar vali = Calendar.getInstance();
		vali.setTime(validade);
		atual.set(Calendar.DAY_OF_MONTH, 01);
		vali.set(Calendar.DAY_OF_MONTH, 01);
		if(vali.after(atual)){
			return false;
		}
		return true;
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
		getInstancia().setTipoBloqueio(TipoBloqueioLoteEnum.V);
		atualizar();
	}

	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setLoteEncontrado(false);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getLote();
		Estoque estoque = new EstoqueLoteConsultaRaiz().consultar(lote);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			setInstancia(estoque);
		}else{
			mensagem("Lote não encontrado.", lote, Constantes.WARN);
		}
	}
	
	@Override
	public boolean atualizar() {
		try {
			Autenticador autenticador = Autenticador.getInstancia();
			getInstancia().setUsuarioBloqueio(autenticador.getUsuarioAtual());
			getInstancia().setDataBloqueio(new Date());
			procedimentoDesbloqueioEstoque();
			if(getInstancia().getTipoBloqueio()!=null&&!getInstancia().getTipoBloqueio().equals(TipoBloqueioLoteEnum.O)){
				getInstancia().setMotivoBloqueio(null);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return super.atualizar();
	}

	private void procedimentoDesbloqueioEstoque() {
		if(!getInstancia().getBloqueado()){
			getInstancia().setBloqueado(false);
			getInstancia().setTipoBloqueio(null);
			getInstancia().setMotivoBloqueio(null);
			getInstancia().setTipoBloqueio(null);
			getInstancia().setDataBloqueio(null);
			getInstancia().setUsuarioBloqueio(null);
		}
	}
	
	public boolean isLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}
	
	
	
}
