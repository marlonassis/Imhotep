package br.com.Imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.auxiliar.Utilities;
import br.com.imhotep.entidade.relatorio.EstoqueVencimento;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioEstoqueVencimentoPeriodo extends ConsultaGeral<Object[]> {
	
	public ArrayList<EstoqueVencimento> consultarResultados(Date dataIni, Date dataFim){
		Calendar df = Utilities.ajustarUltimaHoraDia(dataFim);
		return modeladorResultado(consultaMovimentoPeriodo(dataIni, df.getTime()));
	}

	private ArrayList<EstoqueVencimento> modeladorResultado(List<Object[]> list){
		List<EstoqueVencimento> meul = new ArrayList<EstoqueVencimento>();
		for(Object[] meu : list){		
			EstoqueVencimento obj = new EstoqueVencimento();
			obj.setCodigoMaterial((Integer) meu[0]);
			obj.setNomeMaterial((String) meu[1]);
			obj.setLote((String) meu[2]);
			obj.setDataValidade((Date) meu[3]);
			obj.setQuantidade((Integer) meu[4]);
			obj.setUsuario((String) meu[5]);
			meul.add(obj);
		}
		
		if(meul == null || meul.isEmpty()){
			meul.add(new EstoqueVencimento(Constantes.MENSAGEM_RELATORIO_VAZIO));
		}
		
		return new ArrayList<EstoqueVencimento>(meul);
	}
	
	private List<Object[]> consultaMovimentoPeriodo(Date dataIni, Date dataFim) {
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select o.material.codigoMaterial, o.material.descricao, o.lote, o.dataValidade, o.quantidade, o.usuarioInclusao.login from Estoque o where o.dataValidade >= :dataIni and o.dataValidade <= :dataFim order by o.dataValidade";
		return new ArrayList<Object[]>(cg.consulta(new StringBuilder(sql), map));
	}
	
}
