package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.RestringirAcessoRedeHU;
import br.com.imhotep.consulta.raiz.MaterialConsultaRaiz;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.UnidadeMaterial;
import br.com.imhotep.entidade.extra.MaterialSolicitacaoMedicamento;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoForaRedeHU;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoItemInseridaDuasVezes;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoMedicamentoItemSemQuantidadeSolicitada;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoMedicamentoItemSemSaldo;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoMedicamentoSemItens;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoNaoFechada;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoSemUnidade;
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
	private MaterialSolicitacaoMedicamento material = new MaterialSolicitacaoMedicamento();
	private SolicitacaoMedicamentoUnidade solicitacaoVizualizacao = new SolicitacaoMedicamentoUnidade();

	public SolicitacaoMedicamentoUnidadeSolicitacaoRaiz() {
		carregarMateriais();
	}
	
	public int somaTotalQuantidadeLiberada(SolicitacaoMedicamentoUnidadeItem item){
		int total = 0;
		for(DispensacaoSimples obj : item.getDispensacoes()){
			total += obj.getMovimentoLivro().getQuantidadeMovimentacao();
		}
		return total;
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		prepararAmbienteSolicitacao();
	}
	
	private void prepararAmbienteSolicitacao(){
		setItensSelecionados(new ArrayList<MaterialSolicitacaoMedicamento>());
		setMaterial(new MaterialSolicitacaoMedicamento());
	}
	
	public void addMedicamento(){
			try {
				validarUnidadeDestinoSelecionada();
				if(!getMaterial().isComSaldo())
					throw new ExcecaoSolicitacaoMedicamentoItemSemSaldo();
				if(getMaterial().getQuantidadeSolicitada() == null)
					throw new ExcecaoSolicitacaoMedicamentoItemSemQuantidadeSolicitada();
				if(getMaterial() != null){
					verificarItemDuplicado();
					setExibeMensagemInsercao(false);
					verificarSolicitacaoNaoCadastrada();
					getItensSelecionados().add(getMaterial());
					Date dataAtual = new Date();
					PadraoFluxoTemp.limparFluxo();
					salvarItem(getInstancia(), dataAtual, 0, getMaterial());
					PadraoFluxoTemp.finalizarFluxo();
					PadraoFluxoTemp.limparFluxo();
					setMaterial(new MaterialSolicitacaoMedicamento());
				}
			} catch (ExcecaoSolicitacaoMedicamentoItemSemSaldo e) {
				e.printStackTrace();
			} catch (ExcecaoSolicitacaoMedicamentoItemSemQuantidadeSolicitada e) {
				e.printStackTrace();
			} catch (ExcecaoSolicitacaoItemInseridaDuasVezes e) {
				e.printStackTrace();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			} catch (ExcecaoPadraoFluxo e) {
				e.printStackTrace();
			} catch (ExcecaoSolicitacaoSemUnidade e) {
				e.printStackTrace();
			}
	}

	private void verificarItemDuplicado() throws ExcecaoSolicitacaoItemInseridaDuasVezes {
		for(MaterialSolicitacaoMedicamento item : getItensSelecionados()){
			if(item.equals(getMaterial())){
				throw new ExcecaoSolicitacaoItemInseridaDuasVezes();
			}
		}
	}
	
	public void remMedicamento(){
		if(getItensSelecionados().remove(getMaterial())){
			setMaterial(new MaterialSolicitacaoMedicamento());
			String sql = "DELETE FROM tb_solicitacao_medicamento_unidade_item WHERE id_material = " 
							+ getMaterial().getMaterial().getIdMaterial() 
							+ " and id_solicitacao_medicamento_unidade = " + getInstancia().getIdSolicitacaoMedicamentoUnidade();
			new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar(sql);
		}
	}
	
	public Collection<MaterialSolicitacaoMedicamento> autoCompleteMedicamento(String string){
		string = string.trim().toLowerCase();
		Collection<MaterialSolicitacaoMedicamento> resultado = new ArrayList<MaterialSolicitacaoMedicamento>();
		for(MaterialSolicitacaoMedicamento item : getMateriaisCadastrados()){
			if(item.getMaterial().getDescricao().toLowerCase().contains(string)){
				resultado.add(item);
			}
		}
		return resultado;
	}
	
	public void carregarMateriais(){
		String sql = getSqlTodosMateriais();
		ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar(sql);
		try {
			while(rs.next()){
				int saldo = rs.getInt("saldo");
				int idMaterial = rs.getInt("id_material");
				String sigla = rs.getString("cv_sigla");
				String descricao = rs.getString("material");
				
				Material medicamento = new Material();
				medicamento.setIdMaterial(idMaterial);
				UnidadeMaterial unidade = new UnidadeMaterial();
				unidade.setSigla(sigla);
				medicamento.setUnidadeMaterial(unidade);
				medicamento.setDescricao(descricao);
				MaterialSolicitacaoMedicamento item = new MaterialSolicitacaoMedicamento();
				item.setMaterial(medicamento);
				item.setQuantidadeAtual(saldo);
				getMateriaisCadastrados().add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		int cont = 0;
		for(MaterialSolicitacaoMedicamento item : getItensSelecionados()){
			salvarItem(instancia, dataAtual, cont, item);
		}
		PadraoFluxoTemp.finalizarFluxo();
	}

	private void salvarItem(SolicitacaoMedicamentoUnidade instancia,
			Date dataAtual, int cont, MaterialSolicitacaoMedicamento item)
			throws ExcecaoProfissionalLogado {
		SolicitacaoMedicamentoUnidadeItem msmi = new SolicitacaoMedicamentoUnidadeItem();
		msmi.setDataInsercao(dataAtual);
		msmi.setMaterial(item.getMaterial());
		msmi.setProfissionalInsercao(Autenticador.getProfissionalLogado());
		msmi.setQuantidadeSolicitada(item.getQuantidadeSolicitada());
		msmi.setSolicitacaoMedicamentoUnidade(instancia);
		msmi.setStatusItem(TipoStatusSolicitacaoItemEnum.P);
		PadraoFluxoTemp.getObjetoSalvar().put("Item-"+msmi.hashCode(), msmi);
		System.out.println("ItemSolicitado - " + msmi.getMaterial().getIdMaterial() + " - " + cont + " - " + msmi.hashCode());
	}
	
	public void fecharSolicitacao(){
		try {
			validarUnidadeDestinoSelecionada();
			verificarSolicitacaoVazia();
			setExibeMensagemAtualizacao(false);
			carregarSaldoAtualItens();
			validarSaldo();
			verificarSolicitacaoNaoCadastrada();
//			salvarItens(getInstancia());
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
		} catch (ExcecaoSolicitacaoMedicamentoSemItens e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoSemUnidade e) {
			e.printStackTrace();
		}
	}

	private void validarUnidadeDestinoSelecionada() throws ExcecaoSolicitacaoSemUnidade {
		if(getInstancia().getUnidadeDestino() == null){
			throw new ExcecaoSolicitacaoSemUnidade();
		}
	}

	private void verificarSolicitacaoVazia() throws ExcecaoSolicitacaoMedicamentoSemItens {
		if(getItensSelecionados() == null || getItensSelecionados().isEmpty()){
			throw new ExcecaoSolicitacaoMedicamentoSemItens();
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
			return super.enviar();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoForaRedeHU e) {
			e.printStackTrace();
		}
		return false;
	}

	public void carregarListaItensUltimaSolicitacao() {
		setItensSelecionados(new ArrayList<MaterialSolicitacaoMedicamento>());
		
		if(getInstancia().getUnidadeDestino() == null){
			try {
				throw new ExcecaoSolicitacaoSemUnidade();
			} catch (ExcecaoSolicitacaoSemUnidade e) {
				e.printStackTrace();
				return;
			}
		}
		setExibeMensagemInsercao(false);
		verificarSolicitacaoNaoCadastrada();
		
		String sql = getSqlMateriaisUltimaSolicitacao();
		ResultSet rs = new LinhaMecanica("db_imhotep").consultar(sql);
		try {
			while(rs.next()){
				Integer idMaterial = rs.getInt("id_material");
				String materialDescricao = rs.getString("material");
				Integer saldo = rs.getInt("saldo");
				Integer qtdSolicitado = rs.getInt("qtdSolicitado");
				String unidadeMaterial = rs.getString("cv_sigla");
				
				Material material = new Material();
				material.setIdMaterial(idMaterial);
				material.setDescricao(materialDescricao);
				material.setUnidadeMaterial(new UnidadeMaterial());
				material.getUnidadeMaterial().setSigla(unidadeMaterial);
				
				MaterialSolicitacaoMedicamento scm = new MaterialSolicitacaoMedicamento();
				scm.setMaterial(material);
				scm.setQuantidadeAtual(saldo);
				scm.setQuantidadeSolicitada(qtdSolicitado);
				
				getItensSelecionados().add(scm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void verificarSolicitacaoNaoCadastrada() {
		if(!super.isEdicao()){
			enviar();
		}
	}

	private String getSqlMateriaisUltimaSolicitacao() {
		int idSMU = getInstancia().getIdSolicitacaoMedicamentoUnidade();
		int idSolicitante = getInstancia().getProfissionalInsercao().getIdProfissional();
		String sql = "select  a.id_solicitacao_medicamento_unidade, b.dt_data_fechamento,  "+
						"s.id_material, s.cv_descricao material, e.cv_sigla,   "+
						"(coalesce((select sum(x.in_quantidade_atual) from tb_estoque x "+ 
						"inner join tb_material b on x.id_material = b.id_material   "+
						"where x.bl_bloqueado = false and x.dt_data_validade >= cast(now() as date) and x.in_quantidade_atual > 0 "+ 
						"and x.id_material = s.id_material), 0) -  "+
						"coalesce((select sum(c.in_quantidade_solicitada) from tb_solicitacao_medicamento_unidade_item c "+ 
						"inner join tb_solicitacao_medicamento_unidade d on d.id_solicitacao_medicamento_unidade = c.id_solicitacao_medicamento_unidade "+ 
						"where d.tp_status_dispensacao = 'P' and d.id_solicitacao_medicamento_unidade != "+idSMU+" and c.id_material = s.id_material), 0)) saldo, "+
						"c.in_quantidade_solicitada qtdSolicitado "+
						"from tb_solicitacao_medicamento_unidade a  "+
						"inner join (select max(a.dt_data_fechamento) dt_data_fechamento from tb_solicitacao_medicamento_unidade a "+ 
						"	    where a.id_profissional_insercao = "+idSolicitante+" and a.dt_data_fechamento is not null) b   "+
						"		on a.dt_data_fechamento = b.dt_data_fechamento   "+
						"inner join tb_solicitacao_medicamento_unidade_item c on c.id_solicitacao_medicamento_unidade = a.id_solicitacao_medicamento_unidade "+ 
						"inner join tb_material s on s.id_material = c.id_material  "+
						"inner join tb_unidade_material e on e.id_unidade_material = s.id_unidade_material "+ 
						"where a.id_profissional_insercao = "+idSolicitante+" "+
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
						"where a.bl_bloqueado is false " +
						"group by a.id_material, c.cv_sigla  "+
						"order by to_ascii(lower(a.cv_descricao))";
		return sql;
	}
	
	public List<MaterialSolicitacaoMedicamento> getItensSelecionados() {
		return itensSelecionados;
	}

	public void setItensSelecionados(List<MaterialSolicitacaoMedicamento> itensSelecionados) {
		this.itensSelecionados = itensSelecionados;
	}

	public MaterialSolicitacaoMedicamento getMaterial() {
		return material;
	}

	public void setMaterial(MaterialSolicitacaoMedicamento material) {
		this.material = material;
	}

	public List<MaterialSolicitacaoMedicamento> getMateriaisCadastrados() {
		return materiaisCadastrados;
	}

	public void setMateriaisCadastrados(List<MaterialSolicitacaoMedicamento> materiaisCadastrados) {
		this.materiaisCadastrados = materiaisCadastrados;
	}

	public SolicitacaoMedicamentoUnidade getSolicitacaoVizualizacao() {
		return solicitacaoVizualizacao;
	}

	public void setSolicitacaoVizualizacao(SolicitacaoMedicamentoUnidade solicitacaoVizualizacao) {
		this.solicitacaoVizualizacao = solicitacaoVizualizacao;
	}
	
	
	
	
	
	
	
}
