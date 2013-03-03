package br.com.Imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.consulta.relatorio.ConsultaRelatorioCustoEstoque;
import br.com.Imhotep.entidade.extra.CustoEstoque;

@ManagedBean(name="relatorioCustoEstoque")
@ViewScoped
public class RelatorioCustoEstoque extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	
	public void relatorioMovimentacao() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio = "RelatorioCustoEstoque-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<CustoEstoque> listaCustoEstoque = new ConsultaRelatorioCustoEstoque().pegarResultados(dataIni, ajustaParaUltimaHora().getTime());
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim));
		super.geraRelatorio(Constantes.RELATORIO_CUSTO_ESTOQUE_PATH, nomeRelatorio, listaCustoEstoque, map);
	}

	private Calendar ajustaParaUltimaHora() {
		Calendar dtFim = Calendar.getInstance();
		dtFim.setTime(dataFim);
		dtFim.set(Calendar.HOUR, 23);
		dtFim.set(Calendar.MINUTE, 59);
		dtFim.set(Calendar.SECOND, 59);
		return dtFim;
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
