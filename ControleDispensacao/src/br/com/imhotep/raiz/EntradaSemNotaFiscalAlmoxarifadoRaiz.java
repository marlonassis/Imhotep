package br.com.imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.excecoes.ExcecaoItemSemQuantidade;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EntradaSemNotaFiscalAlmoxarifadoRaiz extends PadraoRaiz<MovimentoLivroAlmoxarifado>{
	
	private boolean exibirDialogLoteEncontrado;
	private boolean iniciarCadastro;
	private EstoqueAlmoxarifado estoqueEscolhido;
	private List<EstoqueAlmoxarifado> estoquesEncontrados;
	
	public void setEcolherLote(EstoqueAlmoxarifado estoqueAlmoxarifado){
		setIniciarCadastro(true); 
		this.estoqueEscolhido = estoqueAlmoxarifado;
	}
	
	public EntradaSemNotaFiscalAlmoxarifadoRaiz(){
		prepararVariaveis();
	}

	@Override
	public void novaInstancia() {
		super.novaInstancia();
		prepararVariaveis();
	}
	
	private void prepararVariaveis() {
		setEstoqueEscolhido(null);
		setEstoquesEncontrados(null);
		setIniciarCadastro(false);
		getInstancia().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
	}
	
	public void exibirDialogLoteEncontrado(){
		setExibirDialogLoteEncontrado(true);
	}
	
	public void ocultarDialogLoteEncontrado(){
		setExibirDialogLoteEncontrado(false);
		setIniciarCadastro(true);
	}
	
	public void usarLoteEncontrado(){
		getInstancia().setEstoqueAlmoxarifado(getEstoqueEscolhido());
		ocultarDialogLoteEncontrado();
		setIniciarCadastro(true);
	}
	
	public void procurarLote() {
		EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getEstoqueAlmoxarifado();
		String lote = estoqueAlmoxarifado.getLote();
		if(lote != null && !lote.trim().equals("")){
			String hql = "select o from EstoqueAlmoxarifado o where o.lote = '"+lote+"'";
			Collection<EstoqueAlmoxarifado> consulta = new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder(hql), new HashMap<Object, Object>()).consulta();
			if(consulta != null && !consulta.isEmpty()){
				setEstoquesEncontrados(new ArrayList<EstoqueAlmoxarifado>(consulta));
				exibirDialogLoteEncontrado();
			}
		}else{
			getInstancia().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
			setIniciarCadastro(true);
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
	
	@Override
	public boolean enviar() {
		try {
			verificarLoteDuplicadoMesmoMaterial();
			verificaQuantidadeInformada();
			varificaItemSemLote();
			getInstancia().setDataMovimento(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
			getInstancia().setTipoMovimentoAlmoxarifado(Parametro.tipoMovimentoEntradaSemNotaFiscalAlmoxarifado());
			new ControleEstoqueAlmoxarifadoTemp().entradaSemNota(getInstancia());
			super.novaInstancia();
			prepararVariaveis();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				new ControleEstoqueAlmoxarifadoTemp().unLockEstoque(getInstancia().getEstoqueAlmoxarifado());
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			} 
		}
		return false;
	}

	private void verificarLoteDuplicadoMesmoMaterial() {
		EstoqueAlmoxarifado estoqueAlmoxarifado = getInstancia().getEstoqueAlmoxarifado();
		String validade = estoqueAlmoxarifado.getDataValidade() == null ? null : "'"+new SimpleDateFormat("yyyy-MM-dd").format(estoqueAlmoxarifado.getDataValidade())+"'";
		String lote = estoqueAlmoxarifado.getLote() == null ? null : "'"+estoqueAlmoxarifado.getLote()+"'";
		Integer fabricante = estoqueAlmoxarifado.getFabricanteAlmoxarifado() == null ? null : estoqueAlmoxarifado.getFabricanteAlmoxarifado().getIdFabricanteAlmoxarifado();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idMaterial", estoqueAlmoxarifado.getMaterialAlmoxarifado().getIdMaterialAlmoxarifado());
		String hql = "select o from EstoqueAlmoxarifado o where "
				+ "o.fabricanteAlmoxarifado.idFabricanteAlmoxarifado = "+fabricante+" and "
				+ "o.materialAlmoxarifado.idMaterialAlmoxarifado = :idMaterial and "
				+ "to_char(o.dataValidade, 'yyyy-MM-dd') = "+validade+" and "
				+ "o.lote = "+lote;
		EstoqueAlmoxarifado obj = new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder(hql), hm).consultaUnica();
		if(obj != null)
			getInstancia().setEstoqueAlmoxarifado(obj);	
	}

	private void verificaQuantidadeInformada() throws ExcecaoItemSemQuantidade {
		if(getInstancia().getQuantidadeMovimentacao() == null || getInstancia().getQuantidadeMovimentacao().intValue() == 0)
			throw new ExcecaoItemSemQuantidade();
	}
	
	public boolean isExibirDialogLoteEncontrado() {
		return exibirDialogLoteEncontrado;
	}

	public void setExibirDialogLoteEncontrado(boolean exibirDialogLoteEncontrado) {
		this.exibirDialogLoteEncontrado = exibirDialogLoteEncontrado;
	}

	public List<EstoqueAlmoxarifado> getEstoquesEncontrados() {
		return estoquesEncontrados;
	}

	public void setEstoquesEncontrados(List<EstoqueAlmoxarifado> estoquesEncontrados) {
		this.estoquesEncontrados = estoquesEncontrados;
	}

	public EstoqueAlmoxarifado getEstoqueEscolhido() {
		return estoqueEscolhido;
	}

	public void setEstoqueEscolhido(EstoqueAlmoxarifado estoqueEscolhido) {
		this.estoqueEscolhido = estoqueEscolhido;
	}

	public boolean isIniciarCadastro() {
		return iniciarCadastro;
	}

	public void setIniciarCadastro(boolean iniciarCadastro) {
		this.iniciarCadastro = iniciarCadastro;
	}


}
