package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioSolicitacaoItem;
import br.com.imhotep.entidade.LaboratorioSolicitacaoItemLog;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameItemEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LaboratorioSolicitacaoItemLogRaiz extends PadraoRaiz<LaboratorioSolicitacaoItemLog>{
	
	public void gerarLogExame(TipoStatusSolicitacaoExameItemEnum tipo, String justificativa, LaboratorioSolicitacaoItem lsi) throws ExcecaoProfissionalLogado, ExcecaoPadraoFluxo {
		LaboratorioSolicitacaoItemLog log = new LaboratorioSolicitacaoItemLog();
		log.setDataLog(new Date());
		log.setJustificativa(justificativa);
		log.setLaboratorioSolicitacaoItem(lsi);
		log.setProfissionalLog(Autenticador.getProfissionalLogado());
		log.setTipoLog(tipo);
		PadraoFluxoTemp.getObjetoAtualizar().put("LaboratorioSolicitacaoItem-"+lsi.hashCode(), lsi);
		PadraoFluxoTemp.getObjetoSalvar().put("LaboratorioSolicitacaoItemLog - "+log.hashCode(), log);
	}
	
}
