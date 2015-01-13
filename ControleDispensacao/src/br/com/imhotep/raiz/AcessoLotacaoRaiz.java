package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.TreeNode;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.FuncaoConsultaRaiz;
import br.com.imhotep.consulta.raiz.ProfissionalConsultaRaiz;
import br.com.imhotep.controle.ControleAcessoFuncaoLocal;
import br.com.imhotep.controle.ControleAcessoLotacaoLocal;
import br.com.imhotep.controle.ControleMenuLocal;
import br.com.imhotep.entidade.AcessoFuncaoPainel;
import br.com.imhotep.entidade.AcessoLotacao;
import br.com.imhotep.entidade.AcessoLotacaoPainel;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalFuncao;
import br.com.imhotep.entidade.EstruturaOrganizacionalPainel;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AcessoLotacaoRaiz extends PadraoRaiz<AcessoLotacao>{
	private EstruturaOrganizacional estruturaOrganizacional;
	private LotacaoProfissional lotacaoProfissional;
	private EstruturaOrganizacionalFuncao funcao;
	private EstruturaOrganizacionalPainel estruturaOrganizacionalPainel;
	private List<EstruturaOrganizacionalFuncao> funcoesLotadas = new ArrayList<EstruturaOrganizacionalFuncao>();
	private List<LotacaoProfissional> profissionaisLotados = new ArrayList<LotacaoProfissional>();
	private List<EstruturaOrganizacionalPainel> paineisLiberados = new ArrayList<EstruturaOrganizacionalPainel>();
	private TreeNode root;
	private TreeNode[] selectedNode;
	
	public List<EstruturaOrganizacionalPainel> getPaineisLotados(){
		if(getEstruturaOrganizacional() != null && (getLotacaoProfissional() != null || getFuncao() != null)){
			int id = getEstruturaOrganizacional().getIdEstruturaOrganizacional();
			String hql = "select o from EstruturaOrganizacionalPainel o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id;
			return new ArrayList<EstruturaOrganizacionalPainel>(new ConsultaGeral<EstruturaOrganizacionalPainel>(new StringBuilder(hql)).consulta());
		}
		return new ArrayList<EstruturaOrganizacionalPainel>();
	}
	
	public void apagarPainel(){
		if(getLotacaoProfissional() != null){
			String sql = "delete from controle.tb_acesso_lotacao_painel where id_lotacao_profissional = " + getLotacaoProfissional().getIdLotacaoProfissional()
							+ " and id_estrutura_organizacional_painel = " + getEstruturaOrganizacionalPainel().getIdEstruturaOrganizacionalPainel();
			new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).executarCUD(sql);
		}else{
			if(getFuncao() != null){
				String sql = "delete from controle.tb_acesso_funcao_painel where id_estrutura_organizacional_funcao = " + getFuncao().getIdEstruturaOrganizacionalFuncao()
								+ " and id_estrutura_organizacional_painel = " + getEstruturaOrganizacionalPainel().getIdEstruturaOrganizacionalPainel();
				new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).executarCUD(sql);
			}
		}
		setEstruturaOrganizacionalPainel(new EstruturaOrganizacionalPainel());
		carregarPaineis();
	}
	
	public void cadastrarPainel(){
		if(getLotacaoProfissional() != null){
			AcessoLotacaoPainel acessoLotacaoPainel = new AcessoLotacaoPainel();
			acessoLotacaoPainel.setEstruturaOrganizacionalPainel(getEstruturaOrganizacionalPainel());
			acessoLotacaoPainel.setLotacaoProfissional(getLotacaoProfissional());
			if(super.enviarGenerico(acessoLotacaoPainel))
				carregarPaineis();
		}else{
			if(getFuncao() != null){
				AcessoFuncaoPainel acessoFuncaoPainel = new AcessoFuncaoPainel();
				acessoFuncaoPainel.setEstruturaOrganizacionalPainel(getEstruturaOrganizacionalPainel());
				acessoFuncaoPainel.setEstruturaOrganizacionalFuncao(getFuncao());
				if(super.enviarGenerico(acessoFuncaoPainel))
					carregarPaineis();
			}
		}
		setEstruturaOrganizacionalPainel(new EstruturaOrganizacionalPainel());
	}
	
	private void carregarPaineis(){
		setPaineisLiberados(new ArrayList<EstruturaOrganizacionalPainel>());
		if(getLotacaoProfissional() != null){
			int id = getLotacaoProfissional().getIdLotacaoProfissional();
			String hql = "select o.estruturaOrganizacionalPainel from AcessoLotacaoPainel o "
					+ "where o.lotacaoProfissional.idLotacaoProfissional = " + id  
					+ " order by to_ascii(lower(o.estruturaOrganizacionalPainel.painel.descricao))";
			Collection<EstruturaOrganizacionalPainel> consulta = new ConsultaGeral<EstruturaOrganizacionalPainel>().consulta(new StringBuilder(hql), null);
			setPaineisLiberados(new ArrayList<EstruturaOrganizacionalPainel>(consulta));
		}else{
			if(getFuncao() != null){
				int id = getFuncao().getIdEstruturaOrganizacionalFuncao();
				String hql = "select o.estruturaOrganizacionalPainel from AcessoFuncaoPainel o "
						+ "where o.estruturaOrganizacionalFuncao.idEstruturaOrganizacionalFuncao = " + id  
						+ " order by to_ascii(lower(o.estruturaOrganizacionalPainel.painel.descricao))";
				Collection<EstruturaOrganizacionalPainel> consulta = new ConsultaGeral<EstruturaOrganizacionalPainel>().consulta(new StringBuilder(hql), null);
				setPaineisLiberados(new ArrayList<EstruturaOrganizacionalPainel>(consulta));
			}
		}
	}
	
	public void alterarAcessoMenu(){
		if(getLotacaoProfissional() != null){
			new ControleAcessoLotacaoLocal().alterarStatusAcesso(getSelectedNode(), getLotacaoProfissional());
		}else{
			if(getFuncao() != null){
				new ControleAcessoFuncaoLocal().alterarStatusAcesso(getSelectedNode(), getFuncao());
			}
		}
	}
	
	public void carregarMenuPainel(){
		carregarMenuAutorizado();
		carregarPaineis();
	}
	
	private void carregarMenuAutorizado(){
		setSelectedNode(null);
		setRoot(null);
		
		if(getLotacaoProfissional() != null){
			atualizarMenuSetor();
			new ControleAcessoLotacaoLocal().carregarMenuAutorizadoNode(getLotacaoProfissional(), getEstruturaOrganizacional(), getRoot());
		}else{
			if(getFuncao() != null){
				atualizarMenuSetor();
				new ControleAcessoFuncaoLocal().carregarMenuAutorizadoNode(getFuncao(), getEstruturaOrganizacional(), getRoot());
			}
		}
	}
	
	public void atualizarDados(){
		carregarDadosEstrutura();
		setFuncao(null);
		setLotacaoProfissional(null);
		setRoot(null);
		setSelectedNode(null);
		setSelectedNode(null);
		setPaineisLiberados(new ArrayList<EstruturaOrganizacionalPainel>());
	}

	private void carregarDadosEstrutura() {
		if(getEstruturaOrganizacional() != null){
			atualizarListaLotados();
			atualizarListaFuncoesLotadas();
		}
	}

	private void atualizarMenuSetor() {
		setRoot(new ControleMenuLocal().carregarMenu(getEstruturaOrganizacional()));
	}

	private void atualizarListaLotados() {
		setProfissionaisLotados(new ProfissionalConsultaRaiz().carregarLotadosEstrutura(getEstruturaOrganizacional()));
	}
	
	private void atualizarListaFuncoesLotadas() {
		setFuncoesLotadas(new FuncaoConsultaRaiz().funcoesLotadas(getEstruturaOrganizacional()));
	}
	
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}

	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	public LotacaoProfissional getLotacaoProfissional() {
		return lotacaoProfissional;
	}

	public void setLotacaoProfissional(LotacaoProfissional lotacaoProfissional) {
		this.lotacaoProfissional = lotacaoProfissional;
	}

	public EstruturaOrganizacionalFuncao getFuncao() {
		return funcao;
	}

	public void setFuncao(EstruturaOrganizacionalFuncao funcao) {
		this.funcao = funcao;
	}

	public List<LotacaoProfissional> getProfissionaisLotados() {
		return profissionaisLotados;
	}

	public void setProfissionaisLotados(List<LotacaoProfissional> profissionaisLotados) {
		this.profissionaisLotados = profissionaisLotados;
	}

	public List<EstruturaOrganizacionalFuncao> getFuncoesLotadas() {
		return funcoesLotadas;
	}

	public void setFuncoesLotadas(List<EstruturaOrganizacionalFuncao> funcoesLotadas) {
		this.funcoesLotadas = funcoesLotadas;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode[] getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode[] selectedNode) {
		this.selectedNode = selectedNode;
	}

	public EstruturaOrganizacionalPainel getEstruturaOrganizacionalPainel() {
		return estruturaOrganizacionalPainel;
	}

	public void setEstruturaOrganizacionalPainel(
			EstruturaOrganizacionalPainel estruturaOrganizacionalPainel) {
		this.estruturaOrganizacionalPainel = estruturaOrganizacionalPainel;
	}

	public List<EstruturaOrganizacionalPainel> getPaineisLiberados() {
		return paineisLiberados;
	}

	public void setPaineisLiberados(List<EstruturaOrganizacionalPainel> paineisLiberados) {
		this.paineisLiberados = paineisLiberados;
	}

}
