package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Psicologia;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class PsicologiaConsulta extends PadraoConsulta<Psicologia> {
	
	public PsicologiaConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		setOrderBy("o.dataAvaliacao desc");
	}
	
	@Override
	public List<Psicologia> getList() {
		setConsultaGeral(new ConsultaGeral<Psicologia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Psicologia o where 1=1"));
		return super.getList();
	}
	
}
