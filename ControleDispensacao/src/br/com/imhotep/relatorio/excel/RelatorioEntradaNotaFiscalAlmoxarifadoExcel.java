package br.com.imhotep.relatorio.excel;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.CellRangeAddress;

import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.extra.FinanceiroGrupoAlmoxarifado;
import br.com.imhotep.entidade.extra.FinanceiroGrupoNotaFiscalAlmoxarifado;
import br.com.imhotep.entidade.extra.FinanceiroNotaFiscalAlmoxarifadoDesconto;

//Solicitação de Mudança #13
public class RelatorioEntradaNotaFiscalAlmoxarifadoExcel  extends RelatorioExcel{

	private List<FinanceiroGrupoAlmoxarifado> list ;
	private List<FinanceiroNotaFiscalAlmoxarifadoDesconto> listaNFDesconto;
	
	public RelatorioEntradaNotaFiscalAlmoxarifadoExcel( List<FinanceiroGrupoAlmoxarifado> list, List<FinanceiroNotaFiscalAlmoxarifadoDesconto> listaNFDesconto,
			String setor, String periodo, int qtdColunas ){
		
		super("Entrada de Nota Fiscal", setor, periodo, 4);
		this.list = list;
		this.listaNFDesconto = listaNFDesconto;
	}
	
	@Override
	public void gerarPlanilha() {
		DecimalFormat df = new DecimalFormat("#,###.00");  
		
		criarCabecalho();
		
		int[] qtdCaracteres = {15, 40, 25, 15};
		criarColunasComNome(null, qtdCaracteres);
		criarLinha("", (short)4);
		
		// header
		String[] header = { "Nota Fiscal", "Fornecedor", "CPF/CNPJ", "Valor da Nota"};
		for (FinanceiroGrupoAlmoxarifado mat : list) {
			criarLinha(mat.getDescricao(), "cell_h",(short)4);			
			criarColunasComNome(header, qtdCaracteres);
			
			for (Iterator<FinanceiroGrupoNotaFiscalAlmoxarifado> iterator = mat.getItens().iterator(); iterator.hasNext();) {
				FinanceiroGrupoNotaFiscalAlmoxarifado nota = iterator.next();
				
				addCelulasLn(nota.getIdentificacao(), nota.getRazaoSocial(), nota.getCadastroPessoaFisicaJuridica(), df.format(nota.getValorDescontado()));
			}
			addCelulasLn( new StringBuilder("cell_bg"), "", "", "Total", df.format(mat.getTotal()));
			criarLinha("", (short)4);
		}
	}

}
