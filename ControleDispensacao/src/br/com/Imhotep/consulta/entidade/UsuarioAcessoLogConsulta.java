package br.com.Imhotep.consulta.entidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Profissional;
import br.com.Imhotep.entidade.Usuario;
import br.com.Imhotep.entidade.UsuarioAcessoLog;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class UsuarioAcessoLogConsulta extends PadraoConsulta<UsuarioAcessoLog> {
	
	private Profissional profissional;
	private Usuario usuario;
	
	private void carregarCamposConsulta() {
		getInstancia().setUsuario(getUsuario());
		if(getProfissional() != null){
			if(getInstancia().getUsuario()==null)
				getInstancia().setUsuario(getProfissional().getUsuario());
		}
		
		setCamposConsulta(new HashMap<String, String>());
		getCamposConsulta().put("o.usuario.profissional", IGUAL);
		getCamposConsulta().put("o.usuario", IGUAL);
		getCamposConsulta().put("o.tipoLog", IGUAL);
		getCamposConsulta().put("o.dataLog", MAIOR_IGUAL);
		if(getInstancia().getDataLog() != null)
			setOrderBy("o.dataLog asc");
		else
			setOrderBy("o.dataLog desc");
	}
	
	@Override
	public List<UsuarioAcessoLog> getList() {
		carregarCamposConsulta();
		setConsultaGeral(new ConsultaGeral<UsuarioAcessoLog>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from UsuarioAcessoLog o where 1=1"));
		return super.getList();
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
