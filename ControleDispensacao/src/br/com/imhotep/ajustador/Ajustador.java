/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.ajustador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.raiz.SolicitacaoMedicamentoUnidadeItemRaiz;
import br.com.imhotep.temp.PadraoFluxoTemp;

/**
 * @author marlonassis
 * Classe usada para criar m�todos que tenham por objetivo 
 * fazer algum tipo de ajuste no sistema que causado por algum bug.
 */
public class Ajustador {
	private String getIdsItens(){
		String sql = "select a.id_solicitacao_medicamento_unidade_item	from tb_solicitacao_medicamento_unidade_item a "+ 
		"full outer join tb_dispensacao_simples b on b.id_solicitacao_medicamento_unidade_item = a.id_solicitacao_medicamento_unidade_item "+
		"where (a.tp_tipo_status_item = 'D' or a.tp_tipo_status_item = 'DP') "+
		"and b.id_solicitacao_medicamento_unidade_item is null "+
		 "order by a.dt_data_insercao, a.id_solicitacao_medicamento_unidade ";
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("200.133.41.8");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		String ids = "";
		try {
			while (rs.next()) { 
				int id = rs.getInt("id_solicitacao_medicamento_unidade_item");
				ids += id + ",";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ids += "0";
		return ids;
	}
	
	
	public void iniciarAtualizacao() {
		try{
			System.out.println("In�cio");
			System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			TipoMovimento tipoMovimentoDS = Parametro.tipoMovimentoDispensacaoSimples();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	//		System.out.print("idRM: ");
	//		String idRM = in.readLine();
			String hql = "select o from SolicitacaoMedicamentoUnidadeItem o where o.idSolicitacaoMedicamentoUnidadeItem in ("+ getIdsItens() + ") "+ 
//						"full join o.dispensacaoSimples b "+
//						"where  "+
//						"b.solicitacaoMedicamentoUnidadeItem is null and "+
//						"b.solicitacaoMedicamentoUnidadeItem.solicitacaoMedicamentoUnidade.idSolicita.solicitacaoMedicamentoUnidade.idSolicitacaoMedicamentoUnidade >= 2900 and "+
//						"(o.solicitacaoMedicamentoUnidade.statusDispensacao = 'D' or o.solicitacaoMedicamentoUnidade.statusDispensacao = 'DP') "+  
						"order by o.solicitacaoMedicamentoUnidade.idSolicitacaoMedicamentoUnidade, o.material.descricao";
			System.out.println("Meio");
			List<SolicitacaoMedicamentoUnidadeItem> list = new SolicitacaoMedicamentoUnidadeItemRaiz().getBusca(hql);
			System.out.println("Tamanho: "+list.size());
			for(SolicitacaoMedicamentoUnidadeItem item : list){
				String desc = "id: " + item.getIdSolicitacaoMedicamentoUnidadeItem() + " \nsolicitado: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(item.getDataInsercao()) + " \nqtd: " + item.getQuantidadeSolicitada() + " \nMaterial: "+ item.getMaterial().getCodigoMaterial() +" - " + item.getMaterial().getDescricao() + " \nstatus: " + item.getStatusItem().getLabel();
				desc += " \nSolicitacao: "+item.getSolicitacaoMedicamentoUnidade().getIdSolicitacaoMedicamentoUnidade();
				System.out.println(desc);
					
				Map<String, String> lotes = new HashMap<String, String>();
				if(item.getStatusItem().getLabel().equals("Recusado")){
					continue;
				}
				String lote = "";
				while(true){
					System.out.print("Lote: "); 
					lote = in.readLine();
					
					if(lote.equals(".") || lote.equals("...")){
						break;
					}else{
						if(lote.equals("..")){
							throw new Exception();
						}
					}
	
					System.out.print("Informe a qtd liberada: ");  
					String qtd = in.readLine();
					
					lotes.put(lote, qtd);
					
				}
				
				if(lote.equals("...")){
					continue;
				}
				
				Set<String> keys = lotes.keySet();
				for(String l: keys){
					System.out.println("lote: "+ l);
					System.out.println("qtd: " + lotes.get(l));
					
					Estoque estoque = new EstoqueConsultaRaiz().consultarEstoque(l);
					if(estoque == null || estoque.getMaterial().getIdMaterial() != item.getMaterial().getIdMaterial()){
						System.out.println("Erro estoque");
						throw new Exception();
					}
					MovimentoLivro ml = new MovimentoLivro();
					ml.setEstoque(estoque);
					ml.setTipoMovimento(tipoMovimentoDS);
					ml.setQuantidadeMovimentacao(Integer.valueOf(lotes.get(l)));
					ml.setDataMovimento(item.getSolicitacaoMedicamentoUnidade().getDataDispensacao());
					ml.setQuantidadeAtual(0);
					ml.setProfissionalMovimentacao(item.getSolicitacaoMedicamentoUnidade().getProfissionalDispensacao());
					
					DispensacaoSimples ds = new DispensacaoSimples();
					ds.setSolicitacaoMedicamentoUnidadeItem(item);
					ds.setMovimentoLivro(ml);
					ds.setUnidadeDispensada(item.getSolicitacaoMedicamentoUnidade().getUnidadeDestino());
					
					PadraoFluxoTemp.limparFluxo();
					PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro-"+ml.hashCode(), ml);
					PadraoFluxoTemp.getObjetoSalvar().put("DispensacaoSimples-"+ds.hashCode(), ds);
					PadraoFluxoTemp.finalizarFluxo();
				}
				
				System.out.println("\n•••••••••••\n");
				
			}
		}catch(Exception e){
			
		}
		System.out.println("Fim");
	}
	
	
	
	public static void main(String[] args) {
		try{
			System.out.println("In�cio");
			System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			String sql = "select id_solicitacao_medicamento_unidade_item from tb_solicitacao_medicamento_unidade_item order by id_solicitacao_medicamento_unidade_item"; 
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
			lm.setIp("200.133.41.8");
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			try {
				while (rs.next()) { 
					int id = rs.getInt("id_solicitacao_medicamento_unidade_item");
					String sql2 = "select id_movimento_livro from tb_dispensacao_simples where id_solicitacao_medicamento_unidade_item = "+id+" order by id_dispensacao_simples";
					lm = new LinhaMecanica();
					lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
					lm.setIp("200.133.41.8");
					ResultSet rs2 = lm.consultar(lm.utf8_to_latin1(sql2));
					try {
						while (rs2.next()) { 
							Integer id2 = rs2.getInt("id_movimento_livro");
//							int cont=0;
//							cont++;
							String sql3 = "update tb_solicitacao_medicamento_unidade_item set id_movimento_livro = "+id2+" where id_solicitacao_medicamento_unidade_item = "+id;
							System.out.println(sql3);
//							if(cont>1){
//								System.out.println("erro");
//							}
							System.out.println(sql3);
							if(!lm.executarCUD(sql3)){
								System.out.println("Erro - "+id);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
		}
	
	
	
	
}
