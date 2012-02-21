package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Paciente;
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
		return super.getBusca("select o from Paciente as o where o.nome like '%"+busca+"%' ");
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setUsuarioAlteracao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
