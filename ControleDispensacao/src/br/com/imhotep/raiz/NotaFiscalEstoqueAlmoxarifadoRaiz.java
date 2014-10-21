package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstoqueAlmoxarifadoVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoMovimentoLivroNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class NotaFiscalEstoqueAlmoxarifadoRaiz extends PadraoRaiz<NotaFiscalEstoqueAlmoxarifado>{

	public void salvarItem(NotaFiscalEstoqueAlmoxarifado notaFiscalEstoque, Integer quantidadeMovimentada) throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueUnLock, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoMovimentoLivroNaoCadastrado, InstantiationException, IllegalAccessException, ClassNotFoundException  {
		int qtdMovimentada = quantidadeMovimentada == null ? 0 : quantidadeMovimentada;
		TipoMovimentoAlmoxarifado tipoMovimento = Parametro.tipoMovimentoNotaFiscalEntradaAlmoxarifado();
		int quantidadeAtual = notaFiscalEstoque.getEstoqueAlmoxarifado().getQuantidadeAtual();
		new ControleEstoqueAlmoxarifadoTemp().liberarAjusteEstoqueAlmoxarifado(notaFiscalEstoque.getEstoqueAlmoxarifado(), qtdMovimentada, tipoMovimento);
		MovimentoLivroAlmoxarifado mla = new MovimentoLivroAlmoxarifadoRaiz().criarNovoMovimento(notaFiscalEstoque.getEstoqueAlmoxarifado(), qtdMovimentada, quantidadeAtual, tipoMovimento);
		mla.setJustificativa("NF: "+notaFiscalEstoque.getNotaFiscalAlmoxarifado().getIdentificacao());
		notaFiscalEstoque.setMovimentoLivroAlmoxarifado(mla);
		notaFiscalEstoque.setDataInsercao(new Date());
		notaFiscalEstoque.setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
		addNotaFiscalEstoqueFluxo(notaFiscalEstoque);
	}

	private void addNotaFiscalEstoqueFluxo(NotaFiscalEstoqueAlmoxarifado notaFiscalEstoqueAlmoxarifado) {
		if(notaFiscalEstoqueAlmoxarifado.getIdNotaFiscalEstoqueAlmoxarifado() > 0)
			PadraoFluxoTemp.getObjetoAtualizar().put("notaFiscalEstoqueAlmoxarifado", notaFiscalEstoqueAlmoxarifado);
		else
			PadraoFluxoTemp.getObjetoSalvar().put("notaFiscalEstoqueAlmoxarifado", notaFiscalEstoqueAlmoxarifado);
	}

	
}
