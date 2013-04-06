package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.Collection;
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

@ManagedBean(name="pacienteRaiz")
@SessionScoped
public class PacienteRaiz extends PadraoHome<Paciente>{
	
	public static PacienteRaiz getInstanciaHome() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (PacienteRaiz) new ControleInstancia().procuraInstancia(PacienteRaiz.class);
	}
	
	public void procurarPaciente() throws IOException{
		try {
			String numeroSus = getInstancia().getNumeroSus();
			ConsultaGeral<Paciente> cg = new ConsultaGeral<Paciente>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("numeroSus", numeroSus);
			Paciente paciente = cg.consultaUnica(new StringBuilder("select o from Paciente o where o.numeroSus = :numeroSus"), hm);
			if(paciente != null){
				PacienteEntradaRaiz.getInstanciaHome().setNumeroSus(numeroSus);
				PacienteEntradaRaiz.getInstanciaHome().getInstancia().setPaciente(paciente);
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_ENTRADA_PACIENTE);
			}else{
				super.mensagem("O número do SUS informado não está cadastrado.", "Verifique se você informou o número certo ou cadastre esse novo usuário.", FacesMessage.SEVERITY_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Ocorreu um problema ao procurar o paciente", "", FacesMessage.SEVERITY_ERROR);
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
	
}
