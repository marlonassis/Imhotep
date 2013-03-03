package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="unidadeConsulta")
@SessionScoped
public class UnidadeConsulta extends PadraoConsulta<Unidade> {
	public UnidadeConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Unidade> getList() {
		setConsultaGeral(new ConsultaGeral<Unidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Unidade o where 1=1"));
		return super.getList();
	}
}