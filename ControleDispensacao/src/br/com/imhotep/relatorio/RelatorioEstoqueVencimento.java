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
import br.com.imhotep.auxiliar.Utilities;
import br.com.imhotep.consulta.relatorio.ConsultaRelatorioEstoqueVencimentoPeriodo;
import br.com.imhotep.entidade.relatorio.EstoqueVencimento;

@ManagedBean(name="relatorioEstoqueVencimento")
@ViewScoped
public class RelatorioEstoqueVencimento extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioEstoqueVencimentoPeriodo.jasper";
		String nomeRelatorio = "RelatorioEstoqueVencido-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		dataFim = new Utilities().ajustarUltimoDiaMesHoraMaximo(dataFim);
		List<EstoqueVencimento> lista = new ConsultaRelatorioEstoqueVencimentoPeriodo().consultarResultados(dataIni, dataFim);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
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
	
}
