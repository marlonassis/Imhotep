package br.com.imhotep.relatorio.excel;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupo;
import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupoMaterial;
import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes;

//Requisito Funcional #18
public class RelatorioMovimentacaoGrupoMaterialPeriodoExcel extends RelatorioExcel {
	List<MovimentacoesGrupoAlmoxarifadoGrupo> list;
	
	public RelatorioMovimentacaoGrupoMaterialPeriodoExcel( List<MovimentacoesGrupoAlmoxarifadoGrupo> list, String setor, String periodo, int qtdColunas ){
		super("Movimentação do Material por Grupo", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();
		
		criarLinha("", (short)3);
		int[] qtdCaracteres = {80, 15, 22};		
		String[] header = { "Material", "Consumo", "Saldo Atual"};
		
		int[] qtdCaracteresMov = {80,15, 22, 13, 6, 13, 15, 15, 20};		
		String[] headerMov = { "Justificativa","Operação", "Tipo Movimento", "Lote", "Saldo", "Quantidade", "Saldo Restante", "Data", "Usuário"};
		
		for(Iterator<MovimentacoesGrupoAlmoxarifadoGrupo> itMov = list.iterator(); itMov.hasNext();){
			MovimentacoesGrupoAlmoxarifadoGrupo mov = itMov.next();
			criarLinha(mov.getDescricaoCompleta(), "cell_blue", (short)3);
			
			
			for(Iterator<MovimentacoesGrupoAlmoxarifadoGrupoMaterial> itMat = mov.getMateriais().iterator(); itMat.hasNext();){
				MovimentacoesGrupoAlmoxarifadoGrupoMaterial material = itMat.next();
				
				criarColunasComNome(header, qtdCaracteres);
				addCelulasLn(new StringBuilder("cell_bb"), material.getMaterial(), material.getConsumo()+"", material.getSaldoEstoque()+"");
				criarColunasComNome(headerMov, qtdCaracteresMov);
				
				for(Iterator<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes> itMov2 = material.getMovimentacoes().iterator();itMov2.hasNext();){
					MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes m = itMov2.next();
					
					addCelulasLn(m.getJustificativa(), (m.getTipoOperacao().equals("S")?"Saída":(m.getTipoOperacao().equals("E")?"Entrada":m.getTipoOperacao())), m.getTipoMovimento(), m.getLote(), 
							m.getQtdAtual().toString(), m.getQtdMovimentada().toString(), (m.getQtdAtual()- m.getQtdMovimentada())+"", 
							new SimpleDateFormat("dd/MM/yyyy").format(m.getDataMovimento()), m.getUsuario());
				}
				criarLinha("", (short)3);
			}
		}
	}
}
