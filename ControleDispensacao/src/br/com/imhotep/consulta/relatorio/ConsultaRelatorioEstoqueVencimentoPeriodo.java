package br.com.imhotep.consulta.relatorio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.relatorio.EstoqueVencimento;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioEstoqueVencimentoPeriodo extends ConsultaGeral<Object[]> {
	
	public List<EstoqueVencimento> consultarResultados(Date dataIni, Date dataFim){
		return consultaMovimentoPeriodo(dataIni, dataFim);
	}

	private List<EstoqueVencimento> consultaMovimentoPeriodo(Date dataIni, Date dataFim) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from ");
		stringBuilder.append("Estoque o where date(o.dataValidade) between date('");
		stringBuilder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataIni));
		stringBuilder.append("') and date('");
		stringBuilder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataFim));
		stringBuilder.append("') order by lower(to_ascii(o.material.descricao))");
		
		
		ArrayList<Estoque> lista = new ArrayList<Estoque>(new ConsultaGeral<Estoque>(stringBuilder).consulta());
		ArrayList<EstoqueVencimento> resultado = new ArrayList<EstoqueVencimento>();
		
		for(Estoque item : lista){
			String nomeResumido = item.getProfissionalInclusao() == null ? "" : item.getProfissionalInclusao().getNomeResumido();
			EstoqueVencimento obj = new br.com.imhotep.entidade.relatorio.EstoqueVencimento(item.getMaterial().getCodigoMaterial(), 
																							item.getMaterial().getDescricao(), item.getLote(), 
																							item.getDataValidade(), item.getQuantidadeAtual(), 
																							nomeResumido, 
																							item.getBloqueado());
			resultado.add(obj);
		}
		
		return resultado;
	}
	
}
