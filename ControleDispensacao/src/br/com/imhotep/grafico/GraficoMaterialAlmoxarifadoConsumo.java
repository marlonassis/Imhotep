package br.com.imhotep.grafico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.comparador.DataMesAnoComparador;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class GraficoMaterialAlmoxarifadoConsumo {

	private CartesianChartModel linearModel;
	
	public CartesianChartModel montarGrafico(MaterialAlmoxarifado materialAlmoxarifado, Date dataIni, Date dataFim){
		linearModel = new CartesianChartModel();  
        LineChartSeries series1 = new LineChartSeries();  
        series1.setLabel("Dispensações Simples");
        Map<String, Integer> mapGra = consultaQuantidades(materialAlmoxarifado, dataIni, dataFim);
        List<String> keys = new ArrayList<String>(mapGra.keySet());
        Collections.sort(keys, new DataMesAnoComparador());
        for(String key : keys){
        	series1.set(key, (long) mapGra.get(key));
        }
        linearModel.addSeries(series1);
        return linearModel;
	}
	
	private Map<String, Integer> consultaQuantidades(MaterialAlmoxarifado materialAlmoxarifado, Date dataIni, Date dataFim){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select extract(month from a.dt_data_movimento) as mes, "+ 
						"extract(year from a.dt_data_movimento) as ano, "+ 
						"sum(a.in_quantidade_movimentacao) total "+
						"from tb_movimento_livro_almoxarifado a "+
						"inner join tb_estoque_almoxarifado b on b.id_estoque_almoxarifado = a .id_estoque_almoxarifado "+
						"inner join tb_material_almoxarifado c on c.id_material_almoxarifado = b.id_material_almoxarifado "+
						"where c.id_material_almoxarifado = "+materialAlmoxarifado.getIdMaterialAlmoxarifado()+
						" and a.id_tipo_movimento_almoxarifado = 6 "+
						"and to_char(a.dt_data_movimento, 'YYYY-MM-DD') BETWEEN '"+sdf.format(dataIni)+"' and '"+sdf.format(dataFim)+"' "+
						"group by ano, mes "+
						"order by ano, mes";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) { 
				int mes = rs.getInt("mes");
				int ano = rs.getInt("ano");
				int total = rs.getInt("total");
				String data = mes+"/"+ano;
				map.put(mes < 10 ? "0"+data : data, total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		adicionaMesesSemMovimento(dataIni, dataFim, map);
		
		return map;
	}

	private void adicionaMesesSemMovimento(Date dataIni, Date dataFim, Map<String, Integer> map) {
		Calendar dataI = Calendar.getInstance();
		dataI.setTime(dataIni);
		dataI.set(Calendar.DAY_OF_MONTH, 1);
		Calendar dataF = Calendar.getInstance();
		dataF.setTime(dataFim);
		dataF.set(Calendar.DAY_OF_MONTH, 1);
		int comp = dataI.getTime().compareTo(dataF.getTime());
		while(comp < 0 || comp == 0){
			String d = new SimpleDateFormat("MM/yyyy").format(dataI.getTime());
			if(!map.containsKey(d)){
				map.put(d, 0);
			}
			dataI.add(Calendar.MONTH, 1);
			comp = dataI.getTime().compareTo(dataF.getTime());
		}
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	public void setLinearModel(CartesianChartModel linearModel) {
		this.linearModel = linearModel;
	}
	
}
