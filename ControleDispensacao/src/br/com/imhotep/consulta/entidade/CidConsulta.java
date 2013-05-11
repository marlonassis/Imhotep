package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Cid;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class CidConsulta extends PadraoConsulta<Cid> {
	public CidConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("o.nome");
	}
	
	@Override
	public List<Cid> getList() {
		setConsultaGeral(new ConsultaGeral<Cid>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Cid o where 1=1"));
		return super.getList();
	}
}
