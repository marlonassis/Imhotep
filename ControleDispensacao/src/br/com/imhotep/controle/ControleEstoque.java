package br.com.imhotep.controle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import br.com.imhotep.auxiliar.Utilities;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.raiz.EstoqueRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoGeralTemp;
import br.com.remendo.ConsultaGeral;

public class ControleEstoque extends PadraoGeralTemp {
	
	private void estoqueReservado(long sobra, long reservado, int quantidadeSolicitada) throws ExcecaoEstoqueReservado {
		if(quantidadeSolicitada > sobra)
			throw new ExcecaoEstoqueReservado(reservado, sobra);
	}
	
	private void estoqueInsuficiente(long totalEstoque, long quantidadeMovimentacao) throws ExcecaoSaldoInsuficienteEstoque {
		if(quantidadeMovimentacao > totalEstoque)
			throw new ExcecaoSaldoInsuficienteEstoque(totalEstoque);
	}

	private void estoqueVazio(long quantidade) throws ExcecaoEstoqueVazio {
		if(quantidade == 0)
			throw new ExcecaoEstoqueVazio();
	}
	
	private void estoqueBloqueado(Estoque estoque) throws ExcecaoEstoqueBloqueado{
		if(estoque.getBloqueado())
			throw new ExcecaoEstoqueBloqueado();
	}
	
	private void estoqueVencido(Date dataValidade) throws ExcecaoEstoqueVencido {
		Calendar dataAtual = zerarHoraDataAtual();
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.setTime(new Utilities().ajustarUltimoDiaMesHoraMaximo(dataValidade));
		if(dataAtual.after(dataVencimento))
			throw new ExcecaoEstoqueVencido();
	}

	private Calendar zerarHoraDataAtual() {
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.set(Calendar.HOUR_OF_DAY, 0);
		dataAtual.set(Calendar.MINUTE, 0);
		dataAtual.set(Calendar.SECOND, 0);
		return dataAtual;
	}
	
	private StringBuilder getSBConsultaEstoqueDisponivelMaterial(){
		StringBuilder sb = new StringBuilder();
		sb.append("select coalesce(sum(o.quantidadeAtual), 0) as quantidadeTotal, "); 
		sb.append("(select coalesce(sum(a.quantidade), 0) from PrescricaoItemDose a where a.prescricaoItem.dispensado = 'N' "); 
		sb.append("and a.prescricaoItem.status = 'S' and a.prescricaoItem.material.idMaterial = :idMaterial) as quantidadeReservadaPrescricao, ");
		sb.append("(select coalesce(sum(b.quantidadeSolicitada), 0) from SolicitacaoMedicamentoUnidade b where b.statusDispensacao = 'P' and b.material.idMaterial = :idMaterial) as quantidadeReservadaSolicitacao ");
		sb.append("from Estoque o where o.material.idMaterial = :idMaterial and o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= :dataAtual");
		return sb;
	}
	
	/**
	 * Retorna a quantidade total do medicamento no estoque e a quantidade total reservada por prescricao e solicitacao
	 * @param material
	 * @return Object[] com três elementos: o primeiro com o total em estoque, o segundo com o total reservado por prescricoes e o terceiro com o total reservado por solicitacoes das unidades.
	 */
	private Object[] consultaEstoqueMaterial(Material material) {
		String mesAnoAtual = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", material.getIdMaterial());
		map.put("dataAtual", mesAnoAtual);
		Object[] qtds = new ConsultaGeral<Object[]>().consultaUnica(getSBConsultaEstoqueDisponivelMaterial(), map);
		return qtds;
	}
	
