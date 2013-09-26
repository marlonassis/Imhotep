package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoItemNotaFiscalNaoCadastrada;
import br.com.imhotep.excecoes.ExcecaoMovimentoLivroNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class NotaFiscalEstoqueAlmoxarifadoRaiz extends PadraoHome<NotaFiscalEstoqueAlmoxarifado>{

	public void salvarItem(NotaFiscalEstoqueAlmoxarifado notaFiscalEstoque, Integer quantidadeMovimentada) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueUnLock, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoMovimentoLivroNaoCadastrado, ExcecaoItemNotaFiscalNaoCadastrada {
		int qtd = quantidadeMovimentada == null ? 0 : quantidadeMovimentada;
		MovimentoLivroAlmoxarifado mla = new ControleEstoqueAlmoxarifado().validarEstoqueAlmoxarifado(notaFiscalEstoque.getEstoqueAlmoxarifado(), qtd, Parametro.tipoMovimentoNotaFiscalEntradaAlmoxarifado());
		notaFiscalEstoque.setMovimentoLivroAlmoxarifado(mla);
		notaFiscalEstoque.setDataInsercao(new Date());
		notaFiscalEstoque.setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
		addNotaFiscalEstoqueFluxo(notaFiscalEstoque);
	}

	private void addNotaFiscalEstoqueFluxo(NotaFiscalEstoqueAlmoxarifado notaFiscalEstoque) {
		if(notaFiscalEstoque.getIdNotaFiscalEstoqueAlmoxarifado() > 0)
			PadraoFluxoTemp.getObjetoAtualizar().put("notaFiscalEstoque", notaFiscalEstoque);
		else
			PadraoFluxoTemp.getObjetoSalvar().put("notaFiscalEstoque", notaFiscalEstoque);
	}

	
}
