package br.com.imhotep.relatorio.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.extra.MediaConsumoAlmoxarifado;

public class RelatorioMediaConsumoAlmoxarifadoExcel extends RelatorioExcel{
	List<MediaConsumoAlmoxarifado> list;
	Date dataIni;
	
	public RelatorioMediaConsumoAlmoxarifadoExcel( List<MediaConsumoAlmoxarifado> list, String setor, String periodo, int qtdColunas, Date dataIni ){
		super("Média de Consumo", setor, periodo, qtdColunas);
		this.list = list;
		this.dataIni = dataIni;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();		
		criarLinha("", (short)10);
		
		Calendar dataReferencia = Calendar.getInstance(Constantes.LOCALE_BRASIL);
		dataReferencia.setTime(dataIni);
		List<String> meses = new ArrayList<String>();
		for(int i = 11; i >= 0; i--){
			meses.add(Utilitarios.mesAnoDescricaoResumido(dataReferencia.getTime()));
			dataReferencia.add(Calendar.MONTH, -1);
		}
		
		int[] qtdCaracteresMov = {80,13, 13, 13, 13, 13, 13, 15, 20};		
		String[] headerMov = { "Material",meses.get(5), meses.get(4), meses.get(3), meses.get(2), meses.get(1), meses.get(0), "Saldo Atual", 
				"Média de consumo", "Previsão de Duração (meses)"};		
		
		String[] header2 = {"", meses.get(11),meses.get(10),meses.get(9),meses.get(8),meses.get(7),meses.get(6),"","",""};
		
		
		criarColunasComNome(headerMov, qtdCaracteresMov);
		criarColunasComNome(header2 );
		
		for(Iterator<MediaConsumoAlmoxarifado> itMed = list.iterator(); itMed.hasNext();){
			MediaConsumoAlmoxarifado med = itMed.next();
			Object[] cons = med.getTotalConsumoList().toArray();
			
			List<Long> prevEstoque = med.getPrevEstoque();
			
			addCelulasLn(med.getDescricaoCompleta(), cons[5].toString(), cons[4].toString(), cons[3].toString(), cons[2].toString(),
					cons[1].toString(), cons[0].toString(), med.getSaldoAtual().toString(), med.getMediaConsumo().toString(), med.getPrevisaoTermino().toString() );
			
			addCelulasLn("", prevEstoque.get(0).toString(), prevEstoque.get(1).toString(), prevEstoque.get(2).toString(), prevEstoque.get(3).toString()
					, prevEstoque.get(4).toString(), prevEstoque.get(5).toString());
		}
	}
}