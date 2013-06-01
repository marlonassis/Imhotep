package br.com.imhotep.relatorio;

import java.io.IOException;
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
import br.com.imhotep.consulta.relatorio.ConsultaRelatorioMovimentacaoEstoqueUnidade;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueUnidade;

@ManagedBean(name="relatorioMovimentacaoEstoqueUnidade")
@ViewScoped
public class RelatorioMovimentacaoEstoqueUnidade extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	private TipoMovimento tipoMovimento;
	private Unidade unidade;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMovimentacaoEstoqueUnidade.jasper";
		String nomeRelatorio = "EstoqueMovimentacaoUnidade-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		List<MovimentacaoEstoqueUnidade> lista = new ConsultaRelatorioMovimentacaoEstoqueUnidade().consultarResultados(dataIni, dataFim, getUnidade(), getTipoMovimento());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		map.put("nomeUnidade", unidade != null ? unidade.getNome() : "Todos");
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
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
	
}
