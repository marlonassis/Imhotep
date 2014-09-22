package br.com.imhotep.raiz;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoReservaVazia;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoItemInseridaDuasVezes;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoMedicamentoItemNaoAdicionado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeItemRaiz extends PadraoRaiz<SolicitacaoMedicamentoUnidadeItem>{
	
	public SolicitacaoMedicamentoUnidadeItemRaiz(){
		super();
	}
	
	public SolicitacaoMedicamentoUnidadeItemRaiz(SolicitacaoMedicamentoUnidadeItem item){
		super();
		setInstancia(item);
	}
	
	private void verificaItemInseridoDuasVezes(List<SolicitacaoMedicamentoUnidadeItem> itens) throws ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoSolicitacaoItemInseridaDuasVezes, ExcecaoReservaVazia, ExcecaoSolicitacaoMedicamentoItemNaoAdicionado{
		for(SolicitacaoMedicamentoUnidadeItem item : itens){
			if(item.getMaterial().equals(getInstancia().getMaterial())){
				Integer total = item.getQuantidadeSolicitada();
				total += getInstancia().getQuantidadeSolicitada();
				ControleEstoqueTemp ce = new ControleEstoqueTemp();
				ce.liberarReserva(total, item.getMaterial());
				item.setQuantidadeSolicitada(total);
				if(new SolicitacaoMedicamentoUnidadeItemRaiz(item).atualizar()){
					super.novaInstancia();
					throw new ExcecaoSolicitacaoItemInseridaDuasVezes();
				}else{
					throw new ExcecaoSolicitacaoMedicamentoItemNaoAdicionado();
				}
			}
		}
	}
	
	public boolean enviar(SolicitacaoMedicamentoUnidade smu) throws ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoReservaVazia, ExcecaoSolicitacaoMedicamentoItemNaoAdicionado, ExcecaoProfissionalLogado {
		try {
			verificaItemInseridoDuasVezes(smu.getItens());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setDataInsercao(new Date());
			getInstancia().setSolicitacaoMedicamentoUnidade(smu);
			getInstancia().setStatusItem(TipoStatusSolicitacaoItemEnum.P);
			if(super.enviar()){
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoSolicitacaoItemInseridaDuasVezes e) {
			e.printStackTrace();
		}
		return false;
	}
}
