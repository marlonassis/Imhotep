package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifado;
import br.com.imhotep.entidade.AjusteEstoqueAlmoxarifado;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class AjusteEstoqueAlmoxarifadoRaiz extends PadraoHome<AjusteEstoqueAlmoxarifado>{
	
	private Boolean loteEncontrado;
	private Integer quantidadeMovimentada;
	private TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado;
	
	public AjusteEstoqueAlmoxarifadoRaiz() {
		limpar();
	}

	private void limpar() {
		getInstancia().setMovimentoLivroAlmoxarifado(new MovimentoLivroAlmoxarifado());
		getInstancia().getMovimentoLivroAlmoxarifado().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		setLoteEncontrado(null);
		setTipoMovimentoAlmoxarifado(null);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getMovimentoLivroAlmoxarifado().getEstoqueAlmoxarifado().getLote();
		if(lote == null || lote.isEmpty()){
			mensagem("Informe o lote", lote, Constantes.WARN);
		}else{
			EstoqueAlmoxarifado estoque = new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoqueLivre(lote);
			loteEncontrado = estoque != null;
			if(loteEncontrado){
				getInstancia().getMovimentoLivroAlmoxarifado().setEstoqueAlmoxarifado(estoque);
			}else{
				mensagem("Lote não encontrado.", lote, Constantes.WARN);
			}
		}
	}
	
	public void carregarEstoque(EstoqueAlmoxarifado ea){
		getInstancia().getMovimentoLivroAlmoxarifado().setEstoqueAlmoxarifado(ea);
		setLoteEncontrado(true);
	}
	
	@Override
	public boolean enviar(){
		try {
			int qtd = getQuantidadeMovimentada() == null ? 0 : getQuantidadeMovimentada();
			EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getMovimentoLivroAlmoxarifado().getEstoqueAlmoxarifado();
			MovimentoLivroAlmoxarifado mla = new ControleEstoqueAlmoxarifado().validarEstoqueAlmoxarifado(estoqueAlmoxarifado, qtd, getTipoMovimentoAlmoxarifado());
			getInstancia().setMovimentoLivroAlmoxarifado(mla);
			addAjusteFluxo();
			processarFluxo();
			limparFluxo();
			unlockEstoque();
			novaInstancia();
			return true;
		} catch (Exception e){
			e.printStackTrace();
		}
		unlockEstoque();
		return false;
	}

	private void unlockEstoque() {
		try {
			new ControleEstoqueAlmoxarifado().unLockEstoque(getInstancia().getMovimentoLivroAlmoxarifado().getEstoqueAlmoxarifado());
		} catch (ExcecaoEstoqueUnLock e1) {
			e1.printStackTrace();
		}
	}
	
	private void addAjusteFluxo() {
		PadraoFluxoTemp.getObjetoAtualizar().put("ajusteEstoque", getInstancia());
	}
	
	private void processarFluxo() throws ExcecaoPadraoFluxo {
		new PadraoFluxoTemp().processarFluxo();
	}

	private void limparFluxo() {
		PadraoFluxoTemp.limparFluxo();
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

	public Integer getQuantidadeMovimentada() {
		return quantidadeMovimentada;
	}

	public void setQuantidadeMovimentada(Integer quantidadeMovimentada) {
		this.quantidadeMovimentada = quantidadeMovimentada;
	}

	public TipoMovimentoAlmoxarifado getTipoMovimentoAlmoxarifado() {
		return tipoMovimentoAlmoxarifado;
	}

	public void setTipoMovimentoAlmoxarifado(TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado) {
		this.tipoMovimentoAlmoxarifado = tipoMovimentoAlmoxarifado;
	}
	
}
