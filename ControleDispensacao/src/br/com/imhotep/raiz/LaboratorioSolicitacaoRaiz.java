package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.LaboratorioExame;
import br.com.imhotep.entidade.LaboratorioSolicitacao;
import br.com.imhotep.entidade.LaboratorioSolicitacaoItem;
import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.entidade.PainelAviso;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameItemEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoExameItemSemJustificativa;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoExameSemItens;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoExameSemPaciente;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LaboratorioSolicitacaoRaiz extends PadraoRaiz<LaboratorioSolicitacao>{
	
	private LaboratorioExame[] examesSelecionados;
	private List<LaboratorioExame> exames;
	private List<LaboratorioSolicitacaoItem> itensSolicitados = new ArrayList<LaboratorioSolicitacaoItem>();
	private LaboratorioSolicitacaoItem item;
	private boolean exibirDialogJustificativaItem;
	private Paciente paciente;
	private Unidade unidade;
	private String valorPesquisaExame;
	
	public LaboratorioSolicitacaoRaiz(){
		atualizarListaExames();
	}
	
	public void removerItemSolicitado(){
		for(LaboratorioSolicitacaoItem itemSoli : getItensSolicitados()){
			if(itemSoli.equals(getItem())){
				getItensSolicitados().remove(itemSoli);
				getExames().add(itemSoli.getLaboratorioExame());
			}
		}
	}
	
	public void atualizarListaExames(){
		Collection<LaboratorioExame> consulta = new ConsultaGeral<LaboratorioExame>(new StringBuilder("select o from LaboratorioExame o where o.bloqueado is false")).consulta();
		setExames(new ArrayList<LaboratorioExame>(consulta));
	}
	
	public void adicionarExames(){
		for(LaboratorioExame exame : examesSelecionados){
			getExames().remove(exame);
			LaboratorioSolicitacaoItem itemSolicitado = new LaboratorioSolicitacaoItem();
			itemSolicitado.setLaboratorioExame(exame);
			getItensSolicitados().add(itemSolicitado );
		}
	}
	
	public void pesquisarItens(){
		verificarItemNaoAdicionado();
	}

	private void verificarItemNaoAdicionado() {
		String[] itensPesquisados = getValorPesquisaExame().split(",");
		String itensNaoEncontrados = "";
		for(String item : itensPesquisados){
			Integer exameCodigo = Integer.valueOf(item.trim());
			LaboratorioExame exame = verificarItemNaoAdicionadoBuscaItem(exameCodigo);
			if(exame != null){
				getExames().remove(exame);
				LaboratorioSolicitacaoItem itemSolicitado = new LaboratorioSolicitacaoItem();
				itemSolicitado.setLaboratorioExame(exame);
				getItensSolicitados().add(itemSolicitado );
			}else{
				if(item.equals(itensPesquisados[itensPesquisados.length-1]))
					itensNaoEncontrados += item;
				else
					itensNaoEncontrados += item+", ";
			}
		}
		setValorPesquisaExame(itensNaoEncontrados);
	}
	
	private LaboratorioExame verificarItemNaoAdicionadoBuscaItem(Integer exameCodigo){ 
		for(LaboratorioExame exame : exames){
			if(exame.getCodigo().equals(exameCodigo)){
				return exame;
			}
		}
		return null;
	}
	
	@Override
	public void novaInstancia() {
		examesSelecionados = null;
		itensSolicitados = new ArrayList<LaboratorioSolicitacaoItem>();
		item = null;
		exibirDialogJustificativaItem = false;
		paciente = null;
		super.novaInstancia();
	}
	
	@Override
	public boolean enviar() {
		try {
			verificarSolicitacaoVazia();
			verificarSolicitacaoSemPaciente();
			verificarItensSemJustificativa();
			Date dataSolicitacao = new Date();
			PadraoFluxoTemp.limparFluxo();
			LaboratorioSolicitacao ls = new LaboratorioSolicitacao();
			ls.setDataSolicitacao(dataSolicitacao);
			ls.setPaciente(getPaciente());
			ls.setUnidade(getUnidade());
			ls.setProfissionalSolicitacao(Autenticador.getProfissionalLogado());
			ls.setStatusSolicitacao(TipoStatusSolicitacaoExameEnum.AA);
			ls.setPaciente(getPaciente());
			PadraoFluxoTemp.getObjetoSalvar().put("LaboratorioSolicitacao-"+ls.hashCode(), ls);
			for(LaboratorioSolicitacaoItem linha : getItensSolicitados()){
				linha.setProfissionalSolicitacao(Autenticador.getProfissionalLogado());
				linha.setStatusItem(TipoStatusSolicitacaoExameItemEnum.BA);
				linha.setLaboratorioSolicitacao(ls);
				PadraoFluxoTemp.getObjetoSalvar().put("LaboratorioSolicitacaoItem-"+linha.hashCode(), linha);
			}
			PadraoFluxoTemp.finalizarFluxo();
			ControlePainelAviso.getInstancia().setAvisosNaoMonitorado(new ArrayList<PainelAviso>());
			ControlePainelAviso.getInstancia().gerarAvisoRE(ls.getIdLaboratorioSolicitacao());
			novaInstancia();
			atualizarListaExames();
			return true;
		} catch (ExcecaoSolicitacaoExameItemSemJustificativa e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoExameSemPaciente e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoExameSemItens e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void verificarSolicitacaoVazia() throws ExcecaoSolicitacaoExameSemItens{
		if(getItensSolicitados() == null || getItensSolicitados().isEmpty())
			throw new ExcecaoSolicitacaoExameSemItens();
	}
	
	private void verificarSolicitacaoSemPaciente() throws ExcecaoSolicitacaoExameSemPaciente{
		if(getPaciente() == null)
			throw new ExcecaoSolicitacaoExameSemPaciente();
	}
	
	private void verificarItensSemJustificativa() throws ExcecaoSolicitacaoExameItemSemJustificativa{
		for(LaboratorioSolicitacaoItem item : getItensSolicitados()){
			if(item.getLaboratorioExame().getJustificativaObrigatoria() && 
					(item.getJustificativaSolicitacao() == null || item.getJustificativaSolicitacao().isEmpty()) ){
				throw new ExcecaoSolicitacaoExameItemSemJustificativa();
			}
		}
	}
	
	public void atualizarJustificativa(){
		ocultarDialogJustificativaItem();
	}
	
	public void exibirDialogJustificativaItem(){
		setExibirDialogJustificativaItem(true);
	}
	
	public void ocultarDialogJustificativaItem(){
		setExibirDialogJustificativaItem(false);
		setItem(null);
	}
	
	public List<LaboratorioSolicitacaoItem> getItensSolicitados() {
		return itensSolicitados;
	}

	public void setItensSolicitados(List<LaboratorioSolicitacaoItem> itensSolicitados) {
		this.itensSolicitados = itensSolicitados;
	}

	public LaboratorioSolicitacaoItem getItem() {
		return item;
	}

	public void setItem(LaboratorioSolicitacaoItem item) {
		this.item = item;
	}

	public boolean isExibirDialogJustificativaItem() {
		return exibirDialogJustificativaItem;
	}

	public void setExibirDialogJustificativaItem(
			boolean exibirDialogJustificativaItem) {
		this.exibirDialogJustificativaItem = exibirDialogJustificativaItem;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getValorPesquisaExame() {
		return valorPesquisaExame;
	}

	public void setValorPesquisaExame(String valorPesquisaExame) {
		this.valorPesquisaExame = valorPesquisaExame;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public LaboratorioExame[] getExamesSelecionados() {
		return examesSelecionados;
	}

	public void setExamesSelecionados(LaboratorioExame[] examesSelecionados) {
		this.examesSelecionados = examesSelecionados;
	}

	public List<LaboratorioExame> getExames() {
		return exames;
	}

	public void setExames(List<LaboratorioExame> exames) {
		this.exames = exames;
	}

}
