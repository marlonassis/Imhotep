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
import br.com.imhotep.consulta.entidade.EstornoMovimentacaoAlmoxarifadoConsulta;
import br.com.imhotep.consulta.raiz.EstornoMovimentacaoAlmoxarifadoConsultaRaiz;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.EstornoMovimentoAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstornoFalha;
import br.com.imhotep.excecoes.ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarEstoque;
import br.com.imhotep.excecoes.ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos;
import br.com.imhotep.excecoes.ExcecaoEstornoNaoAutorizado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoSemJustificativa;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorEstoqueAlmoxarifadoLM;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;
import br.com.remendo.gerenciador.ControleInstancia;

@ManagedBean
@SessionScoped
public class EstornoMovimentacaoAlmoxarifadoRaiz extends PadraoRaiz<MovimentoLivroAlmoxarifado>{
	
	private MovimentoLivroAlmoxarifado itemMovimentoLivroAlmoxarifado = new MovimentoLivroAlmoxarifado();
	private MaterialAlmoxarifado materialAlmoxarifado = new MaterialAlmoxarifado();
	private List<EstoqueAlmoxarifado> estoquesAlmoxarifado = new ArrayList<EstoqueAlmoxarifado>();
	private String justificativa;
	private boolean exigirJustificativa;
	private MovimentoLivroAlmoxarifado item = new MovimentoLivroAlmoxarifado();
	
	public void carregarEstoques(){
		if(getMaterialAlmoxarifado() != null && getMaterialAlmoxarifado().getIdMaterialAlmoxarifado() != 0){
			List<EstoqueAlmoxarifado> busca = new EstoqueAlmoxarifadoRaiz().getBusca("select o from EstoqueAlmoxarifado o where o.materialAlmoxarifado.idMaterialAlmoxarifado = "+getMaterialAlmoxarifado().getIdMaterialAlmoxarifado());
			setEstoquesAlmoxarifado(busca);
		}else{
			setEstoquesAlmoxarifado(new ArrayList<EstoqueAlmoxarifado>());
		}
	}
	
