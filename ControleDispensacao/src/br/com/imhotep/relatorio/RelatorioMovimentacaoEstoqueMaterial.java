package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.CartesianChartModel;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.entidade.TipoMovimentoConsulta;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.consulta.relatorio.ConsultaRelatorioMovimentacaoEstoqueMaterial;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueMaterial;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.grafico.GraficoMaterialConsumo;
import br.com.imhotep.relatorio.excel.RelatorioMovimentacaoEstoqueMaterialExcel;

@ManagedBean
@ViewScoped
public class RelatorioMovimentacaoEstoqueMaterial extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	private Material material;
	private TipoMovimento tipoMovimento;
	private Unidade unidade;
	private TipoOperacaoEnum tipoOperacao;
	private boolean agruparPorLote;
	private CartesianChartModel linearModel = new CartesianChartModel();
	private boolean exibirGrafico;
	private boolean excel;
	
	public void gerarGrafico(){
		if(getMaterial() != null && getDataFim() != null && getDataIni() != null){
			setLinearModel(new GraficoMaterialConsumo().montarGrafico(getMaterial(), getDataIni(), getDataFim()));
			setExibirGrafico(true);
		}
	}
	
	public void fecharModal(){
		setExibirGrafico(false);
	}
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio;
		List<MovimentacaoEstoqueMaterial> lista = new ConsultaRelatorioMovimentacaoEstoqueMaterial().consultarResultados(getMaterial(), dataIni, dataFim, getUnidade(), getTipoMovimento(), getTipoOperacao(), getAgruparPorLote());
		
		dataFim = Utilitarios.ajustarUltimaHoraDia(dataFim);
		String dataI =  new SimpleDateFormat("MM/yyyy").format(dataIni);
		String dataF = new SimpleDateFormat("MM/yyyy").format(dataFim);
		
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioMovimentacaoEstoqueMaterial.jasper";
			nomeRelatorio = "EstoqueMovimentacao-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dataIni", dataI );
			map.put("dataFim",dataF );
			map.put("estoques", new EstoqueConsultaRaiz().consultarEstoquesMaterial(getMaterial()));
			map.put("nomeMaterial", material.getDescricao());
			map.put("totalEntrada", totalEntrada(lista));
			int totalSaida = totalSaida(lista);
			map.put("totalSaida", totalSaida);
			int qtdDias = Utilitarios.qtdDias(getDataIni(), getDataFim());
			map.put("totalDias", qtdDias);
			NumberFormat nf = NumberFormat.getInstance(Constantes.LOCALE_BRASIL);
			nf.setMaximumFractionDigits(2);
			map.put("consumoMedio",  nf.format((float)totalSaida/qtdDias));
			
			InputStream subInputStreamEstoques = this.getClass().getResourceAsStream("RelatorioMovimentacaoEstoqueMaterialEstoques.jasper");
			map.put("SUBREPORT_INPUT_STREAM_ESTOQUES", subInputStreamEstoques);
			super.geraRelatorio(caminho, nomeRelatorio, lista, map);
		}
		else{
			nomeRelatorio = "EstoqueMovimentacao-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioMovimentacaoEstoqueMaterialExcel exc;
	        exc = new RelatorioMovimentacaoEstoqueMaterialExcel(lista, "Farmácia",dataI+" a "+dataF,9, material.getDescricao());
	        exc.gerarPlanilha();
			super.geraRelatorioExcel(nomeRelatorio, exc.getWorkbook());
		}
	}

	private int totalEntrada(List<MovimentacaoEstoqueMaterial> lista) {
		if(lista.get(0).getTipoMovimento() == null)
			return 0;
		
		int total = 0;
		for(MovimentacaoEstoqueMaterial obj : lista){
			if(obj.getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.E)){
				total += obj.getQuantidade();
			}
		}
		return total;
	}
	
	private int totalSaida(List<MovimentacaoEstoqueMaterial> lista) {
		if(lista.get(0).getTipoMovimento() == null)
			return 0;
		
		int total = 0;
		for(MovimentacaoEstoqueMaterial obj : lista){
			if(!obj.getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.E)){
				total += obj.getQuantidade();
			}
		}
		return total;
	}

	public List<TipoMovimento> getListaTipoMovimento(){
		TipoMovimentoConsulta tmc = new TipoMovimentoConsulta();
		if(getTipoOperacao() != null){
			tmc.getInstancia().setTipoOperacao(getTipoOperacao());
		}
		return tmc.getList();
	}
	
	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public TipoOperacaoEnum getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacaoEnum tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public boolean getAgruparPorLote() {
		return agruparPorLote;
	}

	public void setAgruparPorLote(boolean agruparPorLote) {
		this.agruparPorLote = agruparPorLote;
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	public void setLinearModel(CartesianChartModel linearModel) {
		this.linearModel = linearModel;
	}

	public boolean getExibirGrafico() {
		return exibirGrafico;
	}

	public void setExibirGrafico(boolean exibirGrafico) {
		this.exibirGrafico = exibirGrafico;
	}

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	
}
