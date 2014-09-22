package br.com.imhotep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.PainelAvisoDataInicioComparador;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.PainelAviso;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.raiz.EstoqueRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;

@ManagedBean
@SessionScoped
public class ControlePainelAviso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Set<PainelAviso> avisos = new HashSet<PainelAviso>();
	private List<PainelAviso> avisosNaoMonitorado = new ArrayList<PainelAviso>();
	
	public static ControlePainelAviso getInstancia(){
		try {
			return (ControlePainelAviso) Utilitarios.procuraInstancia(ControlePainelAviso.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void atualizarAvisos(){
		StringBuilder sb = new StringBuilder();
		try {
			Integer idProfissional = Autenticador.getProfissionalLogado().getIdProfissional();
			sb = new StringBuilder("select o from PainelAviso o ");  
			sb.append("where ");
			sb.append("(o.idPainelAviso in (select a.painelAviso.idPainelAviso from PainelAvisoEspecialidade a where a.especialidade.idEspecialidade in "); 
			sb.append("(select c.idEspecialidade from Profissional b join b.especialidades c where b.idProfissional = "+idProfissional+")) ");
			sb.append("or ");
			sb.append("o.idPainelAviso not in (select a.painelAviso.idPainelAviso from PainelAvisoEspecialidade a )) ");
			sb.append("and o.dataInicio <= now()  ");
			sb.append("and o.dataFim >= now() ");
			sb.append("and o.liberado = true");
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		setAvisos(new HashSet<PainelAviso>(new ConsultaGeral<PainelAviso>(sb).consulta()));
		carregarAvisosAutomaticos();
		getAvisos().addAll(getAvisosNaoMonitorado());
	}
	
	private void carregarAvisosAutomaticos(){
		ControlePainel cp = ControlePainel.getInstancia();
		if(cp.getPainelAutorizadoStringList().contains(Constantes.PAINEL_MEDICAMENTO_VENCIDO)){
			avisosPainelFarmacia();
		}
	}

	public void gerarAvisoRE(Integer idSolicitacao){
		String aviso = "Nº da RE: "+idSolicitacao;
		PainelAviso pa = gerarAvisoAutomatico(aviso);
//		setAvisosNaoMonitorado(new ArrayList<PainelAviso>());
		getAvisosNaoMonitorado().add(pa);
		atualizarAvisos();
	}
	
	public void gerarAvisoRM(Integer idSolicitacao, Unidade unidade){
		String aviso = "Nº da RM: "+idSolicitacao+" - "+unidade.getSigla();
		PainelAviso pa = gerarAvisoAutomatico(aviso);
//		setAvisosNaoMonitorado(new ArrayList<PainelAviso>());
		getAvisosNaoMonitorado().add(pa);
		atualizarAvisos();
	}
	
	public void gerarAvisoRD(Integer idDevolucao, Unidade unidade){
		String aviso = "Nº da RD: "+idDevolucao+" - "+unidade.getSigla();
		PainelAviso pa = gerarAvisoAutomatico(aviso);
//		setAvisosNaoMonitorado(new ArrayList<PainelAviso>());
		getAvisosNaoMonitorado().add(pa);
		atualizarAvisos();
	}
	
	private void avisosPainelFarmacia() {
		carregarAvisoSolicitacoesPendentes();
		carregarAvisoDevolucoesPendentes();
	}

	private void carregarAvisoDevolucoesPendentes() {
		EstoqueRaiz.getInstanciaAtual().setEstoqueVencido(new EstoqueConsultaRaiz().consultarEstoqueVencidoLimiteSeteDias());
		Long quantidadeDevolucoesPendentes = quantidadeDevolucoesPendentes();
		if(quantidadeDevolucoesPendentes > 0){
			String avisoQuantidadeDevolucoesPendentes = quantidadeDevolucoesPendentes + " devolução(ões) pendente(s)";
			PainelAviso pa = gerarAvisoAutomatico(avisoQuantidadeDevolucoesPendentes);
			getAvisos().add(pa);
		}
	}
	
	private void carregarAvisoSolicitacoesPendentes() {
		Long quantidadeSolicitacoesPendentes = quantidadeSolicitacoesPendentes();
		if(quantidadeSolicitacoesPendentes > 0){
			String avisoQuantidadeSolicitacoesPendentes = quantidadeSolicitacoesPendentes + " solicitação(ões) pendente(s)";
			PainelAviso pa = gerarAvisoAutomatico(avisoQuantidadeSolicitacoesPendentes);
			getAvisos().add(pa);
		}
	}

	private PainelAviso gerarAvisoAutomatico(String avisoQuantidadeSolicitacoesPendentes) {
		PainelAviso pa = new PainelAviso();
		pa.setDataInicio(new Date());
		pa.setDataInsercao(new Date());
		try {
			pa.setProfissionalInsercao(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		pa.setDescricao(avisoQuantidadeSolicitacoesPendentes);
		return pa;
	}
	
	private Long quantidadeDevolucoesPendentes() {
		StringBuilder sb = new StringBuilder("select coalesce(count(o), 0) from DevolucaoMedicamento o where o.status = 'P'");
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		return cg.consultaUnica(sb, null);
	}
	
	private Long quantidadeSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select coalesce(count(o), 0) from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'P'");
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		return cg.consultaUnica(sb, null);
	}
	
	public List<PainelAviso> getAvisosList() {
		List<PainelAviso> list = new ArrayList<PainelAviso>(avisos);
		Collections.sort(list, new PainelAvisoDataInicioComparador());
		return list;
	}
	
	public Set<PainelAviso> getAvisos() {
		return avisos;
	}
	
	public void setAvisos(Set<PainelAviso> avisos) {
		this.avisos = avisos;
	}

	public List<PainelAviso> getAvisosNaoMonitorado() {
		return avisosNaoMonitorado;
	}

	public void setAvisosNaoMonitorado(List<PainelAviso> avisosNaoMonitorado) {
		this.avisosNaoMonitorado = avisosNaoMonitorado;
	}
	
	
	
}