	private StringBuilder getSBConsultaEstoqueEspecifico(){
		StringBuilder sb = new StringBuilder();
		sb.append("select o.quantidadeAtual as quantidadeAtual, ");
		sb.append("(select coalesce(sum(a.quantidade), 0) from PrescricaoItemDose a where a.prescricaoItem.dispensado = 'N' "); 
		sb.append("and a.prescricaoItem.status = 'S' and a.prescricaoItem.material.idMaterial = :idMaterial) as quantidadeReservadaPrescricao, "); 
		sb.append("(select coalesce(sum(b.quantidadeSolicitada), 0) from SolicitacaoMedicamentoUnidade b where b.statusDispensacao = 'P' and b.material.idMaterial = :idMaterial) as quantidadeReservadaSolicitacao, ");
		sb.append("(select coalesce(sum(c.quantidadeAtual), 0)  from Estoque c where c.idEstoque != :idEstoque and c.material.idMaterial = :idMaterial and c.bloqueado = false and to_char(c.dataValidade, 'yyyy-MM') >= :dataAtual) as quantidadeOutrosEstoques ");
		sb.append("from Estoque o where o.idEstoque = :idEstoque and o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= :dataAtual ");
		return sb;
	}
	
	/**
	 * Procura no estoque a quantidade atual do estoque, a quantidade reservada pelas prescricoes e solicitações, e a quantidade total de outros estoques do mesmo material
	 * @param estoque
	 * @return Object[] com quatro elementos: o primeiro com o total em estoque, o segundo com o total reservado por prescricoes, o terceiro com o total reservado por solicitacoes das unidades e a quantidade total dos outros estoques do mesmo material.
	 */
	private Object[] consultaStatusEstoqueEspecifico(Estoque estoque) {
		String mesAnoAtual = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idEstoque", estoque.getIdEstoque());
		map.put("idMaterial", estoque.getMaterial().getIdMaterial());
		map.put("dataAtual", mesAnoAtual);
		Object[] qtds = new ConsultaGeral<Object[]>().consultaUnica(getSBConsultaEstoqueEspecifico(), map);
		return qtds;
	}
	
	/**
	 * O filtro é chamado apenas para os casos em que não está sendo realizada uma reserva
	 * @param estoque
	 * @param tipoOperacao
	 * @param quantidadeSolicitada
	 * @throws ExcecaoEstoqueVencido
	 * @throws ExcecaoEstoqueBloqueado
	 * @throws ExcecaoEstoqueVazio
	 * @throws ExcecaoSaldoInsuficienteEstoque
	 * @throws ExcecaoEstoqueReservado
	 */
	private void filtroEstoque(Estoque estoque, TipoOperacaoEnum tipoOperacao, int quantidadeSolicitada) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado{
		estoqueVencido(estoque.getDataValidade());
		estoqueBloqueado(estoque);
		if(!tipoOperacao.equals(TipoOperacaoEnum.E)){
			Object[] qtds = consultaStatusEstoqueEspecifico(estoque);
			Integer totalEstoque = (Integer) qtds[0];
			estoqueVazio(totalEstoque);
			long totalReservardo = (Long) qtds[1] + (Long) qtds[2];
			long totalRemanescente = (Long) qtds[3] - totalReservardo;
			if(totalRemanescente < 0){
				//a sobra será a quantidade atual menos a o remanescente quando menor que zero. O remanescente será menor que zero 
				// quando a quantidade solicitada for maior que a quantidade nos outros estoques.
				totalRemanescente = totalRemanescente * -1;
				long sobra = totalEstoque - totalRemanescente;
				estoqueReservado(sobra, totalRemanescente, quantidadeSolicitada);
			}
			estoqueInsuficiente(totalEstoque, quantidadeSolicitada);
		}
	}
	
	private void atualizarEstoque(MovimentoLivro movimentoLivro) {
		int id = movimentoLivro.getEstoque().getIdEstoque();
		movimentoLivro.setEstoque(new EstoqueConsultaRaiz().consultaEstoque(id));
	}
	
