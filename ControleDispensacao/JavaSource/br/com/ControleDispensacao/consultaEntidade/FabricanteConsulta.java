package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Fabricante;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="fabricanteConsulta")
@SessionScoped
public class FabricanteConsulta extends PadraoConsulta<Fabricante> {
	public FabricanteConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<Fabricante> getList() {
		setConsultaGeral(new ConsultaGeral<Fabricante>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Fabricante o where 1=1"));
		return super.getList();
	}
}
