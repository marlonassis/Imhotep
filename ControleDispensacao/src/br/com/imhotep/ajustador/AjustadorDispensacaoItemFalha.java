package br.com.imhotep.ajustador;


public class AjustadorDispensacaoItemFalha {
	public static void main(String[] args) {
//		String sql = "select b.id_profissional_dispensacao, b.id_solicitacao_medicamento_unidade, a.id_solicitacao_medicamento_unidade_item, a.in_quantidade_solicitada, b.dt_data_dispensacao, d.id_material "+
//						"from tb_solicitacao_medicamento_unidade_item a "+
//						"inner join tb_solicitacao_medicamento_unidade b on a.id_solicitacao_medicamento_unidade = b.id_solicitacao_medicamento_unidade "+
//						"left join tb_dispensacao_simples c on c.id_solicitacao_medicamento_unidade_item = a.id_solicitacao_medicamento_unidade_item "+
//						"inner join tb_material d on d.id_material = a.id_material "+
//						"where c.id_dispensacao_simples is null and b.tp_status_dispensacao = 'D' and a.tp_tipo_status_item != 'R' "+
//						"order by b.dt_data_dispensacao";
//		
//		LinhaMecanica lm = new LinhaMecanica("db_temp", Constantes.IP_LOCAL);
//		try{
//			TipoMovimento tipoMovimentoDS = Parametro.tipoMovimentoDispensacaoSimples();
//			ResultSet rs = lm.fastConsulta(sql);
//			while(rs.next()){
//				Integer idProfissional = rs.getInt("id_profissional_dispensacao");
//				Integer idSolicitacao = rs.getInt("id_solicitacao_medicamento_unidade");
//				Integer idItem = rs.getInt("id_solicitacao_medicamento_unidade_item");
//				Integer qtdSolicitada = rs.getInt("in_quantidade_solicitada");
//				Integer idMaterial = rs.getInt("id_material");
//				Date dataDispensacao = rs.getTimestamp("dt_data_dispensacao");
//				
//				
//					for(ItemDispensacao item : getItensDispensacao()){
//						item.getItem().setDataLiberacao(date);
//						item.getItem().setProfissionalLiberacao(Autenticador.getProfissionalLogado());
//						if(!item.getItem().getStatusItem().equals(TipoStatusSolicitacaoItemEnum.R)){
//							for(EstoqueDispensacao ed : item.getEstoques()){
//								MovimentoLivro ml = new MovimentoLivro();
//								ml.setEstoque(ed.getEstoque());
//								ml.setTipoMovimento(tipoMovimentoDS);
//								ml.setQuantidadeMovimentacao(ed.getQuantidadeDispensada());
//								ml.setJustificativa("RM: "+getInstancia().getIdSolicitacaoMedicamentoUnidade());
//								new ControleEstoqueTemp().liberarAjuste(date, ml);
//								
//								PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro"+ml.hashCode(), ml);
//								
//								DispensacaoSimples ds = new DispensacaoSimples();
//								ds.setSolicitacaoMedicamentoUnidadeItem(item.getItem());
//								ds.setMovimentoLivro(ml);
//								ds.setUnidadeDispensada(item.getItem().getSolicitacaoMedicamentoUnidade().getUnidadeDestino());
//								PadraoFluxoTemp.getObjetoSalvar().put("DispensacaoSimples"+ds.hashCode(), ds);
//							}
//						}
//						PadraoFluxoTemp.getObjetoAtualizar().put("ItemDispensacao"+item.hashCode(), item.getItem());
//					}
//					getInstancia().setDataDispensacao(date);
//					getInstancia().setProfissionalDispensacao(Autenticador.getProfissionalLogado());
//					getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.D);
//					PadraoFluxoTemp.getObjetoAtualizar().put("Dispensacao"+getInstancia().hashCode(), getInstancia());
//					PadraoFluxoTemp.finalizarFluxo();
//					setExibirDialogProfissionalReceptor(false);
//					setExibirDialogDispensacao(true);
//					SolicitacaoMedicamentoUnidadeConsultaRaiz.getInstanciaAtual().consultarSolicitacoesPendentes();
//				}catch(Exception e){
//					e.printStackTrace();
//					getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.P);
//				}finally{
//					PadraoFluxoTemp.limparFluxo();
//					unlockEstoques();
//					setInstancia(new SolicitacaoMedicamentoUnidadeConsultaRaiz().solicitacaoId(getInstancia().getIdSolicitacaoMedicamentoUnidade()));
//				}
//				
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			lm.fecharConexoes();
//		}
	}
}
