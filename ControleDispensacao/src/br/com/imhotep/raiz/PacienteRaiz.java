package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.excecoes.ExcecaoPacienteDataNascimentoFuturo;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PacienteRaiz extends PadraoRaiz<Paciente>{
	
	private String valorPesquisa;
	private Boolean achouPaciente;
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setAchouPaciente(null);
	}
	
	public static PacienteRaiz getInstanciaHome() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (PacienteRaiz) new ControleInstancia().procuraInstancia(PacienteRaiz.class);
	}
	
	public void procurarPaciente() throws IOException{
		try {
			ConsultaGeral<Paciente> cg = new ConsultaGeral<Paciente>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("valor", getValorPesquisa());
			Paciente paciente = cg.consultaUnica(new StringBuilder("select o from Paciente o where o.numeroSus = :valor or o.prontuario = :valor"), hm);
			if(paciente != null){
				PacienteEntradaRaiz.getInstanciaHome().setValorPesquisa(getValorPesquisa());
				PacienteEntradaRaiz.getInstanciaHome().getInstancia().setPaciente(paciente);
				PacienteEntradaRaiz.getInstanciaHome().carregarUltimoAntendimento();
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_ENTRADA_PACIENTE);
			}else{
				setAchouPaciente(false);
				super.mensagem("O número do SUS informado não está cadastrado.", "Verifique se você informou o número certo ou cadastre esse novo usuário.", FacesMessage.SEVERITY_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Ocorreu um problema ao procurar o paciente", "", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	@Override
	public boolean atualizar() {
		if(getInstancia().getProfissionalInclusao() == null){
			try {
				getInstancia().setProfissionalInclusao(Autenticador.getProfissionalLogado());
				if(getInstancia().getDataNascimento().after(Calendar.getInstance().getTime())){
					throw new ExcecaoPacienteDataNascimentoFuturo();
				}
				getInstancia().setDataInclusao(new Date());
				return super.atualizar();
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			} catch (ExcecaoPacienteDataNascimentoFuturo e) {
				e.printStackTrace();
			}
		}
		
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		try {
			if(getInstancia().getDataNascimento().after(Calendar.getInstance().getTime())){
				throw new ExcecaoPacienteDataNascimentoFuturo();
			}
			getInstancia().setDataInclusao(new Date());
			getInstancia().setProfissionalInclusao(Autenticador.getProfissionalLogado());
			return super.enviar();
		} catch (ExcecaoPacienteDataNascimentoFuturo e) {
			e.printStackTrace();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getValorPesquisa() {
		return valorPesquisa;
	}

	public void setValorPesquisa(String valorPesquisa) {
		this.valorPesquisa = valorPesquisa;
	}

	public Boolean getAchouPaciente() {
		return achouPaciente;
	}

	public void setAchouPaciente(Boolean achouPaciente) {
		this.achouPaciente = achouPaciente;
	}
	
}
