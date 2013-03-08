package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.entidade.DispensacaoSimples;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.fluxo.FluxoDispensacaoSimples;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.remendo.PadraoHome;

@ManagedBean(name="dispensacaoSimplesRaiz")
@SessionScoped
public class DispensacaoSimplesRaiz extends PadraoHome<DispensacaoSimples>{
	
	private boolean loteEncontrado;
	
	public DispensacaoSimplesRaiz() {
		limpar();
	}

	public void procurarLote(){
		String lote = getInstancia().getMovimentoLivro().getEstoque().getLote();
		Estoque estoque = new EstoqueLoteConsultaRaiz().consultar(lote);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			getInstancia().getMovimentoLivro().setEstoque(estoque);
		}else{
			limpar();
			getInstancia().getMovimentoLivro().getEstoque().setLote(lote);
			mensagem("Estoque não encontrado.", lote, Constantes.WARN);
		}
	}
	
	private void limpar() {
		getInstancia().setMovimentoLivro(new MovimentoLivro());
		getInstancia().getMovimentoLivro().setEstoque(new Estoque());
		setLoteEncontrado(false);
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		limpar();
	}
	
	@Override
	public boolean enviar() {
		try {
			FluxoDispensacaoSimples fluxoDispensacaoSimples = new FluxoDispensacaoSimples();
			if(getLoteEncontrado()){
				procurarLote();
				if(fluxoDispensacaoSimples.atualizarEstoque(getInstancia()))
					novaInstancia();
				else
					return false;
			}
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
	
	public boolean getLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}

}