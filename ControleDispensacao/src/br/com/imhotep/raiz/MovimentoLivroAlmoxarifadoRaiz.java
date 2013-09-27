package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class MovimentoLivroAlmoxarifadoRaiz extends PadraoHome<MovimentoLivroAlmoxarifado>{
	
	public MovimentoLivroAlmoxarifado criarNovoMovimento(EstoqueAlmoxarifado estoqueAlmoxarifado, int qtdMovimentacao, int qtdAtual, TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado) throws ExcecaoProfissionalLogado{
		getInstancia().setDataMovimento(new Date());
		getInstancia().setEstoqueAlmoxarifado(estoqueAlmoxarifado);
		getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
		getInstancia().setQuantidadeAtual(qtdAtual);
		getInstancia().setQuantidadeMovimentacao(qtdMovimentacao);
		getInstancia().setTipoMovimentoAlmoxarifado(tipoMovimentoAlmoxarifado);
		addMovimentoAlmoxarifadoFluxo(getInstancia());
		return getInstancia();
	}
	
	private void addMovimentoAlmoxarifadoFluxo(MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado) {
		if(movimentoLivroAlmoxarifado.getIdMovimentoLivro() > 0)
			PadraoFluxoTemp.getObjetoAtualizar().put("movimentoLivroAlmoxarifado", movimentoLivroAlmoxarifado);
		else
			PadraoFluxoTemp.getObjetoSalvar().put("movimentoLivroAlmoxarifado", movimentoLivroAlmoxarifado);
	}
	
}