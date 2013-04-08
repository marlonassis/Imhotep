package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PrescricaoAntiga;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class PrescricaoAntigaConsulta extends PadraoConsulta<PrescricaoAntiga> {
	public PrescricaoAntigaConsulta(){
		getCamposConsulta().put("o.dataPrescricao", IGUAL);
		getCamposConsulta().put("o.paciente", IGUAL);
		getCamposConsulta().put("o.unidade", IGUAL);
		setOrderBy("to_ascii(o.unidade.nome), to_ascii(o.paciente.nome)");
	}
	
	@Override
	public List<PrescricaoAntiga> getList() {
		setConsultaGeral(new ConsultaGeral<PrescricaoAntiga>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from PrescricaoAntiga o where 1=1"));
		return super.getList();
	}
}
