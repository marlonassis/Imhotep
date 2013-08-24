package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.fluxo.FluxoSemNotaFiscal;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EntradaMaterialSemNotaRaiz extends PadraoHome<Estoque>{
	
	private Boolean loteEncontrado;
	private Integer quantidadeMovimentacao;
	
	public EntradaMaterialSemNotaRaiz() {
	}

	public void procurarLote(){
		String lote = getInstancia().getLote();
		Estoque estoque = new EstoqueLoteConsultaRaiz().consultar(lote);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			setInstancia(estoque);
		}else{
			setInstancia(new Estoque());
			getInstancia().setLote(lote);
		}
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setLoteEncontrado(null);
		setQuantidadeMovimentacao(null);
	}
	
	private MovimentoLivro prepararMovimentoLivro() {
		MovimentoLivro movimentoLivro = new MovimentoLivro();
		movimentoLivro.setEstoque(getInstancia());
		movimentoLivro.setQuantidadeMovimentacao(getQuantidadeMovimentacao());
		movimentoLivro.setTipoMovimento(Parametro.tipoMovimentoSemNotaFiscalEntrada());
		return movimentoLivro;
	}
	
	@Override
	public boolean enviar() {
		try {
			MovimentoLivro movimentoLivro = prepararMovimentoLivro();
			FluxoSemNotaFiscal fluxoSemNotaFiscal = new FluxoSemNotaFiscal();
			if(getLoteEncontrado())
				fluxoSemNotaFiscal.atualizarEstoque(getInstancia(), movimentoLivro);
			else{
				getInstancia().setQuantidadeAtual(getQuantidadeMovimentacao());
				fluxoSemNotaFiscal.salvarNovoEstoque(getInstancia(), movimentoLivro);
			}
			novaInstancia();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean getLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(Boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}

	public Integer getQuantidadeMovimentacao() {
		return quantidadeMovimentacao;
	}

	public void setQuantidadeMovimentacao(Integer quantidadeMovimentacao) {
		this.quantidadeMovimentacao = quantidadeMovimentacao;
	}
	
	
}