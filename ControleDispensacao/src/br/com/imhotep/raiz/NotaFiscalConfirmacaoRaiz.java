package br.com.imhotep.raiz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoNotaFiscalValorTotalNaoConfere;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class NotaFiscalConfirmacaoRaiz extends PadraoRaiz<NotaFiscalEstoque>{
	private NotaFiscal notaFiscal;
	private List<NotaFiscalEstoque> itensNotaFiscal = new ArrayList<NotaFiscalEstoque>();
	private boolean exibirDialogConfirmacaoLiberacao;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	public void liberarNotaFiscal(){
		List<Estoque> estoquesLock = new ArrayList<Estoque>();
		try {
			validarValorNotaIgualValorItens();
			PadraoFluxoTemp.limparFluxo();
			Date dataMovimento = new Date();
			TipoMovimento tipoMovimento = Parametro.tipoMovimentoNotaFiscalEntrada();
			for(NotaFiscalEstoque item : getItensNotaFiscal()){
				Estoque estoque = item.getEstoque();
				estoque.setBloqueado(false);
				estoque.setMotivoBloqueio(null);
				String justificativa = "NF: "+item.getNotaFiscal().getIdentificacaoNotaFiscal();
				estoquesLock.add(item.getEstoque());
				MovimentoLivro movimentoLivro = new ControleEstoque().getMovimentoLivroPronto(dataMovimento, estoque , tipoMovimento , 
																								justificativa, item.getQuantidadeEntrada());
				PadraoFluxoTemp.getObjetoAtualizar().put("Estoque-" + movimentoLivro.getEstoque().hashCode(), movimentoLivro.getEstoque());
				PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro-" + movimentoLivro.hashCode(), movimentoLivro);
				item.setMovimentoLivro(movimentoLivro);
				item.setEstoque(movimentoLivro.getEstoque());
				PadraoFluxoTemp.getObjetoAtualizar().put("NotaFiscalEstoque-" + item.hashCode(), item);
			}
			getNotaFiscal().setLiberada(true);
			getNotaFiscal().setProfissionalAutorizacao(Autenticador.getProfissionalLogado());
			PadraoFluxoTemp.getObjetoAtualizar().put("NotaFiscal-"+getNotaFiscal().hashCode(), getNotaFiscal());
			PadraoFluxoTemp.finalizarFluxo();
			setExibirDialogConfirmacaoLiberacao(false);
			((NotaFiscalRaiz) new ControleInstancia().procuraInstancia(NotaFiscalRaiz.class)).setInstancia(getNotaFiscal());
		}catch (ExcecaoEstoqueLockAcimaUmMinuto e){
			e.printStackTrace();
			removerLockLotes(estoquesLock, estoquesLock.size() - 1);
		}catch (Exception e) {
			e.printStackTrace();
			removerLockLotes(estoquesLock, estoquesLock.size());
		}
			
	}

	public Double getValorTotalItens(){
		double total = 0d;
		for(NotaFiscalEstoque item : getItensNotaFiscal()){
			total += item.getTotal().doubleValue();
		}
		return total;
	}
	
	private void validarValorNotaIgualValorItens() throws ExcecaoNotaFiscalValorTotalNaoConfere {
		Double valorTotalItens = Double.valueOf(df.format(getValorTotalItens()).replaceAll(",", ""));
		Double valorTotal = Double.valueOf(df.format(getNotaFiscal().getValorDescontado()).replaceAll(",", ""));
		if(!valorTotalItens.equals(valorTotal)){
			throw new ExcecaoNotaFiscalValorTotalNaoConfere();
		}
	}

	private void removerLockLotes(List<Estoque> estoquesLock, int qtdItens) {
		for(int i = 0; i < qtdItens; i++){
			try {
				Estoque item = estoquesLock.get(i);
				new ControleEstoque().unLockEstoque(item);
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carregarItensNF(int id){
		 String hql = "select o from NotaFiscalEstoque o where o.notaFiscal.idNotaFiscal = " + id + 
				 		" order by to_ascii(lower(o.estoque.material.descricao)), o.estoque.lote";
		setItensNotaFiscal(super.getBusca(hql));
	}
	
	public void exibirDialogConfirmacaoLiberacao(){
		setExibirDialogConfirmacaoLiberacao(true);
		carregarItensNF(getNotaFiscal().getIdNotaFiscal());
		atualizarNotaFiscal();
	}
	
	private void atualizarNotaFiscal() {
		NotaFiscal notaFiscal = new ConsultaGeral<NotaFiscal>(new StringBuilder("select o from NotaFiscal o where o.idNotaFiscal = "+getNotaFiscal().getIdNotaFiscal())).consultaUnica();
		setNotaFiscal(notaFiscal);
	}

	public void ocultarDialogConfirmacaoLiberacao(){
		setExibirDialogConfirmacaoLiberacao(false);
	}
	
	public boolean isExibirDialogConfirmacaoLiberacao() {
		return exibirDialogConfirmacaoLiberacao;
	}

	public void setExibirDialogConfirmacaoLiberacao(boolean exibirDialogConfirmacaoLiberacao) {
		this.exibirDialogConfirmacaoLiberacao = exibirDialogConfirmacaoLiberacao;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public List<NotaFiscalEstoque> getItensNotaFiscal() {
		return itensNotaFiscal;
	}

	public void setItensNotaFiscal(List<NotaFiscalEstoque> itensNotaFiscal) {
		this.itensNotaFiscal = itensNotaFiscal;
	}
	
	
}
