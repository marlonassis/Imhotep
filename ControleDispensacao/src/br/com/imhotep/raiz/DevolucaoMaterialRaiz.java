package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.DevolucaoMaterialConsultaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueMaterialAlmoxarifadoConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.DevolucaoMaterial;
import br.com.imhotep.entidade.DevolucaoMaterialItem;
import br.com.imhotep.entidade.DevolucaoMaterialItemMovimento;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoDevolucao;
import br.com.imhotep.enums.TipoStatusDevolucaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoDevolucaoMaterialItensNaoInformados;
import br.com.imhotep.excecoes.ExcecaoDevolucaoMaterialValidacaoCodigo;
import br.com.imhotep.excecoes.ExcecaoEstoqueAlmoxarifadoVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueBloqueado;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.excecoes.ExcecaoEstoqueNaoCadastrado;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueVencido;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.excecoes.ExcecaoSemJustificativa;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class DevolucaoMaterialRaiz extends PadraoRaiz<DevolucaoMaterial>{
	
	private List<DevolucaoMaterial> devolucoesPendentes = new ArrayList<DevolucaoMaterial>();
	private boolean statusDialogDevolucao = false;
	private boolean statusDialogJustificativaRecusaDevolucao = false;
	private boolean statusDialogJustificativaRecusaItem = false;
	private boolean statusDialogJustificativaQuantidadeDiferente = false;
	private DevolucaoMaterialItem itemRecusado;
	private Map<Integer, List<EstoqueAlmoxarifadoDevolucao>> mapDevolucao = new HashMap<Integer, List<EstoqueAlmoxarifadoDevolucao>>();
	private String codigos;
	private String justificativaDevolucaoDiferente;
	private String justificativaDevolucaoRecusada;
	private String justificativaItemRecusado;
	private List<EstoqueAlmoxarifado> estoqueAlmoxarifadoLock = new ArrayList<EstoqueAlmoxarifado>();
	private List<EstoqueAlmoxarifado> estoqueAlmoxarifadoSugestao = new ArrayList<EstoqueAlmoxarifado>();
	private DevolucaoMaterialItem devolucaoMaterialItemSugestao = new DevolucaoMaterialItem();
	
	public int somaTotalQuantidadeLiberada(DevolucaoMaterialItem item){
		int qtd = 0;
		DevolucaoMaterialItem devolucaoMaterialItem = getInstancia().getItens().get(getInstancia().getItens().indexOf(item));
		for(DevolucaoMaterialItemMovimento m : devolucaoMaterialItem.getDevolucoesEstoque()){
			qtd += m.getMovimentoLivroAlmoxarifado().getQuantidadeMovimentacao();
		}
		return qtd;
	}
	
	@Override
	public boolean atualizar() {
		List<DevolucaoMaterialItem> itens = DevolucaoMaterialItemRaiz.getInstanciaAtual().getItens();
		if(itens != null && !itens.isEmpty()){
			getInstancia().setDataFechamento(new Date());
			getInstancia().setStatus(TipoStatusDevolucaoItemEnum.P);
			if(super.atualizar()){
				ControlePainelAviso.getInstancia().gerarAvisoRD(getInstancia().getIdDevolucaoMaterial(), getInstancia().getUnidadeDevolucao());
				super.novaInstancia();
				return true;
			}
		}else{
			try{
				throw new ExcecaoDevolucaoMaterialItensNaoInformados();
			}catch(ExcecaoDevolucaoMaterialItensNaoInformados e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setDataInsercao(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setStatus(TipoStatusDevolucaoItemEnum.A);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	public void fecharDialogDevolucaoPendente(){
		setStatusDialogDevolucao(false);
		itemRecusado = null;
		mapDevolucao = new HashMap<Integer, List<EstoqueAlmoxarifadoDevolucao>>();
		codigos = null;
		justificativaDevolucaoDiferente = null;
		justificativaDevolucaoRecusada = null;
		justificativaItemRecusado = null;
		super.novaInstancia();
	}
	
	public static DevolucaoMaterialRaiz getInstanciaAtual(){
		try {
			return (DevolucaoMaterialRaiz) Utilitarios.procuraInstancia(DevolucaoMaterialRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void cancelarRecusaItem(){
		setJustificativaItemRecusado(null);
		setStatusDialogDevolucao(true);
		setStatusDialogJustificativaRecusaItem(false);
	}
	
	public void recusarItemDevolvido(){
		if(getJustificativaItemRecusado() == null || getJustificativaItemRecusado().isEmpty()){
			try{
				throw new ExcecaoSemJustificativa();
			}catch(ExcecaoSemJustificativa e){
				e.printStackTrace();
			}
		}else{
			getMapDevolucao().remove(getItemRecusado().getIdDevolucaoMaterialItem());
			getItemRecusado().setStatus(TipoStatusDevolucaoItemEnum.RE);
			getItemRecusado().setJustificativa(getJustificativaItemRecusado());
			setStatusDialogJustificativaRecusaItem(false);
			setStatusDialogDevolucao(true);
		}
	}
	
	public void recusarDevolucao(){
		if(getJustificativaDevolucaoRecusada() == null || getJustificativaDevolucaoRecusada().isEmpty()){
			try{
				throw new ExcecaoSemJustificativa();
			}catch(ExcecaoSemJustificativa e){
				e.printStackTrace();
			}
		}else{
			try {
				getInstancia().setJustificativa(getJustificativaDevolucaoRecusada());
				getInstancia().setDataRecebimento(new Date());
				getInstancia().setProfissionalConfirmacao(Autenticador.getProfissionalLogado());
				getInstancia().setStatus(TipoStatusDevolucaoItemEnum.RE);
				super.atualizar();
				super.novaInstancia();
				setStatusDialogJustificativaRecusaDevolucao(false);
				setJustificativaDevolucaoRecusada(null);
				new DevolucaoMaterialConsultaRaiz().consultarDevolucoesPendentes();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cancelarRecusaDevolucao(){
		setStatusDialogJustificativaRecusaDevolucao(false);
		setJustificativaDevolucaoRecusada(null);
	}
	
	public void preFinalizarDevolucao(){
		int totalAtestado = somarQtdAtestado();
		int totalAlegado = somarQtdAlegado();
		
		if(totalAtestado != totalAlegado){
			setStatusDialogJustificativaQuantidadeDiferente(true);
			setStatusDialogDevolucao(false);
		}else{
			finalizarDevolucao();
		}
	}

	private int somarQtdAlegado() {
		int totalAlegado = 0;
		for(DevolucaoMaterialItem item : getInstancia().getItens()){
			totalAlegado += item.getQuantidadeDevolvida();
		}
		return totalAlegado;
	}

	private int somarQtdAtestado() {
		int totalAtestado = 0;
		for(Integer id : getMapDevolucao().keySet()){
			for(EstoqueAlmoxarifadoDevolucao ed : getMapDevolucao().get(id)){
				totalAtestado += ed.getQuantidadeDevolvida();
			}
		}
		return totalAtestado;
	}
	
	public void cancelarJustificativaDevolucao(){
		setJustificativaDevolucaoDiferente(null);
		setStatusDialogDevolucao(true);
		setStatusDialogJustificativaQuantidadeDiferente(false);
	}
	
	public void finalizarDevolucao(){
		try {
			if(getStatusDialogJustificativaQuantidadeDiferente() && (getJustificativaDevolucaoDiferente() == null || getJustificativaDevolucaoDiferente().isEmpty())){
				throw new ExcecaoSemJustificativa();
			}else{
				if(getJustificativaDevolucaoDiferente() != null & !getJustificativaDevolucaoDiferente().isEmpty()){
					getInstancia().setJustificativa(getJustificativaDevolucaoDiferente());
				}
			}
			unlockEstoqueMaterials();
			PadraoFluxoTemp.limparFluxo();
			estoqueAlmoxarifadoLock = new ArrayList<EstoqueAlmoxarifado>();
			processarDevolucao();
			PadraoFluxoTemp.finalizarFluxo();
			setStatusDialogJustificativaQuantidadeDiferente(false);
			setStatusDialogDevolucao(true);
			new DevolucaoMaterialConsultaRaiz().consultarDevolucoesPendentes();
			setInstancia(new DevolucaoMaterialConsultaRaiz().getDevolucao(getInstancia().getIdDevolucaoMaterial()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}finally{
			unlockEstoqueMaterials();
			PadraoFluxoTemp.limparFluxo();
		}
	}

	private void unlockEstoqueMaterials() {
		for(EstoqueAlmoxarifado e : estoqueAlmoxarifadoLock){
			try {
				new ControleEstoqueAlmoxarifadoTemp().unLockEstoque(e);
			} catch (ExcecaoEstoqueUnLock e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void processarDevolucao() 
			throws ExcecaoProfissionalLogado, ExcecaoQuantidadeZero, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, 
			ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueAlmoxarifadoVazio, ExcecaoEstoqueSaldoInsuficiente, 
			ExcecaoEstoqueReservado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado {
		Date dataPadrao = new Date();
		TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifadoDevolucaoDispensacao = Parametro.tipoMovimentoAlmoxarifadoDevolucaoDispensacao();
		for(DevolucaoMaterialItem item : getInstancia().getItens()){
			if(!item.getStatus().equals(TipoStatusDevolucaoItemEnum.RE)){
				for(EstoqueAlmoxarifadoDevolucao ed : getMapDevolucao().get(item.getIdDevolucaoMaterialItem())){
					MovimentoLivroAlmoxarifado ml = new MovimentoLivroAlmoxarifado();
					ml.setEstoqueAlmoxarifado(ed.getEstoqueAlmoxarifado());
					ml.setJustificativa("RD: "+getInstancia().getIdDevolucaoMaterial());
					ml.setQuantidadeMovimentacao(ed.getQuantidadeDevolvida());
					ml.setTipoMovimentoAlmoxarifado(tipoMovimentoAlmoxarifadoDevolucaoDispensacao);
					ml.setDataMovimento(dataPadrao);
					ml.setQuantidadeAtual(ed.getEstoqueAlmoxarifado().getQuantidadeAtual());
					ml.setProfissionalInsercao(Autenticador.getProfissionalLogado());
					estoqueAlmoxarifadoLock.add(ed.getEstoqueAlmoxarifado());
					new ControleEstoqueAlmoxarifadoTemp().liberarAjusteEstoqueAlmoxarifado(ed.getEstoqueAlmoxarifado(), ml.getQuantidadeMovimentacao(), ml.getTipoMovimentoAlmoxarifado()) ;
					
					PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro"+ml.hashCode(), ml);
					DevolucaoMaterialItemMovimento dmim = new DevolucaoMaterialItemMovimento();
					dmim.setDataInsercao(dataPadrao);
					dmim.setDevolucaoMaterialItem(item);
					dmim.setMovimentoLivroAlmoxarifado(ml);
					PadraoFluxoTemp.getObjetoSalvar().put("DevolucaoMaterialItemMovimento"+dmim.hashCode(), dmim);
				}
			}else{
				PadraoFluxoTemp.getObjetoAtualizar().put("DevolucaoMaterialItem"+item.hashCode(), item);
			}
		}
		
		try {
			getInstancia().setDataRecebimento(new Date());
			getInstancia().setProfissionalConfirmacao(Autenticador.getProfissionalLogado());
			int totalAtestado = somarQtdAtestado();
			int totalAlegado = somarQtdAlegado();
			if(totalAtestado < totalAlegado){
				getInstancia().setStatus(TipoStatusDevolucaoItemEnum.RP);
			}else{
				getInstancia().setStatus(TipoStatusDevolucaoItemEnum.R);
			}
			PadraoFluxoTemp.getObjetoAtualizar().put("DevolucaoMaterial"+getInstancia().hashCode(), getInstancia());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}

	public void validarCodigos(){
		Map<String, Double> lotes = prepararLotes();
		try {
			carregaLotesValidos(lotes);
		} catch (ExcecaoDevolucaoMaterialValidacaoCodigo e) {
			e.printStackTrace();
		}
	}

	private Map<String, Double> prepararLotes() {
		String[] codigosArray = codigos.split("\r");
		Map<String, Double> lotes = new HashMap<String, Double>();
		for(String codigo : codigosArray){
			codigo = codigo.replace("\n", "").replace("\r", "");
			if(codigo.isEmpty()){
				continue;
			}
			String[] item = separarLoteQuantidade(codigo);
			String lote = item[0];
			Double quantidade = Double.valueOf(item[1]);

			if(lotes.containsKey(lote)){
				quantidade =+ lotes.get(lote);
			}
			
			lotes.put(lote, quantidade);
		}
		return lotes;
	}

	private void carregaLotesValidos(Map<String, Double> lotes) throws ExcecaoDevolucaoMaterialValidacaoCodigo {
		String codigosNaoEncontrados = "";
		for(String lote : lotes.keySet()){
			EstoqueAlmoxarifado estoqueAlmoxarifado = new EstoqueMaterialAlmoxarifadoConsultaRaiz().consultarEstoqueMaterialLoteCodigoBarras(lote);
			if(estoqueAlmoxarifado != null && estoqueAlmoxarifado.getIdEstoqueAlmoxarifado() != 0){
				DevolucaoMaterialItem dmi = null;
				for(DevolucaoMaterialItem i : getInstancia().getItens()){
					if(i.getMaterialAlmoxarifado().getIdMaterialAlmoxarifado() == estoqueAlmoxarifado.getMaterialAlmoxarifado().getIdMaterialAlmoxarifado()){
						dmi = i;
						break;
					}
				}
				if(dmi != null){
					if(getMapDevolucao().containsKey(dmi.getIdDevolucaoMaterialItem())){
						List<EstoqueAlmoxarifadoDevolucao> list = getMapDevolucao().get(dmi.getIdDevolucaoMaterialItem());
						int pos = 0;
						boolean achou = false;
						for(EstoqueAlmoxarifadoDevolucao ed : list){
							if(ed.getEstoqueAlmoxarifado().getLote().equals(lote)){
								achou = true;
								break;
							}
							pos++;
						}
						
						if(achou)
							list.get(pos).setQuantidadeDevolvida(list.get(pos).getQuantidadeDevolvida() + lotes.get(lote));
						else
							list.add(new EstoqueAlmoxarifadoDevolucao(estoqueAlmoxarifado, lotes.get(lote), lote));
					}else{
						List<EstoqueAlmoxarifadoDevolucao> list = new ArrayList<EstoqueAlmoxarifadoDevolucao>();
						list.add(new EstoqueAlmoxarifadoDevolucao(estoqueAlmoxarifado, lotes.get(lote), lote));
						getMapDevolucao().put(dmi.getIdDevolucaoMaterialItem(), list);
					}
				}else{
					codigosNaoEncontrados += lote+"\r";
				}
			}else{
				codigosNaoEncontrados += lote+"\r";
			}
		
		}
		
		setCodigos(null);
		setCodigos(codigosNaoEncontrados);
	}
	
	private String[] separarLoteQuantidade(String codigo) {
		//quando houve uma interroga�ao, est� indicado que existe uma quantidade informada
		if(codigo.contains("?"))
			return codigo.split("\\?");
		else
			return new String[]{codigo, "1"};
	}
	
	public void recuperarItem(){
		getItemRecusado().setStatus(TipoStatusDevolucaoItemEnum.P);
		getItemRecusado().setJustificativa(null);
		setJustificativaItemRecusado(null);
	}
	
	public Double quantidadeTotalRecebida(DevolucaoMaterialItem item){
		List<EstoqueAlmoxarifadoDevolucao> list = getMapDevolucao().get(item.getIdDevolucaoMaterialItem());
		Double qtd = 0d;
		if(list != null){
			for(EstoqueAlmoxarifadoDevolucao e : list){
				qtd += e.getQuantidadeDevolvida();
			}
		}
		return qtd;
	}
	
	public void exibirDialogDevolucao(){
		setStatusDialogDevolucao(true);
	}

	public void carregarEstoqueSugestao(){
		setEstoqueAlmoxarifadoSugestao(new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoquesMaterial(getDevolucaoMaterialItemSugestao().getMaterialAlmoxarifado()));
	}
	
	public void preRecusarItem(){
		setStatusDialogJustificativaRecusaItem(true);
		setStatusDialogDevolucao(false);
	}
	
	public void preRecusaDevolucao(){
		setStatusDialogJustificativaRecusaDevolucao(true);
	}
	
	public List<DevolucaoMaterial> getDevolucoesPendentes() {
		return devolucoesPendentes;
	}

	public void setDevolucoesPendentes(List<DevolucaoMaterial> devolucoesPendentes) {
		this.devolucoesPendentes = devolucoesPendentes;
	}

	public boolean getStatusDialogDevolucao() {
		return statusDialogDevolucao;
	}

	public void setStatusDialogDevolucao(boolean statusDialogDevolucao) {
		this.statusDialogDevolucao = statusDialogDevolucao;
	}

	public boolean getStatusDialogJustificativaRecusaDevolucao() {
		return statusDialogJustificativaRecusaDevolucao;
	}

	public void setStatusDialogJustificativaRecusaDevolucao(boolean statusDialogJustificativaRecusaDevolucao) {
		this.statusDialogJustificativaRecusaDevolucao = statusDialogJustificativaRecusaDevolucao;
	}

	public DevolucaoMaterialItem getItemRecusado() {
		return itemRecusado;
	}

	public void setItemRecusado(DevolucaoMaterialItem itemRecusado) {
		this.itemRecusado = itemRecusado;
	}

	public Map<Integer, List<EstoqueAlmoxarifadoDevolucao>> getMapDevolucao() {
		return mapDevolucao;
	}

	public void setMapDevolucao(Map<Integer, List<EstoqueAlmoxarifadoDevolucao>> mapDevolucao) {
		this.mapDevolucao = mapDevolucao;
	}

	public String getCodigos() {
		return codigos;
	}

	public void setCodigos(String codigos) {
		this.codigos = codigos;
	}

	public boolean getStatusDialogJustificativaRecusaItem() {
		return statusDialogJustificativaRecusaItem;
	}

	public void setStatusDialogJustificativaRecusaItem(
			boolean statusDialogJustificativaRecusaItem) {
		this.statusDialogJustificativaRecusaItem = statusDialogJustificativaRecusaItem;
	}

	public boolean getStatusDialogJustificativaQuantidadeDiferente() {
		return statusDialogJustificativaQuantidadeDiferente;
	}

	public void setStatusDialogJustificativaQuantidadeDiferente(
			boolean statusDialogJustificativaQuantidadeDiferente) {
		this.statusDialogJustificativaQuantidadeDiferente = statusDialogJustificativaQuantidadeDiferente;
	}

	public String getJustificativaDevolucaoDiferente() {
		return justificativaDevolucaoDiferente;
	}

	public void setJustificativaDevolucaoDiferente(
			String justificativaDevolucaoDiferente) {
		this.justificativaDevolucaoDiferente = justificativaDevolucaoDiferente;
	}

	public String getJustificativaDevolucaoRecusada() {
		return justificativaDevolucaoRecusada;
	}

	public void setJustificativaDevolucaoRecusada(
			String justificativaDevolucaoRecusada) {
		this.justificativaDevolucaoRecusada = justificativaDevolucaoRecusada;
	}

	public String getJustificativaItemRecusado() {
		return justificativaItemRecusado;
	}

	public void setJustificativaItemRecusado(String justificativaItemRecusado) {
		this.justificativaItemRecusado = justificativaItemRecusado;
	}

	public List<EstoqueAlmoxarifado> getEstoqueAlmoxarifadoSugestao() {
		return estoqueAlmoxarifadoSugestao;
	}

	public void setEstoqueAlmoxarifadoSugestao(
			List<EstoqueAlmoxarifado> estoqueAlmoxarifadoSugestao) {
		this.estoqueAlmoxarifadoSugestao = estoqueAlmoxarifadoSugestao;
	}

	public DevolucaoMaterialItem getDevolucaoMaterialItemSugestao() {
		return devolucaoMaterialItemSugestao;
	}

	public void setDevolucaoMaterialItemSugestao(
			DevolucaoMaterialItem devolucaoMaterialItemSugestao) {
		this.devolucaoMaterialItemSugestao = devolucaoMaterialItemSugestao;
	}
	
}