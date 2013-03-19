package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.entidade.AjusteEstoque;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.fluxo.FluxoAjusteEstoque;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class AjusteEstoqueRaiz extends PadraoHome<AjusteEstoque>{
	
	private boolean loteEncontrado;
	
	public AjusteEstoqueRaiz() {
		limpar();
	}

	private void limpar() {
		getInstancia().setMovimentoLivro(new MovimentoLivro());
		getInstancia().getMovimentoLivro().setEstoque(new Estoque());
		setLoteEncontrado(false);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getMovimentoLivro().getEstoque().getLote();
		Estoque estoque = new EstoqueLoteConsultaRaiz().consultar(lote);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			getInstancia().getMovimentoLivro().setEstoque(estoque);
		}else{
			mensagem("Lote não encontrado.", lote, Constantes.WARN);
		}
	}
	
	@Override
	public boolean enviar() {
		try {
			FluxoAjusteEstoque fluxoAjusteEstoque = new FluxoAjusteEstoque();
			if(loteEncontrado){
				procurarLote();
				fluxoAjusteEstoque.atualizarEstoque(getInstancia());
			}else
				fluxoAjusteEstoque.salvarNovoEstoque(getInstancia());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void carregarEstoqueConsultaMaterial(Estoque estoque){
		loteEncontrado = true;
		getInstancia().getMovimentoLivro().setEstoque(estoque);
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		limpar();
	}
	
	public Boolean getLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(Boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}
	
}
