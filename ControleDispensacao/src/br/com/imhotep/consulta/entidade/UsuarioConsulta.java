package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Usuario;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class UsuarioConsulta extends PadraoConsulta<Usuario> {
	public UsuarioConsulta(){
		getCamposConsulta().put("o.login", INCLUINDO_TUDO);
		getCamposConsulta().put("o.profissional", IGUAL);
		setOrderBy("o.profissional.nome");
	}
	
	@Override
	public List<Usuario> getList() {
		setConsultaGeral(new ConsultaGeral<Usuario>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Usuario o where 1=1"));
		return super.getList();
	}
}
