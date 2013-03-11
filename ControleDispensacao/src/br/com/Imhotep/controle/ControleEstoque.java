package br.com.Imhotep.controle;

import java.util.Date;

import javax.faces.application.FacesMessage;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.enums.TipoOperacaoEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.consulta.raiz.MovimentoLivroConsultaRaiz;
import br.com.imhotep.temp.PadraoGeralTemp;

public class ControleEstoque extends PadraoGeralTemp {
	
	public boolean estoqueInsuficiente(Integer quantidadeAtual, int quantidadeMovimentacao) {
		if(quantidadeMovimentacao > quantidadeAtual){
			super.mensagem("Não há quantidade suficiente no estoque. O máximo disponível é de " + String.valueOf(quantidadeAtual) + " unidade(s)", "", FacesMessage.SEVERITY_ERROR);
			return true;
		}else{
			return false;
		}
	}

	public boolean estoqueVazio(Integer quantidade) {
		if(quantidade > 0){
			return false;
		}else{
			super.mensagem("Este medicamento está em falta.", "", FacesMessage.SEVERITY_ERROR);
			return true;
		}
	}
	
	private boolean estoqueBloqueado(Estoque estoque){
		if(estoque.getBloqueado()){
			super.mensagem("Este estoque está bloqueado.", "", FacesMessage.SEVERITY_ERROR);
			return true;
		}
		return false;
	}
	
	public boolean manipularEstoque(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException,ClassNotFoundException {
		if(!estoqueBloqueado(movimentoLivro.getEstoque())){
			prepararMovimentoLivro(dataAtual, movimentoLivro);
			return prepararEstoque(dataAtual, movimentoLivro.getEstoque(), movimentoLivro.getQuantidadeMovimentacao(), movimentoLivro.getTipoMovimento());
		}
		return false;
	}
	
	private void prepararMovimentoLivro(Date dataAtual, MovimentoLivro movimentoLivro) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		int saldoAtualMaterial = new MovimentoLivroConsultaRaiz().saldoAtualMaterial(movimentoLivro.getEstoque().getMaterial());
		movimentoLivro.setSaldoAnterior(saldoAtualMaterial);
		movimentoLivro.setDataMovimento(dataAtual);
		movimentoLivro.setUnidadeCadastrante(autenticador.getUnidadeAtual());
		movimentoLivro.setUsuarioMovimentacao(autenticador.getUsuarioAtual());
	}

	private boolean prepararEstoque(Date dataAtual, Estoque estoque, int quantidadeMovimentada, TipoMovimento tipoMovimento) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(estoque.getIdEstoque() == 0)
			setarDadosNovoEstoque(estoque, dataAtual);
		int saldoAtualizado = 0;
		int saldoAtual = estoque.getQuantidadeAtual();
		boolean movimentoEntrada = tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E);
		if(movimentoEntrada || (!estoqueVazio(saldoAtual) && !estoqueInsuficiente(saldoAtual, quantidadeMovimentada))){
			if(movimentoEntrada){
				saldoAtualizado = saldoAtual + quantidadeMovimentada;
			}else{
				saldoAtualizado = saldoAtual - quantidadeMovimentada;
			}
			estoque.setQuantidadeAtual(saldoAtualizado);
			return true;
		}
		return false;
	}

	private void setarDadosNovoEstoque(Estoque estoque, Date dataAtual) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Autenticador autenticador = Autenticador.getInstancia();
		estoque.setDataInclusao(dataAtual);
		estoque.setUnidade(autenticador.getUnidadeAtual());
		estoque.setUsuarioInclusao(autenticador.getUsuarioAtual());
	}
	
}
