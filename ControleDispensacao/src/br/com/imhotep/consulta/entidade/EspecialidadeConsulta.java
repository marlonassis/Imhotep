package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Especialidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class EspecialidadeConsulta extends PadraoConsulta<Especialidade> {
	public EspecialidadeConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoConselho", IGUAL);
		getCamposConsulta().put("o.especialidadePai", IGUAL);
		setOrderBy("to_ascii(lower(o.descricao))");
	}
	
	@Override
	public List<Especialidade> getList() {
		setConsultaGeral(new ConsultaGeral<Especialidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Especialidade o where 1=1"));
		return super.getList();
	}
}
