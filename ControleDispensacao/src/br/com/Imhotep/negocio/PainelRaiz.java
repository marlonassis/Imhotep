package br.com.Imhotep.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Painel;
import br.com.remendo.PadraoHome;

@ManagedBean(name="painelRaiz")
@SessionScoped
public class PainelRaiz extends PadraoHome<Painel>{
	
	public Collection<Painel> getListaPainelAutoComplete(String sql){
		return super.getBusca("select o from Painel as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
