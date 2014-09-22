package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.remendo.PadraoRaiz;
import br.com.imhotep.entidade.LaboratorioSolicitacao;
import br.com.imhotep.entidade.LaboratorioSolicitacaoLog;
import br.com.imhotep.enums.TipoStatusSolicitacaoExameEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;

@ManagedBean
@SessionScoped
public class LaboratorioSolicitacaoLogRaiz extends PadraoRaiz<LaboratorioSolicitacaoLog>{
	
	public void gerarLogExame(TipoStatusSolicitacaoExameEnum tipo, String justificativa, LaboratorioSolicitacao ls) throws ExcecaoProfissionalLogado, ExcecaoPadraoFluxo {
		LaboratorioSolicitacaoLog log = new LaboratorioSolicitacaoLog();
		log.setDataLog(new Date());
		log.setJustificativa(justificativa);
		log.setLaboratorioSolicitacao(ls);
		log.setProfissionalLog(Autenticador.getProfissionalLogado());
		log.setTipoLog(tipo);
		PadraoFluxoTemp.getObjetoAtualizar().put("LaboratorioSolicitacao-"+ls.hashCode(), ls);
		PadraoFluxoTemp.getObjetoSalvar().put("LaboratorioSolicitacaoLog - "+log.hashCode(), log);
	}
	
}
