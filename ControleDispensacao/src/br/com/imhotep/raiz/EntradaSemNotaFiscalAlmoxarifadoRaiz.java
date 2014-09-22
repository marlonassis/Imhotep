package br.com.imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EntradaSemNotaFiscalAlmoxarifadoRaiz extends PadraoRaiz<MovimentoLivroAlmoxarifado>{
	
	private Boolean loteEncontrado;
	private Integer quantidadeMovimentada;
	private Double precoMedio;
	
	public EntradaSemNotaFiscalAlmoxarifadoRaiz() {
		limpar();
	}

	private void limpar() {
		setInstancia(new MovimentoLivroAlmoxarifado());
		getInstancia().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		setLoteEncontrado(null);
		setQuantidadeMovimentada(null);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getEstoqueAlmoxarifado().getLote();
		if(lote == null || lote.isEmpty()){
			setLoteEncontrado(false);
		}else{
			EstoqueAlmoxarifado estoque = new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoqueLivre(lote);
			loteEncontrado = estoque != null;
			if(loteEncontrado){
				getInstancia().setEstoqueAlmoxarifado(estoque);
			}else{
				mensagem("Lote n‹o encontrado.", lote, Constantes.WARN);
			}
		}
	}
	
	private void limparCodigoBarras(){
		EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getEstoqueAlmoxarifado();
		if(estoqueAlmoxarifado.getCodigoBarras() == ""){
			estoqueAlmoxarifado.setCodigoBarras(null);
		}
	}
	
	private void varificaItemSemLote() {
		EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getEstoqueAlmoxarifado();
		String lote = estoqueAlmoxarifado.getLote();
		if(lote == null || lote.trim().equals("")){
			String validade = estoqueAlmoxarifado.getDataValidade() == null ? null : "'"+new SimpleDateFormat("yyyy-MM-dd").format(estoqueAlmoxarifado.getDataValidade())+"'";
			String codigoBarras = estoqueAlmoxarifado.getCodigoBarras() == null ? null : "'"+estoqueAlmoxarifado.getCodigoBarras()+"'";
			Integer fabricante = estoqueAlmoxarifado.getFabricanteAlmoxarifado() == null ? null : estoqueAlmoxarifado.getFabricanteAlmoxarifado().getIdFabricanteAlmoxarifado();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idMaterial", estoqueAlmoxarifado.getMaterialAlmoxarifado().getIdMaterialAlmoxarifado());
			String hql = "select o from EstoqueAlmoxarifado o where "
					+ "o.fabricanteAlmoxarifado.idFabricanteAlmoxarifado = "+fabricante+" and "
					+ "o.materialAlmoxarifado.idMaterialAlmoxarifado = :idMaterial and "
					+ "to_char(o.dataValidade, 'yyyy-MM-dd') = "+validade+" and "
					+ "o.codigoBarras = "+codigoBarras;
			EstoqueAlmoxarifado obj = new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder(hql), hm).consultaUnica();
			if(obj != null)
				getInstancia().setEstoqueAlmoxarifado(obj);	
		}
	}
	
	//MŽtodo temporariamente bloqueado
	@Override
	public boolean enviar(){
		try {
			if(true)
				throw new Exception(); 
			limparCodigoBarras();
			varificaItemSemLote();
			int qtd = getQuantidadeMovimentada() == null ? 0 : getQuantidadeMovimentada();
			EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getEstoqueAlmoxarifado();
//			MovimentoLivroAlmoxarifado mla = new ControleEstoqueAlmoxarifadoTemp().validarEstoqueAlmoxarifado(estoqueAlmoxarifado, qtd, Parametro.tipoMovimentoEntradaSemNotaFiscalAlmoxarifado());
//			setInstancia(mla);
			getInstancia().setJustificativa(Constantes.JUSTIFICATIVA_ENTRADA_SEM_NOTA);
			addAjusteFluxo();
			processarFluxo();
			limparFluxo();
			unlockEstoque();
//			gerarPrecoMedioTransportado();
			novaInstancia();
			return true;
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	private void unlockEstoque() {
		try {
			new ControleEstoqueAlmoxarifadoTemp().unLockEstoque(getInstancia().getEstoqueAlmoxarifado());
		} catch (ExcecaoEstoqueUnLock e1) {
			e1.printStackTrace();
		}
	}
	
	private void addAjusteFluxo() {
		PadraoFluxoTemp.getObjetoAtualizar().put("ajusteEstoque", getInstancia());
	}
	
	private void processarFluxo() throws ExcecaoPadraoFluxo {
		new PadraoFluxoTemp().processarFluxo();
	}

	private void limparFluxo() {
		PadraoFluxoTemp.limparFluxo();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		limpar();
	}
	
	public Boolean getLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(Boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}

	public Integer getQuantidadeMovimentada() {
		return quantidadeMovimentada;
	}

	public void setQuantidadeMovimentada(Integer quantidadeMovimentada) {
		this.quantidadeMovimentada = quantidadeMovimentada;
	}
	
	public Double getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}
}
