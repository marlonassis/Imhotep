package br.com.Imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.EstoqueLog;
import br.com.Imhotep.enums.TipoBloqueioLoteEnum;
import br.com.Imhotep.enums.TipoEstoqueLog;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.remendo.PadraoHome;

@ManagedBean(name="bloqueioLoteRaiz")
@SessionScoped
public class BloqueioLoteRaiz extends PadraoHome<Estoque>{
	
	private boolean loteEncontrado;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
	
	public List<Estoque> listaEstoqueVencido(){
		return getBusca("select o from Estoque o where o.bloqueado = false and (to_char(o.dataValidade, 'yyyy-MM') < '"+new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime())+"' or to_char(o.dataValidade, 'yyyy-MM') = '"+new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime())+"') order by o.dataValidade, to_ascii(lower(o.material.descricao))"); 
	}
	
	public void carregarEstoqueConsultaMaterial(Estoque estoque){
		loteEncontrado = true;
		setInstancia(estoque);
	}
	
	public boolean medicamentoVencido(Date validade){
		Calendar atual = Calendar.getInstance();
		Calendar vali = Calendar.getInstance();
		vali.setTime(validade);
		vali.set(Calendar.DAY_OF_MONTH, vali.getActualMaximum(Calendar.DAY_OF_MONTH));
		if(vali.after(atual)){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean enviar() {
		super.mensagem("Não é permitido inserir um estoque", "Inserção não autorizada.", Constantes.ERROR);
		return false;
	}
	
	@Override
	public boolean apagar() {
		super.mensagem("Não é permitido apagar um estoque", "Deleção não autorizada", Constantes.ERROR);
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
		if(super.atualizar()){
			if(getInstancia().getBloqueado()){
				EstoqueLog log = EstoqueLogRaiz.carregarLog(new Date(), getInstancia().getLote(), getInstancia().getMaterial().getDescricao(), TipoEstoqueLog.O, sdf.format(getInstancia().getDataValidade()));
				new EstoqueLogRaiz().gerarLog(log);
			}else{
				EstoqueLog log = EstoqueLogRaiz.carregarLog(new Date(), getInstancia().getLote(), getInstancia().getMaterial().getDescricao(), TipoEstoqueLog.P, sdf.format(getInstancia().getDataValidade()));
				new EstoqueLogRaiz().gerarLog(log);
			}
			return true;
		}
		return false;
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
