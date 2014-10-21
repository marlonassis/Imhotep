package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.entidade.Doacao;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Hospital;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class DoacaoRaiz extends PadraoRaiz<Doacao>{
	
	private Boolean loteEncontrado;
	
	public DoacaoRaiz() {
		limpar();
	}

	public void carregarEstoqueConsultaMaterial(Estoque estoque){
		loteEncontrado = true;
		getInstancia().getMovimentoLivro().setEstoque(estoque);
	}
	
	private void limpar() {
		getInstancia().setMovimentoLivro(new MovimentoLivro());
		getInstancia().getMovimentoLivro().setEstoque(new Estoque());
		setLoteEncontrado(null);
	}
	
	public DoacaoRaiz(Date dataDoacao, Hospital hospital, MovimentoLivro movimentoLivro) {
		getInstancia().setDataDoacao(dataDoacao);
		getInstancia().setHospital(hospital);
		getInstancia().setMovimentoLivro(movimentoLivro);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getMovimentoLivro().getEstoque().getLote();
		Estoque estoque = new EstoqueConsultaRaiz().consultarEstoqueLivre(lote);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			getInstancia().getMovimentoLivro().setEstoque(estoque);
		}else{
			getInstancia().getMovimentoLivro().setTipoMovimento(Parametro.tipoMovimentoDoacaoRecebida());
		}
	}
	
	@Override
	public boolean enviar() {
		try {
			PadraoFluxoTemp.limparFluxo();
			new ControleEstoqueTemp().liberarAjuste(new Date(), getInstancia().getMovimentoLivro());
			PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro-"+getInstancia().getMovimentoLivro().hashCode(), getInstancia().getMovimentoLivro());
			PadraoFluxoTemp.getObjetoSalvar().put("Doacao-"+getInstancia().hashCode(), getInstancia());
			PadraoFluxoTemp.getObjetoAtualizar().put("Estoque-"+getInstancia().getMovimentoLivro().getEstoque().hashCode(), getInstancia().getMovimentoLivro().getEstoque());
			PadraoFluxoTemp.finalizarFluxo();
			PadraoFluxoTemp.limparFluxo();
			novaInstancia();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
