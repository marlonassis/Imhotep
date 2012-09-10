package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Usuario;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="usuarioConsulta")
@SessionScoped
public class UsuarioConsulta extends PadraoConsulta<Usuario> {
	public UsuarioConsulta(){
		getCamposConsulta().put("o.login", IGUAL);
		setOrderBy("o.login");
	}
	
	@Override
	public List<Usuario> getList() {
		setConsultaGeral(new ConsultaGeral<Usuario>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Usuario o where 1=1"));
		return super.getList();
	}
}
