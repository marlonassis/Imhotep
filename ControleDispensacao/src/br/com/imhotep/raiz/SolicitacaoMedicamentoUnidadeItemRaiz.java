package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeItemRaiz extends PadraoHome<SolicitacaoMedicamentoUnidadeItem>{
	
	private void validarQuantidade() throws ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque {
		ControleEstoque ce = new ControleEstoque();
		ce.liberarReserva(getInstancia().getQuantidadeSolicitada(), getInstancia().getMaterial());
	}
	
	@Override
	public boolean enviar() {
		try {
			validarQuantidade();
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setDataInsercao(new Date());
			getInstancia().setSolicitacaoMedicamentoUnidade(SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().getInstancia());
			if(super.enviar()){
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoEstoqueVazio e) {
			e.printStackTrace();
		} catch (ExcecaoSaldoInsuficienteEstoque e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} 
		return false;
	}
	
}
