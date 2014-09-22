package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AjusteEstoqueRaiz extends PadraoRaiz<MovimentoLivro>{
	
	private boolean loteEncontrado;
	
	public AjusteEstoqueRaiz() {
		limpar();
	}

	private void limpar() {
		super.novaInstancia();
		getInstancia().setEstoque(new Estoque());
		setLoteEncontrado(false);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getEstoque().getLote();
		Estoque estoque = new EstoqueConsultaRaiz().consultarEstoqueLivre(lote);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			getInstancia().setEstoque(estoque);
		}else{
			mensagem("Lote n‹o encontrado.", lote, Constantes.WARN);
		}
	}
	
	@Override
	public boolean enviar() {
		boolean r = false;
		try {
			PadraoFluxoTemp.limparFluxo();
			new ControleEstoqueTemp().liberarAjuste(new Date(), getInstancia());
			PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro-"+getInstancia().hashCode(), getInstancia());
			PadraoFluxoTemp.getObjetoAtualizar().put("Estoque-"+getInstancia().getEstoque().hashCode(), getInstancia());
			PadraoFluxoTemp.finalizarFluxo();
			novaInstancia();
			r = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				new ControleEstoqueTemp().unLockEstoque(getInstancia().getEstoque());
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
			PadraoFluxoTemp.limparFluxo();
		}
		return r;
	}
	
	public void carregarEstoqueConsultaMaterial(Estoque estoque){
		loteEncontrado = true;
		getInstancia().setEstoque(estoque);
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
