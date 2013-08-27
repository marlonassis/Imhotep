package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AutorizaPainel;
import br.com.imhotep.entidade.Painel;
import br.com.imhotep.entidade.Usuario;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean
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
