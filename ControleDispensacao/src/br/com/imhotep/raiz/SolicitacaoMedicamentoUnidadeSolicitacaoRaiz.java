package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.RestringirAcessoRedeHU;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.MaterialConsultaRaiz;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.UnidadeMaterial;
import br.com.imhotep.entidade.extra.MaterialSolicitacaoMedicamento;
import br.com.imhotep.enums.TipoConsultaMedicamentoSolicitacaoEnum;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoForaRedeHU;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoMedicamentoItemSemSaldo;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoMedicamentoSemItens;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoNaoFechada;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeSolicitacaoRaiz extends PadraoRaiz<SolicitacaoMedicamentoUnidade>{
	
	private List<MaterialSolicitacaoMedicamento> materiaisCadastrados = new ArrayList<MaterialSolicitacaoMedicamento>();
	private List<MaterialSolicitacaoMedicamento> itensSelecionados = new ArrayList<MaterialSolicitacaoMedicamento>();
	private TipoConsultaMedicamentoSolicitacaoEnum tipoConsulta;
	private boolean exibirDialogConfirmacao;
	
	public SolicitacaoMedicamentoUnidadeSolicitacaoRaiz(){
		super();
		prepararAmbienteSolicitacao();
	}
	
	public void exibirDialogConfirmacao(){
		setExibirDialogConfirmacao(true);
	}
	
	public void ocultarDialogConfirmacao(){
		setExibirDialogConfirmacao(false);
	}
	
	private void carregarSaldoAtualItens() throws ExcecaoSolicitacaoNaoFechada {
		for(MaterialSolicitacaoMedicamento item : getItensSelecionados()){
			try {
				item.setQuantidadeAtual(new MaterialConsultaRaiz().quantidadeMaterialEstoqueSemReserva(item.getMaterial()));
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ExcecaoSolicitacaoNaoFechada();
			}
		}
	}
	
	private void validarSaldo() throws ExcecaoSolicitacaoMedicamentoItemSemSaldo {
		for(MaterialSolicitacaoMedicamento item : getItensSelecionados()){
			if(item.getSaldoInsuficiente() || !item.isComSaldo()){
				throw new ExcecaoSolicitacaoMedicamentoItemSemSaldo();
			}
		}
	}
	
	private void salvarItens(SolicitacaoMedicamentoUnidade instancia) throws ExcecaoProfissionalLogado, ExcecaoPadraoFluxo {
		Date dataAtual = new Date();
		PadraoFluxoTemp.limparFluxo();
		for(MaterialSolicitacaoMedicamento item : getItensSelecionados()){
			SolicitacaoMedicamentoUnidadeItem msmi = new SolicitacaoMedicamentoUnidadeItem();
			msmi.setDataInsercao(dataAtual);
			msmi.setMaterial(item.getMaterial());
			msmi.setProfissionalInsercao(Autenticador.getProfissionalLogado());
			msmi.setQuantidadeSolicitada(item.getQuantidadeSolicitada());
			msmi.setSolicitacaoMedicamentoUnidade(instancia);
			msmi.setStatusItem(TipoStatusSolicitacaoItemEnum.P);
			PadraoFluxoTemp.getObjetoSalvar().put("Item-"+msmi.hashCode(), msmi);
		}
		PadraoFluxoTemp.finalizarFluxo();
	}
	
	public void fecharSolicitacao(){
		try {
			verificarSolicitacaoVazia();
			setExibeMensagemAtualizacao(false);
			carregarSaldoAtualItens();
			validarSaldo();
			salvarItens(getInstancia());
			getInstancia().setDataFechamento(new Date());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.P);
			if(super.atualizar()){
				ControlePainelAviso.getInstancia().gerarAvisoRM(getInstancia().getIdSolicitacaoMedicamentoUnidade(), getInstancia().getUnidadeDestino());
				super.novaInstancia();
				prepararAmbienteSolicitacao();
			}
		} catch (ExcecaoSolicitacaoNaoFechada e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoMedicamentoItemSemSaldo e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoMedicamentoSemItens e) {
			e.printStackTrace();
		}
	}

	public void removerItemSolicitacao(MaterialSolicitacaoMedicamento item){
		getItensSelecionados().remove(item);
	}
	
	@Override
	public boolean atualizar() {
		try {
			atualizarListaMedicamento();
			verificarSolicitacaoVazia();
			exibirDialogConfirmacao();
			return true;
		} catch (ExcecaoSolicitacaoMedicamentoSemItens e) {
			e.printStackTrace();
		}
		return false;
	}

	private void verificarSolicitacaoVazia() throws ExcecaoSolicitacaoMedicamentoSemItens {
		if(getItensSelecionados() == null || getItensSelecionados().isEmpty()){
			throw new ExcecaoSolicitacaoMedicamentoSemItens();
		}
	}
	
	private void prepararAmbienteSolicitacao() {
		setTipoConsulta(TipoConsultaMedicamentoSolicitacaoEnum.UP);
		itensSelecionados = new ArrayList<MaterialSolicitacaoMedicamento>();
		materiaisCadastrados = new ArrayList<MaterialSolicitacaoMedicamento>();
		ocultarDialogConfirmacao();
	}
	
	public void atualizarListaMedicamento(){
		setItensSelecionados(new ArrayList<MaterialSolicitacaoMedicamento>());
		for(MaterialSolicitacaoMedicamento item : getMateriaisCadastrados()){
			if(item.getQuantidadeSolicitada() != null && item.getQuantidadeSolicitada().intValue() != 0 && !getItensSelecionados().contains(item)){
				getItensSelecionados().add(item);
			}else{
				if((item.getQuantidadeSolicitada() == null || item.getQuantidadeSolicitada().intValue() == 0 ) && getItensSelecionados().contains(item)){
					getItensSelecionados().remove(item);
				}
			}
		}
	}
	
	@Override
	public boolean enviar() {
		try {
			boolean liberadoGeral = Parametro.getLiberadoSolicitacaoMedicamentoForaHU();
			if(!liberadoGeral)
				new RestringirAcessoRedeHU().validarAcessoRedeHU();
			getInstancia().setDataInsercao(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.A);
			if(super.enviar()){
				prepararAmbienteSolicitacao();
				carregarListaItensSolicitacao();
				return true;
			}
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoForaRedeHU e) {
			e.printStackTrace();
		}
		return false;
	}

	public void carregarListaItensSolicitacao() {
		String sql = selecionarSql();
		
		ResultSet rs = new LinhaMecanica("db_imhotep").consultar(sql);
		materiaisCadastrados = new ArrayList<MaterialSolicitacaoMedicamento>();
		try {
			while(rs.next()){
				Integer idMaterial = rs.getInt("id_material");
				String materialDescricao = rs.getString("material");
				Integer saldo = rs.getInt("saldo");
				String unidadeMaterial = rs.getString("cv_sigla");
				
				Material material = new Material();
				material.setIdMaterial(idMaterial);
				material.setDescricao(materialDescricao);
				material.setUnidadeMaterial(new UnidadeMaterial());
				material.getUnidadeMaterial().setSigla(unidadeMaterial);
				
				MaterialSolicitacaoMedicamento scm = new MaterialSolicitacaoMedicamento();
				scm.setMaterial(material);
				scm.setQuantidadeAtual(saldo);
				
				materiaisCadastrados.add(scm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String selecionarSql() {
		String sql;
		if(getTipoConsulta().equals(TipoConsultaMedicamentoSolicitacaoEnum.UP))
			sql = getSqlMateriaisUltimaSolicitacao();
		else
			if(getTipoConsulta().equals(TipoConsultaMedicamentoSolicitacaoEnum.TM))
				sql = getSqlTodosMateriais();
			else
				sql = getSqlMateriaisMaisSolicitados();
		return sql;
	}
	
	private String getSqlMateriaisUltimaSolicitacao() {
		int idSMU = getInstancia().getIdSolicitacaoMedicamentoUnidade();
		int idUnidade = getInstancia().getUnidadeDestino().getIdUnidade();
		String sql = "select  a.id_solicitacao_medicamento_unidade, b.dt_data_fechamento, "+ 
						"s.id_material, s.cv_descricao material, e.cv_sigla,  "+
						"(coalesce((select sum(x.in_quantidade_atual) from tb_estoque x "+
						"inner join tb_material b on x.id_material = b.id_material  "+
						"where x.bl_bloqueado = false and x.dt_data_validade >= cast(now() as date) and x.in_quantidade_atual > 0 "+ 
						"and x.id_material = s.id_material), 0) -  "+
						"coalesce((select sum(c.in_quantidade_solicitada) from tb_solicitacao_medicamento_unidade_item c "+ 
						"inner join tb_solicitacao_medicamento_unidade d on d.id_solicitacao_medicamento_unidade = c.id_solicitacao_medicamento_unidade "+ 
						"where d.tp_status_dispensacao = 'P' and d.id_solicitacao_medicamento_unidade != "+idSMU+" and c.id_material = s.id_material), 0)) saldo "+
						"from tb_solicitacao_medicamento_unidade a "+
						"inner join (select max(a.dt_data_fechamento) dt_data_fechamento from tb_solicitacao_medicamento_unidade a "+ 
						"	    where a.id_unidade_destino = "+idUnidade+" and a.dt_data_fechamento is not null) b  "+
						"		on a.dt_data_fechamento = b.dt_data_fechamento  "+
						"inner join tb_solicitacao_medicamento_unidade_item c on c.id_solicitacao_medicamento_unidade = a.id_solicitacao_medicamento_unidade "+
						"inner join tb_material s on s.id_material = c.id_material "+
						"inner join tb_unidade_material e on e.id_unidade_material = s.id_unidade_material "+ 
						"where a.id_unidade_destino = "+idUnidade+" "+
						"order by to_ascii(lower(s.cv_descricao)) ";
		return sql;
	}
	
	private String getSqlTodosMateriais() {
		int idSMU = getInstancia().getIdSolicitacaoMedicamentoUnidade();
		String sql = "select "+
						"(coalesce((select sum(x.in_quantidade_atual) from tb_estoque x "+
						"inner join tb_material b on x.id_material = b.id_material  "+
						"where x.bl_bloqueado = false and x.dt_data_validade >= cast(now() as date) and x.in_quantidade_atual > 0 "+ 
						"and x.id_material = a.id_material), 0) -  "+
						"coalesce((select sum(c.in_quantidade_solicitada) from tb_solicitacao_medicamento_unidade_item c "+ 
						"inner join tb_solicitacao_medicamento_unidade d on d.id_solicitacao_medicamento_unidade = c.id_solicitacao_medicamento_unidade "+ 
						"where d.tp_status_dispensacao = 'P' and d.id_solicitacao_medicamento_unidade != "+idSMU+" and c.id_material = a.id_material), 0)) saldo, "+
						"a.id_material,  "+
						"c.cv_sigla,  "+
						"upper(a.cv_descricao) material from tb_material a "+ 
						"inner join tb_unidade_material c on c.id_unidade_material = a.id_unidade_material "+ 
						"group by a.id_material, c.cv_sigla  "+
						"order by to_ascii(lower(a.cv_descricao))";
		return sql;
	}
	
	private String getSqlMateriaisMaisSolicitados() {
		int idSMU = getInstancia().getIdSolicitacaoMedicamentoUnidade();
		int idUnidade = getInstancia().getUnidadeDestino().getIdUnidade();
		String sql = "select a.id_material, j.cv_sigla, "+
						"upper(a.cv_descricao) material, "+
						"(coalesce((select sum(x.in_quantidade_atual) from tb_estoque x "+
						"inner join tb_material b on x.id_material = b.id_material  "+
						"where x.bl_bloqueado = false and x.dt_data_validade >= cast(now() as date) and x.in_quantidade_atual > 0 "+ 
						"and x.id_material = a.id_material), 0) -  "+
						"coalesce((select sum(c.in_quantidade_solicitada) from tb_solicitacao_medicamento_unidade_item c "+ 
						"inner join tb_solicitacao_medicamento_unidade d on d.id_solicitacao_medicamento_unidade = c.id_solicitacao_medicamento_unidade "+ 
						"where d.tp_status_dispensacao = 'P' and d.id_solicitacao_medicamento_unidade != "+idSMU+" and c.id_material = a.id_material), 0)) saldo "+
						"from tb_material a  "+
						"inner join tb_solicitacao_medicamento_unidade_item s on s.id_material = a.id_material "+
						"inner join tb_solicitacao_medicamento_unidade t on t.id_solicitacao_medicamento_unidade = s.id_solicitacao_medicamento_unidade "+
						"inner join tb_unidade_material j on j.id_unidade_material = a.id_unidade_material  "+
						"where t.id_unidade_destino = "+idUnidade+" "+
						"group by a.id_material, j.cv_sigla "+
						"order by to_ascii(lower(a.cv_descricao))";
		return sql;
	}
	
	public void atribuirUltimoConsumoTresDias(){
		MaterialConsultaRaiz mcr = new MaterialConsultaRaiz();
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
		try {
			lm.criarConexao();
			for(MaterialSolicitacaoMedicamento item : getMateriaisCadastrados()){
				Integer qtd = mcr.quantidadeMaterialSolicitadoUltimaSolicitacao(item.getMaterial(), lm);
				item.setQuantidadeSolicitada(qtd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private SolicitacaoMedicamentoUnidade solicitacaoVizualizacao = new SolicitacaoMedicamentoUnidade();
	private SolicitacaoMedicamentoUnidadeItem itemEdicao;
	private SolicitacaoMedicamentoUnidadeItem itemSolicitacao = new SolicitacaoMedicamentoUnidadeItem();
	
	private boolean exibirDialogAlterarQuantidade;
	
	private Integer quantidadeAlterada;
	
	public int somaTotalQuantidadeLiberada(SolicitacaoMedicamentoUnidadeItem item){
		int total = 0;
		for(DispensacaoSimples obj : item.getDispensacoes()){
			total += obj.getMovimentoLivro().getQuantidadeMovimentacao();
		}
		return total;
	}
	
	public static SolicitacaoMedicamentoUnidadeSolicitacaoRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMedicamentoUnidadeSolicitacaoRaiz) Utilitarios.procuraInstancia(SolicitacaoMedicamentoUnidadeSolicitacaoRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public void apagarItemSolicitacao(){
		if(new SolicitacaoMedicamentoUnidadeItemRaiz(getItemEdicao()).apagar()){
			getInstancia().getItens().remove(getItemEdicao());
		}
		setItemEdicao(new SolicitacaoMedicamentoUnidadeItem());
	}
	
	public void exibirDialogAlteracaoQuantidade(){
		setExibirDialogAlterarQuantidade(true);
	}
	
	public void cancelarAjusteQuantidade(){
		setExibirDialogAlterarQuantidade(false);
		setItemEdicao(new SolicitacaoMedicamentoUnidadeItem());
	}

	public boolean isExibirDialogAlterarQuantidade() {
		return exibirDialogAlterarQuantidade;
	}

	public void setExibirDialogAlterarQuantidade(boolean exibirDialogAlterarQuantidade) {
		this.exibirDialogAlterarQuantidade = exibirDialogAlterarQuantidade;
	}

	public SolicitacaoMedicamentoUnidadeItem getItemEdicao() {
		return itemEdicao;
	}

	public void setItemEdicao(SolicitacaoMedicamentoUnidadeItem itemEdicao) {
		this.itemEdicao = itemEdicao;
	}

	public Integer getQuantidadeAlterada() {
		return quantidadeAlterada;
	}

	public void setQuantidadeAlterada(Integer quantidadeAlterada) {
		this.quantidadeAlterada = quantidadeAlterada;
	}

	public SolicitacaoMedicamentoUnidadeItem getItemSolicitacao() {
		return itemSolicitacao;
	}

	public void setItemSolicitacao(SolicitacaoMedicamentoUnidadeItem itemSolicitacao) {
		this.itemSolicitacao = itemSolicitacao;
	}

	public SolicitacaoMedicamentoUnidade getSolicitacaoVizualizacao() {
		return solicitacaoVizualizacao;
	}

	public void setSolicitacaoVizualizacao(SolicitacaoMedicamentoUnidade solicitacaoVizualizacao) {
		this.solicitacaoVizualizacao = solicitacaoVizualizacao;
	}

	public List<MaterialSolicitacaoMedicamento> getMateriaisCadastrados() {
		return materiaisCadastrados;
	}

	public void setMateriaisCadastrados(List<MaterialSolicitacaoMedicamento> materiaisCadastrados) {
		this.materiaisCadastrados = materiaisCadastrados;
	}

	public TipoConsultaMedicamentoSolicitacaoEnum getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(TipoConsultaMedicamentoSolicitacaoEnum tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public List<MaterialSolicitacaoMedicamento> getItensSelecionados() {
		return itensSelecionados;
	}

	public void setItensSelecionados(List<MaterialSolicitacaoMedicamento> itensSelecionados) {
		this.itensSelecionados = itensSelecionados;
	}

	public boolean isExibirDialogConfirmacao() {
		return exibirDialogConfirmacao;
	}

	public void setExibirDialogConfirmacao(boolean exibirDialogConfirmacao) {
		this.exibirDialogConfirmacao = exibirDialogConfirmacao;
	}

}
