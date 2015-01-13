package br.com.imhotep.raiz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoNotaFiscalValorTotalNaoConfere;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class NotaFiscalAlmoxarifadoConfirmacaoRaiz extends PadraoRaiz<NotaFiscalEstoqueAlmoxarifado>{
	private NotaFiscalAlmoxarifado notaFiscal;
	private List<NotaFiscalEstoqueAlmoxarifado> itensNotaFiscal = new ArrayList<NotaFiscalEstoqueAlmoxarifado>();
	private boolean exibirDialogConfirmacaoLiberacao;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	public void liberarNotaFiscal(){
		List<EstoqueAlmoxarifado> estoquesLock = new ArrayList<EstoqueAlmoxarifado>();
		try {
			validarValorNotaIgualValorItens();
			PadraoFluxoTemp.limparFluxo();
			Date dataMovimento = new Date();
			TipoMovimentoAlmoxarifado tipoMovimento = Parametro.tipoMovimentoNotaFiscalEntradaAlmoxarifado();
			for(NotaFiscalEstoqueAlmoxarifado item : getItensNotaFiscal()){
				EstoqueAlmoxarifado estoque = item.getEstoqueAlmoxarifado();
				estoque.setBloqueado(false);
				estoque.setMotivoBloqueio(null);
				String justificativa = "NF: "+item.getNotaFiscalAlmoxarifado().getIdentificacao();
				estoquesLock.add(item.getEstoqueAlmoxarifado());
				MovimentoLivroAlmoxarifado movimentoLivro = new ControleEstoqueAlmoxarifadoTemp().getMovimentoLivroPronto(
									dataMovimento, estoque , tipoMovimento , justificativa, item.getQuantidadeEntrada());
				String chave = "EstoqueAlmoxarifado-" + movimentoLivro.getEstoqueAlmoxarifado().hashCode();
				PadraoFluxoTemp.getObjetoAtualizar().put(chave, movimentoLivro.getEstoqueAlmoxarifado());
				PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro-" + movimentoLivro.hashCode(), movimentoLivro);
				item.setMovimentoLivroAlmoxarifado(movimentoLivro);
				item.setEstoqueAlmoxarifado(movimentoLivro.getEstoqueAlmoxarifado());
				PadraoFluxoTemp.getObjetoAtualizar().put("NotaFiscalEstoqueAlmoxarifado-" + item.hashCode(), item);
			}
			getNotaFiscal().setLiberada(true);
			getNotaFiscal().setProfissionalAutorizacao(Autenticador.getProfissionalLogado());
			PadraoFluxoTemp.getObjetoAtualizar().put("NotaFiscal-"+getNotaFiscal().hashCode(), getNotaFiscal());
			PadraoFluxoTemp.finalizarFluxo();
			setExibirDialogConfirmacaoLiberacao(false);
			((NotaFiscalAlmoxarifadoRaiz) new ControleInstancia().procuraInstancia(NotaFiscalAlmoxarifadoRaiz.class)).setInstancia(getNotaFiscal());
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
		for(NotaFiscalEstoqueAlmoxarifado item : getItensNotaFiscal()){
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

	private void removerLockLotes(List<EstoqueAlmoxarifado> estoquesLock, int qtdItens) {
		for(int i = 0; i < qtdItens; i++){
			try {
				EstoqueAlmoxarifado item = estoquesLock.get(i);
				new ControleEstoqueAlmoxarifadoTemp().unLockEstoque(item);
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carregarItensNF(int id){
		 String hql = "select o from NotaFiscalEstoqueAlmoxarifado o where o.notaFiscalAlmoxarifado.idNotaFiscalAlmoxarifado = " + id + 
				 		" order by to_ascii(lower(o.estoqueAlmoxarifado.materialAlmoxarifado.descricao)), o.estoqueAlmoxarifado.lote";
		setItensNotaFiscal(super.getBusca(hql));
	}
	
	public void exibirDialogConfirmacaoLiberacao(){
		setExibirDialogConfirmacaoLiberacao(true);
		carregarItensNF(getNotaFiscal().getIdNotaFiscalAlmoxarifado());
		atualizarNotaFiscal();
	}
	
	private void atualizarNotaFiscal() {
		int id = getNotaFiscal().getIdNotaFiscalAlmoxarifado();
		String hql = "select o from NotaFiscalAlmoxarifado o where o.idNotaFiscalAlmoxarifado = "+id;
		NotaFiscalAlmoxarifado notaFiscal = new ConsultaGeral<NotaFiscalAlmoxarifado>(new StringBuilder(hql)).consultaUnica();
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

	public NotaFiscalAlmoxarifado getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscalAlmoxarifado notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public List<NotaFiscalEstoqueAlmoxarifado> getItensNotaFiscal() {
		return itensNotaFiscal;
	}

	public void setItensNotaFiscal(List<NotaFiscalEstoqueAlmoxarifado> itensNotaFiscal) {
		this.itensNotaFiscal = itensNotaFiscal;
	}
	
	
}
