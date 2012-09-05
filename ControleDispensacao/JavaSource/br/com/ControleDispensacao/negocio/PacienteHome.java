package br.com.ControleDispensacao.negocio;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.auxiliar.Constantes;
import br.com.ControleDispensacao.auxiliar.ControleInstancia;
import br.com.ControleDispensacao.entidade.Paciente;
import br.com.ControleDispensacao.entidade.Unidade;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="pacienteHome")
@SessionScoped
public class PacienteHome extends PadraoHome<Paciente>{
	
	public static PacienteHome getInstanciaHome(){
		return new ControleInstancia<PacienteHome>().instancia("pacienteHome");
	}
	
	public void procurarPaciente() throws IOException{
		String numeroSus = getInstancia().getNumeroSus();
		ConsultaGeral<Paciente> cg = new ConsultaGeral<Paciente>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("numeroSus", numeroSus);
		Paciente paciente = cg.consultaUnica(new StringBuilder("select o from Paciente o where o.numeroSus = :numeroSus"), hm);
		if(paciente != null){
			PacienteEntradaHome.getInstanciaHome().setNumeroSus(numeroSus);
			PacienteEntradaHome.getInstanciaHome().getInstancia().setPaciente(paciente);
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_ENTRADA_PACIENTE);
		}else{
			super.mensagem("O número do SUS informado não está cadastro.", "Verifique se você informou o número certo ou cadastre esse novo usuário.");
		}
	}
	
	/**
	 * Método que retorna uma lista de Estado
	 * @param String sql
	 * @return Collection Estado
	 */
	public Collection<Paciente> getListaPacienteAutoComplete(String busca){
		return super.getBusca("select o from Paciente as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+busca+"%')) ");
	}
	
	@Override
	public boolean enviar() {
		Unidade unidadeAtual = Autenticador.getInstancia().getUnidadeAtual();
		if(unidadeAtual != null){
			getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			getInstancia().setDataInclusao(new Date());
			getInstancia().setUnidadeCadastro(unidadeAtual);
			return super.enviar();
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Você não está alocado em uma unidade", "Escolha uma unidade na combo acima do menu."));
		return false;
	}
	
}
