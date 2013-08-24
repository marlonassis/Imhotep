package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.Doacao;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Hospital;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.fluxo.FluxoDoacao;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class DoacaoRaiz extends PadraoHome<Doacao>{
	
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
			FluxoDoacao fluxoDoacao = new FluxoDoacao();
			if(loteEncontrado){
				procurarLote();
				fluxoDoacao.atualizarDoacao(getInstancia());
			}else
				fluxoDoacao.salvarNovaDoacao(getInstancia());
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
