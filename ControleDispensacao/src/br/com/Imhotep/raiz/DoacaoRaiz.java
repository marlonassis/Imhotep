package br.com.Imhotep.raiz;

import java.io.IOException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Doacao;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Hospital;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.remendo.PadraoHome;

@ManagedBean(name="doacaoRaiz")
@SessionScoped
public class DoacaoRaiz extends PadraoHome<Doacao>{
	
	private Boolean loteEncontrado;
	
	public DoacaoRaiz() {
		limpar();
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
	
	public void procurarLote() throws IOException{
		String lote = getInstancia().getMovimentoLivro().getEstoque().getLote();
		Estoque estoque = new EstoqueLoteConsultaRaiz().consultar(lote);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			getInstancia().getMovimentoLivro().setEstoque(estoque);
		}
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
