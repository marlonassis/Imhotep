package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Estado;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="estadoConsulta")
@SessionScoped
public class EstadoConsulta extends PadraoConsulta<Estado> {
	public EstadoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("o.nome");
	}
	
	@Override
	public List<Estado> getList() {
		setConsultaGeral(new ConsultaGeral<Estado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Estado o where 1=1"));
		return super.getList();
	}
}
