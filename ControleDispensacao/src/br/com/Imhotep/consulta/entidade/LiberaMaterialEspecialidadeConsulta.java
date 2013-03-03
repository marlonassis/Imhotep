package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.LiberaMaterialEspecialidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="liberaMaterialEspecialidadeConsulta")
@SessionScoped
public class LiberaMaterialEspecialidadeConsulta extends PadraoConsulta<LiberaMaterialEspecialidade> {
	public LiberaMaterialEspecialidadeConsulta(){
		getCamposConsulta().put("o.material", INCLUINDO_TUDO);
		getCamposConsulta().put("o.especialidade", IGUAL);
		setOrderBy("to_ascii(o.especialidade.descricao), to_ascii(o.material.descricao)");
	}
	
	@Override
	public List<LiberaMaterialEspecialidade> getList() {
		setConsultaGeral(new ConsultaGeral<LiberaMaterialEspecialidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from LiberaMaterialEspecialidade o where 1=1"));
		return super.getList();
	}
}