	/**
	 * Verifica se é posível liberar algum dos ajustes previstos para o estoque: ajuste de saída, dispensação simples, dispensação da prescrição.
	 * @param dataAtual
	 * @param movimentoLivro
	 * @throws ExcecaoEstoqueVencido
	 * @throws ExcecaoEstoqueBloqueado
	 * @throws ExcecaoEstoqueVazio
	 * @throws ExcecaoSaldoInsuficienteEstoque
	 * @throws ExcecaoEstoqueReservado
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws ExcecaoEstoqueNaoCadastrado
	 * @throws ExcecaoEstoqueNaoAtualizado
	 */
	public void liberarAjuste(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado {
		movimentoLivro.getEstoque().setLote(movimentoLivro.getEstoque().getLote().toUpperCase());
		filtroEstoque(movimentoLivro.getEstoque(), movimentoLivro.getTipoMovimento().getTipoOperacao(), movimentoLivro.getQuantidadeMovimentacao());
		prepararMovimentoLivro(dataAtual, movimentoLivro);
		manipularEstoque(dataAtual, movimentoLivro.getEstoque(), movimentoLivro.getQuantidadeMovimentacao(), movimentoLivro.getTipoMovimento());
		atualizarEstoque(movimentoLivro);
	}

	public void liberarReserva(int quantidadeDose, Material material) throws ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque {
		Object[] qtds = consultaEstoqueMaterial(material);
		long totalTodosEstoque = (Long) qtds[0];
		long totalReservardo = (Long) qtds[1] + (Long) qtds[2];
		long totalVirtual = totalTodosEstoque-totalReservardo;
		estoqueVazio(totalTodosEstoque);
		estoqueInsuficiente(totalVirtual, quantidadeDose);
	}
	
	private void prepararMovimentoLivro(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		movimentoLivro.setDataMovimento(dataAtual);
		movimentoLivro.setUnidadeCadastrante(autenticador.getUnidadeAtual());
		movimentoLivro.setUsuarioMovimentacao(autenticador.getUsuarioAtual());
	}

	private void manipularEstoque(Date dataAtual, Estoque estoque, int quantidadeMovimentada, TipoMovimento tipoMovimento) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueVazio, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado {
		if(estoque.getIdEstoque() == 0){
			inserirNovoEstoque(dataAtual, estoque);
		}else{
			String sql = montarSqlAtualizacaoEstoque(estoque.getIdEstoque(), quantidadeMovimentada, tipoMovimento);
			atualizarEstoque(sql, estoque.getIdEstoque());
		}
	}

	private void atualizarEstoque(String sql, int idEstoque) throws ExcecaoEstoqueNaoAtualizado{
		LinhaMecanica lm = new LinhaMecanica(LinhaMecanica.DB_BANCO_IMHOTEP);
		boolean naoExecutou = true;
		int cont = 0;
		while(naoExecutou && cont < 4){
			if(lm.executarCUD(sql)){
				naoExecutou = false;
			}
			cont++;
		}
		if(naoExecutou){
			throw new ExcecaoEstoqueNaoAtualizado();
		}
	}

	private void inserirNovoEstoque(Date dataAtual, Estoque estoque) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoEstoqueNaoCadastrado{
		setarDadosNovoEstoque(estoque, dataAtual);
		EstoqueRaiz er = new EstoqueRaiz(estoque);
		if(!er.enviar()){
			throw new ExcecaoEstoqueNaoCadastrado();
		}
	}

	private String montarSqlAtualizacaoEstoque(int idEstoque, int quantidadeMovimentada, TipoMovimento tipoMovimento) {
		String sql = "update tb_estoque set in_quantidade_atual = (in_quantidade_atual :tipoOperacao "+quantidadeMovimentada+" ) " +
				"where id_estoque = "+idEstoque + " and bl_lock = false";
		boolean movimentoEntrada = tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E);
		if(movimentoEntrada){
			sql = sql.replace(":tipoOperacao", "+");
		}else{
			sql = sql.replace(":tipoOperacao", "-");
		}
		return sql;
	}

	private void setarDadosNovoEstoque(Estoque estoque, Date dataAtual) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		estoque.setDataInclusao(dataAtual);
		estoque.setUnidade(autenticador.getUnidadeAtual());
		estoque.setUsuarioInclusao(autenticador.getUsuarioAtual());
	}
	
}
