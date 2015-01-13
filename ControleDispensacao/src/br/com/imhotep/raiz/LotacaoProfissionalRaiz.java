package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.FuncaoConsultaRaiz;
import br.com.imhotep.consulta.raiz.LotacaoProfissionalFuncaoConsultaRaiz;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.AlteracaoLocacaoLog;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalFuncao;
import br.com.imhotep.entidade.Funcao;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.LotacaoProfissionalFuncao;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.enums.TipoCrudEnum;
import br.com.imhotep.enums.TipoLotacaoProfissionalEnum;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LotacaoProfissionalRaiz extends PadraoRaiz<LotacaoProfissional>{
	private EstruturaOrganizacional estruturaOrganizacional;
	private List<LotacaoProfissional> profissionaisLotados = new ArrayList<LotacaoProfissional>();
	private List<LotacaoProfissionalFuncao> lotacoesProfissional = new ArrayList<LotacaoProfissionalFuncao>();
	private Profissional profissionalLotado;
	private EstruturaOrganizacional estruturaLotado;
	private EstruturaOrganizacionalFuncao funcaoLotada;
	private LotacaoProfissionalFuncao funcaoDelecao;
	private boolean exibirDialogLotacao;
	
	public List<Funcao> getFuncoesNaoEscolhidas(){
		return new FuncaoConsultaRaiz().getFuncoesExcetoEstruturaOrganizacional(getEstruturaOrganizacional());
	}
	
	public void lotarProfissionalFuncao(){
		try {
			PadraoFluxoTemp.limparFluxo();
			LotacaoProfissional lp = new LotacaoProfissional();
			lp.setEstruturaOrganizacional(getEstruturaLotado());
			lp.setProfissional(getProfissionalLotado());
			lp.setTipoLotacao(TipoLotacaoProfissionalEnum.E);
			PadraoFluxoTemp.getObjetoSalvar().put("LotacaoProfissional-" + lp.hashCode(), lp);
			LotacaoProfissionalFuncao lpf = new LotacaoProfissionalFuncao();
			lpf.setEstruturaOrganizacionalFuncao(getFuncaoLotada());
			lpf.setLotacaoProfissional(lp);
			PadraoFluxoTemp.getObjetoSalvar().put("LotacaoProfissionalFuncao-" + lpf.hashCode(), lpf);
			PadraoFluxoTemp.finalizarFluxo();
			PadraoFluxoTemp.limparFluxo();
			gerarLog(TipoCrudEnum.I, null, getEstruturaLotado(), getProfissionalLotado());
			limparDadosChangeProfissionalLotado();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean apagarInstancia() {
		LotacaoProfissional lp = getInstanciaDelecao();
		boolean sucesso = super.apagarInstancia();
		if(sucesso){
			atualizarLotacaoProfissional();
			gerarLog(TipoCrudEnum.D, null, lp.getEstruturaOrganizacional(), lp.getProfissional());
		}
		setFuncaoDelecao(null);
		return sucesso;
	}
	
	public boolean apagarFuncaoLotada() {
		LotacaoProfissionalFuncao lp = getFuncaoDelecao();
		boolean sucesso = super.apagarGenerico(getFuncaoDelecao().getLotacaoProfissional());
		if(sucesso){
			atualizarLotacaoProfissional();
			gerarLog(TipoCrudEnum.D, null, lp.getEstruturaOrganizacionalFuncao().getEstruturaOrganizacional(), lp.getLotacaoProfissional().getProfissional());
		}
		setFuncaoDelecao(null);
		return sucesso;
	}
	
	private void atualizarLotacaoProfissional(){
		lotacoesProfissional = new LotacaoProfissionalFuncaoConsultaRaiz().lotacoesProfissional(getProfissionalLotado());
	}
	
	public void limparDadosChangeProfissionalLotado(){
		setEstruturaLotado(null);
		setFuncaoLotada(null);
		atualizarLotacaoProfissional();
	}
	
	public List<EstruturaOrganizacionalFuncao> getFuncoesEstrutura(){
		return new FuncaoConsultaRaiz().funcoesEstruturaExcetoFuncaoProfissionalExcetoChefia(getEstruturaLotado(), getProfissionalLotado());
	}
	
	public void lotarProfissionalSetor(){
		getInstancia().setEstruturaOrganizacional(getEstruturaOrganizacional());
		if(super.enviar()){
			gerarLog(TipoCrudEnum.I, null, getEstruturaOrganizacional(), getInstancia().getProfissional());
			carregarLotados(getEstruturaOrganizacional());
			carregarProfissionaisFuncoesLotadas(getEstruturaOrganizacional());
			super.novaInstancia();
		}
	}

	private void gerarLog(TipoCrudEnum tipo, String justificativa, EstruturaOrganizacional eo, Profissional profissional) {
		AlteracaoLocacaoLogRaiz allr = new AlteracaoLocacaoLogRaiz();
		AlteracaoLocacaoLog log = allr.montarLog(eo.getNome(), justificativa , profissional, tipo);
		allr.setInstancia(log);
		allr.enviar();
	}
	
	private void carregarProfissionaisFuncoesLotadas(EstruturaOrganizacional estruturaOrganizacional) {
		try {
			LotacaoProfissionalFuncaoRaiz lotacaoFuncao = 
					(LotacaoProfissionalFuncaoRaiz) new ControleInstancia().procuraInstancia(LotacaoProfissionalFuncaoRaiz.class);
			lotacaoFuncao.carregarDadosLotacaoFuncao(estruturaOrganizacional);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void apagarRegistroTabela(){
		EstruturaOrganizacional eo = getInstanciaDelecao().getEstruturaOrganizacional();
		Profissional profissional = getInstanciaDelecao().getProfissional();
		if(super.apagarInstancia()){
			gerarLog(TipoCrudEnum.D, null, eo, profissional);
			carregarLotados(getEstruturaOrganizacional());
		}
	}
	
	private void carregarLotados(EstruturaOrganizacional estruturaOrganizacional) {
		int id = estruturaOrganizacional.getIdEstruturaOrganizacional();
		String hql = "select o from LotacaoProfissional o where o.estruturaOrganizacional.idEstruturaOrganizacional = "+id
						+" order by to_ascii(lower(o.profissional.nome))";
		setProfissionaisLotados(super.getBusca(hql));
	}
	
	public void exibirDialogLotacao(){
		setExibirDialogLotacao(true);
		carregarLotados(getEstruturaOrganizacional());
	}
	
	public void ocultarDialogLotacao(){
		setExibirDialogLotacao(false);
		super.novaInstancia();
		setEstruturaOrganizacional(new EstruturaOrganizacional());
	}
	
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}

	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	public boolean isExibirDialogLotacao() {
		return exibirDialogLotacao;
	}

	public void setExibirDialogLotacao(boolean exibirDialogLotacao) {
		this.exibirDialogLotacao = exibirDialogLotacao;
	}

	public List<LotacaoProfissional> getProfissionaisLotados() {
		return profissionaisLotados;
	}

	public void setProfissionaisLotados(List<LotacaoProfissional> profissionaisLotados) {
		this.profissionaisLotados = profissionaisLotados;
	}

	public Profissional getProfissionalLotado() {
		return profissionalLotado;
	}

	public void setProfissionalLotado(Profissional profissionalLotado) {
		this.profissionalLotado = profissionalLotado;
	}

	public EstruturaOrganizacional getEstruturaLotado() {
		return estruturaLotado;
	}

	public void setEstruturaLotado(EstruturaOrganizacional estruturaLotado) {
		this.estruturaLotado = estruturaLotado;
	}

	public List<LotacaoProfissionalFuncao> getLotacoesProfissional() {
		return lotacoesProfissional;
	}

	public void setLotacoesProfissional(List<LotacaoProfissionalFuncao> lotacoesProfissional) {
		this.lotacoesProfissional = lotacoesProfissional;
	}

	public EstruturaOrganizacionalFuncao getFuncaoLotada() {
		return funcaoLotada;
	}

	public void setFuncaoLotada(EstruturaOrganizacionalFuncao funcaoLotada) {
		this.funcaoLotada = funcaoLotada;
	}

	public LotacaoProfissionalFuncao getFuncaoDelecao() {
		return funcaoDelecao;
	}

	public void setFuncaoDelecao(LotacaoProfissionalFuncao funcaoDelecao) {
		this.funcaoDelecao = funcaoDelecao;
	}

}
