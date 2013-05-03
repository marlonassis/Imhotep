package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.entidade.PacienteEntrada;
import br.com.imhotep.entidade.PacienteEntradaResponsavel;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PacienteEntradaRaiz extends PadraoHome<PacienteEntrada>{
	
	private String numeroSus;
	
	@Override
	public void novaInstancia() {
		Paciente paciente = getInstancia().getPaciente();
		super.novaInstancia();
		getInstancia().setPaciente(paciente);
		getInstancia().setPacienteEntradaResponsavel(new PacienteEntradaResponsavel());
	}
	
	public static PacienteEntradaRaiz getInstanciaHome(){
		try {
			return (PacienteEntradaRaiz) new ControleInstancia().procuraInstancia(PacienteEntradaRaiz.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void preEnvio() {
		getInstancia().setDataInclusao(new Date());
		try {
			getInstancia().setProfissionalInclusao(Autenticador.getInstancia().getProfissionalAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o profissional atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em PacienteEntradaHome");
		}
		super.preEnvio();
	}
	
	public void carregaPaciente() throws IOException{
		String numeroSus = getNumeroSus();
		ConsultaGeral<Paciente> cg = new ConsultaGeral<Paciente>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("numeroSus", numeroSus);
		Paciente paciente = (Paciente) cg.consultaUnica(new StringBuilder("select o from Paciente o where o.numeroSus = :numeroSus"), hm);
		if(paciente == null){
			setInstancia(new PacienteEntrada());
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_ENTRADA_PACIENTE);
			super.mensagem("O número do SUS informado não está cadastro.", "Verifique se você informou o número certo ou cadastre esse novo usuário.", FacesMessage.SEVERITY_ERROR);
		}else{
			getInstancia().setPaciente(paciente);
			carregarUltimoAntendimento();
		}
	}
	
	public void carregarUltimoAntendimento() {
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("paciente", getInstancia().getPaciente().getIdPaciente());
		String sql = "select o from PacienteEntrada o where o.paciente.idPaciente = :paciente and o.dataInclusao = (select max(a.dataInclusao) from PacienteEntrada a where a.paciente.idPaciente = :paciente)";
		ConsultaGeral<PacienteEntrada> cg = new ConsultaGeral<PacienteEntrada>();
		PacienteEntrada pe = (PacienteEntrada) cg.consultaUnica(new StringBuilder(sql), hm);
		setInstancia(pe.clone());
	}
	
	public List<PacienteEntrada> getEntradasPaciente(){
		if(getInstancia().getPaciente() != null){
			ConsultaGeral<PacienteEntrada> cg = new ConsultaGeral<PacienteEntrada>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPaciente", getInstancia().getPaciente().getIdPaciente());
			return new ArrayList<PacienteEntrada>(cg.consulta(new StringBuilder("select o from PacienteEntrada o where o.paciente.idPaciente = :idPaciente"), hm));
		}
		return null;
	}
	
	public String getNumeroSus() {
		return numeroSus;
	}

	public void setNumeroSus(String numeroSus) {
		this.numeroSus = numeroSus;
	}

	public int idPacienteAtual() {
		if(getInstancia().getPaciente() != null)
			return getInstancia().getPaciente().getIdPaciente();
		
		return 0;
	}
	
}
