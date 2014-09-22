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
import br.com.imhotep.consulta.raiz.DevolucaoMedicamentoConsultaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueTemp;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.DevolucaoMedicamento;
import br.com.imhotep.entidade.DevolucaoMedicamentoItem;
import br.com.imhotep.entidade.DevolucaoMedicamentoItemMovimento;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.extra.EstoqueDevolucao;
import br.com.imhotep.enums.TipoStatusDevolucaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoDevolucaoMedicamentoItensNaoInformados;
import br.com.imhotep.excecoes.ExcecaoDevolucaoMedicamentoValidacaoCodigo;
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
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoSemJustificativa;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class DevolucaoMedicamentoRaiz extends PadraoRaiz<DevolucaoMedicamento>{
	
	private List<DevolucaoMedicamento> devolucoesPendentes = new ArrayList<DevolucaoMedicamento>();
	private boolean statusDialogDevolucao = false;
	private boolean statusDialogJustificativaRecusaDevolucao = false;
	private boolean statusDialogJustificativaRecusaItem = false;
	private boolean statusDialogJustificativaQuantidadeDiferente = false;
	private DevolucaoMedicamentoItem itemRecusado;
	private Map<Integer, List<EstoqueDevolucao>> mapDevolucao = new HashMap<Integer, List<EstoqueDevolucao>>();
	private String codigos;
	private String justificativaDevolucaoDiferente;
	private String justificativaDevolucaoRecusada;
	private String justificativaItemRecusado;
	private List<Estoque> estoqueLock = new ArrayList<Estoque>();
	
	public int somaTotalQuantidadeLiberada(DevolucaoMedicamentoItem item){
		int qtd = 0;
		DevolucaoMedicamentoItem devolucaoMedicamentoItem = getInstancia().getItens().get(getInstancia().getItens().indexOf(item));
		for(DevolucaoMedicamentoItemMovimento m : devolucaoMedicamentoItem.getDevolucoesEstoque()){
			qtd += m.getMovimentoLivro().getQuantidadeMovimentacao();
		}
		return qtd;
	}
	
	@Override
	public boolean atualizar() {
		List<DevolucaoMedicamentoItem> itens = DevolucaoMedicamentoItemRaiz.getInstanciaAtual().getItens();
		if(itens != null && !itens.isEmpty()){
			getInstancia().setDataFechamento(new Date());
			getInstancia().setStatus(TipoStatusDevolucaoItemEnum.P);
			if(super.atualizar()){
				ControlePainelAviso.getInstancia().gerarAvisoRD(getInstancia().getIdDevolucaoMedicamento(), getInstancia().getUnidadeDevolucao());
				super.novaInstancia();
				return true;
			}
		}else{
			try{
				throw new ExcecaoDevolucaoMedicamentoItensNaoInformados();
			}catch(ExcecaoDevolucaoMedicamentoItensNaoInformados e){
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
		mapDevolucao = new HashMap<Integer, List<EstoqueDevolucao>>();
		codigos = null;
		justificativaDevolucaoDiferente = null;
		justificativaDevolucaoRecusada = null;
		justificativaItemRecusado = null;
		super.novaInstancia();
	}
	
	public static DevolucaoMedicamentoRaiz getInstanciaAtual(){
		try {
			return (DevolucaoMedicamentoRaiz) Utilitarios.procuraInstancia(DevolucaoMedicamentoRaiz.class);
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
			getMapDevolucao().remove(getItemRecusado().getIdDevolucaoMedicamentoItem());
			getItemRecusado().setStatus(TipoStatusDevolucaoItemEnum.RE);
			getItemRecusado().setJustificativa(getJustificativaItemRecusado());
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
				new DevolucaoMedicamentoConsultaRaiz().consultarDevolucoesPendentes();
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
		for(DevolucaoMedicamentoItem item : getInstancia().getItens()){
			totalAlegado += item.getQuantidadeDevolvida();
		}
		return totalAlegado;
	}

	private int somarQtdAtestado() {
		int totalAtestado = 0;
		for(Integer id : getMapDevolucao().keySet()){
			for(EstoqueDevolucao ed : getMapDevolucao().get(id)){
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
			unlockEstoques();
			PadraoFluxoTemp.limparFluxo();
			estoqueLock = new ArrayList<Estoque>();
			processarDevolucao();
			PadraoFluxoTemp.finalizarFluxo();
			setStatusDialogJustificativaQuantidadeDiferente(false);
			setStatusDialogDevolucao(true);
			new DevolucaoMedicamentoConsultaRaiz().consultarDevolucoesPendentes();
			setInstancia(new DevolucaoMedicamentoConsultaRaiz().getDevolucao(getInstancia().getIdDevolucaoMedicamento()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}finally{
			unlockEstoques();
			PadraoFluxoTemp.limparFluxo();
		}
	}

	private void unlockEstoques() {
		for(Estoque e : estoqueLock){
			try {
				new ControleEstoqueTemp().unLockEstoque(e);
			} catch (ExcecaoEstoqueUnLock e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void processarDevolucao() throws ExcecaoEstoqueVencido, ExcecaoEstoqueBloqueado, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoEstoqueReservado, ExcecaoEstoqueNaoCadastrado, ExcecaoEstoqueNaoAtualizado, ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Date dataPadrao = new Date();
		TipoMovimento tipoMovimentoDevolucaoDispensacao = Parametro.tipoMovimentoDevolucaoDispensacao();
		for(DevolucaoMedicamentoItem item : getInstancia().getItens()){
			if(!item.getStatus().equals(TipoStatusDevolucaoItemEnum.RE)){
				for(EstoqueDevolucao ed : getMapDevolucao().get(item.getIdDevolucaoMedicamentoItem())){
					MovimentoLivro ml = new MovimentoLivro();
					ml.setEstoque(ed.getEstoque());
					ml.setJustificativa("RD: "+getInstancia().getIdDevolucaoMedicamento());
					ml.setQuantidadeMovimentacao(ed.getQuantidadeDevolvida());
					ml.setTipoMovimento(tipoMovimentoDevolucaoDispensacao);
					
					estoqueLock.add(ed.getEstoque());
					new ControleEstoqueTemp().liberarAjuste(dataPadrao, ml);
					
					PadraoFluxoTemp.getObjetoSalvar().put("MovimentoLivro"+ml.hashCode(), ml);
					DevolucaoMedicamentoItemMovimento dmim = new DevolucaoMedicamentoItemMovimento();
					dmim.setDataInsercao(dataPadrao);
					dmim.setDevolucaoMedicamentoItem(item);
					dmim.setMovimentoLivro(ml);
					PadraoFluxoTemp.getObjetoSalvar().put("DevolucaoMedicamentoItemMovimento"+dmim.hashCode(), dmim);
				}
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
			PadraoFluxoTemp.getObjetoAtualizar().put("DevolucaoMedicamento"+getInstancia().hashCode(), getInstancia());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}

	public void validarCodigos(){
		Map<String, Integer> lotes = prepararLotes();
		try {
			carregaLotesValidos(lotes);
		} catch (ExcecaoDevolucaoMedicamentoValidacaoCodigo e) {
			e.printStackTrace();
		}
	}

	private Map<String, Integer> prepararLotes() {
		String[] codigosArray = codigos.split("\r");
		Map<String, Integer> lotes = new HashMap<String, Integer>();
		for(String codigo : codigosArray){
			codigo = codigo.replace("\n", "").replace("\r", "");
			if(codigo.isEmpty()){
				continue;
			}
			String[] item = separarLoteQuantidade(codigo);
			String lote = item[0];
			Integer quantidade = Integer.valueOf(item[1]);

			if(lotes.containsKey(lote)){
				quantidade =+ lotes.get(lote);
			}
			
			lotes.put(lote, quantidade);
		}
		return lotes;
	}

	private void carregaLotesValidos(Map<String, Integer> lotes) throws ExcecaoDevolucaoMedicamentoValidacaoCodigo {
		String codigosNaoEncontrados = "";
		for(String lote : lotes.keySet()){
			Estoque estoque = new EstoqueConsultaRaiz().consultarEstoqueLoteCodigoBarras(lote);
			if(estoque != null && estoque.getIdEstoque() != 0){
				DevolucaoMedicamentoItem dmi = null;
				for(DevolucaoMedicamentoItem i : getInstancia().getItens()){
					if(i.getMaterial().getIdMaterial() == estoque.getMaterial().getIdMaterial()){
						dmi = i;
						break;
					}
				}
				if(dmi != null){
					if(getMapDevolucao().containsKey(dmi.getIdDevolucaoMedicamentoItem())){
						List<EstoqueDevolucao> list = getMapDevolucao().get(dmi.getIdDevolucaoMedicamentoItem());
						int pos = 0;
						boolean achou = false;
						for(EstoqueDevolucao ed : list){
							if(ed.getEstoque().getLote().equals(lote)){
								achou = true;
								break;
							}
							pos++;
						}
						
						if(achou)
							list.get(pos).setQuantidadeDevolvida(list.get(pos).getQuantidadeDevolvida() + lotes.get(lote));
						else
							list.add(new EstoqueDevolucao(estoque, lotes.get(lote), lote));
					}else{
						List<EstoqueDevolucao> list = new ArrayList<EstoqueDevolucao>();
						list.add(new EstoqueDevolucao(estoque, lotes.get(lote), lote));
						getMapDevolucao().put(dmi.getIdDevolucaoMedicamentoItem(), list);
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
		//quando houve uma interrogaçao, está indicado que existe uma quantidade informada
		if(codigo.contains("?"))
			return codigo.split("\\?");
		else
			return new String[]{codigo, "1"};
	}
	
	public void recuperarItem(){
		getItemRecusado().setStatus(TipoStatusDevolucaoItemEnum.P);
		getItemRecusado().setJustificativa(null);
	}
	
	public Integer quantidadeTotalRecebida(DevolucaoMedicamentoItem item){
		List<EstoqueDevolucao> list = getMapDevolucao().get(item.getIdDevolucaoMedicamentoItem());
		Integer qtd = 0;
		if(list != null){
			for(EstoqueDevolucao e : list){
				qtd += e.getQuantidadeDevolvida();
			}
		}
		return qtd;
	}
	
	public void exibirDialogDevolucao(){
		setStatusDialogDevolucao(true);
	}
	
	public void preRecusarItem(){
		setStatusDialogJustificativaRecusaItem(true);
		setStatusDialogDevolucao(false);
	}
	
	public void preRecusaDevolucao(){
		setStatusDialogJustificativaRecusaDevolucao(true);
	}
	
	public List<DevolucaoMedicamento> getDevolucoesPendentes() {
		return devolucoesPendentes;
	}

	public void setDevolucoesPendentes(List<DevolucaoMedicamento> devolucoesPendentes) {
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

	public DevolucaoMedicamentoItem getItemRecusado() {
		return itemRecusado;
	}

	public void setItemRecusado(DevolucaoMedicamentoItem itemRecusado) {
		this.itemRecusado = itemRecusado;
	}

	public Map<Integer, List<EstoqueDevolucao>> getMapDevolucao() {
		return mapDevolucao;
	}

	public void setMapDevolucao(Map<Integer, List<EstoqueDevolucao>> mapDevolucao) {
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
	
}