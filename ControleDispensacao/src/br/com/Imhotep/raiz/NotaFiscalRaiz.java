package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.remendo.PadraoHome;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.NotaFiscal;
import br.com.Imhotep.seguranca.Autenticador;

@ManagedBean(name="notaFiscalRaiz")
@SessionScoped
public class NotaFiscalRaiz extends PadraoHome<NotaFiscal>{

	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	@Override
	protected void aposEnviar() {
		getInstancia().setEstoque(new Estoque());
		super.aposEnviar();
	}
	
	public void gravarItemNotaFiscal(){
		EntradaMaterialRaiz emr = new EntradaMaterialRaiz();
		emr.getItensMovimentoGeral().setQuantidade(getInstancia().getEstoque().getQuantidade());
		emr.getItensMovimentoGeral().getMovimentoGeral().setNumeroDocumento(getInstancia().getIdentificacaoNotaFiscal());
		emr.enviar();
	}
	
}
