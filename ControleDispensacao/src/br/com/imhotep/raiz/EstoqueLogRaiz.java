package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstoqueLog;
import br.com.imhotep.enums.TipoEstoqueLog;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EstoqueLogRaiz extends PadraoHome<EstoqueLog>{
	
	public static EstoqueLog carregarLog(Date data, String lote, String material, TipoEstoqueLog tipoAlteracao, String dataValidade) {
		EstoqueLog estoqueLog = new EstoqueLog();
		try {
			estoqueLog.setDataLog(data);
			estoqueLog.setLote(lote);
			estoqueLog.setMaterial(material);
			estoqueLog.setProfissionalAlteracao(Autenticador.getInstancia().getProfissionalAtual());
			estoqueLog.setTipoAlteracao(tipoAlteracao);
			estoqueLog.setDataValidade(dataValidade);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return estoqueLog;
	}
	
	public void gerarLog(EstoqueLog ...estoqueLog){
		for(EstoqueLog log : estoqueLog){
			gerarLog(log);
		}
	}
	
	public void gerarLog(Date data, String lote, String material, TipoEstoqueLog tipoAlteracao, String dataValidade){
		EstoqueLog log = EstoqueLogRaiz.carregarLog(data, lote, material, tipoAlteracao, dataValidade);
		gerarLog(log);
	}
	
	public void gerarLog(EstoqueLog estoqueLog){
		setInstancia(estoqueLog);
		setExibeMensagemInsercao(false);
		super.enviar();
		super.novaInstancia();
	}
	
}
