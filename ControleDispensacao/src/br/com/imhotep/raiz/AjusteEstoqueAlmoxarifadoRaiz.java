package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AjusteEstoqueAlmoxarifadoRaiz extends PadraoRaiz<MovimentoLivroAlmoxarifado>{
	
	private Boolean loteEncontrado;
	private Integer quantidadeMovimentada;
	private TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado;
	
	public AjusteEstoqueAlmoxarifadoRaiz() {
		limpar();
	}

	private void limpar() {
		setInstancia(new MovimentoLivroAlmoxarifado());
		getInstancia().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		setLoteEncontrado(null);
		setTipoMovimentoAlmoxarifado(null);
		setQuantidadeMovimentada(null);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getEstoqueAlmoxarifado().getLote();
		if(lote == null || lote.isEmpty()){
			mensagem("Informe o lote", lote, Constantes.WARN);
		}else{
			EstoqueAlmoxarifado estoque = new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoqueLivre(lote);
			loteEncontrado = estoque != null;
			if(loteEncontrado){
				getInstancia().setEstoqueAlmoxarifado(estoque);
			}else{
				mensagem("Lote n‹o encontrado.", lote, Constantes.WARN);
			}
		}
	}
	
	public void carregarEstoque(EstoqueAlmoxarifado ea){
		getInstancia().setEstoqueAlmoxarifado(ea);
		setLoteEncontrado(true);
	}
	
	@Override
	public boolean enviar(){
		try {
			int qtd = getQuantidadeMovimentada() == null ? 0 : getQuantidadeMovimentada();
			getInstancia().setDataMovimento(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setTipoMovimentoAlmoxarifado(getTipoMovimentoAlmoxarifado());
			getInstancia().setQuantidadeAtual(getInstancia().getEstoqueAlmoxarifado().getQuantidadeAtual());
			getInstancia().setQuantidadeMovimentacao(getQuantidadeMovimentada());
			new ControleEstoqueAlmoxarifadoTemp().liberarAjustarEstoqueAlmoxarifado(getInstancia().getEstoqueAlmoxarifado(), qtd, getTipoMovimentoAlmoxarifado());
			PadraoFluxoTemp.getObjetoAtualizar().put("movimentoLivroAlmoxarifado", getInstancia());
			PadraoFluxoTemp.getObjetoAtualizar().put("estoqueAlmoxarifado", getInstancia().getEstoqueAlmoxarifado());
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
			new ControleEstoqueAlmoxarifadoTemp().unLockEstoque(getInstancia().getEstoqueAlmoxarifado());
		} catch (ExcecaoEstoqueUnLock e1) {
			e1.printStackTrace();
		}
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
