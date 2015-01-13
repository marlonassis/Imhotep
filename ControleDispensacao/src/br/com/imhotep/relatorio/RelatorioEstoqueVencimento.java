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
import br.com.imhotep.consulta.relatorio.ConsultaRelatorioEstoqueVencimentoPeriodo;
import br.com.imhotep.entidade.relatorio.EstoqueVencimento;
import br.com.imhotep.relatorio.excel.RelatorioEstoqueVencimentoExcel;
import br.com.imhotep.relatorio.excel.RelatorioMovimentacaoGrupoMaterialPeriodoExcel;

@ManagedBean(name="relatorioEstoqueVencimento")
@ViewScoped
public class RelatorioEstoqueVencimento extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	private boolean excel;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio;
		dataFim = new Utilitarios().ajustarUltimoDiaMesHoraMaximo(dataFim);
		List<EstoqueVencimento> lista = new ConsultaRelatorioEstoqueVencimentoPeriodo().consultarResultados(dataIni, dataFim);
		
		String dfim = new SimpleDateFormat("dd/MM/yyyy").format(dataFim);
		String dIni = new SimpleDateFormat("dd/MM/yyyy").format(dataIni);
		
		//Requisito Funcional #23
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioEstoqueVencimentoPeriodo.jasper";
			nomeRelatorio = "RelatorioEstoqueVencido-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dataIni", dIni );
			map.put("dataFim", dfim);
			super.geraRelatorio(caminho, nomeRelatorio, lista, map);
		}
		else{
			nomeRelatorio = "RelatorioEstoqueVencido-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioEstoqueVencimentoExcel exc;
	        exc = new RelatorioEstoqueVencimentoExcel( lista, "Farmácia", dIni+" a "+dfim,7);
	        exc.gerarPlanilha();
			super.geraRelatorioExcel(nomeRelatorio, exc.getWorkbook());
		}
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

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	
}
