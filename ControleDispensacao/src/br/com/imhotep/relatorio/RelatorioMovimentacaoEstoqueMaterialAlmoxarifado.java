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

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.chart.CartesianChartModel;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.entidade.TipoMovimentoAlmoxarifadoConsulta;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.relatorio.ConsultaRelatorioMovimentacaoEstoqueMaterialAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueMaterialAlmoxarifado;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.grafico.GraficoMaterialAlmoxarifadoConsumo;

@ManagedBean
@ViewScoped
public class RelatorioMovimentacaoEstoqueMaterialAlmoxarifado extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	private MaterialAlmoxarifado materialAlmoxarifado;
	private TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado;
	private Unidade unidade;
	private TipoOperacaoEnum tipoOperacao;
	private boolean agruparPorLote;
	private CartesianChartModel linearModel = new CartesianChartModel();
	private boolean exibirGrafico;
	
	public void gerarGrafico(){
		if(getMaterialAlmoxarifado() != null && getDataFim() != null && getDataIni() != null){
			setLinearModel(new GraficoMaterialAlmoxarifadoConsumo().montarGrafico(getMaterialAlmoxarifado(), getDataIni(), getDataFim()));
			setExibirGrafico(true);
		}
	}
	
	public void fecharModal(){
		setExibirGrafico(false);
	}
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMovimentacaoEstoqueMaterialAlmoxarifado.jasper";
		String nomeRelatorio = "EstoqueMovimentacaoAmloxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		List<MovimentacaoEstoqueMaterialAlmoxarifado> lista = new ConsultaRelatorioMovimentacaoEstoqueMaterialAlmoxarifado().consultarResultados(getMaterialAlmoxarifado(), dataIni, dataFim, getUnidade(), getTipoMovimentoAlmoxarifado(), getTipoOperacao(), getAgruparPorLote());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		map.put("estoques", new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoquesMaterial(getMaterialAlmoxarifado()));
		map.put("nomeMaterial", materialAlmoxarifado.getIdMaterialAlmoxarifado() + " - " + materialAlmoxarifado.getDescricao());
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

	private int totalEntrada(List<MovimentacaoEstoqueMaterialAlmoxarifado> lista) {
		if(lista.get(0).getTipoMovimentoAlmoxarifado() == null)
			return 0;
		
		int total = 0;
		for(MovimentacaoEstoqueMaterialAlmoxarifado obj : lista){
			if(obj.getTipoMovimentoAlmoxarifado().getTipoOperacao().equals(TipoOperacaoEnum.E)){
				total += obj.getQuantidade();
			}
		}
		return total;
	}
	
	private int totalSaida(List<MovimentacaoEstoqueMaterialAlmoxarifado> lista) {
		if(lista.get(0).getTipoMovimentoAlmoxarifado() == null)
			return 0;
		
		int total = 0;
		for(MovimentacaoEstoqueMaterialAlmoxarifado obj : lista){
			if(!obj.getTipoMovimentoAlmoxarifado().getTipoOperacao().equals(TipoOperacaoEnum.E)){
				total += obj.getQuantidade();
			}
		}
		return total;
	}

	public List<TipoMovimentoAlmoxarifado> getListaTipoMovimento(){
		TipoMovimentoAlmoxarifadoConsulta tmc = new TipoMovimentoAlmoxarifadoConsulta();
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

	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}

	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
	}

	public TipoMovimentoAlmoxarifado getTipoMovimentoAlmoxarifado() {
		return tipoMovimentoAlmoxarifado;
	}

	public void setTipoMovimentoAlmoxarifado(TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado) {
		this.tipoMovimentoAlmoxarifado = tipoMovimentoAlmoxarifado;
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
	
}
