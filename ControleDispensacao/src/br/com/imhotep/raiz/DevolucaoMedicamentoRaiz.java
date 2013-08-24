package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.DevolucaoMedicamentoConsultaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.DevolucaoMedicamento;
import br.com.imhotep.entidade.DevolucaoMedicamentoItem;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.extra.EstoqueDevolucao;
import br.com.imhotep.enums.TipoStatusDevolucaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoDevolucaoMedicamentoSemItens;
import br.com.imhotep.excecoes.ExcecaoJustificativaDevolucao;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class DevolucaoMedicamentoRaiz extends PadraoHome<DevolucaoMedicamento>{
	
	private List<DevolucaoMedicamento> devolucoesPendentes = new ArrayList<DevolucaoMedicamento>();
	private boolean statusDialogDevolucao = false;
	private boolean statusDialogJustificativa = false;
	private boolean statusDialogJustificativaDevolucaoParcial = false;
	private String codigos;
	private String justificativa;
	private HashMap<DevolucaoMedicamentoItem, List<EstoqueDevolucao>> devolvidosMap = new HashMap<DevolucaoMedicamentoItem, List<EstoqueDevolucao>>();
	private boolean statusDialogRecusaItem;
	private DevolucaoMedicamentoItem itemRecusado = new DevolucaoMedicamentoItem(); 
	
	public void recuperarItem(){
		recuperarItemRecusado();
		setStatusDialogRecusaItem(false);
		setItemRecusado(new DevolucaoMedicamentoItem());
	}

	private void recuperarItemRecusado() {
		for(DevolucaoMedicamentoItem dmi : getInstancia().getItens()){
			if(dmi.equals(getItemRecusado())){
				dmi.setStatus(TipoStatusDevolucaoItemEnum.P);
				dmi.setJustificativa(null);
				break;
			}
		}
	}
	
	public void recusarItemDevolvido(){
		if(getItemRecusado().getJustificativa() == null || getItemRecusado().getJustificativa().isEmpty()){
			try{
				throw new ExcecaoJustificativaDevolucao();
			}catch(ExcecaoJustificativaDevolucao e){
				e.printStackTrace();
			}
		}else{
			for(DevolucaoMedicamentoItem dmi : getInstancia().getItens()){
				if(dmi.equals(getItemRecusado())){
					atualizarItemRecusado(dmi);
					break;
				}
			}
			setStatusDialogRecusaItem(false);
			setItemRecusado(new DevolucaoMedicamentoItem());
		}
	}

	private void atualizarItemRecusado(DevolucaoMedicamentoItem dmi) {
		getDevolvidosMap().remove(dmi);
		dmi.setStatus(TipoStatusDevolucaoItemEnum.RE);
		dmi.setQuantidadeRecebida(0);
	}
	
	public void preRecusarItem(){
		setStatusDialogRecusaItem(true);
	}
	
	public void validarCodigos(){
		String[] codigosArray = codigos.split("\r");
		String codigosNaoEncontrados = "";
		for(String codigo : codigosArray){
			codigo = codigo.replace("\n", "").replace("\r", "");
			if(codigo.isEmpty()){
				continue;
			}
			String[] item = separarLoteQuantidade(codigo);
			String lote = item[0];
			Integer quantidade = Integer.valueOf(item[1]);
			if(!loteDevolucaoEncotradoAtualizado(lote, quantidade)){
				Estoque estoque  = new EstoqueConsultaRaiz().consultarEstoqueLoteCodigoBarras(lote);
				if(estoque != null && estoque.getIdEstoque() != 0){
					if(!adicionarEstoqueDevolucaoExistente(estoque, quantidade)){
						adicionarEstoqueDevolucao(estoque, quantidade);
					}
				}else{
					codigosNaoEncontrados = codigosNaoEncontrados.concat(codigo).concat("\n");
				}
			}
			
		}
		setCodigos(null);
		setCodigos(codigosNaoEncontrados);
	}

	private boolean adicionarEstoqueDevolucao(Estoque estoque, Integer quantidade) {
		for(DevolucaoMedicamentoItem item : getInstancia().getItens()){
			if(item.getMaterial().equals(estoque.getMaterial())){
				EstoqueDevolucao ed = new EstoqueDevolucao();
				ed.setDevolucaoMedicamentoItem(item);
				ed.setEstoque(estoque);
				ed.setQuantidadeDevolvida(quantidade);
				ArrayList<EstoqueDevolucao> listaInicial = new ArrayList<EstoqueDevolucao>();
				listaInicial.add(ed);
				getDevolvidosMap().put(item, listaInicial);
				return true;
			}
		}
		return false;
	}
	
	private boolean adicionarEstoqueDevolucaoExistente(Estoque estoque, Integer quantidade) {
		Set<DevolucaoMedicamentoItem> chaves = getDevolvidosMap().keySet();
		for(DevolucaoMedicamentoItem chave : chaves){
			if(chave.getMaterial().equals(estoque.getMaterial())){
				EstoqueDevolucao ed = new EstoqueDevolucao();
				ed.setDevolucaoMedicamentoItem(chave);
				ed.setEstoque(estoque);
				ed.setQuantidadeDevolvida(quantidade);
				getDevolvidosMap().get(chave).add(ed);
				return true;
			}
		}
		return false;
	}

	private boolean loteDevolucaoEncotradoAtualizado(String lote, Integer quantidade) {
		Set<DevolucaoMedicamentoItem> chaves = getDevolvidosMap().keySet();
		for(DevolucaoMedicamentoItem dm : chaves){
			List<EstoqueDevolucao> lotes = getDevolvidosMap().get(dm);
			for(EstoqueDevolucao ed : lotes){
				if(ed.getEstoque().getLote().equalsIgnoreCase(lote)){
					ed.somarQuantidadeDevolvida(quantidade);
					removerItensVazios(dm, ed);
					return true;
				}
			}
		}
		return false;
	}

	private void removerItensVazios(DevolucaoMedicamentoItem dm, EstoqueDevolucao ed) {
		if(ed.getQuantidadeDevolvida().equals(0)){
			getDevolvidosMap().get(dm).remove(ed);
			if(getDevolvidosMap().get(dm) == null || getDevolvidosMap().get(dm).isEmpty()){
				getDevolvidosMap().remove(dm);
			}
		}
	}

	private String[] separarLoteQuantidade(String codigo) {
		//quando houve uma interrogaçao, está indicado que existe uma quantidade informada
		if(codigo.contains("?"))
			return codigo.split("\\?");
		else
			return new String[]{codigo, "1"};
	}
	
	public void fecharDialogDevolucaoPendente(){
		setStatusDialogJustificativa(false);
		setStatusDialogJustificativaDevolucaoParcial(false);
		setCodigos(null);
		setJustificativa(null);
		setStatusDialogDevolucao(false);
		setJustificativa(null);
		devolucoesPendentes = new ArrayList<DevolucaoMedicamento>();
		devolvidosMap = new HashMap<DevolucaoMedicamentoItem, List<EstoqueDevolucao>>();
		super.novaInstancia();
	}
	
	public void cancelarRecusa(){
		getInstancia().setJustificativa(null);
		setStatusDialogJustificativa(false);
		super.novaInstancia();
	}
	
	public void cancelarRecusaItem(){
		setStatusDialogRecusaItem(false);
		setItemRecusado(null);
	}
	public void recusarDevolucao(){
		if(getInstancia().getJustificativa() == null || getInstancia().getJustificativa().isEmpty()){
			try{
				throw new ExcecaoJustificativaDevolucao();
			}catch(ExcecaoJustificativaDevolucao e){
				e.printStackTrace();
			}
		}else{
			try {
				getInstancia().setStatus(TipoStatusDevolucaoItemEnum.RE);
				getInstancia().setDataRecebimento(new Date());
				getInstancia().setProfissionalConfirmacao(Autenticador.getProfissionalLogado());
				super.atualizar();
				atualizarItensDevolucaoRecusada();
				setStatusDialogJustificativa(false);
				super.novaInstancia();
				new DevolucaoMedicamentoConsultaRaiz().consultarDevolucoesPendentes();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}
	}
	
	private void atualizarItensDevolucaoRecusada() {
		for(int i = 0; i < getInstancia().getItens().size(); i++){
			DevolucaoMedicamentoItem dmi = getInstancia().getItens().get(i);
			dmi.setQuantidadeRecebida(0);
			dmi.setJustificativa(null);
			dmi.setStatus(TipoStatusDevolucaoItemEnum.RE);
			new DevolucaoMedicamentoItemRaiz(dmi).atualizar();
		}
		
	}

	public void preRecusaDevolucao(){
		setStatusDialogJustificativa(true);
	}
	
	public void exibirDialogDevolucao(){
		setStatusDialogDevolucao(true);
	}
	
	private void verificaJustificativaVazia() throws ExcecaoJustificativaDevolucao{
		if(getInstancia().getStatus().equals(TipoStatusDevolucaoItemEnum.RP) && getJustificativa().isEmpty()){
			throw new ExcecaoJustificativaDevolucao();
		}
		getInstancia().setJustificativa(getJustificativa());
	}
	
	public void finalizarDevolucao(){
		try {
			verificaJustificativaVazia();
			atualizarItens();
			atualizarLotes();
			getInstancia().setProfissionalConfirmacao(Autenticador.getProfissionalLogado());
			getInstancia().setDataRecebimento(new Date());
			super.atualizar();
			setStatusDialogJustificativaDevolucaoParcial(false);
			new DevolucaoMedicamentoConsultaRaiz().consultarDevolucoesPendentes();
			ControlePainelAviso.getInstancia().atualizarAvisos();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoJustificativaDevolucao e) {
			e.printStackTrace();
		}
	}
	
	private void atualizarLotes() {
		TipoMovimento tipoMovimentoDevolucaoDispensacao = Parametro.tipoMovimentoDevolucaoDispensacao();
		Set<DevolucaoMedicamentoItem> chaves = getDevolvidosMap().keySet();
		for(DevolucaoMedicamentoItem chave : chaves){
			List<EstoqueDevolucao> itens = getDevolvidosMap().get(chave);
			for(EstoqueDevolucao item : itens){
				DispensacaoSimplesRaiz dsr = new DispensacaoSimplesRaiz();
				dsr.novaInstancia();
				dsr.setAtivarMensagem(false);
				dsr.setLoteEncontrado(true);
				dsr.getInstancia().getMovimentoLivro().setEstoque(item.getEstoque());
				dsr.getInstancia().setUnidadeDispensada(getInstancia().getUnidadeDevolucao());
				dsr.getInstancia().getMovimentoLivro().setQuantidadeMovimentacao(item.getQuantidadeDevolvida());
				dsr.getInstancia().getMovimentoLivro().setTipoMovimento(tipoMovimentoDevolucaoDispensacao);
				dsr.getInstancia().setDevolucaoMedicamentoItem(item.getDevolucaoMedicamentoItem());
				dsr.enviar();
			}
		}
	}

	private void atualizarItens() {
		DevolucaoMedicamentoItemRaiz dmir = new DevolucaoMedicamentoItemRaiz();
		for(int i = 0; i < getInstancia().getItens().size(); i++){
			DevolucaoMedicamentoItem item = getInstancia().getItens().get(i);
			if(!item.getStatus().equals(TipoStatusDevolucaoItemEnum.RE)){
				Integer quantidadeRecebida = quantidadeTotalRecebida(item);
				item.setQuantidadeRecebida(quantidadeRecebida);
				if(item.getQuantidadeRecebida().compareTo(item.getQuantidadeDevolvida()) < 0){
					item.setStatus(TipoStatusDevolucaoItemEnum.RP);
				}else{
					item.setStatus(TipoStatusDevolucaoItemEnum.R);
				}
			}
			dmir.setInstancia(item);
			dmir.setExibeMensagemAtualizacao(false);
			dmir.atualizar();
		}
	}

	public String stringLotesDevolvidos(DevolucaoMedicamentoItem item) {
		Set<DevolucaoMedicamentoItem> chaves = getDevolvidosMap().keySet();
		for(DevolucaoMedicamentoItem dmi : chaves){
			if(dmi.equals(item)){
				List<EstoqueDevolucao> itens = getDevolvidosMap().get(dmi);
				String lotes = "";
				for(EstoqueDevolucao ed : itens){
					lotes += ed.getEstoque().getLote().concat(" - ").concat(String.valueOf(ed.getQuantidadeDevolvida())).concat("<br/>");
				}
				return lotes;
			}
			
		}
		return "";
	}

	
	public Integer quantidadeTotalRecebida(DevolucaoMedicamentoItem item) {
		Set<DevolucaoMedicamentoItem> chaves = getDevolvidosMap().keySet();
		for(DevolucaoMedicamentoItem dmi : chaves){
			if(dmi.equals(item)){
				List<EstoqueDevolucao> itens = getDevolvidosMap().get(dmi);
				Integer total = 0;
				for(EstoqueDevolucao ed : itens){
					total += ed.getQuantidadeDevolvida();
				}
				return total;
			}
			
		}
		return 0;
	}

	public void preFinalizarDevolucao(){
		try {
			if(getDevolvidosMap() == null || getDevolvidosMap().isEmpty() || itensNaoInformados())
				throw new ExcecaoDevolucaoMedicamentoSemItens();
			if(recebidoCompletamente()){
				getInstancia().setStatus(TipoStatusDevolucaoItemEnum.R);
				finalizarDevolucao();
			}else{
				getInstancia().setStatus(TipoStatusDevolucaoItemEnum.RP);
				setStatusDialogJustificativaDevolucaoParcial(true);
			}
		} catch (ExcecaoDevolucaoMedicamentoSemItens e) {
			e.printStackTrace();
		}
	}
	
	private boolean itensNaoInformados() {
		for(DevolucaoMedicamentoItem dmi : getInstancia().getItens()){
			if(dmi.getStatus() == null || (!dmi.getStatus().equals(TipoStatusDevolucaoItemEnum.RE) && !getDevolvidosMap().containsKey(dmi))){
				return true;
			}
		}
		return false;
	}

	private boolean recebidoCompletamente() {
		Set<DevolucaoMedicamentoItem> chaves = getDevolvidosMap().keySet();
		for(DevolucaoMedicamentoItem dmi : getInstancia().getItens()){
			if(dmi.getStatus() != null && !dmi.getStatus().equals(TipoStatusDevolucaoItemEnum.RE)){
				if(chaves.contains(dmi)){
					Integer total = quantidadeTotalRecebida(dmi);
					if(total.compareTo(dmi.getQuantidadeDevolvida()) < 0){
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return true;
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
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setDataInsercao(new Date());
			getInstancia().setStatus(TipoStatusDevolucaoItemEnum.A);
			DevolucaoMedicamentoItemRaiz.getInstanciaAtual().novaInstancia();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	@Override
	public boolean atualizar() {
		try {
			if(new DevolucaoMedicamentoConsultaRaiz().quantidadeItens(getInstancia()) < 1){
				throw new ExcecaoDevolucaoMedicamentoSemItens();
			}
			getInstancia().setDataFechamento(new Date());
			getInstancia().setStatus(TipoStatusDevolucaoItemEnum.P);
			if(super.atualizar()){
				ControlePainelAviso.getInstancia().gerarAvisoRD(getInstancia().getIdDevolucaoMedicamento(), getInstancia().getUnidadeDevolucao());
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoDevolucaoMedicamentoSemItens e) {
			e.printStackTrace();
		}
		return false;
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

	public boolean getStatusDialogJustificativa() {
		return statusDialogJustificativa;
	}

	public void setStatusDialogJustificativa(boolean statusDialogJustificativa) {
		this.statusDialogJustificativa = statusDialogJustificativa;
	}

	public String getCodigos() {
		return codigos;
	}

	public void setCodigos(String codigos) {
		this.codigos = codigos;
	}

	public boolean getStatusDialogJustificativaDevolucaoParcial() {
		return statusDialogJustificativaDevolucaoParcial;
	}

	public void setStatusDialogJustificativaDevolucaoParcial(
			boolean statusDialogJustificativaDevolucaoParcial) {
		this.statusDialogJustificativaDevolucaoParcial = statusDialogJustificativaDevolucaoParcial;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public HashMap<DevolucaoMedicamentoItem, List<EstoqueDevolucao>> getDevolvidosMap() {
		return devolvidosMap;
	}

	public void setDevolvidosMap(HashMap<DevolucaoMedicamentoItem, List<EstoqueDevolucao>> devolucaoMap) {
		this.devolvidosMap = devolucaoMap;
	}

	public boolean getStatusDialogRecusaItem() {
		return statusDialogRecusaItem;
	}

	public void setStatusDialogRecusaItem(boolean statusDialogRecusaItem) {
		this.statusDialogRecusaItem = statusDialogRecusaItem;
	}

	public DevolucaoMedicamentoItem getItemRecusado() {
		return itemRecusado;
	}

	public void setItemRecusado(DevolucaoMedicamentoItem itemRecusado) {
		this.itemRecusado = itemRecusado;
	}

}