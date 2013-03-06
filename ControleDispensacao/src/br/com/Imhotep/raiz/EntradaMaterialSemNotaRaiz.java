package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.auxiliar.Parametro;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.fluxo.FluxoSemNotaFiscal;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.remendo.PadraoHome;

@ManagedBean(name="entradaMaterialSemNotaRaiz")
@SessionScoped
public class EntradaMaterialSemNotaRaiz extends PadraoHome<Estoque>{
	
	private Boolean loteEncontrado;
	private int quantidadeMovimentacao;
	
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
		setQuantidadeMovimentacao(0);
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
				if(fluxoSemNotaFiscal.atualizarEstoque(getInstancia(), movimentoLivro))
					novaInstancia();
				else
					return false;
			else
				if(fluxoSemNotaFiscal.salvarNovoEstoque(getInstancia(), movimentoLivro))
					novaInstancia();
				else
					return false;
			return true;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

	public int getQuantidadeMovimentacao() {
		return quantidadeMovimentacao;
	}

	public void setQuantidadeMovimentacao(int quantidadeMovimentacao) {
		this.quantidadeMovimentacao = quantidadeMovimentacao;
	}
	
	
}