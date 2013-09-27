package br.com.imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifado;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoDataContabil;
import br.com.imhotep.excecoes.ExcecaoEstoqueRepetidoNotaFiscal;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class NotaFiscalAlmoxarifadoRaiz extends PadraoHome<NotaFiscalAlmoxarifado>{	
	private NotaFiscalEstoqueAlmoxarifado item = new NotaFiscalEstoqueAlmoxarifado();
	private Boolean achouLote = null;
	private Integer quantidadeMovimentada;
	
	public NotaFiscalAlmoxarifadoRaiz(){
		super();
		novaInstancia();
	}
	
	@Override
	public boolean enviar() {
		try {
			validarDataContabil();
			return super.enviar();
		} catch (ExcecaoDataContabil e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void validarDataContabil() throws ExcecaoDataContabil {
		Calendar dataContabil = Calendar.getInstance();
		dataContabil.setTime(Utilitarios.ajustarZeroHoraDia(getInstancia().getDataContabil()));
		Calendar mesAtual = Calendar.getInstance();
		mesAtual.set(Calendar.DAY_OF_MONTH, 01);
		mesAtual.setTime(Utilitarios.ajustarZeroHoraDia(mesAtual.getTime()));
		if(dataContabil.before(mesAtual)){
			throw new ExcecaoDataContabil();
		}
	}

	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setItem(new NotaFiscalEstoqueAlmoxarifado());
		getItem().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		setAchouLote(null);
	}
	
	public void procurarLote(){
		String lote = getItem().getEstoqueAlmoxarifado().getLote();
		if(lote == null || lote.trim().equals("")){
			super.mensagem("Lote não informado", null, Constantes.INFO);
			setAchouLote(false);
			return;
		}
		
		String sql2 = "select a from NotaFiscalEstoqueAlmoxarifado a where a.estoqueAlmoxarifado.lote = '"+lote+"' and a.notaFiscalAlmoxarifado.idNotaFiscalAlmoxarifado="+getInstancia().getIdNotaFiscalAlmoxarifado();
		List<EstoqueAlmoxarifado> busca = new EstoqueAlmoxarifadoRaiz().getBusca(sql2);
		if(busca != null && busca.size() > 0){
			setAchouLote(null);
			super.mensagem("Lote já cadastrado para esta nota-fiscal", null, Constantes.ERROR);
		}else{
			String sql = "select o from EstoqueAlmoxarifado o where o.lote = '"+lote+"'";
			busca = new EstoqueAlmoxarifadoRaiz().getBusca(sql);
			if(busca != null && busca.size() > 0){
				getItem().setEstoqueAlmoxarifado(busca.get(0));
				setAchouLote(true);
			}else{
				super.mensagem("Lote não encontrado", null, Constantes.WARN);
				setAchouLote(false);
			}
		}
	}
	
	public void addItemNotaFiscal(){
		try {
			varificaItemSemLote();
			getItem().setNotaFiscalAlmoxarifado(getInstancia());
			getItem().setQuantidadeEntrada(getQuantidadeMovimentada());
			new NotaFiscalEstoqueAlmoxarifadoRaiz().salvarItem(getItem(), getQuantidadeMovimentada());
			processarFluxo();
			limparFluxo();
			finalizarAddItemNotaFiscal();
		} catch (Exception e){
			unlockEstoque();
			e.printStackTrace();
		}
	}

	private void unlockEstoque() {
		try {
			new ControleEstoqueAlmoxarifado().unLockEstoque(getItem().getEstoqueAlmoxarifado());
		} catch (ExcecaoEstoqueUnLock e1) {
			e1.printStackTrace();
		}
	}

	private void processarFluxo() throws ExcecaoPadraoFluxo {
		new PadraoFluxoTemp().processarFluxo();
	}

	private void limparFluxo() {
		PadraoFluxoTemp.limparFluxo();
	}
	
	private void finalizarAddItemNotaFiscal() {
		unlockEstoque();
		getInstancia().getItens().add(getItem());
		setItem(new NotaFiscalEstoqueAlmoxarifado());
		getItem().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		setAchouLote(null);
		setQuantidadeMovimentada(null);
	}
	
	private void varificaItemSemLote() throws ExcecaoEstoqueRepetidoNotaFiscal {
		String lote = getItem().getEstoqueAlmoxarifado().getLote();
		if(lote == null || lote.trim().equals("")){
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idMaterial", getItem().getEstoqueAlmoxarifado().getMaterialAlmoxarifado().getIdMaterialAlmoxarifado());
			hm.put("idFabricante", getItem().getEstoqueAlmoxarifado().getFabricanteAlmoxarifado().getIdFabricanteAlmoxarifado());
			hm.put("dataValidade", new SimpleDateFormat("yyyy-MM-dd").format(getItem().getEstoqueAlmoxarifado().getDataValidade()));
			hm.put("codigoBarras", getItem().getEstoqueAlmoxarifado().getCodigoBarras());
			String hql = "select o from EstoqueAlmoxarifado o where o.fabricanteAlmoxarifado.idFabricanteAlmoxarifado = :idFabricante and "
					+ "o.materialAlmoxarifado.idMaterialAlmoxarifado = :idMaterial and "
					+ "to_char(o.dataValidade, 'yyyy-MM-dd') = :dataValidade and "
					+ "o.codigoBarras = :codigoBarras";
			EstoqueAlmoxarifado ea = new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder(hql), hm).consultaUnica();
			if(ea != null && ea.getIdEstoqueAlmoxarifado() != 0){
				String sql2 = "select a from NotaFiscalEstoqueAlmoxarifado a where a.estoqueAlmoxarifado.idEstoqueAlmoxarifado = "+ea.getIdEstoqueAlmoxarifado()+" and a.notaFiscalAlmoxarifado.idNotaFiscalAlmoxarifado="+getInstancia().getIdNotaFiscalAlmoxarifado();
				List<EstoqueAlmoxarifado> busca = new EstoqueAlmoxarifadoRaiz().getBusca(sql2);
				if(busca != null && busca.size() > 0){
					throw new ExcecaoEstoqueRepetidoNotaFiscal();
				}else					
					getItem().setEstoqueAlmoxarifado(ea);
			}
		}
	}

	public void bloquearNotaFiscal(){
		getInstancia().setBloqueada(false);
		super.atualizar();
		super.novaInstancia();
	}
	
	public void cancelarItem(){
		setItem(new NotaFiscalEstoqueAlmoxarifado());
		getItem().setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
		setAchouLote(null);
	}
	
	public NotaFiscalEstoqueAlmoxarifado getItem() {
		return item;
	}

	public void setItem(NotaFiscalEstoqueAlmoxarifado item) {
		this.item = item;
	}

	public Boolean getAchouLote() {
		return achouLote;
	}

	public void setAchouLote(Boolean achouLote) {
		this.achouLote = achouLote;
	}

	public Integer getQuantidadeMovimentada() {
		return quantidadeMovimentada;
	}

	public void setQuantidadeMovimentada(Integer quantidadeMovimentada) {
		this.quantidadeMovimentada = quantidadeMovimentada;
	}
		
	
}
