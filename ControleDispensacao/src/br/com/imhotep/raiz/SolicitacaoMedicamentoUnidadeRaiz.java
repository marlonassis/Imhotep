package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilities;
import br.com.imhotep.consulta.raiz.SolicitacaoMedicamentoUnidadeConsultaRaiz;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.extra.SolicitacaoMedicamento;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoUnidadeAtual;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeRaiz extends PadraoHome<SolicitacaoMedicamentoUnidade>{
	
	public void setInstancia2(Object linha){
		SolicitacaoMedicamentoUnidade obj = new SolicitacaoMedicamentoUnidadeConsultaRaiz().solicitacaoId(((SolicitacaoMedicamento)linha).getIdSolicitacaoMedicamentoUnidade());
		setInstancia(obj);
	}
	
	public boolean removerItem(SolicitacaoMedicamentoUnidadeItem item){
		if(getInstancia().getItens().remove(item)){
			return super.atualizar();
		}
		return false;
	}
	
	public static SolicitacaoMedicamentoUnidadeRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMedicamentoUnidadeRaiz) Utilities.procuraInstancia(SolicitacaoMedicamentoUnidadeRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.P);
		getInstancia().setDataInsercao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		try {
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setUnidadeProfissionalInsercao(Autenticador.getUnidadeProfissional());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.A);
			return super.enviar();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoUnidadeAtual e) {
			e.printStackTrace();
		}
		return false;
	}

}
