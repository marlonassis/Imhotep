package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.Doacao;
import br.com.imhotep.entidade.DoacaoItem;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class DoacaoConfirmacaoRaiz extends PadraoRaiz<DoacaoItem>{
	private Doacao doacao;
	private List<DoacaoItem> itens = new ArrayList<DoacaoItem>();
	private boolean exibirDialogConfirmacaoLiberacao;
	
	public void liberarDoacao(){
		List<Estoque> estoquesLock = new ArrayList<Estoque>();
		try {
			PadraoFluxoTemp.limparFluxo();
			Date dataMovimento = new Date();
			TipoMovimento tipoMovimento = getDoacao().getTipoMovimento();
			for(DoacaoItem item : getItens()){
				Estoque estoque = item.getEstoque();
				estoque.setBloqueado(false);
				estoque.setMotivoBloqueio(null);
				String justificativa = "Doação: "+item.getDoacao().getIdDoacao();
				estoquesLock.add(item.getEstoque());
				MovimentoLivro movimentoLivro = new ControleEstoque().getMovimentoLivroPronto(dataMovimento, estoque , tipoMovimento, 
																								justificativa, item.getQuantidade().intValue());
				PadraoFluxoTemp.getObjetoAtualizar().put("Estoque-" + movimentoLivro.getEstoque().hashCode(), movimentoLivro.getEstoque());
				PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro-" + movimentoLivro.hashCode(), movimentoLivro);
				item.setMovimentoLivro(movimentoLivro);
				item.setEstoque(movimentoLivro.getEstoque());
				PadraoFluxoTemp.getObjetoAtualizar().put("DoacaoItem-" + item.hashCode(), item);
			}
			getDoacao().setLiberado(true);
			getDoacao().setProfissionalAutorizacao(Autenticador.getProfissionalLogado());
			PadraoFluxoTemp.getObjetoAtualizar().put("Doacao-"+getDoacao().hashCode(), getDoacao());
			PadraoFluxoTemp.finalizarFluxo();
			setExibirDialogConfirmacaoLiberacao(false);
			((DoacaoRaiz) new ControleInstancia().procuraInstancia(NotaFiscalRaiz.class)).setInstancia(getDoacao());
		}catch (ExcecaoEstoqueLockAcimaUmMinuto e){
			e.printStackTrace();
			removerLockLotes(estoquesLock, estoquesLock.size() - 1);
		}catch (Exception e) {
			e.printStackTrace();
			removerLockLotes(estoquesLock, estoquesLock.size());
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
	
	private void carregarItensDoacao(Doacao doacao){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from DoacaoItem o where o.doacao.idDoacao = ");
		stringBuilder.append(doacao.getIdDoacao());
		stringBuilder.append(" order by o.estoque.lote");
		setItens(new ArrayList<DoacaoItem>(new ConsultaGeral<DoacaoItem>(new StringBuilder(stringBuilder.toString())).consulta()));
	}
	
	public void exibirDialogConfirmacaoLiberacao(){
		carregarItensDoacao(getDoacao());
		setExibirDialogConfirmacaoLiberacao(true);
//		atualizarNotaFiscal();
	}
	
//	private void atualizarNotaFiscal() {
//		NotaFiscal notaFiscal = new ConsultaGeral<NotaFiscal>(new StringBuilder("select o from NotaFiscal o where o.idNotaFiscal = "+getNotaFiscal().getIdNotaFiscal())).consultaUnica();
//		setNotaFiscal(notaFiscal);
//	}

	public void ocultarDialogConfirmacaoLiberacao(){
		setExibirDialogConfirmacaoLiberacao(false);
	}
	
	public boolean isExibirDialogConfirmacaoLiberacao() {
		return exibirDialogConfirmacaoLiberacao;
	}

	public void setExibirDialogConfirmacaoLiberacao(boolean exibirDialogConfirmacaoLiberacao) {
		this.exibirDialogConfirmacaoLiberacao = exibirDialogConfirmacaoLiberacao;
	}

	public Doacao getDoacao() {
		return doacao;
	}

	public void setDoacao(Doacao doacao) {
		this.doacao = doacao;
	}

	public List<DoacaoItem> getItens() {
		return itens;
	}

	public void setItens(List<DoacaoItem> itens) {
		this.itens = itens;
	}

	
	
}
