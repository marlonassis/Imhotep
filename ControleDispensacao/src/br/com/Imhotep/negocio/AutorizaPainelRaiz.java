package br.com.Imhotep.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.AutorizaPainel;
import br.com.Imhotep.entidade.Painel;
import br.com.Imhotep.entidade.Usuario;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="autorizaPainelRaiz")
@SessionScoped
public class AutorizaPainelRaiz extends PadraoHome<AutorizaPainel>{
	
	public List<Usuario> getListaEspecialidadeAutoComplete(String nome){
		ConsultaGeral<Usuario> cg = new ConsultaGeral<Usuario>();
		return new ArrayList<Usuario>(cg.consulta(new StringBuilder("select distinct o.especialidade from AutorizaPainel o where lower(to_ascii(o.especialidade.descricao)) like lower(to_ascii('%"+nome+"%'))"), null));
	}
	
	public List<Painel> getListaPainelAutoComplete(String nome){
		ConsultaGeral<Painel> cg = new ConsultaGeral<Painel>();
		return new ArrayList<Painel>(cg.consulta(new StringBuilder("select distinct o.painel from AutorizaPainel o where lower(to_ascii(o.painel.descricao)) like lower(to_ascii('%"+nome+"%')) "), null));
	}
}
