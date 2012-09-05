package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.EstoqueCentroCirurgicoLivro;
import br.com.ControleDispensacao.enums.TipoMovimentacaoEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="estoqueCentroCirurgicoLivroHome")
@SessionScoped
public class EstoqueCentroCirurgicoLivroHome extends PadraoHome<EstoqueCentroCirurgicoLivro> {
	
	@Override
	public boolean enviar() {
		if(!saldoNegativo(getInstancia())){
			preEnvio(getInstancia());
			boolean ret = super.enviar();
			if(ret)
				super.novaInstancia();
			return ret;
		}else
			super.mensagem("Quantidade em estoque está abaixo do solicitado.", "Consulte o estoque deste material para ver o saldo atual.");
		return false;
	}

	private void preEnvio(EstoqueCentroCirurgicoLivro estoqueCentroCirurgicoLivro) {
		estoqueCentroCirurgicoLivro.setDataMovimento(new Date());
		estoqueCentroCirurgicoLivro.setProfissionalMovimentacao(Autenticador.getInstancia().getProfissionalAtual());
		carregaSaldoAnterior(estoqueCentroCirurgicoLivro);
		calculaSaldoAtual(estoqueCentroCirurgicoLivro);
	}

	private boolean saldoNegativo(EstoqueCentroCirurgicoLivro estoqueCentroCirurgicoLivro) {
		if(!estoqueCentroCirurgicoLivro.getTipoMovimentacao().equals(TipoMovimentacaoEnum.E)){
			Integer saldoAnterior = buscaSaldoAnterior(estoqueCentroCirurgicoLivro.getEstoqueCentroCirurgico().getLote());
			Integer quantidadeMovimentada = estoqueCentroCirurgicoLivro.getQuantidadeMovimentacao();
			return (saldoAnterior - quantidadeMovimentada) < 0;
		}
		return false;
	}
	
	private void calculaSaldoAtual(EstoqueCentroCirurgicoLivro estoqueCentroCirurgicoLivro) {
		Integer saldoAnterior = estoqueCentroCirurgicoLivro.getSaldoAnterior();
		Integer quantidadeMovimentada = estoqueCentroCirurgicoLivro.getQuantidadeMovimentacao();
		if(estoqueCentroCirurgicoLivro.getTipoMovimentacao().equals(TipoMovimentacaoEnum.E))
			estoqueCentroCirurgicoLivro.setSaldoAtual(saldoAnterior + quantidadeMovimentada);
		else
			estoqueCentroCirurgicoLivro.setSaldoAtual(saldoAnterior - quantidadeMovimentada);
	}

	private void carregaSaldoAnterior(EstoqueCentroCirurgicoLivro estoqueCentroCirurgicoLivro) {
		Integer saldoAnterior = buscaSaldoAnterior(estoqueCentroCirurgicoLivro.getEstoqueCentroCirurgico().getLote());
		estoqueCentroCirurgicoLivro.setSaldoAnterior(saldoAnterior);
	}

	private Integer buscaSaldoAnterior(String lote) {
		String sql = "select o.saldoAtual from EstoqueCentroCirurgicoLivro o where o.estoqueCentroCirurgico.lote = '"+lote+"'and o.dataMovimento = (select max(b.dataMovimento) from EstoqueCentroCirurgicoLivro b where " +
					 "b.estoqueCentroCirurgico = o.estoqueCentroCirurgico and b.estoqueCentroCirurgico.bloqueado = 'N')";
		Integer qtd = new ConsultaGeral<Integer>().consultaUnica(new StringBuilder(sql));
		return qtd == null ? 0 : qtd;
	}
	
}