package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.MaterialConsultaRaiz;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.InventarioFarmaciaControle;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorMovimento;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AjusteGeralEstoqueRaiz extends PadraoRaiz<InventarioFarmaciaControle>{
	
	private List<Material> materiais = new ArrayList<Material>();
	private List<Estoque> estoques = new ArrayList<Estoque>();
	private List<MovimentoLivro> movimentos = new ArrayList<MovimentoLivro>();
	private List<MovimentoLivro> movimentosSelecionados = new ArrayList<MovimentoLivro>();
	private Material material;
	private Estoque estoque;
	private Estoque estoqueDestino;
	private String tipoOperacao;
	private boolean exibirModalMigrarMovimento;
	private boolean exibirModalApagarMovimento;
	
	public void apagarMovimentos(){
		super.setExibeMensagemAtualizacao(false);
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, "127.0.0.1");
		try {
			for(MovimentoLivro mov : getMovimentosSelecionados()){
				mov.setEstoque(getEstoqueDestino());
				String sql = "select (b.id_dispensacao_simples is not null) dispensado, b.id_solicitacao_medicamento_unidade_item item from tb_movimento_livro a "+ 
								"left join tb_dispensacao_simples b on a.id_movimento_livro = b.id_movimento_livro "+
								"where a.id_movimento_livro = " + mov.getIdMovimentoLivro();
				ResultSet rs = lm.consultar(sql);
				rs.next();
				boolean dispensado = rs.getBoolean("dispensado");
				if(dispensado){
					int idItem = rs.getInt("item");
					String data = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
					String sql1 = "update tb_solicitacao_medicamento_unidade_item set tp_tipo_status_item = 'E', "+ 
									"cv_justificativa = 'Item estornado em "+data+"' where "+
									"id_solicitacao_medicamento_unidade_item = "+ idItem;
					String sql2 = "delete from tb_dispensacao_simples where id_movimento_livro = " + mov.getIdMovimentoLivro();
					
					lm.executarCUD(sql1);
					lm.executarCUD(sql2);
				}
				
				String sql3 = "delete from tb_movimento_livro where id_movimento_livro = "+mov.getIdMovimentoLivro();
				lm.executarCUD(sql3);
			}
			super.setExibeMensagemAtualizacao(true);
			atualizarMovimentos(lm, getEstoque().getIdEstoque(), getEstoque().getLote());
			carregarMovimentos();
			ocultarModalApagarMovimento();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void migrarMovimentos(){
		super.setExibeMensagemAtualizacao(false);
		for(MovimentoLivro mov : getMovimentosSelecionados()){
			mov.setEstoque(getEstoqueDestino());
			super.atualizarGenerico(mov);
		}
		super.setExibeMensagemAtualizacao(true);
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, "127.0.0.1");
		atualizarMovimentos(lm, getEstoque().getIdEstoque(), getEstoque().getLote());
		atualizarMovimentos(lm, getEstoqueDestino().getIdEstoque(), getEstoqueDestino().getLote());
		carregarMovimentos();
	}
	
	private void atualizarMovimentos(LinhaMecanica lm, int idEstoque, String lote) {
		try {
			lm.criarConexao();
			AtualizadorMovimento.atualizarMovimentacoes(lm, idEstoque, lote);
			AtualizadorMovimento.atualizarSaldoAtual(lm, idEstoque);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}
	
	public void exibirModalApagarMovimento(){
		atualizarMovimentos();
		setExibirModalApagarMovimento(true);
	}
	
	public void ocultarModalApagarMovimento(){
		setExibirModalApagarMovimento(false);
		atualizarVariaveis();
	}
	
	public void exibirModalMigrarMovimento(){
		atualizarMovimentos();
		setExibirModalMigrarMovimento(true);
	}

	private void atualizarMovimentos() {
		List<MovimentoLivro> temp = new ArrayList<MovimentoLivro>();
		for(MovimentoLivro mov : getMovimentosSelecionados()){
			String hql = "select o from MovimentoLivro o where o.idMovimentoLivro = "+mov.getIdMovimentoLivro();
			MovimentoLivro m = new ConsultaGeral<MovimentoLivro>().consultaUnica(new StringBuilder(hql), null);
			temp.add(m);
		}
		setMovimentosSelecionados(temp);
	}
	
	public void ocultarModalMigrarMovimento(){
		setExibirModalMigrarMovimento(false);
		atualizarVariaveis();
	}

	private void atualizarVariaveis() {
		setMovimentosSelecionados(new ArrayList<MovimentoLivro>());
		setEstoqueDestino(new Estoque());
		carregarLotes();
	}
	
	public void carregarMovimentos(){
		setMovimentos(new ArrayList<MovimentoLivro>());
		if(getEstoque() != null){
			String sqlMovimento = "select d.id_movimento_livro, d.cv_justificativa, e.id_tipo_movimento, e.cv_descricao descTipo, e.tp_operacao, "+
								"d.dt_data_movimento, d.in_quantidade_movimentacao, d.in_quantidade_atual from tb_movimento_livro d "+
								"inner join tb_tipo_movimento e on e.id_tipo_movimento = d.id_tipo_movimento "+
								"where d.id_estoque = " + getEstoque().getIdEstoque() + " order by d.dt_data_movimento"; 
			LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
			ResultSet rs = lm.consultar(sqlMovimento);
			try {
				while(rs.next()){
					Integer idMovimentoLivro = rs.getInt("id_movimento_livro");
					Date dataMovimento = rs.getTimestamp("dt_data_movimento");
					int idTipoMovimento = rs.getInt("id_tipo_movimento");
					String tipoOperacao = rs.getString("tp_operacao");
					String descricaoTipo = rs.getString("descTipo");
					String justificativa = rs.getString("cv_justificativa");
					int quantidadeMovimento = rs.getInt("in_quantidade_movimentacao");
					int quantidadeAtual = rs.getInt("in_quantidade_atual");
					
					MovimentoLivro ml = new MovimentoLivro();
					ml.setIdMovimentoLivro(idMovimentoLivro);
					ml.setDataMovimento(dataMovimento);
					ml.setJustificativa(justificativa);
					ml.setTipoMovimento(new TipoMovimento());
					ml.getTipoMovimento().setIdTipoMovimento(idTipoMovimento);
					ml.getTipoMovimento().setTipoOperacao(getTipoOp(tipoOperacao));
					ml.getTipoMovimento().setDescricao(descricaoTipo);
					ml.setQuantidadeMovimentacao(quantidadeMovimento);
					ml.setQuantidadeAtual(quantidadeAtual);
					ml.setEstoque(getEstoque());
					getMovimentos().add(ml);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private TipoOperacaoEnum getTipoOp(String tipo){
		for(TipoOperacaoEnum op : TipoOperacaoEnum.values()){
			if(op.name().equals(tipo))
				return op;
		}
		return null;
	}
	
	public void carregarLotes(){
		setEstoques(new ArrayList<Estoque>());
		if(getMaterial() != null){
			String sqlEstoque = "select d.id_estoque from tb_estoque d "+
					"where cast(d.dt_data_validade as timestamp) >= cast('2014-07-13 23:59:59' as timestamp) and "+  
					"cast(d.dt_data_inclusao as timestamp) <= cast('2014-07-13 23:59:59' as timestamp) and "+ 
					"d.id_material = " + getMaterial().getIdMaterial(); 
			LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
			ResultSet rs = lm.consultar(sqlEstoque);
			try {
				String ids = "";
				while(rs.next()){
					Integer idEstoque = rs.getInt("id_estoque");
					ids += "," + idEstoque;
				}
				ids = ids.replaceFirst(",", "");
				if(!ids.isEmpty()){
					String hql = "select o from Estoque o where o.idEstoque in ("+ids+") order by upper(o.lote)";
					setEstoques(new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(new StringBuilder(hql), null)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<SelectItem> getTipoOperacaoItens(){
		List<SelectItem> itens = new ArrayList<SelectItem>();
		
		itens.add(new SelectItem("Negativos", "Negativos"));
		itens.add(new SelectItem("Todos", "Todos"));
		
		return itens;
	} 
	
	public void carregarMaterialConformeTipo(){
		if(getTipoOperacao().equals("Todos"))
			setMateriais(new MaterialConsultaRaiz().getMateriaisCadastrados());
		else
			if(getTipoOperacao().equals("Negativos"))
				setMateriais(new MaterialConsultaRaiz().getMateriaisNegativos());
	}

	public List<Material> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<Material> materiais) {
		this.materiais = materiais;
	}

	public List<Estoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<Estoque> estoques) {
		this.estoques = estoques;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public List<MovimentoLivro> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<MovimentoLivro> movimentos) {
		this.movimentos = movimentos;
	}

	public List<MovimentoLivro> getMovimentosSelecionados() {
		return movimentosSelecionados;
	}

	public void setMovimentosSelecionados(List<MovimentoLivro> movimentosSelecionados) {
		this.movimentosSelecionados = movimentosSelecionados;
	}

	public boolean isExibirModalMigrarMovimento() {
		return exibirModalMigrarMovimento;
	}

	public void setExibirModalMigrarMovimento(boolean exibirModalMigrarMovimento) {
		this.exibirModalMigrarMovimento = exibirModalMigrarMovimento;
	}

	public boolean isExibirModalApagarMovimento() {
		return exibirModalApagarMovimento;
	}

	public void setExibirModalApagarMovimento(boolean exibirModalApagarMovimento) {
		this.exibirModalApagarMovimento = exibirModalApagarMovimento;
	}

	public Estoque getEstoqueDestino() {
		return estoqueDestino;
	}

	public void setEstoqueDestino(Estoque estoqueDestino) {
		this.estoqueDestino = estoqueDestino;
	} 
	
}
