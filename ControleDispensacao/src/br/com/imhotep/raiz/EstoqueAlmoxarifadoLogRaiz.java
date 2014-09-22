package br.com.imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.EstoqueAlmoxarifadoLog;
import br.com.imhotep.enums.TipoEstoqueAlmoxarifadoLog;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstoqueAlmoxarifadoLogRaiz extends PadraoRaiz<EstoqueAlmoxarifadoLog>{
	
	public EstoqueAlmoxarifadoLog carregarLog(EstoqueAlmoxarifado estoqueAlmoxarifado, Date dataLog, TipoEstoqueAlmoxarifadoLog tipoLog){
		String lote = "Sem Lote";
		if(estoqueAlmoxarifado.getLote() != null && !estoqueAlmoxarifado.getLote().trim().isEmpty())
			lote = estoqueAlmoxarifado.getLote();
		String material = estoqueAlmoxarifado.getMaterialAlmoxarifado().getDescricaoId();
		String dataValidade = "Sem Validade";
		if(estoqueAlmoxarifado.getDataValidade() != null)
			dataValidade = new SimpleDateFormat("dd/MM/yyyy").format(estoqueAlmoxarifado.getDataValidade());
		String codigoBarras = estoqueAlmoxarifado.getCodigoBarras();
		return carregarLog(dataLog, lote, material, tipoLog, dataValidade, codigoBarras);
	}
	
	public EstoqueAlmoxarifadoLog carregarLog(Date data, String lote, 
														String material, TipoEstoqueAlmoxarifadoLog tipoAlteracao, 
														String dataValidade, String codigoBarras) {
		EstoqueAlmoxarifadoLog estoqueLog = new EstoqueAlmoxarifadoLog();
		try {
			estoqueLog.setDataLog(data);
			estoqueLog.setLote(lote);
			estoqueLog.setMaterial(material);
			estoqueLog.setProfissionalAlteracao(Autenticador.getInstancia().getProfissionalAtual());
			estoqueLog.setTipoAlteracao(tipoAlteracao);
			estoqueLog.setDataValidade(dataValidade);
			estoqueLog.setCodigoBarras(codigoBarras);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return estoqueLog;
	}
	
	public void gerarLog(EstoqueAlmoxarifadoLog ...estoqueLog){
		for(EstoqueAlmoxarifadoLog log : estoqueLog){
			gerarLog(log);
		}
	}
	
	public void gerarLog(Date data, String lote, String material, TipoEstoqueAlmoxarifadoLog tipoAlteracao, String dataValidade, String codigoBarras){
		EstoqueAlmoxarifadoLog log = carregarLog(data, lote, material, tipoAlteracao, dataValidade, codigoBarras);
		gerarLog(log);
	}
	
	public void gerarLog(EstoqueAlmoxarifadoLog estoqueLog){
		setInstancia(estoqueLog);
		setExibeMensagemInsercao(false);
		super.enviar();
		super.novaInstancia();
	}
	
}