	public void preEstorno(){
		boolean tipoEntrada = getItem().getTipoMovimentoAlmoxarifado().getTipoOperacao().equals(TipoOperacaoEnum.E);
		boolean tipoPerda = getItem().getTipoMovimentoAlmoxarifado().getIdTipoMovimentoAlmoxarifado() == Constantes.ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_PERDA_ALMOXARIFADO;
		boolean tipoSaida = getItem().getTipoMovimentoAlmoxarifado().getIdTipoMovimentoAlmoxarifado() == Constantes.ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_SAIDA_ALMOXARIFADO;
		try {
			if(tipoEntrada || tipoPerda || tipoSaida){
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
		boolean primeiroMovimento = new EstornoMovimentacaoAlmoxarifadoConsultaRaiz().isPrimeiroMovimento(getItem());
		boolean temSegundoMovimentoSaida = new EstornoMovimentacaoAlmoxarifadoConsultaRaiz().isSegundoMovimentoSaida(getItem());
		if(primeiroMovimento && temSegundoMovimentoSaida){
			throw new ExcecaoEstornoNaoAutorizado();
		}else{
			boolean diferecaNegativa = new EstornoMovimentacaoAlmoxarifadoConsultaRaiz().isDiferecaNegativa(getItem());
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
		item = new MovimentoLivroAlmoxarifado();
	}
	
	public void apagarMovimento(){
		PadraoFluxoTemp.limparFluxo();
		int idEstoqueAlmoxarifado = getItem().getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado();
		String lote = getItem().getEstoqueAlmoxarifado().getLote();
		try {
			if(getJustificativa() == null || getJustificativa().isEmpty()){
				throw new ExcecaoSemJustificativa();
			}
			new AtualizadorEstoqueAlmoxarifadoLM().lockEstoque(idEstoqueAlmoxarifado, lote, 0);
			verificarNFE(getItem());
			EstornoMovimentoAlmoxarifado ema = new EstornoMovimentoAlmoxarifado();
			gerarLogEstornoMovimento(ema);
//			estornarItemNotaFiscalSePrimeiroMovimento(getItem());
			PadraoFluxoTemp.getObjetoSalvar().put("LogEstornoMovimentoLivroALmoxarifado-"+ema.hashCode(), ema);
			PadraoFluxoTemp.getObjetoDeletar().put("DeleteMLA-"+getItem().hashCode(), getItem());
			PadraoFluxoTemp.finalizarFluxo();
			atualizarMovimentoAlmoxarifado(idEstoqueAlmoxarifado);
			atualizarEstoqueAlmoxarifado(idEstoqueAlmoxarifado);
//			deletarEstoqueSemMovimento(idEstoqueAlmoxarifado);
			carregarEstoques();
			((EstornoMovimentacaoAlmoxarifadoConsulta) new ControleInstancia().procuraInstancia(EstornoMovimentacaoAlmoxarifadoConsulta.class)).carregarResultado();
			limparEstorno();
		} catch (ExcecaoEstoqueLockAcimaUmMinuto e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueLock e) {
			e.printStackTrace();
			unlockEstoqueAlmoxarifado(idEstoqueAlmoxarifado);
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
			unlockEstoqueAlmoxarifado(idEstoqueAlmoxarifado);
		} catch (ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarEstoque e) {
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
		}finally{
			PadraoFluxoTemp.limparFluxo();
			try {
				new AtualizadorEstoqueAlmoxarifadoLM().unLockEstoque(idEstoqueAlmoxarifado);
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
		}
	}

//	private void estornarItemNotaFiscalSePrimeiroMovimento(MovimentoLivroAlmoxarifado mla) throws ExcecaoEstornoFalha {
//		boolean primeiroMovimento = new EstornoMovimentacaoAlmoxarifadoConsultaRaiz().isPrimeiroMovimento(getItem());
//		if(primeiroMovimento){
//			NotaFiscalEstoqueAlmoxarifado nfea = new EstoqueAlmoxarifadoConsultaRaiz().itemEstoqueNotaFiscalAlmoxarifado(mla);
//			if(nfea != null && nfea.getIdNotaFiscalEstoqueAlmoxarifado() != 0)
//				PadraoFluxoTemp.getObjetoDeletar().put("DeleteNFEA-"+nfea.hashCode(), nfea);
//		}
//	}

	private void gerarLogEstornoMovimento(EstornoMovimentoAlmoxarifado ema) throws ExcecaoProfissionalLogado {
		ema.setDataEstorno(new Date());
		ema.setJustificativa(getJustificativa());
		ema.setProfissionalEstorno(Autenticador.getProfissionalLogado());
		String resumo = getItem().getIdMovimentoLivroAlmoxarifado()+";"+getItem().getJustificativa()+";"+getItem().getQuantidadeAtual()+";"+getItem().getQuantidadeMovimentacao()+";"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getItem().getDataMovimento())+";"+getItem().getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado()+";"+getItem().getProfissionalInsercao().getIdProfissional()+";"+getItem().getTipoMovimentoAlmoxarifado().getIdTipoMovimentoAlmoxarifado();
		ema.setMovimentoCompleto(resumo);
		ema.setEstoqueAlmoxarifado(getItem().getEstoqueAlmoxarifado());
		ema.setTipoMovimentoAlmoxarifado(getItem().getTipoMovimentoAlmoxarifado());
		ema.setQuantidadeEstornada(getItem().getQuantidadeMovimentacao());
	}

	private void unlockEstoqueAlmoxarifado(int idEstoqueAlmoxarifado) {
		try {
			new AtualizadorEstoqueAlmoxarifadoLM().unLockEstoque(idEstoqueAlmoxarifado);
		} catch (ExcecaoEstoqueUnLock e1) {
			e1.printStackTrace();
		}
	}

	private void verificarNFE(MovimentoLivroAlmoxarifado item) {
		TipoMovimentoAlmoxarifado tpNotaFiscal = Parametro.tipoMovimentoNotaFiscalEntradaAlmoxarifado();
		if(item.getTipoMovimentoAlmoxarifado().equals(tpNotaFiscal)){
			String sql = "select o from NotaFiscalEstoqueAlmoxarifado o where "
					+ "o.movimentoLivroAlmoxarifado.idMovimentoLivroAlmoxarifado = "+item.getIdMovimentoLivroAlmoxarifado();
			List<NotaFiscalEstoqueAlmoxarifado> busca = new NotaFiscalEstoqueAlmoxarifadoRaiz().getBusca(sql);
			if(busca != null && !busca.isEmpty()){
				NotaFiscalEstoqueAlmoxarifado nfea = busca.get(0);
				PadraoFluxoTemp.getObjetoDeletar().put("DeleteNFE-"+nfea.hashCode(), nfea);
			}
		}
	}
	
	private void atualizarMovimentoAlmoxarifado(int idEstoqueAlmoxarifado) throws SQLException, ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos{
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		ResultSet rs2 = lm.consultar("select a.id_movimento_livro_almoxarifado, b.tp_operacao, a.in_quantidade_movimentacao from tb_movimento_livro_almoxarifado a "
					+ "inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "
					+ "where a.id_estoque_almoxarifado = " + idEstoqueAlmoxarifado + " order by a.dt_data_movimento");
		int valorAnterior = 0;
		while (rs2.next()) {
			String op = rs2.getString("tp_operacao");
			Integer qtdMov = rs2.getInt("in_quantidade_movimentacao");
			Integer id = rs2.getInt("id_movimento_livro_almoxarifado");
			
			String sqlUpd = "update tb_movimento_livro_almoxarifado set in_quantidade_atual="+valorAnterior+" where id_movimento_livro_almoxarifado = "+id;
			System.out.println("EstoqueAlmoxarifado: "+idEstoqueAlmoxarifado+" - "+sqlUpd);
			if(valorAnterior < 0){
				throw new ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos();
			}
			if(!lm.executarCUD(sqlUpd)){
				throw new ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos();
			}
			
			if(op.equalsIgnoreCase("E")){
				valorAnterior += qtdMov;
			}else{
				valorAnterior -= qtdMov;
			}
		}
	}
	
	private void atualizarEstoqueAlmoxarifado(int idEstoque) throws SQLException, ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarEstoque{
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		ResultSet rs2 = lm.consultar("select coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a "+
										"inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
										"where b.tp_operacao = 'E' and a.id_estoque_almoxarifado = "+idEstoque+"), 0) "+
										"- "+
										"coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a "+ 
										"inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
										"where b.tp_operacao != 'E' and a.id_estoque_almoxarifado = "+idEstoque+"), 0) as saldo");
		while (rs2.next()) {
			int saldo = rs2.getInt("saldo");
			String sqlUpd = "update tb_estoque_almoxarifado set in_quantidade_atual="+saldo+" where id_estoque_almoxarifado = "+idEstoque;
			System.out.println("Estoque: "+idEstoque+" - "+sqlUpd);
			if(!lm.executarCUD(sqlUpd)){
				throw new ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarEstoque();
			}
		}
			
	}
	
	public MovimentoLivroAlmoxarifado getItemMovimentoLivroAlmoxarifado() {
		return itemMovimentoLivroAlmoxarifado;
	}

	public void setItemMovimentoLivroAlmoxarifado(
			MovimentoLivroAlmoxarifado itemMovimentoLivroAlmoxarifado) {
		this.itemMovimentoLivroAlmoxarifado = itemMovimentoLivroAlmoxarifado;
	}

	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}

	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
	}

	public List<EstoqueAlmoxarifado> getEstoquesAlmoxarifado() {
		return estoquesAlmoxarifado;
	}

	public void setEstoquesAlmoxarifado(List<EstoqueAlmoxarifado> estoquesAlmoxarifado) {
		this.estoquesAlmoxarifado = estoquesAlmoxarifado;
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



	public MovimentoLivroAlmoxarifado getItem() {
		return item;
	}



	public void setItem(MovimentoLivroAlmoxarifado item) {
		this.item = item;
	}

}
