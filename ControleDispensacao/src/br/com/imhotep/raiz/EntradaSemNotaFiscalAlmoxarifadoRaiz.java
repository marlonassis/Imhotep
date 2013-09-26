package br.com.imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifado;
import br.com.imhotep.entidade.AjusteEstoqueAlmoxarifado;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EntradaSemNotaFiscalAlmoxarifadoRaiz extends PadraoHome<AjusteEstoqueAlmoxarifado>{
	
	private Boolean loteEncontrado;
	private Integer quantidadeMovimentada;
	
	public EntradaSemNotaFiscalAlmoxarifadoRaiz() {
		limpar();
	}

	private void limpar() {
		getInstancia().setMovimentoLivroAlmoxarifado(new MovimentoLivroAlmoxarifado());
		getInstancia().getMovimentoLivroAlmoxarifado().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		setLoteEncontrado(null);
		setQuantidadeMovimentada(null);
	}
	
	public void procurarLote(){
		String lote = getInstancia().getMovimentoLivroAlmoxarifado().getEstoqueAlmoxarifado().getLote();
		if(lote == null || lote.isEmpty()){
			setLoteEncontrado(false);
		}else{
			EstoqueAlmoxarifado estoque = new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoqueLivre(lote);
			loteEncontrado = estoque != null;
			if(loteEncontrado){
				getInstancia().getMovimentoLivroAlmoxarifado().setEstoqueAlmoxarifado(estoque);
			}else{
				mensagem("Lote não encontrado.", lote, Constantes.WARN);
			}
		}
	}
	
	private void varificaItemSemLote() {
		EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getMovimentoLivroAlmoxarifado().getEstoqueAlmoxarifado();
		String lote = estoqueAlmoxarifado.getLote();
		if(lote == null || lote.trim().equals("")){
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idMaterial", estoqueAlmoxarifado.getMaterialAlmoxarifado().getIdMaterialAlmoxarifado());
			hm.put("idFabricante", estoqueAlmoxarifado.getFabricanteAlmoxarifado().getIdFabricanteAlmoxarifado());
			hm.put("dataValidade", new SimpleDateFormat("yyyy-MM-dd").format(estoqueAlmoxarifado.getDataValidade()));
			hm.put("codigoBarras", estoqueAlmoxarifado.getCodigoBarras());
			String hql = "select o.idEstoqueAlmoxarifado from EstoqueAlmoxarifado o where o.fabricanteAlmoxarifado.idFabricanteAlmoxarifado = :idFabricante and "
					+ "o.materialAlmoxarifado.idMaterialAlmoxarifado = :idMaterial and "
					+ "to_char(o.dataValidade, 'yyyy-MM-dd') = :dataValidade and "
					+ "o.codigoBarras = :codigoBarras";
			Integer id = new ConsultaGeral<Integer>(new StringBuilder(hql), hm).consultaUnica();
			getInstancia().getMovimentoLivroAlmoxarifado().getEstoqueAlmoxarifado().setIdEstoqueAlmoxarifado((int) (id==null ? 0 : id));	
		}
	}
	
	@Override
	public boolean enviar(){
		try {
			varificaItemSemLote();
			int qtd = getQuantidadeMovimentada() == null ? 0 : getQuantidadeMovimentada();
			EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getMovimentoLivroAlmoxarifado().getEstoqueAlmoxarifado();
			MovimentoLivroAlmoxarifado mla = new ControleEstoqueAlmoxarifado().validarEstoqueAlmoxarifado(estoqueAlmoxarifado, qtd, Parametro.tipoMovimentoEntradaSemNotaFiscalAlmoxarifado());
			getInstancia().setMovimentoLivroAlmoxarifado(mla);
			getInstancia().setJustificativa(Constantes.JUSTIFICATIVA_ENTRADA_SEM_NOTA);
			addAjusteFluxo();
			processarFluxo();
			limparFluxo();
			novaInstancia();
			return true;
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
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
	
}
