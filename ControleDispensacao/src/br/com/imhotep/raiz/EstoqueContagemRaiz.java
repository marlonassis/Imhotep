package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.EstoqueContagem;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstoqueContagemRaiz extends PadraoRaiz<EstoqueContagem>{

	@Override
	public boolean enviar() {
		try {
			Date dataCadastro = new Date();
			
			getInstancia().getEstoque().setQuantidadeAtual(getInstancia().getQuantidadeContada());
			
			getInstancia().setQuantidadeAtual(new EstoqueConsultaRaiz().consultarQuantidadeEstoque(getInstancia().getEstoque()));
			getInstancia().setResponsavel(Autenticador.getProfissionalLogado());
			getInstancia().setDataCadastro(dataCadastro);
			
			MovimentoLivro ml = new MovimentoLivro();
			ml.setDataMovimento(dataCadastro);
			ml.setEstoque(getInstancia().getEstoque());
			ml.setJustificativa("Invent‡rio pelo m—dulo de contagem de estoque");
			
			Integer quantidadeAtual = getInstancia().getQuantidadeAtual();
			Integer quantidadeContada = getInstancia().getQuantidadeContada();
			
			ml.setQuantidadeAtual(quantidadeAtual);
			ml.setQuantidadeMovimentacao(quantidadeMovimentada(quantidadeContada, quantidadeAtual));
			ml.setTipoMovimento(tipoMovimentoInventario(quantidadeContada, quantidadeAtual));
			ml.setUsuarioMovimentacao(Autenticador.getProfissionalLogado().getUsuario());
			
			getInstancia().setMovimentoLivro(ml);
			
			PadraoFluxoTemp.limparFluxo();
			PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro", ml);
			PadraoFluxoTemp.getObjetoSalvar().put("EstoqueContagem", getInstancia());
			PadraoFluxoTemp.getObjetoAtualizar().put("Estoque", getInstancia().getEstoque());
			PadraoFluxoTemp.finalizarFluxo();
			PadraoFluxoTemp.limparFluxo();
			
			super.novaInstancia();
			
			return true;
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
		return false;
	}

	private Integer quantidadeMovimentada(Integer quantidadeContada, Integer quantidadeAtual) {
		int saldo = quantidadeContada - quantidadeAtual;
		if(saldo < 0){
			return saldo * -1; 
		}else{
			return saldo;
		}
	}
	
	private TipoMovimento tipoMovimentoInventario(Integer quantidadeContada, Integer quantidadeAtual) {
		int saldo = quantidadeContada - quantidadeAtual;
		if(saldo < 0){
			return Parametro.tipoMovimentoInventarioSaida();
		}else{
			return Parametro.tipoMovimentoInventarioEntrada();
		}
	}	
}
