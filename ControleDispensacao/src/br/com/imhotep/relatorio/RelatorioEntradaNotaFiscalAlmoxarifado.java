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
import br.com.imhotep.consulta.relatorio.RelatorioNotaFiscalAlmoxarifado;
import br.com.imhotep.entidade.extra.FinanceiroGrupoAlmoxarifado;
import br.com.imhotep.entidade.extra.FinanceiroNotaFiscalAlmoxarifadoDesconto;

@ManagedBean
@ViewScoped
public class RelatorioEntradaNotaFiscalAlmoxarifado extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioNotaFiscalAlmoxarifadoPeriodo.jasper";
		String nomeRelatorio = "RelatorioEntradaNotaFiscalAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<FinanceiroGrupoAlmoxarifado> lista = new RelatorioNotaFiscalAlmoxarifado().consultarResultados(dataIni);
		List<FinanceiroNotaFiscalAlmoxarifadoDesconto> listaNFDesconto = new RelatorioNotaFiscalAlmoxarifado().listaNFDesconto(dataIni);

		double total = calcularTotal(lista);
		double desconto = calcularTotalDesconto(listaNFDesconto);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("MMMM/yyyy", Constantes.LOCALE_BRASIL).format(dataIni) );
		map.put("total", total);
		map.put("nfDescontoList", listaNFDesconto);
		map.put("totalDesconto", desconto);
		map.put("valorFinal", (total-desconto));
		
		InputStream subInputStreamNotaFiscal = this.getClass().getResourceAsStream("RelatorioNotaFiscalAlmoxarifadoPeriodoItem.jasper");
		map.put("SUBREPORT_INPUT_STREAM_NOTAS_FISCAIS", subInputStreamNotaFiscal);
		InputStream subInputStreamNotaFiscalDesconto = this.getClass().getResourceAsStream("RelatorioNotaFiscalAlmoxarifadoPeriodoItemDesconto.jasper");
		map.put("SUBREPORT_INPUT_STREAM_NOTAS_FISCAIS_DESCONTO", subInputStreamNotaFiscalDesconto);
		
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
	}
	
	private double calcularTotalDesconto(List<FinanceiroNotaFiscalAlmoxarifadoDesconto> listaNFDesconto){
		double total = 0d;
		for(FinanceiroNotaFiscalAlmoxarifadoDesconto linha : listaNFDesconto){
			total += linha.getDesconto();
		}
		return total;
	}
	
	private double calcularTotal(List<FinanceiroGrupoAlmoxarifado> lista) {
		double total = 0d;
		for(FinanceiroGrupoAlmoxarifado fga : lista){
			total += fga.getTotal();
		}
		return total;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

}
