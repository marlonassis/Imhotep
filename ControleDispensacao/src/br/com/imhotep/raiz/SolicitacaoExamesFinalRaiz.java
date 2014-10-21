package br.com.imhotep.raiz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.LaboratorioFormularioItem;
import br.com.imhotep.entidade.LaboratorioSolicitacao;
import br.com.imhotep.entidade.LaboratorioSolicitacaoItem;
import br.com.imhotep.entidade.LaboratorioSolicitacaoItemLog;
import br.com.imhotep.entidade.LaboratorioSolicitacaoLog;
import br.com.imhotep.enums.TipoAutorizacaoSolicitacaoExameEnum;
import br.com.imhotep.enums.TipoAutorizacaoSolicitacaoItemExameEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameItemEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.relatorio.RelatorioExameVisualizacao;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class SolicitacaoExamesFinalRaiz extends PadraoRaiz<LaboratorioSolicitacao>{
	
	private boolean exibirDialogItens;
	private boolean exibirDialogRecusa;
	private boolean exibirDialogAnalise;
	private boolean exibirDialogLogExame;
	private boolean exibirDialogResultados;
	private boolean exibirDialogLogExameItem;
	private boolean exibirDialogJustificativaReabrirExame;
	
	private LaboratorioFormularioItem[] itensAvaliacao;
	
	private boolean exibirDialogRecusaItemExame;
	private boolean exibirDialogReabrirExameItem;
	
	private LaboratorioSolicitacaoItem itemExame;
	
	private String justificativaRecusa;
	private String justificativaReabertura;
	
	private String justificativaRecusaItem;
	private String justificativaReaberturaItem;
	
	public void vizualizarExame(String id){
		try {
			String sql = "select o from LaboratorioSolicitacao o where o.idLaboratorioSolicitacao = "+id;
			new RelatorioExameVisualizacao().gerarRelatorio(getBusca(sql).get(0));
//			new RelatorioExameVisualizacao().gerarRelatorio(getInstancia());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void exibirDialogLogExameItem(){
		setExibirDialogLogExameItem(true);
		atualizarLogExameItem();
	}
	
	public void ocultarDialogLogExameItem(){
		setExibirDialogLogExameItem(false);
		setItemExame(null);
	}
	
	public void exibirDialogLogExame(){
		setExibirDialogLogExame(true);
		atualizarLogExame();
	}

	private void atualizarLogExame() {
		String hql = "select o from LaboratorioSolicitacaoLog o where o.laboratorioSolicitacao.idLaboratorioSolicitacao = " + getInstancia().getIdLaboratorioSolicitacao();
		ConsultaGeral<LaboratorioSolicitacaoLog> consultaGeral = new ConsultaGeral<LaboratorioSolicitacaoLog>(new StringBuilder(hql));
		getInstancia().setLog(new HashSet<LaboratorioSolicitacaoLog>(consultaGeral.consulta()));
	}
	
	private void atualizarLogExameItem() {
		String hql = "select o from LaboratorioSolicitacaoItemLog o where o.laboratorioSolicitacaoItem.idLaboratorioSolicitacaoItem = " + getItemExame().getIdLaboratorioSolicitacaoItem();
		ConsultaGeral<LaboratorioSolicitacaoItemLog> consultaGeral = new ConsultaGeral<LaboratorioSolicitacaoItemLog>(new StringBuilder(hql));
		getItemExame().setLog(new HashSet<LaboratorioSolicitacaoItemLog>(consultaGeral.consulta()));
	}
	
	public void ocultarDialogLogExame(){
		setExibirDialogLogExame(false);
		setItemExame(null);
	}
	
	public void atualizarItem(){
		if(super.atualizarGenerico(getItemExame()) != null)
			ocultarDialogResultados();
	}
	
	public void exibirDialogRecusaItemExame(){
		setExibirDialogRecusaItemExame(true);
	}
	
	public void ocultarDialogRecusaItemExame(){
		setExibirDialogRecusaItemExame(false);
		setJustificativaRecusaItem(null);
	}
	
	public void exibirDialogReabrirExameItem(){
		setExibirDialogReabrirExameItem(true);
	}
	
	public void ocultarDialogReabrirExameItem(){
		setExibirDialogReabrirExameItem(false);
		setJustificativaReaberturaItem(null);
	}
	
	public void exibirDialogItens(){
		setExibirDialogItens(true);
	}
	
	public void ocultarDialogItens(){
		setExibirDialogItens(false);
	}
	
	public void exibirDialogRecusa(){
		setExibirDialogRecusa(true);
	}
	
	public void ocultarDialogRecusa(){
		setExibirDialogRecusa(false);
	}
	
	public void exibirDialogResultados(){
		setExibirDialogResultados(true);
	}
	
	public void ocultarDialogResultados(){
		setExibirDialogResultados(false);
		setItemExame(null);
	}
	
	public boolean getRecusaItemLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoItemExameEnum.BB.name();
		return consultaFaseLiberadaItem(tipo);
	}
	
	public boolean getColetaItemLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoItemExameEnum.BA.name();
		return consultaFaseLiberadaItem(tipo);
	}
	
	public boolean getLancarResultadosLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoItemExameEnum.BC.name();
		return consultaFaseLiberadaItem(tipo);
	}
	
	public boolean getReaberturaItemLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoItemExameEnum.BE.name();
		return consultaFaseLiberadaItem(tipo);
	}
	
	public boolean getAnaliseItemLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoItemExameEnum.BD.name();
		return consultaFaseLiberadaItem(tipo);
	}
	
	public boolean getReaberturaSolicitacaoLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoExameEnum.AF.name();
		return consultaFaseLiberada(tipo);
	}
	
	public boolean getCofirmacaoResultadoLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoExameEnum.AD.name();
		return consultaFaseLiberada(tipo);
	}
	
	public boolean getExameRecusaLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoExameEnum.AB.name();
		return consultaFaseLiberada(tipo);
	}
	
	public boolean exameRecusado(LaboratorioSolicitacao linha){
		return linha.getStatusSolicitacao().equals(TipoStatusSolicitacaoExameEnum.AC);
	}
	
	private boolean consultaFaseLiberadaItem(String tipo) {
		String sql = "select '"+tipo+"' in (select tp_fase_solicitacao_item from laboratorio.tb_laboratorio_exame_autoriza_profissional "
				+ "where id_profissional = "+getIdProfissionalLogado()+")";
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		Object obj = lm.getListaResultado(sql, 1).get(0)[0];
		return obj.toString().equals("true") ? true : false;
	}
	
	private boolean consultaFaseLiberada(String tipo) {
		String sql = "select '"+tipo+"' in (select tp_fase_solicitacao from laboratorio.tb_laboratorio_exame_autoriza_profissional "
				+ "where id_profissional = "+getIdProfissionalLogado()+")";
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		Object obj = lm.getListaResultado(sql, 1).get(0)[0];
		return obj.toString().equals("true") ? true : false;
	}
	
	public void exibirDialogAnalise(){
		List<LaboratorioFormularioItem> itensLista = getItemExame().getLaboratorioExame().getFormulario().getItensLista();
		itensAvaliacao = itensLista.toArray(itensAvaliacao);
		setExibirDialogAnalise(true);
	}
	
	public int getIdProfissionalLogado(){
		try {
			return Autenticador.getProfissionalLogado().getIdProfissional();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void ocultarDialogAnalise(){
		setExibirDialogAnalise(false);
	}
	
	public void exibirDialogJustificativaReabrirExame(){
		setExibirDialogJustificativaReabrirExame(true);
	}
	
	public void ocultarDialogJustificativaReabrirExame(){
		setExibirDialogJustificativaReabrirExame(false);
		setJustificativaReabertura(null);
	}
	
	public void reabrirExameItem(){
		gerarLogSolicitacaoItemExame(getItemExame(), TipoStatusSolicitacaoExameItemEnum.BA, getJustificativaReaberturaItem());
		atualizarStatusItem(getItemExame(), TipoStatusSolicitacaoExameItemEnum.BA);
		ocultarDialogReabrirExameItem();
	}
	
	public void recusarExameItem(){
		gerarLogSolicitacaoItemExame(getItemExame(), TipoStatusSolicitacaoExameItemEnum.BB, getJustificativaRecusaItem());
		atualizarStatusItem(getItemExame(), TipoStatusSolicitacaoExameItemEnum.BB);
		ocultarDialogRecusaItemExame();
	}
	
	public boolean exameItemRecusado(LaboratorioSolicitacaoItem linha){
		return linha.getStatusItem().equals(TipoStatusSolicitacaoExameItemEnum.BB);
	}
	
	public boolean getVisualizarItensLiberado(){
		String tipo = TipoAutorizacaoSolicitacaoExameEnum.AC.name();
		boolean faseLancarResultadosLiberada = consultaFaseLiberada(tipo);
		return getExameRecusaLiberado() || 
				getCofirmacaoResultadoLiberado() || 
				getReaberturaSolicitacaoLiberado() || 
				faseLancarResultadosLiberada;
	}
	
	public boolean exameVerificado(LaboratorioSolicitacao linha){
		boolean verificado = linha.getStatusSolicitacao().equals(TipoStatusSolicitacaoExameEnum.AE);
		boolean impresso = linha.getStatusSolicitacao().equals(TipoStatusSolicitacaoExameEnum.AF);
		return impresso || verificado;
	}
	
	public boolean exameLiberadoImpressao(LaboratorioSolicitacao linha){
		String tipo = TipoAutorizacaoSolicitacaoExameEnum.AE.name();
		boolean faseImpressaoLiberada = consultaFaseLiberada(tipo);
		boolean exameVerificado = linha.getStatusSolicitacao().equals(TipoStatusSolicitacaoExameEnum.AE);
		boolean impresso = linha.getStatusSolicitacao().equals(TipoStatusSolicitacaoExameEnum.AF);
		return (exameVerificado || impresso) && faseImpressaoLiberada;
	}
	
	public void informarColetaItem(){
		gerarLogSolicitacaoItemExame(getItemExame(), TipoStatusSolicitacaoExameItemEnum.BC, null);
		atualizarStatusItem(getItemExame(), TipoStatusSolicitacaoExameItemEnum.BC);
	}
	
	public void imprimirExame(){
		gerarLogSolicitacaoExame(TipoStatusSolicitacaoExameEnum.AF, null);
	}
	
	public void reabrirExame(){
		gerarLogSolicitacaoExame(TipoStatusSolicitacaoExameEnum.AB, getJustificativaReabertura());
		ocultarDialogJustificativaReabrirExame();
	}
	
	public void confirmarVerificacaoExame(){
		gerarLogSolicitacaoExame(TipoStatusSolicitacaoExameEnum.AE, null);
		atualizarStatusExame(getInstancia(), TipoStatusSolicitacaoExameEnum.AE);
		ocultarDialogItens();
	}
	
	private void atualizarStatusItem(LaboratorioSolicitacaoItem item, TipoStatusSolicitacaoExameItemEnum tl){
		item.setStatusItem(tl);
		super.atualizarGenerico(item);
	}
	
	private void atualizarStatusExame(LaboratorioSolicitacao exame, TipoStatusSolicitacaoExameEnum tl){
		exame.setStatusSolicitacao(tl);
		super.atualizarGenerico(exame);
	}
	
	private void gerarLogSolicitacaoItemExame(LaboratorioSolicitacaoItem item, TipoStatusSolicitacaoExameItemEnum tl, String justificativa) {
		item.setStatusItem(tl); 
		try {
			PadraoFluxoTemp.limparFluxo();
			new LaboratorioSolicitacaoItemLogRaiz().gerarLogExame(tl, justificativa, item);
			PadraoFluxoTemp.finalizarFluxo();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
	}
	
	private void gerarLogSolicitacaoExame(TipoStatusSolicitacaoExameEnum tl, String justificativa) {
		getInstancia().setStatusSolicitacao(tl);
		try {
			PadraoFluxoTemp.limparFluxo();
			new LaboratorioSolicitacaoLogRaiz().gerarLogExame(tl, justificativa, getInstancia());
			PadraoFluxoTemp.finalizarFluxo();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
	}
	

	public boolean isExibirDialogJustificativaReabrirExame() {
		return exibirDialogJustificativaReabrirExame;
	}

	public void setExibirDialogJustificativaReabrirExame(
			boolean exibirDialogJustificativaReabrirExame) {
		this.exibirDialogJustificativaReabrirExame = exibirDialogJustificativaReabrirExame;
	}

	public String getJustificativaReabertura() {
		return justificativaReabertura;
	}

	public void setJustificativaReabertura(String justificativaReabertura) {
		this.justificativaReabertura = justificativaReabertura;
	}

	public boolean isExibirDialogAnalise() {
		return exibirDialogAnalise;
	}

	public void setExibirDialogAnalise(boolean exibirDialogAnalise) {
		this.exibirDialogAnalise = exibirDialogAnalise;
	}

	public boolean isExibirDialogResultados() {
		return exibirDialogResultados;
	}

	public void setExibirDialogResultados(boolean exibirDialogResultados) {
		this.exibirDialogResultados = exibirDialogResultados;
	}

	public boolean isExibirDialogRecusa() {
		return exibirDialogRecusa;
	}

	public void setExibirDialogRecusa(boolean exibirDialogRecusa) {
		this.exibirDialogRecusa = exibirDialogRecusa;
	}

	public String getJustificativaRecusa() {
		return justificativaRecusa;
	}

	public void setJustificativaRecusa(String justificativaRecusa) {
		this.justificativaRecusa = justificativaRecusa;
	}

	public boolean isExibirDialogItens() {
		return exibirDialogItens;
	}

	public void setExibirDialogItens(boolean exibirDialogItens) {
		this.exibirDialogItens = exibirDialogItens;
	}

	public LaboratorioSolicitacaoItem getItemExame() {
		return itemExame;
	}

	public void setItemExame(LaboratorioSolicitacaoItem itemExame) {
		this.itemExame = itemExame;
	}

	public String getJustificativaRecusaItem() {
		return justificativaRecusaItem;
	}

	public void setJustificativaRecusaItem(String justificativaRecusaItem) {
		this.justificativaRecusaItem = justificativaRecusaItem;
	}

	public String getJustificativaReaberturaItem() {
		return justificativaReaberturaItem;
	}

	public void setJustificativaReaberturaItem(
			String justificativaReaberturaItem) {
		this.justificativaReaberturaItem = justificativaReaberturaItem;
	}

	public boolean isExibirDialogRecusaItemExame() {
		return exibirDialogRecusaItemExame;
	}

	public void setExibirDialogRecusaItemExame(boolean exibirDialogRecusaItemExame) {
		this.exibirDialogRecusaItemExame = exibirDialogRecusaItemExame;
	}

	public boolean isExibirDialogReabrirExameItem() {
		return exibirDialogReabrirExameItem;
	}

	public void setExibirDialogReabrirExameItem(boolean exibirDialogReabrirExameItem) {
		this.exibirDialogReabrirExameItem = exibirDialogReabrirExameItem;
	}

	public boolean isExibirDialogLogExame() {
		return exibirDialogLogExame;
	}

	public void setExibirDialogLogExame(boolean exibirDialogLogExame) {
		this.exibirDialogLogExame = exibirDialogLogExame;
	}

	public boolean isExibirDialogLogExameItem() {
		return exibirDialogLogExameItem;
	}

	public void setExibirDialogLogExameItem(boolean exibirDialogLogExameItem) {
		this.exibirDialogLogExameItem = exibirDialogLogExameItem;
	}

	public LaboratorioFormularioItem[] getItensAvaliacao() {
		return itensAvaliacao;
	}

	public void setItensAvaliacao(LaboratorioFormularioItem[] itensAvaliacao) {
		this.itensAvaliacao = itensAvaliacao;
	}

}
