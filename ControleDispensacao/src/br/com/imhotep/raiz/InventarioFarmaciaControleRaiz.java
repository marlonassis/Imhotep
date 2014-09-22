package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.MaterialConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.InventarioFarmaciaControle;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.entidade.extra.EstoqueApoio;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorMovimento;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioFarmaciaControleRaiz extends PadraoRaiz<InventarioFarmaciaControle>{
	
	private List<Material> materiaisNaoLiberados = new ArrayList<Material>();
	private List<Material> materiaisLiberados = new ArrayList<Material>();
	private List<EstoqueApoio> estoquesValidos = new ArrayList<EstoqueApoio>();
	private List<EstoqueApoio> estoquesInventariados = new ArrayList<EstoqueApoio>();
 	private Material materialEdicao;
	private EstoqueApoio estoqueEdicao;
	private Estoque estoqueCadastro;
	private boolean exibirModalMateriaisLiberados;
	private boolean exibirModalAnaliseInventario;
	private boolean exibirModalAdicionarNovoLote;
	private boolean exibirModalAtualizacaoLote;
	
	public InventarioFarmaciaControleRaiz(){
		carregarMateriaisNaoLiberados();
	}
	
	public void atualizarEstoque(){
		String dataAtu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getEstoqueEdicao().getEstoque().getDataValidade());
		String lote = getEstoqueEdicao().getEstoque().getLote();
		String sql = "update tb_estoque set cv_lote = '"+lote+"', dt_data_validade = '"+dataAtu+"' where id_estoque = "+getEstoqueEdicao().getEstoque().getIdEstoque();
		if(new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).executarCUD(sql)){
			ocultarModalAtualizacaoLote();
		}
	}
	
	public void exibirModalAtualizacaoLote(){
		setExibirModalAtualizacaoLote(true);
	}
	
	public void ocultarModalAtualizacaoLote(){
		setExibirModalAtualizacaoLote(false);
		carregarEstoquesCadastrados();
	}
	
	public void tornarMedicamentoPendente(){
		new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).executarCUD("delete from farmacia.tb_inventario_controle where id_material = " + getMaterialEdicao().getIdMaterial());
		carregarMateriaisLiberados();
	}
	
	public void liberarMedicamento(){
		try {
			InventarioFarmaciaControle obj = new InventarioFarmaciaControle();
			obj.setMaterial(getMaterialEdicao());
			obj.setProfisionalCadastro(Autenticador.getProfissionalLogado());
			if(super.enviarGenerico(obj)){
				carregarMateriaisNaoLiberados();
				setExibirModalAnaliseInventario(false);
			}
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	public void cadastrarLote(){
		try {
			PadraoFluxoTemp.limparFluxo();
			PadraoFluxoTemp.getObjetoSalvar().put("Estoque-"+getEstoqueCadastro().hashCode(), getEstoqueCadastro());
			MovimentoLivro ml = new MovimentoLivro();
			ml.setDataMovimento(dataHoraRetroativo());
			ml.setEstoque(getEstoqueCadastro());
			ml.setQuantidadeAtual(0);
			ml.setQuantidadeMovimentacao(getEstoqueCadastro().getQuantidadeAtual());
			ml.setTipoMovimento(getTipoMovimentoEntradaInventario());
			ml.setUsuarioMovimentacao(getUsuario());
			PadraoFluxoTemp.getObjetoSalvar().put("ML-"+ml.hashCode(), ml);
			PadraoFluxoTemp.finalizarFluxo();
			ocultarModalAdicionarNovoLote();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
	}
	
	public boolean loteCadastrado(String lote){
		ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar("select id_estoque from tb_estoque where lower(cv_lote) = lower('"+lote+"')");
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void exibirModalAdicionarNovoLote(){
		prepararEstoque();
		setExibirModalAdicionarNovoLote(true);
	}

	private void prepararEstoque() {
		setEstoqueCadastro(new Estoque());
		getEstoqueCadastro().setDataInclusao(dataHoraRetroativo());
		getEstoqueCadastro().setLote(getEstoqueEdicao().getLote());
		getEstoqueCadastro().setMaterial(getMaterialEdicao());
		getEstoqueCadastro().setUsuarioInclusao(getUsuario());
		getEstoqueCadastro().setDataValidade(getEstoqueEdicao().getDataValidade());
	}
	
	public void ocultarModalAdicionarNovoLote(){
		setEstoqueCadastro(null);
		setEstoqueEdicao(null);
		setExibirModalAdicionarNovoLote(false);
		carregarEstoquesValidos();
	}
	
	public Integer getSaldoRetroativo(){
		int qtd = 0;
		try {
			int idMaterial = getIdMaterial();
			
			String sqlEstoque = "select d.id_estoque, d.cv_lote, d.dt_data_validade from tb_estoque d "+
					"where cast(d.dt_data_validade as timestamp) >= cast('2014-07-13 23:59:59' as timestamp) and "+  
					"cast(d.dt_data_inclusao as timestamp) <= cast('2014-07-13 23:59:59' as timestamp) and "+ 
					"d.id_material = " + idMaterial; 
			ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar(sqlEstoque);
			while(rs.next()){
				Integer idEstoque = rs.getInt("id_estoque");
				String sql = "select (coalesce((select sum(c.in_quantidade_movimentacao) from tb_movimento_livro c "+ 
						"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento  "+
						"where f.tp_operacao = 'E' and c.id_estoque = "+idEstoque+" and "+
						"cast(c.dt_data_movimento as timestamp) <= cast('2014-07-13 23:59:59' as timestamp)), 0) - "+  
						"coalesce((select sum(c.in_quantidade_movimentacao) from tb_movimento_livro c "+ 
						"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento  "+
						"where f.tp_operacao != 'E' and c.id_estoque = "+idEstoque+" and "+
						"cast(c.dt_data_movimento as timestamp) <= cast('2014-07-13 23:59:59' as timestamp)), 0)) saldo";
				ResultSet rs2 = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar(sql);
				rs2.next();
				qtd += rs2.getInt("saldo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qtd;
	}

	private int getIdMaterial() {
		int idMaterial = 0;
		if(getMaterialEdicao() != null)
			idMaterial = getMaterialEdicao().getIdMaterial();
		return idMaterial;
	}
	
	public Integer getQuantidadeInventariada(){
		int qtd = 0;
		for(EstoqueApoio obj : getEstoquesInventariados()){
			qtd += obj.getQuantidadeAtual();
		}
		return qtd;
	}
	
	public String getResolucao(){
		Integer saldoReferencia = getSaldoRetroativo();
		Integer saldoInventario = getQuantidadeInventariada();
		if(saldoInventario.intValue() > saldoReferencia.intValue()){
			int qtd = saldoInventario.intValue() - saldoReferencia.intValue();
			return "Some "+qtd+" unidades";
		}
		
		if(saldoInventario.intValue() < saldoReferencia.intValue()){
			int qtd = saldoReferencia.intValue() - saldoInventario.intValue();
			return "Subtraia "+qtd+" unidades";
		}
		
		if(saldoInventario.intValue() == saldoReferencia.intValue()){
			return "O saldo do dia 13 Ž igual ao saldo inventariado.";
		}
		
		return "Ocorreu uma falha.";
	}
	
	public Integer getSaldoAtualLotesValidos(){
		int saldo = 0;
		for(EstoqueApoio e : getEstoquesValidos()){
			saldo += e.getEstoque().getQuantidadeAtual();
		}
		return Integer.valueOf(saldo);
	}
	
	public void apagarEstoque(){
		if(super.apagarGenerico(getEstoqueEdicao())){
			carregarEstoquesValidos();
		}
	}
	
	public void adicionarSaldoEstoque(){
		if(getEstoqueEdicao().getQuantidadeAtual() == null || getEstoqueEdicao().getQuantidadeAtual().intValue() == 0){
			super.mensagem("Informe alguma quantidade", null, Constantes.WARN);
			return;
		}
		gerarMovimentoEntrada();
		carregarEstoquesValidos();
	}
	
	public void removerSaldoEstoque(){
		if(getEstoqueEdicao().getQuantidadeAtual() == null || getEstoqueEdicao().getQuantidadeAtual().intValue() == 0){
			super.mensagem("Informe alguma quantidade", null, Constantes.WARN);
			return;
		}
		gerarMovimentoSaida();
		carregarEstoquesValidos();
	}
	
	private void gerarMovimentoSaida(){
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
		try {
			new ControleEstoqueTemp().lockEstoque(getEstoqueEdicao().getEstoque());
			MovimentoLivro ml = new MovimentoLivro();
			ml.setDataMovimento(dataHoraRetroativo());
			ml.setEstoque(getEstoqueEdicao().getEstoque());
			ml.setQuantidadeAtual(getEstoqueEdicao().getEstoque().getQuantidadeAtual());
			ml.setQuantidadeMovimentacao(getEstoqueEdicao().getQuantidadeAtual());
			ml.setTipoMovimento(getTipoMovimentoSaidaInventario());
			ml.setUsuarioMovimentacao(getUsuario());
			if(super.enviarGenerico(ml)){
				lm.executarCUD("update tb_estoque set in_quantidade_atual = in_quantidade_atual - " + 
								getEstoqueEdicao().getQuantidadeAtual() + " where id_estoque = " + 
								getEstoqueEdicao().getEstoque().getIdEstoque());
			}
		} catch (ExcecaoEstoqueLockAcimaUmMinuto e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueLock e) {
			e.printStackTrace();
		}finally{
			try {
				new ControleEstoqueTemp().unLockEstoque(getEstoqueEdicao().getEstoque());
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
			carregarEstoquesValidos();
			atualizarMovimentos(lm);
		}
	}
	
	private void gerarMovimentoEntrada(){
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
		try {
			new ControleEstoqueTemp().lockEstoque(getEstoqueEdicao().getEstoque());
			MovimentoLivro ml = new MovimentoLivro();
			ml.setDataMovimento(dataHoraRetroativo());
			ml.setEstoque(getEstoqueEdicao().getEstoque());
			ml.setQuantidadeAtual(getEstoqueEdicao().getEstoque().getQuantidadeAtual());
			ml.setQuantidadeMovimentacao(getEstoqueEdicao().getQuantidadeAtual());
			ml.setTipoMovimento(getTipoMovimentoEntradaInventario());
			ml.setUsuarioMovimentacao(getUsuario());
			if(super.enviarGenerico(ml)){
				lm.executarCUD("update tb_estoque set in_quantidade_atual = in_quantidade_atual + " + 
								getEstoqueEdicao().getQuantidadeAtual() + " where id_estoque = " + 
								getEstoqueEdicao().getEstoque().getIdEstoque());
			}
		} catch (ExcecaoEstoqueLockAcimaUmMinuto e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueLock e) {
			e.printStackTrace();
		}finally{
			try {
				new ControleEstoqueTemp().unLockEstoque(getEstoqueEdicao().getEstoque());
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
			carregarEstoquesValidos();
			atualizarMovimentos(lm);
		}
	}

	private void atualizarMovimentos(LinhaMecanica lm) {
		try {
			lm.criarConexao();
			AtualizadorMovimento.atualizarMovimentacoes(lm, getEstoqueEdicao().getEstoque().getIdEstoque(), getEstoqueEdicao().getEstoque().getLote());
			AtualizadorMovimento.atualizarSaldoAtual(lm, getEstoqueEdicao().getEstoque().getIdEstoque());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}
	
	private Usuario getUsuario(){
		Usuario usuario = null;
		try {
			usuario = Autenticador.getInstancia().getUsuarioAtual();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	private TipoMovimento getTipoMovimentoEntradaInventario(){
		TipoMovimento tipo = new TipoMovimento();
		tipo.setIdTipoMovimento(30);
		return tipo;
	}
	
	private TipoMovimento getTipoMovimentoSaidaInventario(){
		TipoMovimento tipo = new TipoMovimento();
		tipo.setIdTipoMovimento(29);
		return tipo;
	}
	
	private Date dataHoraRetroativo() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 13);
		c.set(Calendar.MONTH, 06);
		c.set(Calendar.YEAR, 2014);
		c.set(Calendar.HOUR_OF_DAY, (8 + new Random().nextInt(18-8)));
		c.set(Calendar.MINUTE, new Random().nextInt(59));
		c.set(Calendar.SECOND, new Random().nextInt(59));
		c.set(Calendar.MILLISECOND, new Random().nextInt(1000));
		return c.getTime();
	}
	
	public void carregarMateriaisNaoLiberados(){
		setMateriaisNaoLiberados(new MaterialConsultaRaiz().getMateriaisNaoLiberadosInventario());
	}
	
	public void carregarMateriaisLiberados(){
		setMateriaisLiberados(new MaterialConsultaRaiz().getMateriaisLiberadosInventario());
	}
	
	public void exibirModalAnaliseInventario(){
		carregarEstoquesValidos();
		setExibirModalAnaliseInventario(true);
	}
	
	private void carregarEstoquesValidos() {
		carregarEstoquesCadastrados();
		carregarEstoquesInventariados();
	}

	private void carregarEstoquesInventariados() {
		setEstoquesInventariados(new ArrayList<EstoqueApoio>());
		String sqlEstoque = "select cv_lote, in_quantidade_contada, dt_data_validade from farmacia.tb_inventario where id_material = "+ getMaterialEdicao().getIdMaterial();
		ResultSet rs2 = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar(sqlEstoque);
		try {
			while(rs2.next()){
				String lote = rs2.getString("cv_lote");
				Integer quantidadeContada = rs2.getInt("in_quantidade_contada");
				Date dataValidade = rs2.getTimestamp("dt_data_validade");
				EstoqueApoio mce = new EstoqueApoio();
				mce.setLote(lote);
				mce.setQuantidadeAtual(quantidadeContada);
				mce.setTipo("Inventariado");
				if(dataValidade != null)
					mce.setValidade(new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(dataValidade));
				mce.setDataValidade(dataValidade);
				getEstoquesInventariados().add(mce);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void carregarEstoquesCadastrados() {
		setEstoquesValidos(new ArrayList<EstoqueApoio>());
		String sqlEstoque = "select d.id_estoque, d.cv_lote, d.dt_data_validade, d.in_quantidade_atual from tb_estoque d "+
				"where cast(d.dt_data_validade as timestamp) >= cast('2014-07-13 23:59:59' as timestamp) and "+  
				"cast(d.dt_data_inclusao as timestamp) <= cast('2014-07-13 23:59:59' as timestamp) and "+ 
				"d.id_material = " + getMaterialEdicao().getIdMaterial(); 
			LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
			ResultSet rs = lm.consultar(sqlEstoque);
			try {
				while(rs.next()){
					Date validade = rs.getTimestamp("dt_data_validade");
					String lote = rs.getString("cv_lote");
					Integer idEstoque = rs.getInt("id_estoque");
					
					
					String sql666 = "select (coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b "+ 
							"inner join tb_tipo_movimento c on b.id_tipo_movimento = c.id_tipo_movimento   "+
							"where c.tp_operacao = 'E' and b.id_estoque = "+idEstoque+" and b.dt_data_movimento <= cast('2014-07-13 23:59:59' as timestamp)), 0) - "+ 
							"coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b   "+
							"inner join tb_tipo_movimento c on b.id_tipo_movimento = c.id_tipo_movimento   "+
							"where c.tp_operacao != 'E' and b.id_estoque = "+idEstoque+" and b.dt_data_movimento <= cast('2014-07-13 23:59:59' as timestamp)), 0) ) qtdAtual";
					ResultSet rs666 = lm.consultar(sql666);
					rs666.next();
					Integer quantidadeAtual =  rs666.getInt("qtdAtual"); //rs.getInt("in_quantidade_atual");
					
							
					Estoque estoque = new Estoque();
					estoque.setLote(lote);
					estoque.setIdEstoque(idEstoque);
					estoque.setDataValidade(validade);
					estoque.setQuantidadeAtual(quantidadeAtual);
					EstoqueApoio estoqueApoio = new EstoqueApoio();
					estoqueApoio.setEstoque(estoque);
					getEstoquesValidos().add(estoqueApoio);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void ocultarModalAnaliseInventario(){
		setEstoquesValidos(new ArrayList<EstoqueApoio>());
		setExibirModalAnaliseInventario(false);
	}
	
	public void ocultarModalMateriaisLiberados(){
		setMateriaisLiberados(new ArrayList<Material>());
		setExibirModalMateriaisLiberados(false);
		carregarMateriaisNaoLiberados();
	}
	
	public void exibirModalMateriaisLiberados(){
		carregarMateriaisLiberados();
		setExibirModalMateriaisLiberados(true);
	}
	
	public List<Material> getMateriaisNaoLiberados() {
		return materiaisNaoLiberados;
	}

	public void setMateriaisNaoLiberados(List<Material> materiaisNaoLiberados) {
		this.materiaisNaoLiberados = materiaisNaoLiberados;
	}

	public List<Material> getMateriaisLiberados() {
		return materiaisLiberados;
	}

	public void setMateriaisLiberados(List<Material> materiaisLiberados) {
		this.materiaisLiberados = materiaisLiberados;
	}

	public boolean isExibirModalMateriaisLiberados() {
		return exibirModalMateriaisLiberados;
	}

	public void setExibirModalMateriaisLiberados(
			boolean exibirModalMateriaisLiberados) {
		this.exibirModalMateriaisLiberados = exibirModalMateriaisLiberados;
	}

	public boolean isExibirModalAnaliseInventario() {
		return exibirModalAnaliseInventario;
	}

	public void setExibirModalAnaliseInventario(boolean exibirModalAnaliseInventario) {
		this.exibirModalAnaliseInventario = exibirModalAnaliseInventario;
	}

	public List<EstoqueApoio> getEstoquesValidos() {
		return estoquesValidos;
	}

	public void setEstoquesValidos(List<EstoqueApoio> estoquesValidos) {
		this.estoquesValidos = estoquesValidos;
	}

	public Material getMaterialEdicao() {
		return materialEdicao;
	}

	public void setMaterialEdicao(Material materialEdicao) {
		this.materialEdicao = materialEdicao;
	}

	public EstoqueApoio getEstoqueEdicao() {
		return estoqueEdicao;
	}

	public void setEstoqueEdicao(EstoqueApoio estoqueEdicao) {
		this.estoqueEdicao = estoqueEdicao;
	}

	public List<EstoqueApoio> getEstoquesInventariados() {
		return estoquesInventariados;
	}

	public void setEstoquesInventariados(List<EstoqueApoio> estoquesInventariados) {
		this.estoquesInventariados = estoquesInventariados;
	}

	public boolean isExibirModalAdicionarNovoLote() {
		return exibirModalAdicionarNovoLote;
	}

	public void setExibirModalAdicionarNovoLote(boolean exibirModalAdicionarNovoLote) {
		this.exibirModalAdicionarNovoLote = exibirModalAdicionarNovoLote;
	}

	public Estoque getEstoqueCadastro() {
		return estoqueCadastro;
	}

	public void setEstoqueCadastro(Estoque estoqueCadastro) {
		this.estoqueCadastro = estoqueCadastro;
	}

	public boolean isExibirModalAtualizacaoLote() {
		return exibirModalAtualizacaoLote;
	}

	public void setExibirModalAtualizacaoLote(boolean exibirModalAtualizacaoLote) {
		this.exibirModalAtualizacaoLote = exibirModalAtualizacaoLote;
	}
	
}
