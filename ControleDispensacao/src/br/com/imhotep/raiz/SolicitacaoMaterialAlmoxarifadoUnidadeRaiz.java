	package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.AlmoxarifadoUnidadeCotaConsultaRaiz;
import br.com.imhotep.consulta.raiz.DispensacaoSimplesAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.raiz.MaterialAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.raiz.SolicitacaoMaterialAlmoxarifadoUnidadeConsultaRaiz;
import br.com.imhotep.consulta.raiz.SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.DispensacaoSimplesAlmoxarifado;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.PainelAviso;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidade;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidadeItem;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoDispensacao;
import br.com.imhotep.entidade.extra.SolicitacaoMaterialAlmoxarifado;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueAlmoxarifadoVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoMovimentoLivroNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoDispensada;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoEmLock;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoRecusadaSemJustificativa;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class SolicitacaoMaterialAlmoxarifadoUnidadeRaiz extends PadraoRaiz<SolicitacaoMaterialAlmoxarifadoUnidade>{
	
	private Boolean solicitacaoAberta = false;
	private Boolean exibirModalAlterarQuantidadeItem = false;
	private Boolean exibirModalJustificativaRecusaSolicitacao = false;
	private Boolean exibirModalJustificativaQuantidadeDiferente = false;
	private Boolean exibirModalProfissionalReceptor = false;
	private Boolean exibirModalInicializacaoDispensacao = false;
	private Boolean exibirModalRecusaItemMaterial = false;
	private boolean exibirDialogAvisoSolicitacaoLock;
	private List<SolicitacaoMaterialAlmoxarifadoUnidade> listaSolicitacoesPendentes = new ArrayList<SolicitacaoMaterialAlmoxarifadoUnidade>();
	private List<SolicitacaoMaterialAlmoxarifado> itensAtuais = new ArrayList<SolicitacaoMaterialAlmoxarifado>();
	private List<EstoqueAlmoxarifadoDispensacao> estoquesEdicao = new ArrayList<EstoqueAlmoxarifadoDispensacao>();
	private List<SolicitacaoMaterialAlmoxarifadoUnidade> solicitacoesProfissional = new ArrayList<SolicitacaoMaterialAlmoxarifadoUnidade>();
	private List<Integer> idEstoqueLock = new ArrayList<Integer>();
	private SolicitacaoMaterialAlmoxarifado itemSolicitado = new SolicitacaoMaterialAlmoxarifado();
	private SolicitacaoMaterialAlmoxarifadoUnidadeItem itemSolicitacao = new SolicitacaoMaterialAlmoxarifadoUnidadeItem();
	private EstoqueAlmoxarifadoDispensacao estoqueAlmoxarifadoDispensacao = new EstoqueAlmoxarifadoDispensacao();
	private Double quantidadeAlteracaoItem;
	private Double quantidadeDispensada;
	private String justificativaRecusaSolicitacaoItem;
	private String justificativaQuantidadeDiferenteSolicitada;
	
	public void exibirDialogAvisoSolicitacaoLock(){
		setExibirDialogAvisoSolicitacaoLock(true);
	}
	
	public void ocultarDialogAvisoSolicitacaoLock(){
		setExibirDialogAvisoSolicitacaoLock(false);
	}
	
	public void unlockSolicitacao(){
		getInstancia().setProfissionalLock(null);
		super.atualizar();
		ocultarDialogAvisoSolicitacaoLock();
	}
	
	public void swapLockSolicitacao(){
		getInstancia().setProfissionalLock(null);
		super.atualizar();
		iniciarDispensacao();
		setExibirDialogAvisoSolicitacaoLock(false);
		setExibirModalInicializacaoDispensacao(true);
	}
	
	public SolicitacaoMaterialAlmoxarifadoUnidadeRaiz(){
		super();
	}
	
	public SolicitacaoMaterialAlmoxarifadoUnidadeRaiz(SolicitacaoMaterialAlmoxarifadoUnidade item){
		super();
		setInstancia(item);
	}
	
	public int somaTotalQuantidadeLiberada(SolicitacaoMaterialAlmoxarifadoUnidadeItem item){
		int total = 0;
		for(DispensacaoSimplesAlmoxarifado obj : item.getDispensacoes()){
			total += obj.getMovimentoLivroAlmoxarifado().getQuantidadeMovimentacao();
		}
		return total;
	}
	
	public void fecharItemLiberadoComJustificativa(){
		if(getJustificativaQuantidadeDiferenteSolicitada() != null && !getJustificativaQuantidadeDiferenteSolicitada().isEmpty()){
			try {
				getItemSolicitado().getItem().setJustificativa(getJustificativaQuantidadeDiferenteSolicitada()); 
				getItemSolicitado().getItem().setDataJustificativa(new Date());
				getItemSolicitado().getItem().setProfissionalJustificativa(Autenticador.getProfissionalLogado());
				exibirModalJustificativaQuantidadeDiferente = false;
				exibirModalInicializacaoDispensacao = true;
				atualizaEstoquesSolicitados();
				setItemSolicitado(new SolicitacaoMaterialAlmoxarifado());
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}else{
			super.mensagem("Informe a justificativa", "", Constantes.WARN);
		}
	}
	
	public void cancelarJustificativa(){
		exibirModalJustificativaQuantidadeDiferente = false;
		exibirModalInicializacaoDispensacao = true;
		setJustificativaQuantidadeDiferenteSolicitada(null);
	}
	
	public void preLiberacaoEstoquesItem(){
		Double quantidadeSolicitada = getItemSolicitado().getItem().getQuantidadeSolicitada();
		int qtdTotal = somaQuantidadeSolicitadaEstoques();
		if(qtdTotal != quantidadeSolicitada.intValue() && qtdTotal != 0){
			exibirModalJustificativaQuantidadeDiferente = true;
			exibirModalInicializacaoDispensacao = false;
		}else{
			if(qtdTotal == 0){
				super.mensagem("A quantidade total n�o pode ser zero", "", Constantes.ERROR);
			}else{
				cancelarLiberacaoEstoquesItem();
			}
		}
	}
	
	private void atualizaEstoquesSolicitados() {
		getItemSolicitado().setEstoqueReservado(new HashMap<EstoqueAlmoxarifado, Double>());
		for(EstoqueAlmoxarifadoDispensacao obj : getEstoquesEdicao()){
			if(obj.getQuantidadeDispensada() > 0)
				getItemSolicitado().getEstoqueReservado().put(obj.getEstoqueAlmoxarifado(), obj.getQuantidadeDispensada());
		}
	}

	private int somaQuantidadeSolicitadaEstoques() {
		int qtdTotal = 0;
		for(EstoqueAlmoxarifadoDispensacao obj : getEstoquesEdicao()){
			Double quantidadeDispensada2 = obj.getQuantidadeDispensada();
			qtdTotal += quantidadeDispensada2 == null ? 0 : quantidadeDispensada2;
		}
		return qtdTotal;
	}
	
	public void atualizarEstoqueDispensacaoEdicao(){
		try {
			Double quantidadeReservada = new SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz().totalReservardo(getItemSolicitado().getItem().getMaterialAlmoxarifado(), getInstancia());
			Double quantidadeAtual = getEstoqueAlmoxarifadoDispensacao().getEstoqueAlmoxarifado().getQuantidadeAtual();
			Double quantidadeVirtual = quantidadeAtual - quantidadeReservada;
			
			if(quantidadeVirtual < getQuantidadeDispensada().intValue())
				throw new ExcecaoEstoqueReservado(quantidadeReservada, quantidadeVirtual);
			
			getEstoqueAlmoxarifadoDispensacao().setQuantidadeDispensada(getQuantidadeDispensada());
			estoqueAlmoxarifadoDispensacao = new EstoqueAlmoxarifadoDispensacao();
		} catch (ExcecaoEstoqueReservado e) {
			e.printStackTrace();
		}
	}
	
	public void cancelarLiberacaoEstoquesItem(){
		setItemSolicitado(new SolicitacaoMaterialAlmoxarifado());
	}
	
	public void cancelarEstoqueDispensacaoEdicao(){
		setQuantidadeDispensada(null);
		estoqueAlmoxarifadoDispensacao = new EstoqueAlmoxarifadoDispensacao();
	}
	
	public void carregarEstoquesDispensacao(){
		List<EstoqueAlmoxarifado> consultarEstoquesMaterial = new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoquesMaterial(getItemSolicitado().getItem().getMaterialAlmoxarifado());
		setEstoquesEdicao(new ArrayList<EstoqueAlmoxarifadoDispensacao>());
		for(EstoqueAlmoxarifado obj : consultarEstoquesMaterial){
			EstoqueAlmoxarifadoDispensacao ead = new EstoqueAlmoxarifadoDispensacao();
			ead.setEstoqueAlmoxarifado(obj);
			Double quantidade = buscaQuantidadeExistente(ead);
			ead.setQuantidadeDispensada(quantidade);
			getEstoquesEdicao().add(ead);
		}
	}

	private Double buscaQuantidadeExistente(EstoqueAlmoxarifadoDispensacao ead) {
		Double quantidade = 0d;
		Set<EstoqueAlmoxarifado> keys = getItemSolicitado().getEstoqueReservado().keySet();
		for(EstoqueAlmoxarifado key : keys){
			if(key.getIdEstoqueAlmoxarifado() == ead.getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado()){
				quantidade = getItemSolicitado().getEstoqueReservado().get(key);
				break;
			}
		}
		return quantidade;
	}
	
	public void preRecusaItem(){
		exibirModalRecusaItemMaterial = true;
		exibirModalInicializacaoDispensacao = false;
		getItemSolicitado().getItem().setJustificativa(null);
	}
	
	public void fecharTelaDispensacaoAtual(){
		setItensAtuais(new ArrayList<SolicitacaoMaterialAlmoxarifado>());
		itemSolicitado = new SolicitacaoMaterialAlmoxarifado();
		itemSolicitacao = new SolicitacaoMaterialAlmoxarifadoUnidadeItem();
		quantidadeAlteracaoItem = null;
		super.novaInstancia();
		setExibirModalInicializacaoDispensacao(false);
	}
	
	public void cancelarRecusaItem(){
		getItemSolicitado().getItem().setStatusItem(TipoStatusSolicitacaoItemEnum.P);
		getItemSolicitado().getItem().setJustificativa(null);
		setJustificativaRecusaSolicitacaoItem(null);
		setItemSolicitado(new SolicitacaoMaterialAlmoxarifado());
		exibirModalRecusaItemMaterial = false;
		exibirModalInicializacaoDispensacao = true;
	}
	
	public void recusarItem(){
		if(getJustificativaRecusaSolicitacaoItem() == null || getJustificativaRecusaSolicitacaoItem().isEmpty()){
			try {
				throw new ExcecaoSolicitacaoRecusadaSemJustificativa();
			} catch (ExcecaoSolicitacaoRecusadaSemJustificativa e) {
				e.printStackTrace();
			}
		}else{
			try {
				getItemSolicitado().getItem().setJustificativa(getJustificativaRecusaSolicitacaoItem());
				getItemSolicitado().getItem().setStatusItem(TipoStatusSolicitacaoItemEnum.R);
				getItemSolicitado().getItem().setDataJustificativa(new Date());
				getItemSolicitado().getItem().setProfissionalJustificativa(Autenticador.getProfissionalLogado());
				setItemSolicitado(new SolicitacaoMaterialAlmoxarifado());
				exibirModalRecusaItemMaterial = false;
				exibirModalInicializacaoDispensacao = true;
				setJustificativaRecusaSolicitacaoItem(null);
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}
	}
	
	public void preFechamentoDispensacao(){
		exibirModalProfissionalReceptor = true;
		exibirModalInicializacaoDispensacao = false;
		getInstancia().setProfissionalReceptor(getInstancia().getProfissionalInsercao());
	}
	
	public void fecharConfirmacaoReceptor(){
		exibirModalProfissionalReceptor = false;
		exibirModalInicializacaoDispensacao = true;
		getInstancia().setProfissionalReceptor(null);
	}
	
	public void finalizarDispensacao(){
		if(getInstancia().getProfissionalReceptor() == null){
			super.mensagem("Informe o receptor", "", Constantes.WARN);
		}else{
			try {
				verificarSolicitacaoDispensada();
				dispensar();
				exibirModalProfissionalReceptor = false;
				exibirModalInicializacaoDispensacao = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			PadraoFluxoTemp.limparFluxo();
			removerLockEstoque();
			setIdEstoqueLock(new ArrayList<Integer>());
		}
	}
	
	private void removerLockEstoque() {
		for(SolicitacaoMaterialAlmoxarifado obj : getItensAtuais()){
			if(!obj.getItem().getStatusItem().equals(TipoStatusSolicitacaoItemEnum.R)){
				Set<EstoqueAlmoxarifado> keys = obj.getEstoqueReservado().keySet();
				for(EstoqueAlmoxarifado ea : keys)
					new LinhaMecanica().unLockEstoqueAlmoxarifado(ea.getIdEstoqueAlmoxarifado());
			}
		}
					
	}

	private void dispensar() throws ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueVazio, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVencido, ExcecaoEstoqueUnLock, ExcecaoProfissionalLogado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoMovimentoLivroNaoCadastrado, ExcecaoPadraoFluxo, ExcecaoEstoqueAlmoxarifadoVazio {
		TipoMovimentoAlmoxarifado tipoMovimentoDispensacaoSimplesAlmoxarifado = Parametro.tipoMovimentoDispensacaoSimplesAlmoxarifado();
		PadraoFluxoTemp.limparFluxo();
		Date dataDispensacao = new Date();
		for(SolicitacaoMaterialAlmoxarifado obj : getItensAtuais()){
			if(!obj.getItem().getStatusItem().equals(TipoStatusSolicitacaoItemEnum.R)){
				Set<EstoqueAlmoxarifado> keys = obj.getEstoqueReservado().keySet();
				for(EstoqueAlmoxarifado estoque : keys){
					Double quantidadeMovimentada = obj.getEstoqueReservado().get(estoque);
					MovimentoLivroAlmoxarifado mla = new MovimentoLivroAlmoxarifado();
					mla.setDataMovimento(dataDispensacao);
					mla.setEstoqueAlmoxarifado(estoque);
					mla.setJustificativa("RM: " + getInstancia().getIdSolicitacaoMaterialAlmoxarifadoUnidade());
					mla.setQuantidadeMovimentacao(quantidadeMovimentada);
					new ControleEstoqueAlmoxarifadoTemp().liberarDispensacao(mla, quantidadeMovimentada, tipoMovimentoDispensacaoSimplesAlmoxarifado);
					getIdEstoqueLock().add(estoque.getIdEstoqueAlmoxarifado());
					DispensacaoSimplesAlmoxarifado ds = new DispensacaoSimplesAlmoxarifado();
					ds.setMovimentoLivroAlmoxarifado(mla);
					ds.setSolicitacaoMaterialAlmoxarifadoUnidadeItem(obj.getItem());
					ds.setUnidadeDispensada(getInstancia().getUnidadeDestino());
					PadraoFluxoTemp.getObjetoSalvar().put("movimentoLivroAlmoxarifado"+mla.hashCode(), mla);
					PadraoFluxoTemp.getObjetoSalvar().put("dispensacaoSimplesAlmoxarifado"+ds.hashCode(), ds);
				}
				TipoStatusSolicitacaoItemEnum statusItem = obj.dispensadoEmParte() ? TipoStatusSolicitacaoItemEnum.DP : TipoStatusSolicitacaoItemEnum.D;
				obj.getItem().setStatusItem(statusItem);
			}
			PadraoFluxoTemp.getObjetoAtualizar().put("SolicitacaoMaterialAlmoxarifadoItem"+obj.hashCode(), obj.getItem());
		}
		getInstancia().setDataDispensacao(dataDispensacao);
		getInstancia().setProfissionalDispensacao(Autenticador.getProfissionalLogado());
		getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.D);
		PadraoFluxoTemp.getObjetoAtualizar().put("SolicitacaoMaterialAlmoxarifadoUnidade", getInstancia());
		PadraoFluxoTemp.finalizarFluxo();
		setInstancia(new SolicitacaoMaterialAlmoxarifadoUnidadeConsultaRaiz().consultarSolicitacao(getInstancia().getIdSolicitacaoMaterialAlmoxarifadoUnidade()));
		new SolicitacaoMaterialAlmoxarifadoUnidadeConsultaRaiz().consultarSolicitacoesPendentes();
	}

	public void cancelarRecusa(){
		exibirModalJustificativaRecusaSolicitacao = false;
		super.novaInstancia();
	}
	
	public void recusarSolicitacao(){
		try {
			if(getInstancia().getJustificativa() != null && !getInstancia().getJustificativa().isEmpty()){
				getInstancia().setDataDispensacao(new Date());
				getInstancia().setProfissionalDispensacao(Autenticador.getProfissionalLogado());
				getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.R);
				if(super.atualizar()){
					exibirModalJustificativaRecusaSolicitacao = false;
					super.novaInstancia();
					new SolicitacaoMaterialAlmoxarifadoUnidadeConsultaRaiz().consultarSolicitacoesPendentes();
				}
			}else{
				super.mensagem("Informe a justificativa", "", Constantes.WARN);
			}
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	public void preRecusaSolicitacao(){
		exibirModalJustificativaRecusaSolicitacao = true;
	}
	
	public void setCarregarDispensacao(SolicitacaoMaterialAlmoxarifadoUnidade item){
		setInstancia(item);
	}
	
	public void iniciarDispensacao(){
		try {
			verificarSolicitacaoDispensada();
			verificarLockSolicitacao();
			setExibirModalInicializacaoDispensacao(true);
			carregarSugestaoDispensacaoItens();
		} catch (ExcecaoSolicitacaoDispensada e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoEmLock e) {
			e.printStackTrace();
			setExibirDialogAvisoSolicitacaoLock(true);
		}
	}
	
	private void verificarSolicitacaoDispensada() throws ExcecaoSolicitacaoDispensada {
		String sql = "select tp_status_dispensacao from tb_solicitacao_material_almoxarifado_unidade where id_solicitacao_material_almoxarifado_unidade = " + getInstancia().getIdSolicitacaoMaterialAlmoxarifadoUnidade();
		List<Object[]> listaResultado = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).getListaResultado(sql);
		String status = String.valueOf(listaResultado.get(0)[0]);
		if(!status.equals("P")){
			throw new ExcecaoSolicitacaoDispensada();
		}
	}

	private void verificarLockSolicitacao() throws ExcecaoSolicitacaoEmLock {
		String sql = "select id_profissional_lock from tb_solicitacao_material_almoxarifado_unidade where id_solicitacao_material_almoxarifado_unidade = " + getInstancia().getIdSolicitacaoMaterialAlmoxarifadoUnidade();
		List<Object[]> listaResultado = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).getListaResultado(sql);
		String id = String.valueOf(listaResultado.get(0)[0]);
		if(id != null && !id.equals("null")){
			throw new ExcecaoSolicitacaoEmLock();
		}else{
			try {
				getInstancia().setProfissionalLock(Autenticador.getProfissionalLogado());
				super.atualizar();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}
		
	}

	private void carregarSugestaoDispensacaoItens() {
		setItensAtuais(new ArrayList<SolicitacaoMaterialAlmoxarifado>());
		for(SolicitacaoMaterialAlmoxarifadoUnidadeItem item : getInstancia().getItens()){
			List<EstoqueAlmoxarifado> estoquesMaterial = new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoquesMaterial(item.getMaterialAlmoxarifado());
			Map<EstoqueAlmoxarifado, Double> estoqueReservado = new HashMap<EstoqueAlmoxarifado, Double>();
			Double quantidadeSolicitada = item.getQuantidadeSolicitada().doubleValue();
			for(EstoqueAlmoxarifado estoque : estoquesMaterial){
				Double qtdAtual = estoque.getQuantidadeAtual();
				if(quantidadeSolicitada == qtdAtual || quantidadeSolicitada < qtdAtual){
					estoqueReservado.put(estoque, quantidadeSolicitada);
					break;
				}else{
					estoqueReservado.put(estoque, qtdAtual);
					quantidadeSolicitada -= qtdAtual;
				}
			}
			SolicitacaoMaterialAlmoxarifado obj = new SolicitacaoMaterialAlmoxarifado();
			obj.setItem(item);
			obj.setEstoqueReservado(estoqueReservado);
			obj.setCota(new AlmoxarifadoUnidadeCotaConsultaRaiz().cotaMaterialUnidade(item.getMaterialAlmoxarifado(), getInstancia().getUnidadeDestino()));
			obj.setQuantidadeRecebidaMes(new DispensacaoSimplesAlmoxarifadoConsultaRaiz().saldoTotalLiberadoMes(item.getMaterialAlmoxarifado(), getInstancia().getUnidadeDestino()));
			obj.setTotalEstoque(new MaterialAlmoxarifadoConsultaRaiz().saldoTotalEstoque(item.getMaterialAlmoxarifado()));
			getItensAtuais().add(obj);
		}
	}

	@Override
	protected void preEnvio() {
		try {
			getInstancia().setDataInsercao(new Date());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.A);
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	@Override
	public boolean atualizar() {
		if(getInstancia().getItens() != null && getInstancia().getItens().size() > 0){
			getInstancia().setDataFechamento(new Date());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.P);
			if(super.atualizar()){
				ControlePainelAviso.getInstancia().setAvisosNaoMonitorado(new ArrayList<PainelAviso>());
				ControlePainelAviso.getInstancia().gerarAvisoRM(getInstancia().getIdSolicitacaoMaterialAlmoxarifadoUnidade(), getInstancia().getUnidadeDestino());
				super.novaInstancia();
				itemSolicitacao = new SolicitacaoMaterialAlmoxarifadoUnidadeItem();
				return true;
			}
		}
		super.mensagem("Adicione algum material", "", Constantes.WARN);
		return false;
	}
	
	public void exibirModalAlterarItemAdicionado(){
		setQuantidadeAlteracaoItem(null);
		setExibirModalAlterarQuantidadeItem(true);
	}
	
	public void cancelarQuantidadeSolicitacaoAberta(){
		setItemSolicitacao(new SolicitacaoMaterialAlmoxarifadoUnidadeItem());
		setQuantidadeAlteracaoItem(null);
		setExibirModalAlterarQuantidadeItem(false);
	}
	
	public void apagarItemSolicitacaoAberta(){
		if(new SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz(getItemSolicitacao()).apagar()){
			getInstancia().getItens().remove(getItemSolicitacao());
			setItemSolicitacao(new SolicitacaoMaterialAlmoxarifadoUnidadeItem());
		}
	}
	
	public void alterarQuantidadeSolicitacaoAberta(){
		try {
			new ControleEstoqueAlmoxarifadoTemp().liberarSolicitacao(getQuantidadeAlteracaoItem(), getItemSolicitacao().getMaterialAlmoxarifado(), getInstancia());
			getItemSolicitacao().setQuantidadeSolicitada(getQuantidadeAlteracaoItem());
			if(new SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz(getItemSolicitacao()).atualizar()){
				setItemSolicitacao(new SolicitacaoMaterialAlmoxarifadoUnidadeItem());
				setQuantidadeAlteracaoItem(null);
				setExibirModalAlterarQuantidadeItem(false);
			}
		} catch (ExcecaoEstoqueSaldoInsuficiente e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueReservado e) {
			e.printStackTrace();
		} catch (ExcecaoQuantidadeZero e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueAlmoxarifadoVazio e) {
			e.printStackTrace();
		}
	}
	
	public static SolicitacaoMaterialAlmoxarifadoUnidadeRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMaterialAlmoxarifadoUnidadeRaiz) Utilitarios.procuraInstancia(SolicitacaoMaterialAlmoxarifadoUnidadeRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SolicitacaoMaterialAlmoxarifadoUnidade> getListaSolicitacoesPendentes() {
		return listaSolicitacoesPendentes;
	}

	public void setListaSolicitacoesPendentes(
			List<SolicitacaoMaterialAlmoxarifadoUnidade> listaSolicitacoesPendentes) {
		this.listaSolicitacoesPendentes = listaSolicitacoesPendentes;
	}

	public Boolean getSolicitacaoAberta() {
		return solicitacaoAberta;
	}

	public void setSolicitacaoAberta(Boolean solicitacaoAberta) {
		this.solicitacaoAberta = solicitacaoAberta;
	}

	public List<SolicitacaoMaterialAlmoxarifado> getItensAtuais() {
		return itensAtuais;
	}

	public void setItensAtuais(List<SolicitacaoMaterialAlmoxarifado> itensAtuais) {
		this.itensAtuais = itensAtuais;
	}

	public SolicitacaoMaterialAlmoxarifado getItemSolicitado() {
		return itemSolicitado;
	}

	public void setItemSolicitado(SolicitacaoMaterialAlmoxarifado itemSolicitado) {
		this.itemSolicitado = itemSolicitado;
	}

	public List<SolicitacaoMaterialAlmoxarifadoUnidade> getSolicitacoesProfissional() {
		return solicitacoesProfissional;
	}

	public void setSolicitacoesProfissional(List<SolicitacaoMaterialAlmoxarifadoUnidade> solicitacoesProfissional) {
		this.solicitacoesProfissional = solicitacoesProfissional;
	}

	public SolicitacaoMaterialAlmoxarifadoUnidadeItem getItemSolicitacao() {
		return itemSolicitacao;
	}

	public void setItemSolicitacao(SolicitacaoMaterialAlmoxarifadoUnidadeItem itemSolicitacao) {
		this.itemSolicitacao = itemSolicitacao;
	}

	public Boolean getExibirModalAlterarQuantidadeItem() {
		return exibirModalAlterarQuantidadeItem;
	}

	public void setExibirModalAlterarQuantidadeItem(
			Boolean exibirModalAlterarQuantidadeItem) {
		this.exibirModalAlterarQuantidadeItem = exibirModalAlterarQuantidadeItem;
	}

	public Double getQuantidadeAlteracaoItem() {
		return quantidadeAlteracaoItem;
	}

	public void setQuantidadeAlteracaoItem(Double quantidadeAlteracaoItem) {
		this.quantidadeAlteracaoItem = quantidadeAlteracaoItem;
	}

	public Boolean getExibirModalJustificativaRecusaSolicitacao() {
		return exibirModalJustificativaRecusaSolicitacao;
	}

	public void setExibirModalJustificativaRecusaSolicitacao(
			Boolean exibirModalJustificativaRecusaSolicitacao) {
		this.exibirModalJustificativaRecusaSolicitacao = exibirModalJustificativaRecusaSolicitacao;
	}

	public Boolean getExibirModalProfissionalReceptor() {
		return exibirModalProfissionalReceptor;
	}

	public void setExibirModalProfissionalReceptor(
			Boolean exibirModalProfissionalReceptor) {
		this.exibirModalProfissionalReceptor = exibirModalProfissionalReceptor;
	}

	public Boolean getExibirModalInicializacaoDispensacao() {
		return exibirModalInicializacaoDispensacao;
	}

	public void setExibirModalInicializacaoDispensacao(
			Boolean exibirModalInicializacaoDispensacao) {
		this.exibirModalInicializacaoDispensacao = exibirModalInicializacaoDispensacao;
	}

	public Boolean getExibirModalRecusaItemMaterial() {
		return exibirModalRecusaItemMaterial;
	}

	public void setExibirModalRecusaItemMaterial(
			Boolean exibirModalRecusaItemMaterial) {
		this.exibirModalRecusaItemMaterial = exibirModalRecusaItemMaterial;
	}

	public Boolean getExibirModalJustificativaQuantidadeDiferente() {
		return exibirModalJustificativaQuantidadeDiferente;
	}

	public void setExibirModalJustificativaQuantidadeDiferente(
			Boolean exibirModalJustificativaQuantidadeDiferente) {
		this.exibirModalJustificativaQuantidadeDiferente = exibirModalJustificativaQuantidadeDiferente;
	}

	public List<EstoqueAlmoxarifadoDispensacao> getEstoquesEdicao() {
		return estoquesEdicao;
	}

	public void setEstoquesEdicao(List<EstoqueAlmoxarifadoDispensacao> estoquesEdicao) {
		this.estoquesEdicao = estoquesEdicao;
	}

	public EstoqueAlmoxarifadoDispensacao getEstoqueAlmoxarifadoDispensacao() {
		return estoqueAlmoxarifadoDispensacao;
	}

	public void setEstoqueAlmoxarifadoDispensacao(
			EstoqueAlmoxarifadoDispensacao estoqueAlmoxarifadoDispensacao) {
		this.estoqueAlmoxarifadoDispensacao = estoqueAlmoxarifadoDispensacao;
	}

	public Double getQuantidadeDispensada() {
		return quantidadeDispensada;
	}

	public void setQuantidadeDispensada(Double quantidadeDispensada) {
		this.quantidadeDispensada = quantidadeDispensada;
	}

	public String getJustificativaRecusaSolicitacaoItem() {
		return justificativaRecusaSolicitacaoItem;
	}

	public void setJustificativaRecusaSolicitacaoItem(
			String justificativaRecusaSolicitacaoItem) {
		this.justificativaRecusaSolicitacaoItem = justificativaRecusaSolicitacaoItem;
	}

	public String getJustificativaQuantidadeDiferenteSolicitada() {
		return justificativaQuantidadeDiferenteSolicitada;
	}

	public void setJustificativaQuantidadeDiferenteSolicitada(
			String justificativaQuantidadeDiferenteSolicitada) {
		this.justificativaQuantidadeDiferenteSolicitada = justificativaQuantidadeDiferenteSolicitada;
	}

	public List<Integer> getIdEstoqueLock() {
		return idEstoqueLock;
	}

	public void setIdEstoqueLock(List<Integer> idEstoqueLock) {
		this.idEstoqueLock = idEstoqueLock;
	}

	public boolean isExibirDialogAvisoSolicitacaoLock() {
		return exibirDialogAvisoSolicitacaoLock;
	}

	public void setExibirDialogAvisoSolicitacaoLock(
			boolean exibirDialogAvisoSolicitacaoLock) {
		this.exibirDialogAvisoSolicitacaoLock = exibirDialogAvisoSolicitacaoLock;
	}

}
