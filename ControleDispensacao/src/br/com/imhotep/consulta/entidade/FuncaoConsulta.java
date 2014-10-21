package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Funcao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class FuncaoConsulta extends PadraoConsulta<Funcao> {
	
	public FuncaoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.funcaoPai", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Funcao> getList() {
		setConsultaGeral(new ConsultaGeral<Funcao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Funcao o where 1=1"));
		return super.getList();
	}
}
