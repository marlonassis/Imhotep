package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AlteracaoEstruturaLog;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AlteracaoEstruturaLogRaiz extends PadraoRaiz<AlteracaoEstruturaLog>{
	
	private EstruturaOrganizacional estruturaOrganizacional;
	private boolean exibirDialogLog;
	private List<AlteracaoEstruturaLog> logList = new ArrayList<AlteracaoEstruturaLog>();
	
	public void carregarDados(EstruturaOrganizacional estruturaOrganizacional){
		setEstruturaOrganizacional(estruturaOrganizacional);
		carregarLog();
	}
	
	public void exibirDialogLog(){
		setExibirDialogLog(true);
		carregarLog();
	}

	private void carregarLog() {
		int id = getEstruturaOrganizacional().getIdEstruturaOrganizacional();
		String hql = "select o from AlteracaoEstruturaLog o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id + " order by o.dataCadastro desc";
		Collection<AlteracaoEstruturaLog> consulta = new ConsultaGeral<AlteracaoEstruturaLog> (new StringBuilder(hql)).consulta();
		setLogList(new ArrayList<AlteracaoEstruturaLog>(consulta));
	}
	
	public void ocultarDialogLog(){
		setExibirDialogLog(false);
		setLogList(new ArrayList<AlteracaoEstruturaLog>());
	}
	
	
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}
	
	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	public boolean isExibirDialogLog() {
		return exibirDialogLog;
	}

	public void setExibirDialogLog(boolean exibirDialogLog) {
		this.exibirDialogLog = exibirDialogLog;
	}

	public List<AlteracaoEstruturaLog> getLogList() {
		return logList;
	}

	public void setLogList(List<AlteracaoEstruturaLog> logList) {
		this.logList = logList;
	}
}
