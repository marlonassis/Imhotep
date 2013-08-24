package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.fluxo.FluxoDispensacaoSimples;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class DispensacaoSimplesRaiz extends PadraoHome<DispensacaoSimples>{
	
	private boolean loteEncontrado;
	private boolean ativarMensagem = true; 
	
	public DispensacaoSimplesRaiz() {
		super();
		limpar();
	}
	
	public DispensacaoSimplesRaiz(boolean exibeMensagemInsercao, boolean exibeMensagemAtualizacao){
		super();
		setExibeMensagemAtualizacao(exibeMensagemAtualizacao);
		setExibeMensagemInsercao(exibeMensagemInsercao);
		limpar();
	}
	
	public void carregarEstoqueConsultaMaterial(Estoque estoque){
		loteEncontrado = true;
		getInstancia().getMovimentoLivro().setEstoque(estoque);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getMovimentoLivro().getEstoque().getLote();
		Estoque estoque = new EstoqueConsultaRaiz().consultarEstoqueLivre(lote);
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
			FluxoDispensacaoSimples fluxoDispensacaoSimples = new FluxoDispensacaoSimples(isAtivarMensagem());
			
			if(getLoteEncontrado()){
				procurarLote();
				fluxoDispensacaoSimples.atualizarEstoque(getInstancia());
				novaInstancia();
			}
			return true;
		} catch (Exception e) {
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

	public boolean isAtivarMensagem() {
		return ativarMensagem;
	}

	public void setAtivarMensagem(boolean ativarMensagem) {
		this.ativarMensagem = ativarMensagem;
	}

}