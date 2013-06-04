package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.SolicitacaoMedicamentoUnidadeConsultaRaiz;
import br.com.imhotep.consulta.raiz.SolicitacaoMedicamentoUnidadeItemConsultaRaiz;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.extra.SolicitacaoMedicamento;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoDispensacaoSolicitacaoItemPendente;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoProfissionalReceptorNaoInformado;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoRecusadaSemJustificativa;
import br.com.imhotep.excecoes.ExcecaoUnidadeAtual;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeRaiz extends PadraoHome<SolicitacaoMedicamentoUnidade>{
	//essas variáveis são alteradas na classe SolicitacaoMedicamentoUnidadeConsultaRaiz.java
	private Long qtdSolicitacoesPendentes = 0L;
	private List<SolicitacaoMedicamentoUnidade> solicitacoesPendentes = new ArrayList<SolicitacaoMedicamentoUnidade>();
	/////////////
	private Boolean solicitarProfissionalRecepcao;
	private Boolean solicitarJustificativaRecusaSolicitacao;
	private Boolean iniciarDispensacao;
	private String justificativaRecusa;
	//TODO remover a dispensacao item da sessao e criar uma varivel de solicitacao item aqui dentro
	
	public void iniciarDispensacao(){
		setIniciarDispensacao(true);
	}
	
	public void cancelarRecusa(){
		setSolicitarJustificativaRecusaSolicitacao(false);
		setJustificativaRecusa(null);
		super.novaInstancia();
	}
	
	public void recusarSolicitacao(){
		if(getJustificativaRecusa() != null && !getJustificativaRecusa().isEmpty()){
			//TODO criar fluxo
			getInstancia().setJustificativa(getJustificativaRecusa());
			try {
				Date dataAtual = new Date();
				for(SolicitacaoMedicamentoUnidadeItem item : getInstancia().getItens()){
						item.setStatusItem(TipoStatusSolicitacaoItemEnum.R);
						item.setJustificativa("Solicitação Recusada");
						item.setProfissionalLiberacao(Autenticador.getProfissionalLogado());
						item.setDataLiberacao(dataAtual);
						item.setUnidadeProfissionalLiberacao(Autenticador.getUnidadeProfissional());
						SolicitacaoMedicamentoUnidadeItemRaiz smuri = new SolicitacaoMedicamentoUnidadeItemRaiz(item);
						smuri.setInstancia(item);
						smuri.setExibeMensagemAtualizacao(false);
						smuri.atualizar();
				}
				
				getInstancia().setDataDispensacao(dataAtual);
				getInstancia().setProfissionalDispensacao(Autenticador.getProfissionalLogado());
				getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.R);
				if(super.atualizar()){
					setSolicitarJustificativaRecusaSolicitacao(false);
					setJustificativaRecusa(null);
					getSolicitacoesPendentes().remove(getInstancia());
					super.novaInstancia();
				}
				
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			} catch (ExcecaoUnidadeAtual e) {
				e.printStackTrace();
			}
		}else{
			try {
				throw new ExcecaoSolicitacaoRecusadaSemJustificativa();
			} catch (ExcecaoSolicitacaoRecusadaSemJustificativa e) {
				e.printStackTrace();
			}
		}
	}
	
	public void preRecusaSolicitacao(){
		getInstancia().setJustificativa(null);
		setSolicitarJustificativaRecusaSolicitacao(true);
	}
	
	public void fecharTelaDispensacaoAtual(){
		SolicitacaoMedicamentoUnidadeItemRaiz.getInstanciaAtual().novaInstancia();
		setIniciarDispensacao(false);
		novaInstancia();
	}
	
	public void fecharConfirmacaoReceptor(){
		setSolicitarProfissionalRecepcao(false);
	}
	
	public void preFechamentoDispensacao(){
		try {
			SolicitacaoMedicamentoUnidadeItemRaiz.getInstanciaAtual().verificaItensPendentes();
			setSolicitarProfissionalRecepcao(true);
		} catch (ExcecaoDispensacaoSolicitacaoItemPendente e) {
			e.printStackTrace();
		}
	}
	
	public void fecharDispensacao(){
		try {
			if(getInstancia().getProfissionalReceptor() == null)
				throw new ExcecaoProfissionalReceptorNaoInformado();
			setSolicitarProfissionalRecepcao(false);
			SolicitacaoMedicamentoUnidadeItemRaiz.getInstanciaAtual().fecharSolicitacaoItens();
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.D);
			getInstancia().setDataDispensacao(new Date());
			getInstancia().setProfissionalDispensacao(Autenticador.getProfissionalLogado());
			super.atualizar();
			setIniciarDispensacao(false);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoDispensacaoSolicitacaoItemPendente e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalReceptorNaoInformado e) {
			e.printStackTrace();
		}
	}
	
	public void setInstancia2(SolicitacaoMedicamento linha){
		SolicitacaoMedicamentoUnidade obj = new SolicitacaoMedicamentoUnidadeConsultaRaiz().solicitacaoId(linha.getIdSolicitacaoMedicamentoUnidade());
		setInstancia(obj);
		SolicitacaoMedicamentoUnidadeItemRaiz.getInstanciaAtual().setItens(new SolicitacaoMedicamentoUnidadeItemConsultaRaiz().getItensSolicitacao());
	}
	
	@Override
	public void setInstancia(SolicitacaoMedicamentoUnidade instancia) {
		super.setInstancia(instancia);
		SolicitacaoMedicamentoUnidadeItemRaiz.getInstanciaAtual().setItens(new SolicitacaoMedicamentoUnidadeItemConsultaRaiz().getItensSolicitacao());
	}
	
	public boolean removerItem(SolicitacaoMedicamentoUnidadeItem item){
		if(getInstancia().getItens().remove(item)){
			return super.atualizar();
		}
		return false;
	}
	
	public static SolicitacaoMedicamentoUnidadeRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMedicamentoUnidadeRaiz) Utilitarios.procuraInstancia(SolicitacaoMedicamentoUnidadeRaiz.class);
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
		if(super.atualizar()){
			novaInstancia();
			return true;
		}
		return false;
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

	public Long getQtdSolicitacoesPendentes() {
		return qtdSolicitacoesPendentes;
	}

	public void setQtdSolicitacoesPendentes(Long qtdSolicitacoesPendentes) {
		this.qtdSolicitacoesPendentes = qtdSolicitacoesPendentes;
	}

	public List<SolicitacaoMedicamentoUnidade> getSolicitacoesPendentes() {
		return solicitacoesPendentes;
	}

	public void setSolicitacoesPendentes(List<SolicitacaoMedicamentoUnidade> solicitacoesPendentes) {
		this.solicitacoesPendentes = solicitacoesPendentes;
	}

	public Boolean getSolicitarProfissionalRecepcao() {
		return solicitarProfissionalRecepcao;
	}

	public void setSolicitarProfissionalRecepcao(
			Boolean solicitarProfissionalRecepcao) {
		this.solicitarProfissionalRecepcao = solicitarProfissionalRecepcao;
	}

	public Boolean getSolicitarJustificativaRecusaSolicitacao() {
		return solicitarJustificativaRecusaSolicitacao;
	}

	public void setSolicitarJustificativaRecusaSolicitacao(
			Boolean solicitarJustificativaRecusaSolicitacao) {
		this.solicitarJustificativaRecusaSolicitacao = solicitarJustificativaRecusaSolicitacao;
	}

	public Boolean getIniciarDispensacao() {
		return iniciarDispensacao;
	}

	public void setIniciarDispensacao(Boolean iniciarDispensacao) {
		this.iniciarDispensacao = iniciarDispensacao;
	}

	public String getJustificativaRecusa() {
		return justificativaRecusa;
	}

	public void setJustificativaRecusa(String justificativaRecusa) {
		this.justificativaRecusa = justificativaRecusa;
	}

}
