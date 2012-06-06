package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.entidade.Paciente;
import br.com.ControleDispensacao.entidade.Unidade;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="pacienteHome")
@SessionScoped
public class PacienteHome extends PadraoHome<Paciente>{
	
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
