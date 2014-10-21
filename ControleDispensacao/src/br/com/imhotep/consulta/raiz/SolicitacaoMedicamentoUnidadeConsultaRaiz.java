package br.com.imhotep.consulta.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.SolicitacaoDevolucoesProfissionalComparador;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.extra.SolicitacoesDevolucoesProfissional;
import br.com.imhotep.entidade.extra.SolicitacoesDevolucoesProfissionalItem;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.raiz.SolicitacaoMedicamentoUnidadeRaiz;
import br.com.imhotep.raiz.SolicitacoesDevolucoesProfissionalRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.gerenciador.ControleInstancia;

@ManagedBean
@RequestScoped
public class SolicitacaoMedicamentoUnidadeConsultaRaiz  extends ConsultaGeral<SolicitacaoMedicamentoUnidade>{

	public static final String SIGLA_SOLICITACAO_ALMOXARIFADO = "Solicitação ao Almoxarifado";
	public static final String SIGLA_SOLICITACAO_FARMACIA = "Solicitação à Farmácia";
	public static final String SIGLA_DEVOLUCAO_ALMOXARIFADO = "Devolução ao Almoxarifado";
	public static final String SIGLA_DEVOLUCAO_FARMACIA = "Devolução para à Farmácia";

	public static SolicitacaoMedicamentoUnidadeConsultaRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMedicamentoUnidadeConsultaRaiz) Utilitarios.procuraInstancia(SolicitacaoMedicamentoUnidadeConsultaRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void listaSolicitacoesSemReceptor(){
		StringBuilder sb = new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'D' and o.profissionalReceptor is null");
		ConsultaGeral<SolicitacaoMedicamentoUnidade> cg = new ConsultaGeral<SolicitacaoMedicamentoUnidade>();
		SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().setSolicitacoesPendentes(new ArrayList<SolicitacaoMedicamentoUnidade>(cg.consulta(sb, null)));
	}
 	
	public void consultarSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'P'");
		ConsultaGeral<SolicitacaoMedicamentoUnidade> cg = new ConsultaGeral<SolicitacaoMedicamentoUnidade>();
		SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().setSolicitacoesPendentes(new ArrayList<SolicitacaoMedicamentoUnidade>(cg.consulta(sb, null)));
	}
	
	public SolicitacaoMedicamentoUnidade solicitacaoId(int id){
		return new ConsultaGeral<SolicitacaoMedicamentoUnidade>().consultaUnica(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.idSolicitacaoMedicamentoUnidade = "+id));
	}

	public Long quantidadeItens(SolicitacaoMedicamentoUnidade smu){ 
		String sql = "select count(o.idSolicitacaoMedicamentoUnidadeItem) from SolicitacaoMedicamentoUnidadeItem o "+ 
					 "where o.solicitacaoMedicamentoUnidade.idSolicitacaoMedicamentoUnidade = "+smu.getIdSolicitacaoMedicamentoUnidade();
		return new ConsultaGeral<Long>(new StringBuilder(sql)).consultaUnica();
	}
	
	public void consultarSolicitacoesProfissional(){ 
		List<SolicitacoesDevolucoesProfissional> sols = new ArrayList<SolicitacoesDevolucoesProfissional>();
		try {
			carregarSolicitacoesAlmoxarifado(sols);
			carregarSolicitacoesFarmacia(sols);
			carregarDevolucoesAlmoxarifado(sols);
			carregarDevolucoesFarmacia(sols);
			Collections.sort(sols, new SolicitacaoDevolucoesProfissionalComparador());
			SolicitacoesDevolucoesProfissionalRaiz sdpr = (SolicitacoesDevolucoesProfissionalRaiz) new ControleInstancia().procuraInstancia(SolicitacoesDevolucoesProfissionalRaiz.class);
			sdpr.setSolicitacoesProfissional(sols);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String normatizarStatusDispensacao(String status) {
		if(status.equals("A")){
			status = "Aberta";
		}else{
			if(status.equals("P")){
				status = "Pendente";
			}else{
				if(status.equals("R")){
					status = "Recusado";
				}else{
					if(status.equals("D") || status.equals("DP")){
						status="Dispensado";
					}
				}
			}
		}
		return status;
	}
	
	private String normatizarStatusDevolucao(String status) {
		if(status.equals("A")){
			status = "Aberta";
		}else{
			if(status.equals("P")){
				status = "Pendente";
			}else{
				if(status.equals("RE")){
					status = "Recusado";
				}else{
					if(status.equals("R") || status.equals("RP")){
						status="Recebido";
					}
				}
			}
		}
		return status;
	}
	
	private void carregarDevolucoesFarmacia(List<SolicitacoesDevolucoesProfissional> sols) throws ExcecaoProfissionalLogado, SQLException{
		Calendar dataTresDiasAtras = Calendar.getInstance();
		dataTresDiasAtras.add(Calendar.DAY_OF_MONTH, -3);
		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataTresDiasAtras.getTime());
		
		String sqlSolicitacoesAlmoxarifado = "select a.dt_data_insercao, a.dt_data_fechamento, "+ 
											 "b.cv_nome unidade, a.tp_status status, "+
											 "a.id_devolucao_medicamento codigo, a.cv_justificativa "+
											 "from tb_devolucao_medicamento a "+
											 "inner join tb_unidade b on b.id_unidade = a.id_unidade_devolucao "+
											 "where a.id_profissional_insercao = "+Autenticador.getProfissionalLogado().getIdProfissional()+
											 " and cast(a.dt_data_insercao as date) > cast('"+data+"' as date)";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_IMHOTEP_LINHA_MECANICA);
		ResultSet rs = lm.consultar(sqlSolicitacoesAlmoxarifado);
		int cont=0;
		while (rs.next()) {
			Date dataInsercao =  rs.getTimestamp("dt_data_insercao");
			Date dataFechamento =  rs.getTimestamp("dt_data_fechamento");
			String unidade = rs.getString("unidade");
			String status = normatizarStatusDevolucao(rs.getString("status"));
			String codigo  = rs.getString("codigo");
			String justificativa = rs.getString("cv_justificativa");
			
			String sqlSolicitacoesAlmoxarifadoItem = "select b.id_material codigo, b.cv_descricao, a.in_quantidade_devolvida, "+ 
													 "a.cv_jutificativa, a.tp_status "+
													 "from tb_devolucao_medicamento_item a "+ 
													 "inner join tb_material b on a.id_material = b.id_material "+
													 "where a.id_devolucao_medicamento = "+codigo;
			List<SolicitacoesDevolucoesProfissionalItem> itens = new ArrayList<SolicitacoesDevolucoesProfissionalItem>();
			ResultSet rs2 = lm.consultar(sqlSolicitacoesAlmoxarifadoItem);
			int cont2=0;
			while (rs2.next()) {
				String codigoItem = rs2.getString("codigo");
				String descricaoItem = rs2.getString("cv_descricao");
				int qtdSolicitadaItem = rs2.getInt("in_quantidade_devolvida");
				String justificativaItem  = rs2.getString("cv_jutificativa");
				String statusItem  = normatizarStatusDevolucao(rs2.getString("tp_status"));
				cont2++;
				SolicitacoesDevolucoesProfissionalItem item = new SolicitacoesDevolucoesProfissionalItem(cont2, codigoItem, descricaoItem, 
						qtdSolicitadaItem, justificativaItem, statusItem);
				itens.add(item);
			}
			
			cont++;
			Date dataExibicao = status.equals("Aberta") ? dataInsercao : dataFechamento;
			SolicitacoesDevolucoesProfissional sdp = new SolicitacoesDevolucoesProfissional(cont, dataExibicao, unidade, status, codigo, justificativa, SIGLA_DEVOLUCAO_FARMACIA, itens);
			sols.add(sdp);
		}
		
	}

	private void carregarDevolucoesAlmoxarifado(List<SolicitacoesDevolucoesProfissional> sols) throws ExcecaoProfissionalLogado, SQLException{
		Calendar dataTresDiasAtras = Calendar.getInstance();
		dataTresDiasAtras.add(Calendar.DAY_OF_MONTH, -3);
		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataTresDiasAtras.getTime());
		
		String sqlDevolucoesAlmoxarifado = "select a.dt_data_insercao, a.dt_data_fechamento, "+ 
											 "b.cv_nome unidade, a.tp_status status, "+
											 "a.id_devolucao_material codigo, a.cv_justificativa "+
											 "from tb_devolucao_material a "+
											 "inner join tb_unidade b on b.id_unidade = a.id_unidade_devolucao "+
											 "where a.id_profissional_insercao = "+Autenticador.getProfissionalLogado().getIdProfissional()+
											 " and cast(a.dt_data_insercao as date) > cast('"+data+"' as date)";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_IMHOTEP_LINHA_MECANICA);
		ResultSet rs = lm.consultar(sqlDevolucoesAlmoxarifado);
		int cont=0;
		while (rs.next()) {
			Date dataInsercao =  rs.getTimestamp("dt_data_insercao");
			Date dataFechamento =  rs.getTimestamp("dt_data_fechamento");
			String unidade = rs.getString("unidade");
			String status  = normatizarStatusDevolucao(rs.getString("status"));
			String codigo  = rs.getString("codigo");
			String justificativa = rs.getString("cv_justificativa");

			String sqlDevolucoesAlmoxarifadoItem = "select b.id_material_almoxarifado codigo, b.cv_descricao, a.in_quantidade_devolvida, "+ 
													 "a.cv_jutificativa, a.tp_status "+
													 "from tb_devolucao_material_item a "+ 
													 "inner join tb_material_almoxarifado b on a.id_material_almoxarifado = b.id_material_almoxarifado "+
													 "where a.id_devolucao_material = "+codigo;
			List<SolicitacoesDevolucoesProfissionalItem> itens = new ArrayList<SolicitacoesDevolucoesProfissionalItem>();
			ResultSet rs2 = lm.consultar(sqlDevolucoesAlmoxarifadoItem);
			int cont2=0;
			while (rs2.next()) {
				String codigoItem = rs2.getString("codigo");
				String descricaoItem = rs2.getString("cv_descricao");
				int qtdSolicitadaItem = rs2.getInt("in_quantidade_devolvida");
				String justificativaItem  = rs2.getString("cv_jutificativa");
				String statusItem  = normatizarStatusDevolucao(rs2.getString("tp_status"));
				cont2++;
				SolicitacoesDevolucoesProfissionalItem item = new SolicitacoesDevolucoesProfissionalItem(cont2, codigoItem, descricaoItem, 
						qtdSolicitadaItem, justificativaItem, statusItem);
				itens.add(item);
			}
			
			cont++;
			Date dataExibicao = status.equals("Aberta") ? dataInsercao : dataFechamento;
			SolicitacoesDevolucoesProfissional sdp = new SolicitacoesDevolucoesProfissional(cont, dataExibicao, unidade, status, codigo, justificativa, SIGLA_DEVOLUCAO_ALMOXARIFADO, itens);
			sols.add(sdp);
		}
		
	}
	
	private void carregarSolicitacoesFarmacia(List<SolicitacoesDevolucoesProfissional> sols) throws ExcecaoProfissionalLogado, SQLException{
		Calendar dataTresDiasAtras = Calendar.getInstance();
		dataTresDiasAtras.add(Calendar.DAY_OF_MONTH, -3);
		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataTresDiasAtras.getTime());
		
		String sqlSolicitacoesMedicamento = "select a.dt_data_insercao, a.dt_data_fechamento, "+ 
											 "b.cv_nome unidade, a.tp_status_dispensacao status, "+
											 "a.id_solicitacao_medicamento_unidade codigo, a.cv_justificativa "+
											 "from tb_solicitacao_medicamento_unidade a "+
											 "inner join tb_unidade b on b.id_unidade = a.id_unidade_destino "+
											 "where a.id_profissional_insercao = "+Autenticador.getProfissionalLogado().getIdProfissional()+
											 " and cast(a.dt_data_insercao as date) > cast('"+data+"' as date)";
		
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_IMHOTEP_LINHA_MECANICA);
		ResultSet rs = lm.consultar(sqlSolicitacoesMedicamento);
		int cont=0;
		while (rs.next()) {
			Date dataInsercao =  rs.getTimestamp("dt_data_insercao");
			Date dataFechamento =  rs.getTimestamp("dt_data_fechamento");
			String unidade = rs.getString("unidade");
			String status  = normatizarStatusDispensacao(rs.getString("status"));
			String codigo  = rs.getString("codigo");
			String justificativa = rs.getString("cv_justificativa");

			String sqlSolicitacoesFarmaciaItem = "select b.id_material codigo, b.cv_descricao, a.in_quantidade_solicitada, "+ 
													 "a.cv_justificativa, a.tp_tipo_status_item "+
													 "from tb_solicitacao_medicamento_unidade_item a "+ 
													 "inner join tb_material b on a.id_material = b.id_material "+
													 "where a.id_solicitacao_medicamento_unidade = "+codigo;
			List<SolicitacoesDevolucoesProfissionalItem> itens = new ArrayList<SolicitacoesDevolucoesProfissionalItem>();
			ResultSet rs2 = lm.consultar(sqlSolicitacoesFarmaciaItem);
			int cont2=0;
			while (rs2.next()) {
				String codigoItem = rs2.getString("codigo");
				String descricaoItem = rs2.getString("cv_descricao");
				int qtdSolicitadaItem = rs2.getInt("in_quantidade_solicitada");
				String justificativaItem  = rs2.getString("cv_justificativa");
				String statusItem  = normatizarStatusDispensacao(rs2.getString("tp_tipo_status_item"));
				cont2++;
				SolicitacoesDevolucoesProfissionalItem item = new SolicitacoesDevolucoesProfissionalItem(cont2, codigoItem, descricaoItem, 
						qtdSolicitadaItem, justificativaItem, statusItem);
				itens.add(item);
			}
			
			cont++;
			Date dataExibicao = status.equals("Aberta") ? dataInsercao : dataFechamento;
			SolicitacoesDevolucoesProfissional sdp = new SolicitacoesDevolucoesProfissional(cont, dataExibicao, unidade, status, codigo, justificativa, SIGLA_SOLICITACAO_FARMACIA, itens);
			sols.add(sdp);
		}
		
	}
	
	private void carregarSolicitacoesAlmoxarifado(List<SolicitacoesDevolucoesProfissional> sols)
			throws ExcecaoProfissionalLogado, SQLException {
		Calendar dataTresDiasAtras = Calendar.getInstance();
		dataTresDiasAtras.add(Calendar.DAY_OF_MONTH, -3);
		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataTresDiasAtras.getTime());
		
		String sqlSolicitacoesAlmoxarifado = "select a.dt_data_insercao, a.dt_data_fechamento, b.cv_nome unidade, a.tp_status_dispensacao status, "+
											 "a.id_solicitacao_material_almoxarifado_unidade codigo, a.cv_justificativa "+
											 "from tb_solicitacao_material_almoxarifado_unidade a "+
											 "inner join tb_unidade b on b.id_unidade = a.id_unidade_destino "+
											 "where a.id_profissional_insercao = "+Autenticador.getProfissionalLogado().getIdProfissional()+
											 " and cast(a.dt_data_insercao as date) > cast('"+data+"' as date)";
		
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_IMHOTEP_LINHA_MECANICA);
		ResultSet rs = lm.consultar(sqlSolicitacoesAlmoxarifado);
		int cont=0;
		while (rs.next()) {
			Date dataInsercao =  rs.getTimestamp("dt_data_insercao");
			Date dataFechamento =  rs.getTimestamp("dt_data_fechamento");
			String unidade = rs.getString("unidade");
			String status  = normatizarStatusDispensacao(rs.getString("status"));
			String codigo  = rs.getString("codigo");
			String justificativa = rs.getString("cv_justificativa");

			String sqlSolicitacoesAlmoxarifadoItem = "select b.id_material_almoxarifado codigo, b.cv_descricao, a.in_quantidade_solicitada, "+ 
													"a.cv_justificativa, a.tp_tipo_status_item "+
													"from tb_solicitacao_material_almoxarifado_unidade_item a "+ 
													"inner join tb_material_almoxarifado b on a.id_material_almoxarifado = b.id_material_almoxarifado "+
													"where a.id_solicitacao_material_almoxarifado_unidade = "+codigo;
			List<SolicitacoesDevolucoesProfissionalItem> itens = new ArrayList<SolicitacoesDevolucoesProfissionalItem>();
			ResultSet rs2 = lm.consultar(sqlSolicitacoesAlmoxarifadoItem);
			int cont2=0;
			while (rs2.next()) {
				String codigoItem = rs2.getString("codigo");
				String descricaoItem = rs2.getString("cv_descricao");
				int qtdSolicitadaItem = rs2.getInt("in_quantidade_solicitada");
				String justificativaItem  = rs2.getString("cv_justificativa");
				String statusItem  = normatizarStatusDispensacao(rs2.getString("tp_tipo_status_item"));
				cont2++;
				SolicitacoesDevolucoesProfissionalItem item = new SolicitacoesDevolucoesProfissionalItem(cont2, codigoItem, descricaoItem, 
						qtdSolicitadaItem, justificativaItem, statusItem);
				itens.add(item);
			}
			
			cont++;
			Date dataExibicao = status.equals("Aberta") ? dataInsercao : dataFechamento;
			SolicitacoesDevolucoesProfissional sdp = new SolicitacoesDevolucoesProfissional(cont, dataExibicao, unidade, status, codigo, justificativa, SIGLA_SOLICITACAO_ALMOXARIFADO, itens);
			sols.add(sdp);
		}
	}
	
}