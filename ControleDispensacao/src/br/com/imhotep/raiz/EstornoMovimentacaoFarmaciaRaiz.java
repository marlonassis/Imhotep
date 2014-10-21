package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.entidade.EstornoMovimentacaoFarmaciaConsulta;
import br.com.imhotep.consulta.raiz.EstornoMovimentacaoFarmaciaConsultaRaiz;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.EstornoMovimentoFarmacia;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstornoFalha;
import br.com.imhotep.excecoes.ExcecaoEstornoFarmaciaMovimentoLivroAtualizarEstoque;
import br.com.imhotep.excecoes.ExcecaoEstornoFarmaciaMovimentoLivroAtualizarMovimentos;
import br.com.imhotep.excecoes.ExcecaoEstornoNaoAutorizado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoSemJustificativa;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorEstoqueLM;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;
import br.com.remendo.gerenciador.ControleInstancia;

@ManagedBean
@SessionScoped
public class EstornoMovimentacaoFarmaciaRaiz extends PadraoRaiz<MovimentoLivro>{
	
	private MovimentoLivro itemMovimentoLivro = new MovimentoLivro();
	private Material Material = new Material();
	private List<Estoque> estoquesFarmacia = new ArrayList<Estoque>();
	private String justificativa;
	private boolean exigirJustificativa;
	private MovimentoLivro item = new MovimentoLivro();
	
	public void carregarEstoques(){
		if(getMaterial() != null && getMaterial().getIdMaterial() != 0){
			List<Estoque> busca = new EstoqueRaiz().getBusca("select o from Estoque o where o.material.idMaterial = "+getMaterial().getIdMaterial());
			setEstoquesFarmacia(busca);
		}else{
			setEstoquesFarmacia(new ArrayList<Estoque>());
		}
	}
	
