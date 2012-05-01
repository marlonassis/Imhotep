package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Painel;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="painelHome")
@SessionScoped
public class PainelHome extends PadraoHome<Painel>{
	
	public Collection<Painel> getListaPainelAutoComplete(String sql){
		return super.getBusca("select o from Painel as o where lower(o.descricao) like lower('%"+sql+"%') ");
	}
	
}