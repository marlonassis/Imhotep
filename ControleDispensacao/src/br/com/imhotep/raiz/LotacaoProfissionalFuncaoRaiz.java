package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.FuncaoConsultaRaiz;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalFuncao;
import br.com.imhotep.entidade.Funcao;
import br.com.imhotep.entidade.HistoricoChefia;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.LotacaoProfissionalFuncao;
import br.com.imhotep.enums.TipoHistoricoChefiaEnum;
import br.com.imhotep.excecoes.ExcecaoLotacaoFuncaoSemPortariaData;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LotacaoProfissionalFuncaoRaiz  extends PadraoRaiz<LotacaoProfissionalFuncao>{
	private EstruturaOrganizacional estruturaOrganizacional;
	private List<LotacaoProfissionalFuncao> lotacoesFuncao = new ArrayList<LotacaoProfissionalFuncao>();
	private List<LotacaoProfissional> profissionaisNaoLotados = new ArrayList<LotacaoProfissional>();
	private List<HistoricoChefia> historicoChefiaList = new ArrayList<HistoricoChefia>();
	private boolean exibirDialogExoneracao;
	
	public void ocultarDialogExoneracao(){
		setExibirDialogExoneracao(false);
	}
	
	public List<EstruturaOrganizacionalFuncao> getFuncoesEstruturaList() {
		List<EstruturaOrganizacionalFuncao> list = new ArrayList<EstruturaOrganizacionalFuncao>();
		if(getEstruturaOrganizacional() != null){
			int id = getEstruturaOrganizacional().getIdEstruturaOrganizacional();
			String hql = "select o from EstruturaOrganizacionalFuncao o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id + 
							" order by to_ascii(lower(o.funcao.nome))";
			list = new ArrayList<EstruturaOrganizacionalFuncao>(new ConsultaGeral<EstruturaOrganizacionalFuncao>().consulta(new StringBuilder(hql), null));
		}
		return list;
	}
	
	private void carregarNaoLotados(EstruturaOrganizacional estruturaOrganizacional) {
		int id = estruturaOrganizacional.getIdEstruturaOrganizacional();
		String hql = "select o from LotacaoProfissional o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id
						+ " and o.profissional.idProfissional not in ( "
						+ " select a.lotacaoProfissional.profissional.idProfissional from LotacaoProfissionalFuncao a "
						+ " where a.lotacaoProfissional.estruturaOrganizacional.idEstruturaOrganizacional = " + id
						+ ") order by to_ascii(lower(o.profissional.nome))";
		Collection<LotacaoProfissional> consulta = new ConsultaGeral<LotacaoProfissional>(new StringBuilder(hql)).consulta();
		setProfissionaisNaoLotados(new ArrayList<LotacaoProfissional>(consulta));
	}
	
	public List<Funcao> getFuncoesLotadas(){
		return new FuncaoConsultaRaiz().getFuncoesEstruturaOrganizacional(getEstruturaOrganizacional());
	}
	
	public void carregarDadosLotacaoFuncao(EstruturaOrganizacional estruturaOrganizacional){
		setEstruturaOrganizacional(estruturaOrganizacional);
		setEstruturaOrganizacional(getEstruturaOrganizacional());
		carregarFuncoesLotadas(getEstruturaOrganizacional());
		carregarNaoLotados(getEstruturaOrganizacional());
		carregarHistoricoChefia(getEstruturaOrganizacional());
	}
	
	private void carregarHistoricoChefia(EstruturaOrganizacional estruturaOrganizacional) {
		int id = estruturaOrganizacional.getIdEstruturaOrganizacional();
		String hql = "select o from HistoricoChefia o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id
						+ " order by o.dataCadastro desc";
		Collection<HistoricoChefia> consulta = new ConsultaGeral<HistoricoChefia>(new StringBuilder(hql)).consulta();
		setHistoricoChefiaList(new ArrayList<HistoricoChefia>(consulta));
	}
	
	public void exonerarChefe(){
		try{
			HistoricoChefia historicoChefia = montarHistoricoChefia(TipoHistoricoChefiaEnum.EX);
			int hash = getInstancia().hashCode();
			PadraoFluxoTemp.limparFluxo();
			PadraoFluxoTemp.getObjetoDeletar().put("LotaProfisFun-"+hash, getInstancia());
			PadraoFluxoTemp.getObjetoSalvar().put("historicoChefia-"+historicoChefia.hashCode(), historicoChefia);
			PadraoFluxoTemp.finalizarFluxo();
			carregarHistoricoChefia(getEstruturaOrganizacional());
			carregarNaoLotados(getEstruturaOrganizacional());
			carregarFuncoesLotadas(getEstruturaOrganizacional());
			ocultarDialogExoneracao();
			super.setInstanciaDelecao(null);
			super.novaInstancia();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
		PadraoFluxoTemp.limparFluxo();
		setExibirDialogExoneracao(false);
	}
	
	public void apagarLotacaoFuncao(){
		boolean chefia = getInstanciaDelecao().getEstruturaOrganizacionalFuncao().getFuncao().getChefia();
		if(chefia){
			setInstancia(getInstanciaDelecao());
			setExibirDialogExoneracao(true);
			getInstanciaDelecao().setDataPosse(null);
			getInstanciaDelecao().setPortaria(null);
		}else{
			if(super.apagarInstancia()){
				carregarFuncoesLotadas(getEstruturaOrganizacional());
				carregarNaoLotados(getEstruturaOrganizacional());
			}
		}
	}
	
	public void lotarProfissionalFuncao(){
		try{
			boolean chefia = getInstancia().getEstruturaOrganizacionalFuncao().getFuncao().getChefia();
			if(chefia){
				verificarLotacaoFuncaoSemPortariaDataPosse();
				HistoricoChefia historicoChefia = montarHistoricoChefia(TipoHistoricoChefiaEnum.PO);
				int hash = getInstancia().hashCode();
				PadraoFluxoTemp.limparFluxo();
				PadraoFluxoTemp.getObjetoSalvar().put("LotaProfisFun-"+hash, getInstancia());
				PadraoFluxoTemp.getObjetoSalvar().put("historicoChefia-"+historicoChefia.hashCode(), historicoChefia);
				PadraoFluxoTemp.finalizarFluxo();
				carregarHistoricoChefia(getEstruturaOrganizacional());
				super.novaInstancia();
			}else{
				super.enviar();
			}
			carregarFuncoesLotadas(getEstruturaOrganizacional());
			carregarNaoLotados(getEstruturaOrganizacional());
		}catch(ExcecaoLotacaoFuncaoSemPortariaData e){
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
		PadraoFluxoTemp.limparFluxo();
		super.novaInstancia();
	}

	private HistoricoChefia montarHistoricoChefia(TipoHistoricoChefiaEnum tipo) throws ExcecaoProfissionalLogado {
		HistoricoChefia historicoChefia = new HistoricoChefia();
		historicoChefia.setDataCadastro(new Date());
		historicoChefia.setDataPortaria(getInstancia().getDataPosse());
		historicoChefia.setEstruturaOrganizacional(getEstruturaOrganizacional());
		historicoChefia.setPortaria(getInstancia().getPortaria());
		historicoChefia.setProfissionalCadastro(Autenticador.getProfissionalLogado());
		historicoChefia.setTipo(tipo);
		historicoChefia.setProfissionalChefe(getInstancia().getLotacaoProfissional().getProfissional());
		return historicoChefia;
	}

	private void verificarLotacaoFuncaoSemPortariaDataPosse() throws ExcecaoLotacaoFuncaoSemPortariaData {
		if(getInstancia().getDataPosse() == null || getInstancia().getPortaria() == null){
			throw new ExcecaoLotacaoFuncaoSemPortariaData();
		}
	}
	
	private void carregarFuncoesLotadas(EstruturaOrganizacional estruturaOrganizacional){
		int id = estruturaOrganizacional.getIdEstruturaOrganizacional();
		String hql = "select o from LotacaoProfissionalFuncao o where o.lotacaoProfissional.estruturaOrganizacional.idEstruturaOrganizacional = " + id
						+ " order by to_ascii(lower(o.lotacaoProfissional.profissional.nome))";
		List<LotacaoProfissionalFuncao> busca = super.getBusca(hql);
		setLotacoesFuncao(busca);
	}
	
	public List<LotacaoProfissionalFuncao> getLotacoesFuncao() {
		return lotacoesFuncao;
	}

	public void setLotacoesFuncao(List<LotacaoProfissionalFuncao> lotacoesFuncao) {
		this.lotacoesFuncao = lotacoesFuncao;
	}

	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}

	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	public List<LotacaoProfissional> getProfissionaisNaoLotados() {
		return profissionaisNaoLotados;
	}

	public void setProfissionaisNaoLotados(List<LotacaoProfissional> profissionaisNaoLotados) {
		this.profissionaisNaoLotados = profissionaisNaoLotados;
	}

	public List<HistoricoChefia> getHistoricoChefiaList() {
		return historicoChefiaList;
	}

	public void setHistoricoChefiaList(List<HistoricoChefia> historicoChefiaList) {
		this.historicoChefiaList = historicoChefiaList;
	}

	public boolean isExibirDialogExoneracao() {
		return exibirDialogExoneracao;
	}

	public void setExibirDialogExoneracao(boolean exibirDialogExoneracao) {
		this.exibirDialogExoneracao = exibirDialogExoneracao;
	}
	
}