	public void preEstorno(){
		boolean tipoEntrada = getItem().getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.E);
		boolean tipoPerda = getItem().getTipoMovimento().getIdTipoMovimento() == Constantes.ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_PERDA_FARMACIA;
		boolean tipoSaida = getItem().getTipoMovimento().getIdTipoMovimento() == Constantes.ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_SAIDA_FARMACIA;
		boolean tipoDevolucao = getItem().getTipoMovimento().getIdTipoMovimento() == Constantes.ID_TIPO_MOVIMENTO_DEVOLUCAO_MEDICAMENTO;
		try {
			if((tipoEntrada || tipoPerda || tipoSaida) && !tipoDevolucao){
				if(tipoEntrada)
					verificarMovimentoPrimeiraEntrada();
				setExigirJustificativa(true);
			}else{
				throw new ExcecaoEstornoNaoAutorizado();
			}
		} catch (ExcecaoEstornoNaoAutorizado e) {
			e.printStackTrace();
		} catch (ExcecaoEstornoFalha e) {
			e.printStackTrace();
		}
	}
	
	private void verificarMovimentoPrimeiraEntrada() throws ExcecaoEstornoNaoAutorizado, ExcecaoEstornoFalha {
		boolean primeiroMovimento = new EstornoMovimentacaoFarmaciaConsultaRaiz().isPrimeiroMovimento(getItem());
		boolean temSegundoMovimentoSaida = new EstornoMovimentacaoFarmaciaConsultaRaiz().isSegundoMovimentoSaida(getItem());
		if(primeiroMovimento && temSegundoMovimentoSaida){
			throw new ExcecaoEstornoNaoAutorizado();
		}else{
			boolean diferecaNegativa = new EstornoMovimentacaoFarmaciaConsultaRaiz().isDiferecaNegativa(getItem());
			if(primeiroMovimento && !temSegundoMovimentoSaida && diferecaNegativa){
				throw new ExcecaoEstornoNaoAutorizado();
			}else{
				if(!primeiroMovimento && diferecaNegativa){
					throw new ExcecaoEstornoNaoAutorizado();
				}
			}
		}
	}

	public void cancelarJustificativa(){
		limparEstorno();
	}

	private void limparEstorno() {
		setExigirJustificativa(false);
		setJustificativa("");
		item = new MovimentoLivro();
	}
	
	public void apagarMovimento(){
		PadraoFluxoTemp.limparFluxo();
		int idEstoque = getItem().getEstoque().getIdEstoque();
		String lote = getItem().getEstoque().getLote();
		try {
			if(getJustificativa() == null || getJustificativa().isEmpty()){
				throw new ExcecaoSemJustificativa();
			}
			new AtualizadorEstoqueLM().lockEstoque(idEstoque, lote, 0);
			verificarNFE(getItem());
			EstornoMovimentoFarmacia ema = new EstornoMovimentoFarmacia();
			gerarLogEstornoMovimento(ema);
//			estornarItemNotaFiscalSePrimeiroMovimento(getItem().getEstoque());
			PadraoFluxoTemp.getObjetoSalvar().put("LogEstornoMovimentoLivro-"+ema.hashCode(), ema);
			PadraoFluxoTemp.getObjetoDeletar().put("DeleteML-"+getItem().hashCode(), getItem());
			PadraoFluxoTemp.finalizarFluxo();
			atualizarMovimentoFarmacia(idEstoque);
			atualizarEstoque(idEstoque);
			deletarEstoqueSemMovimento(idEstoque);
			carregarEstoques();
			((EstornoMovimentacaoFarmaciaConsulta) new ControleInstancia().procuraInstancia(EstornoMovimentacaoFarmaciaConsulta.class)).carregarResultado();
			limparEstorno();
		} catch (ExcecaoEstoqueLockAcimaUmMinuto e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueLock e) {
			e.printStackTrace();
			unlockEstoque(idEstoque);
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
			unlockEstoque(idEstoque);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ExcecaoSemJustificativa e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoEstornoFarmaciaMovimentoLivroAtualizarEstoque e) {
			e.printStackTrace();
		} catch (ExcecaoEstornoFarmaciaMovimentoLivroAtualizarMovimentos e) {
			e.printStackTrace();
		}finally{
			PadraoFluxoTemp.limparFluxo();
			try {
				new AtualizadorEstoqueLM().unLockEstoque(idEstoque);
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
		}
	}

//	private void estornarItemNotaFiscalSePrimeiroMovimento(Estoque estoque) throws ExcecaoEstornoFalha {
//		boolean primeiroMovimento = new EstornoMovimentacaoFarmaciaConsultaRaiz().isPrimeiroMovimento(getItem());
//		if(primeiroMovimento){
//			NotaFiscalEstoque nfea = new  NotaFiscalEstoqueConsultaRaiz().itemEstoqueNotaFiscal(estoque);
//			if(nfea != null && nfea.getIdNotaFiscalEstoque() != 0)
//				PadraoFluxoTemp.getObjetoDeletar().put("DeleteNFE-"+nfea.hashCode(), nfea);
//		}
//	}

	private void gerarLogEstornoMovimento(EstornoMovimentoFarmacia ema) throws ExcecaoProfissionalLogado {
		ema.setDataEstorno(new Date());
		ema.setJustificativa(getJustificativa());
		ema.setProfissionalEstorno(Autenticador.getProfissionalLogado());
		String resumo = getItem().getIdMovimentoLivro()+";"+
						getItem().getJustificativa()+";"+
						getItem().getQuantidadeAtual()+";"+
						getItem().getQuantidadeMovimentacao()+";"+
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getItem().getDataMovimento())+";"+
						getItem().getEstoque().getIdEstoque()+";"+
						getItem().getUsuarioMovimentacao().getProfissional().getIdProfissional()+";"+
						getItem().getTipoMovimento().getIdTipoMovimento();
		ema.setMovimentoCompleto(resumo);
		ema.setEstoque(getItem().getEstoque());
		ema.setTipoMovimento(getItem().getTipoMovimento());
		ema.setQuantidadeEstornada(getItem().getQuantidadeMovimentacao());
	}

	private void deletarEstoqueSemMovimento(int idEstoque) throws SQLException {
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_IMHOTEP_LINHA_MECANICA);
		ResultSet rs2 = lm.consultar("select coalesce(count(a.id_movimento_livro), 0) total from tb_movimento_livro a "
					+ "where a.id_estoque = " + idEstoque);
		while (rs2.next()) {
			Integer total = rs2.getInt("total");
			
			if(total != null && total.equals(0)){
				String sqlUpd = "delete tb_estoque where id_estoque = "+idEstoque;
				System.out.println("Estoque: "+idEstoque+" - "+sqlUpd);
				lm.executarCUD(sqlUpd);
			}
			
		}
	}

	private void unlockEstoque(int idEstoque) {
		try {
			new AtualizadorEstoqueLM().unLockEstoque(idEstoque);
		} catch (ExcecaoEstoqueUnLock e1) {
			e1.printStackTrace();
		}
	}

	private void verificarNFE(MovimentoLivro item) {
		TipoMovimento tpNotaFiscal = Parametro.tipoMovimentoNotaFiscalEntrada();
		if(item.getTipoMovimento().equals(tpNotaFiscal)){
			String sql = "select o from NotaFiscalEstoque o where "
					+ "o.movimentoLivro.idMovimentoLivro = "+item.getIdMovimentoLivro();
			List<NotaFiscalEstoque> busca = new NotaFiscalEstoqueRaiz().getBusca(sql);
			if(busca != null && !busca.isEmpty()){
				NotaFiscalEstoque nfea = busca.get(0);
				PadraoFluxoTemp.getObjetoDeletar().put("DeleteNFE-"+nfea.hashCode(), nfea);
			}
		}
	}
	
	private void atualizarMovimentoFarmacia(int idEstoque) throws SQLException, ExcecaoEstornoFarmaciaMovimentoLivroAtualizarMovimentos{
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_IMHOTEP_LINHA_MECANICA);
		ResultSet rs2 = lm.consultar("select a.id_movimento_livro, b.tp_operacao, a.in_quantidade_movimentacao from tb_movimento_livro a "
					+ "inner join tb_tipo_movimento b on a.id_tipo_movimento = b.id_tipo_movimento "
					+ "where a.id_estoque = " + idEstoque + " order by a.dt_data_movimento");
		int valorAnterior = 0;
		while (rs2.next()) {
			String op = rs2.getString("tp_operacao");
			Integer qtdMov = rs2.getInt("in_quantidade_movimentacao");
			Integer id = rs2.getInt("id_movimento_livro");
			
			String sqlUpd = "update tb_movimento_livro set in_quantidade_atual="+valorAnterior+" where id_movimento_livro = "+id;
			System.out.println("Estoque: "+idEstoque+" - "+sqlUpd);
			if(valorAnterior < 0){
				throw new ExcecaoEstornoFarmaciaMovimentoLivroAtualizarMovimentos();
			}
			if(!lm.executarCUD(sqlUpd)){
				throw new ExcecaoEstornoFarmaciaMovimentoLivroAtualizarMovimentos();
			}
			
			if(op.equalsIgnoreCase("E")){
				valorAnterior += qtdMov;
			}else{
				valorAnterior -= qtdMov;
			}
		}
	}
	
	private void atualizarEstoque(int idEstoque) throws SQLException, ExcecaoEstornoFarmaciaMovimentoLivroAtualizarEstoque{
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_IMHOTEP_LINHA_MECANICA);
		ResultSet rs2 = lm.consultar("select coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro a "+
										"inner join tb_tipo_movimento b on a.id_tipo_movimento = b.id_tipo_movimento "+
										"where b.tp_operacao = 'E' and a.id_estoque = "+idEstoque+"), 0) "+
										"- "+
										"coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro a "+ 
										"inner join tb_tipo_movimento b on a.id_tipo_movimento = b.id_tipo_movimento "+
										"where b.tp_operacao != 'E' and a.id_estoque = "+idEstoque+"), 0) as saldo");
		while (rs2.next()) {
			int saldo = rs2.getInt("saldo");
			String sqlUpd = "update tb_estoque set in_quantidade_atual="+saldo+" where id_estoque = "+idEstoque;
			System.out.println("Estoque: "+idEstoque+" - "+sqlUpd);
			if(!lm.executarCUD(sqlUpd)){
				throw new ExcecaoEstornoFarmaciaMovimentoLivroAtualizarEstoque();
			}
		}
			
	}
	
	public MovimentoLivro getItemMovimentoLivro() {
		return itemMovimentoLivro;
	}

	public void setItemMovimentoLivro(
			MovimentoLivro itemMovimentoLivro) {
		this.itemMovimentoLivro = itemMovimentoLivro;
	}

	public Material getMaterial() {
		return Material;
	}

	public void setMaterial(Material Material) {
		this.Material = Material;
	}

	public List<Estoque> getEstoquesFarmacia() {
		return estoquesFarmacia;
	}

	public void setEstoquesFarmacia(List<Estoque> estoquesFarmacia) {
		this.estoquesFarmacia = estoquesFarmacia;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public boolean getExigirJustificativa() {
		return exigirJustificativa;
	}

	public void setExigirJustificativa(boolean exigirJustificativa) {
		this.exigirJustificativa = exigirJustificativa;
	}

	public MovimentoLivro getItem() {
		return item;
	}
	public void setItem(MovimentoLivro item) {
		this.item = item;
	}

}
