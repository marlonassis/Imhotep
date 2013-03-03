package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Chamado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="chamadoConsulta")
@SessionScoped
public class ChamadoConsulta extends PadraoConsulta<Chamado> {
	public ChamadoConsulta(){
		getCamposConsulta().put("o.assunto", INCLUINDO_TUDO);
		getCamposConsulta().put("o.usuario", IGUAL);
		setOrderBy("o.dataChamado desc");
	}
	
	@Override
	public List<Chamado> getList() {
		setConsultaGeral(new ConsultaGeral<Chamado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Chamado o where 1=1"));
		return super.getList();
	}
}
