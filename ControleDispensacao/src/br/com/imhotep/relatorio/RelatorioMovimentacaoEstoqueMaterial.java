package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMovimentacaoEstoqueMaterial.jasper";
		String nomeRelatorio = "EstoqueMovimentacao-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		List<MovimentacaoEstoqueMaterial> lista = new ConsultaRelatorioMovimentacaoEstoqueMaterial().consultarResultados(getMaterial(), dataIni, dataFim, getUnidade(), getTipoMovimento(), getTipoOperacao(), getAgruparPorLote());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		map.put("estoques", new EstoqueConsultaRaiz().consultarEstoquesMaterial(getMaterial()));
		map.put("nomeMaterial", material.getDescricao());
		InputStream subInputStreamEstoques = this.getClass().getResourceAsStream("RelatorioMovimentacaoEstoqueMaterialEstoques.jasper");
		map.put("SUBREPORT_INPUT_STREAM_ESTOQUES", subInputStreamEstoques);
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
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
	
}
