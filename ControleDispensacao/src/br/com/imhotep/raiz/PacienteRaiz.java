package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PacienteRaiz extends PadraoHome<Paciente>{
	
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
		if(getInstancia().getProfissionalInclusao() == null || getInstancia().getUnidadeCadastro() == null){
			try {
				getInstancia().setProfissionalInclusao(Autenticador.getInstancia().getProfissionalAtual());
				getInstancia().setUnidadeCadastro(Autenticador.getInstancia().getUnidadeAtual());
				return super.atualizar();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			return super.atualizar();
		}
		super.mensagem("Erro ao atualizar", null, Constantes.ERROR);
		return false;
	}
	
	@Override
	public boolean enviar() {
		Unidade unidadeAtual = null;
		try {
			unidadeAtual = Autenticador.getInstancia().getUnidadeAtual();
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar a unidade atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em EstoqueCentroCirurgico");
		}
		if(unidadeAtual != null){
			try {
				getInstancia().setProfissionalInclusao(Autenticador.getInstancia().getProfissionalAtual());
			} catch (Exception e) {
				e.printStackTrace();
				super.mensagem("Erro ao pegar a usuário atual.", null, FacesMessage.SEVERITY_ERROR);
				System.out.print("Erro em PacienteHome");
			}
			getInstancia().setDataInclusao(new Date());
			getInstancia().setUnidadeCadastro(unidadeAtual);
			return super.enviar();
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Você não está alocado em uma unidade", "Escolha uma unidade na combo acima do menu."));
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
