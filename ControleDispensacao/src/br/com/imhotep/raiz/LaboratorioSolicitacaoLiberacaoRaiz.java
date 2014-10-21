package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.LaboratorioSolicitacao;
import br.com.imhotep.entidade.LaboratorioSolicitacaoItem;
import br.com.imhotep.entidade.LaboratorioSolicitacaoItemLog;
import br.com.imhotep.entidade.LaboratorioSolicitacaoLog;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameItemEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LaboratorioSolicitacaoLiberacaoRaiz extends PadraoRaiz<LaboratorioSolicitacao>{
	
	private List<LaboratorioSolicitacao> examesSolicitados = new ArrayList<LaboratorioSolicitacao>();
	private LaboratorioSolicitacao exame = new LaboratorioSolicitacao();
	private LaboratorioSolicitacaoItem itemExame = new LaboratorioSolicitacaoItem();
	private boolean exibirDialogExame;
	private boolean exibirDialogJustificativaExame;
	private boolean exibirDialogJustificativaExameItem;
	private boolean exibirDialogJustificativaSolicitacaoExameItem;
	private String justificativaItem;
	private String justificativaExame;

	public void exibirDialogJustificativaSolicitacaoExameItem(){
		setExibirDialogJustificativaSolicitacaoExameItem(true);
	}
	
	public void ocultarDialogJustificativaSolicitacaoExameItem(){
		setExibirDialogJustificativaSolicitacaoExameItem(false);
	}
	
	public void desfazerRecusaItem(){
		if(getJustificativaItem() != null){
			try {
				PadraoFluxoTemp.limparFluxo();
				getItemExame().setStatusItem(TipoStatusSolicitacaoExameItemEnum.BA);
				gerarLogItem(TipoStatusSolicitacaoExameItemEnum.BA, getJustificativaItem(), getItemExame());
				PadraoFluxoTemp.finalizarFluxo();
				PadraoFluxoTemp.limparFluxo();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			} catch (ExcecaoPadraoFluxo e) {
				e.printStackTrace();
			}
		}else{
			super.mensagem("Informe a justificativa", null, Constantes.WARN);
		}
	}
	
	public void liberarSolicitacao(){
		try {
			PadraoFluxoTemp.limparFluxo();
			getExame().setStatusSolicitacao(TipoStatusSolicitacaoExameEnum.AB);
			PadraoFluxoTemp.getObjetoAtualizar().put("LaboratorioSolicitacao - "+getExame().hashCode(), getExame());
			gerarLogExame(TipoStatusSolicitacaoExameEnum.AB, null);
			PadraoFluxoTemp.finalizarFluxo();
			PadraoFluxoTemp.limparFluxo();
			setExibirDialogExame(false);
			consultarExamesSolicitados();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
	}
	
	public void recusarExameItem(){
		if(getJustificativaItem() != null){
			try {
				PadraoFluxoTemp.limparFluxo();
				getItemExame().setStatusItem(TipoStatusSolicitacaoExameItemEnum.BB);
				gerarLogItem(TipoStatusSolicitacaoExameItemEnum.BB, getJustificativaItem(), getItemExame());
				PadraoFluxoTemp.finalizarFluxo();
				PadraoFluxoTemp.limparFluxo();
				setJustificativaItem(null);
				ocultarDialogRecusaExameItem();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			} catch (ExcecaoPadraoFluxo e) {
				e.printStackTrace();
			}
		}else{
			super.mensagem("Informe a justificativa", null, Constantes.WARN);
		}
	}
	
	private void gerarLogItem(TipoStatusSolicitacaoExameItemEnum tipo, String justificativa, LaboratorioSolicitacaoItem item) throws ExcecaoProfissionalLogado, ExcecaoPadraoFluxo {
		LaboratorioSolicitacaoItemLog log = new LaboratorioSolicitacaoItemLog();
		log.setDataLog(new Date());
		log.setJustificativa(justificativa);
		log.setLaboratorioSolicitacaoItem(item);
		log.setProfissionalLog(Autenticador.getProfissionalLogado());
		log.setTipoLog(tipo);
		PadraoFluxoTemp.getObjetoAtualizar().put("LaboratorioSolicitacaoItem-"+item.hashCode(), item);
		PadraoFluxoTemp.getObjetoSalvar().put("LaboratorioSolicitacaoItemLog - "+log.hashCode(), log);
	}
	
	private void gerarLogExame(TipoStatusSolicitacaoExameEnum tipo, String justificativa) throws ExcecaoProfissionalLogado,
			ExcecaoPadraoFluxo {
		LaboratorioSolicitacaoLog log = new LaboratorioSolicitacaoLog();
		log.setDataLog(new Date());
		log.setJustificativa(justificativa);
		log.setLaboratorioSolicitacao(getExame());
		log.setProfissionalLog(Autenticador.getProfissionalLogado());
		log.setTipoLog(tipo);
		PadraoFluxoTemp.getObjetoAtualizar().put("LaboratorioSolicitacao-"+getExame().hashCode(), getExame());
		PadraoFluxoTemp.getObjetoSalvar().put("LaboratorioSolicitacaoLog - "+log.hashCode(), log);
	}
	
	public void consultarExamesSolicitados(){
		setExamesSolicitados(getBusca("select o from LaboratorioSolicitacao o where o.statusSolicitacao = 'AA'"));
	}
	
	public void exibirDialogRecusaExameItem(){
		setExibirDialogJustificativaExameItem(true);
	}
	
	public void ocultarDialogRecusaExameItem(){
		setExibirDialogJustificativaExameItem(false);
		setItemExame(null);
	}
	
	public void exibirDialogRecusaExame(){
		setExibirDialogJustificativaExame(true);
	}
	
	public void ocultarDialogRecusaExame(){
		setExibirDialogJustificativaExame(false);
		setExame(null);
	}
	
	public void exibirDialogExame(){
		setExibirDialogExame(true);
	}
	
	public void ocultarDialogExame(){
		setExibirDialogExame(false);
		setExame(null);
	}
	
	public List<LaboratorioSolicitacao> getExamesSolicitados() {
		return examesSolicitados;
	}
	public void setExamesSolicitados(List<LaboratorioSolicitacao> examesSolicitados) {
		this.examesSolicitados = examesSolicitados;
	}
	public LaboratorioSolicitacao getExame() {
		return exame;
	}
	public void setExame(LaboratorioSolicitacao exame) {
		this.exame = exame;
	}
	public LaboratorioSolicitacaoItem getItemExame() {
		return itemExame;
	}
	public void setItemExame(LaboratorioSolicitacaoItem item) {
		this.itemExame = item;
	}
	public boolean isExibirDialogExame() {
		return exibirDialogExame;
	}
	public void setExibirDialogExame(boolean exibirDialogExame) {
		this.exibirDialogExame = exibirDialogExame;
	}

	public boolean isExibirDialogJustificativaExame() {
		return exibirDialogJustificativaExame;
	}

	public void setExibirDialogJustificativaExame(boolean exibirDialogJustificativaExame) {
		this.exibirDialogJustificativaExame = exibirDialogJustificativaExame;
	}

	public boolean isExibirDialogJustificativaExameItem() {
		return exibirDialogJustificativaExameItem;
	}

	public void setExibirDialogJustificativaExameItem(boolean exibirDialogJustificativaExameItem) {
		this.exibirDialogJustificativaExameItem = exibirDialogJustificativaExameItem;
	}

	public String getJustificativaItem() {
		return justificativaItem;
	}

	public void setJustificativaItem(String justificativaRecusaItem) {
		this.justificativaItem = justificativaRecusaItem;
	}

	public String getJustificativaExame() {
		return justificativaExame;
	}

	public void setJustificativaExame(String justificativaRecusaExame) {
		this.justificativaExame = justificativaRecusaExame;
	}

	public boolean isExibirDialogJustificativaSolicitacaoExameItem() {
		return exibirDialogJustificativaSolicitacaoExameItem;
	}

	public void setExibirDialogJustificativaSolicitacaoExameItem(
			boolean exibirDialogJustificativaSolicitacaoExameItem) {
		this.exibirDialogJustificativaSolicitacaoExameItem = exibirDialogJustificativaSolicitacaoExameItem;
	}
	
}
