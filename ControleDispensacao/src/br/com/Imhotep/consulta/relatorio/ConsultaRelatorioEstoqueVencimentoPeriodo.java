package br.com.Imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.relatorio.EstoqueVencimento;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioEstoqueVencimentoPeriodo extends ConsultaGeral<Object[]> {
	
	public List<EstoqueVencimento> consultarResultados(Date dataIni, Date dataFim){
		return consultaMovimentoPeriodo(dataIni, dataFim);
	}

	private List<EstoqueVencimento> consultaMovimentoPeriodo(Date dataIni, Date dataFim) {
		ConsultaGeral<EstoqueVencimento> cg = new ConsultaGeral<EstoqueVencimento>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select new br.com.imhotep.entidade.relatorio.EstoqueVencimento(o.material.codigoMaterial, o.material.descricao, o.lote, o.dataValidade, o.quantidadeAtual, o.usuarioInclusao.login, o.bloqueado) from Estoque o where o.dataValidade >= :dataIni and o.dataValidade <= :dataFim order by o.dataValidade";
		
		ArrayList<EstoqueVencimento> lista = new ArrayList<EstoqueVencimento>(cg.consulta(new StringBuilder(sql), map));
		
		if(lista == null || lista.isEmpty()){
			lista.add(new EstoqueVencimento(Constantes.MENSAGEM_RELATORIO_VAZIO));
		}
		
		return lista;
	}
	
}
