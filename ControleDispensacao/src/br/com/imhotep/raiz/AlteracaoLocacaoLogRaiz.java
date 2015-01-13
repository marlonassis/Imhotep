package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AlteracaoLocacaoLog;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.enums.TipoCrudEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AlteracaoLocacaoLogRaiz extends PadraoRaiz<AlteracaoLocacaoLog>{
	
	private Profissional profissional;
	private boolean exibirDialogLog;
	private List<AlteracaoLocacaoLog> logList = new ArrayList<AlteracaoLocacaoLog>();
	
	public void exibirDialogLog(){
		setExibirDialogLog(true);
		carregarLog();
	}

	private void carregarLog() {
		int id = getProfissional().getIdProfissional();
		String hql = "select o from AlteracaoLocacaoLog o where o.profissional.idProfissional = " + id + " order by o.dataCadastro desc";
		Collection<AlteracaoLocacaoLog> consulta = new ConsultaGeral<AlteracaoLocacaoLog> (new StringBuilder(hql)).consulta();
		setLogList(new ArrayList<AlteracaoLocacaoLog>(consulta));
	}
	
	public void ocultarDialogLog(){
		setExibirDialogLog(false);
		setLogList(new ArrayList<AlteracaoLocacaoLog>());
	}
	
	
	public AlteracaoLocacaoLog montarLog(String estrutura, String justificativa, Profissional profissional, TipoCrudEnum tipo){
		AlteracaoLocacaoLog log = new AlteracaoLocacaoLog();
		log.setDataCadastro(new Date());
		log.setEstrutura(estrutura);
		log.setJustificativa(justificativa);
		log.setProfissional(profissional);
		log.setProfissionalCadastro(getProfissionalLogado());
		log.setTipo(tipo);
		return log;
	}

	private Profissional getProfissionalLogado() {
		try {
			return Autenticador.getProfissionalLogado();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		return null;
	}

	public Profissional getProfissional() {
		return profissional;
	}
	
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public boolean isExibirDialogLog() {
		return exibirDialogLog;
	}

	public void setExibirDialogLog(boolean exibirDialogLog) {
		this.exibirDialogLog = exibirDialogLog;
	}

	public List<AlteracaoLocacaoLog> getLogList() {
		return logList;
	}

	public void setLogList(List<AlteracaoLocacaoLog> logList) {
		this.logList = logList;
	}
}
