package br.com.imhotep.consulta.relatorio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.imhotep.entidade.NotaFiscal;
import br.com.remendo.ConsultaGeral;

public class RelatorioNotaFiscal extends ConsultaGeral<NotaFiscal> {
	
	public List<NotaFiscal> consultarResultados(Date dataIni, Date dataFim){
		return consultaMovimentoPeriodo(dataIni, dataFim);
	}
	
	private List<NotaFiscal> consultaMovimentoPeriodo(Date dataIni, Date dataFim) {
		ConsultaGeral<NotaFiscal> cg = new ConsultaGeral<NotaFiscal>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", new SimpleDateFormat("yyyy-MM-dd").format(dataIni));
		map.put("dataFim", new SimpleDateFormat("yyyy-MM-dd").format(dataFim));
		String sql = "select o from NotaFiscal o " +
				"where to_char(o.dataContabil, 'YYYY-MM-DD') >= :dataIni and to_char(o.dataContabil, 'YYYY-MM-DD') <= :dataFim and o.doacao = false";
		sql += " order by o.identificacaoNotaFiscal";
		
		ArrayList<NotaFiscal> list = new ArrayList<NotaFiscal>(cg.consulta(new StringBuilder(sql), map));
		
		if(list == null || list.isEmpty()){
			list.add(new NotaFiscal());
		}
		
		return list;
	}
	
}
