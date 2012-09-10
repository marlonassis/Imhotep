package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Hospital;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="hospitalConsulta")
@SessionScoped
public class HospitalConsulta extends PadraoConsulta<Hospital> {
	public HospitalConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Hospital> getList() {
		setConsultaGeral(new ConsultaGeral<Hospital>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Hospital o where 1=1"));
		return super.getList();
	}
}
