package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.GrupoOrigemPrescricao;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="grupoOrigemPrescricaoHome")
@SessionScoped
public class GrupoOrigemPrescricaoHome extends PadraoHome<GrupoOrigemPrescricao>{
	
	/**
	 * Método que retorna uma lista de GrupoOrigemPrescricao
	 * @param String sql
	 * @return Collection GrupoOrigemPrescricao
	 */
	public Collection<GrupoOrigemPrescricao> getListaGrupoOrigemPrescricaoAutoComplete(String sql){
		return super.getBusca("select o from GrupoOrigemPrescricao as o where o.descricao like '%"+sql+"%' ");
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
