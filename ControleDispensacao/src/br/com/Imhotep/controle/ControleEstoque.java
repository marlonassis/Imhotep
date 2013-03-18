package br.com.Imhotep.controle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.enums.TipoOperacaoEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.consulta.raiz.MovimentoLivroConsultaRaiz;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.imhotep.temp.PadraoGeralTemp;
import br.com.remendo.ConsultaGeral;

public class ControleEstoque extends PadraoGeralTemp {
	
	public void estoqueReservado(int sobra, int reservado) throws ExcecaoEstoqueReservado {
		if(reservado > sobra)
			throw new ExcecaoEstoqueReservado(reservado);
	}
	
	public void estoqueInsuficiente(int totalEstoque, int quantidadeMovimentacao) throws ExcecaoSaldoInsuficienteEstoque {
		if(quantidadeMovimentacao > totalEstoque)
			throw new ExcecaoSaldoInsuficienteEstoque(totalEstoque);
	}

	public void estoqueVazio(Integer quantidade) throws ExcecaoEstoqueVazio {
		if(quantidade == 0)
			throw new ExcecaoEstoqueVazio();
	}
	
	private void estoqueBloqueado(Estoque estoque) throws ExcecaoEstoqueBloqueado{
		if(estoque.getBloqueado())
			throw new ExcecaoEstoqueBloqueado();
	}
	
	private void estoqueVencido(Estoque estoque) throws ExcecaoEstoqueVencido {
		Calendar dataAtual = zerarHoraDataAtual();
		Calendar dataVencimento = setarUltimoDiaMesVencimento(estoque, dataAtual);
		if(dataAtual.after(dataVencimento))
			throw new ExcecaoEstoqueVencido();
	}

	private Calendar setarUltimoDiaMesVencimento(Estoque estoque, Calendar dataAtual) {
		Calendar dataVencimento = dataAtual;
		dataVencimento.setTime(estoque.getDataValidade());
		dataVencimento.set(Calendar.DAY_OF_MONTH, dataVencimento.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dataVencimento;
	}

	private Calendar zerarHoraDataAtual() {
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.set(Calendar.HOUR_OF_DAY, 0);
		dataAtual.set(Calendar.MINUTE, 0);
		dataAtual.set(Calendar.SECOND, 0);
		return dataAtual;
	}
	
	private Object[] consultaEstoqueMaterial(Material material) {
		String mesAnoAtual = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		StringBuilder sb = new StringBuilder("select CASE WHEN sum(o.quantidade) = null THEN 0 ELSE sum(o.quantidade)END, ");
		sb.append("(select CASE WHEN sum(a.quantidade) = null THEN 0 ELSE sum(a.quantidade) END ");
		sb.append("from PrescricaoItemDose a where a.prescricaoItem.dispensado = 'N' and a.prescricaoItem.status = 'S' and a.prescricaoItem.material.idMaterial = :idMaterial) ");
		sb.append("from Estoque o where o.material.idMaterial = :idMaterial and o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') > :dataAtual");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", material.getIdMaterial());
		map.put("dataAtual", mesAnoAtual);
		Object[] qtds = new ConsultaGeral<Object[]>().consultaUnica(sb, map);
		return qtds;
	}
	
	private void filtroEstoque(Estoque estoque, TipoOperacaoEnum tipoOperacao, int quantidadeSolicitada) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado{
		estoqueVencido(estoque);
		estoqueBloqueado(estoque);
		if(!tipoOperacao.equals(TipoOperacaoEnum.E)){
			estoqueVazio(estoque.getQuantidadeAtual());
			Object[] qtds = consultaEstoqueMaterial(estoque.getMaterial());
			int totalEstoque = (Integer) qtds[0];
			int totalReservardo = (Integer) qtds[1];
			estoqueInsuficiente(estoque.getQuantidadeAtual(), quantidadeSolicitada);
			int sobra = totalEstoque-quantidadeSolicitada;
			estoqueReservado(sobra, totalReservardo);
		}
	}
	
	public void liberarAjuste(Date dataAtual, MovimentoLivro movimentoLivro) throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueReservado, InstantiationException, IllegalAccessException, ClassNotFoundException {
		filtroEstoque(movimentoLivro.getEstoque(), movimentoLivro.getTipoMovimento().getTipoOperacao(), movimentoLivro.getQuantidadeMovimentacao());
		upperCaseLote(movimentoLivro.getEstoque().getLote());
		prepararMovimentoLivro(dataAtual, movimentoLivro);
		prepararEstoque(dataAtual, movimentoLivro.getEstoque(), movimentoLivro.getQuantidadeMovimentacao(), movimentoLivro.getTipoMovimento());
	}
	
	public void liberarDose(int quantidadeDose, Material material) throws ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque {
		Object[] qtds = consultaEstoqueMaterial(material);
		int totalEstoque = (Integer) qtds[0];
		int totalReservardo = (Integer) qtds[1];
		int totalVirtual = totalEstoque-totalReservardo;
		estoqueVazio(totalEstoque);
		estoqueInsuficiente(totalVirtual, quantidadeDose);
	}
	
	private void upperCaseLote(String lote) {
		lote = lote.toUpperCase();		
	}

	private void prepararMovimentoLivro(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		int saldoAtualMaterial = new MovimentoLivroConsultaRaiz().saldoAtualMaterial(movimentoLivro.getEstoque().getMaterial());
		movimentoLivro.setSaldoAnterior(saldoAtualMaterial);
		movimentoLivro.setDataMovimento(dataAtual);
		movimentoLivro.setUnidadeCadastrante(autenticador.getUnidadeAtual());
		movimentoLivro.setUsuarioMovimentacao(autenticador.getUsuarioAtual());
	}

	private void prepararEstoque(Date dataAtual, Estoque estoque, int quantidadeMovimentada, TipoMovimento tipoMovimento) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoSaldoInsuficienteEstoque, ExcecaoEstoqueVazio {
		if(estoque.getIdEstoque() == 0)
			setarDadosNovoEstoque(estoque, dataAtual);
		int saldoAtualizado = 0;
		int saldoAtual = estoque.getQuantidadeAtual();
		boolean movimentoEntrada = tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E);
		if(movimentoEntrada){
			saldoAtualizado = saldoAtual + quantidadeMovimentada;
		}else{
			saldoAtualizado = saldoAtual - quantidadeMovimentada;
		}
		estoque.setQuantidadeAtual(saldoAtualizado);
	}

	private void setarDadosNovoEstoque(Estoque estoque, Date dataAtual) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		estoque.setDataInclusao(dataAtual);
		estoque.setUnidade(autenticador.getUnidadeAtual());
		estoque.setUsuarioInclusao(autenticador.getUsuarioAtual());
	}
	
}
