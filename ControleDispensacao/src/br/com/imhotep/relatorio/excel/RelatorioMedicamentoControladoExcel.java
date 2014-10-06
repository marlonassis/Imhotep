package br.com.imhotep.relatorio.excel;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.extra.MedicamentoControladoLista;
import br.com.imhotep.entidade.extra.MedicamentoControladoListaMedicamento;
import br.com.imhotep.entidade.extra.MedicamentoControladoListaMedicamentoEstoque;

public class RelatorioMedicamentoControladoExcel extends RelatorioExcel {
	List<MedicamentoControladoLista> list;
	
	public RelatorioMedicamentoControladoExcel( List<MedicamentoControladoLista> list, String setor, String periodo, int qtdColunas ){
		super("Medicamentos Controlados", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();

		criarLinha("", (short)7);
		int[] qtdCaracteres = {20, 20, 20, 20};		
		String[] header = { "Lote", "Fabricante","Validade", "Saldo Atual"};
		
		
		for(Iterator<MedicamentoControladoLista> itList = list.iterator(); itList.hasNext();){
			MedicamentoControladoLista l = itList.next();
			
			criarLinha(l.getLista(), "cell_blue", (short)numColunas);
			for(Iterator<MedicamentoControladoListaMedicamento> itEst = l.getMateriais().iterator();itEst.hasNext();){
				MedicamentoControladoListaMedicamento est = itEst.next();				
				
				criarLinha(est.getMaterial(), "cell_bb", (short)numColunas);
				criarColunasComNome(header, qtdCaracteres);
				for(Iterator<MedicamentoControladoListaMedicamentoEstoque> itMed = est.getEstoques().iterator(); itMed.hasNext();){
					MedicamentoControladoListaMedicamentoEstoque med = itMed.next();
					
					addCelulasLn(med.getLote(), med.getFabricante(), new SimpleDateFormat("MM/yyyy").format(med.getDataValidade()), med.getQuantidadeAtual()+"");
				}
				addCelulasLn(new StringBuilder("cell_bg"), "", "", "total", "");
				criarLinha("", (short)numColunas);
			}
		}
			
	}
}
