package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Painel;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="painelConsulta")
@SessionScoped
public class PainelConsulta extends PadraoConsulta<Painel> {
	public PainelConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.url", INCLUINDO_TUDO);
		setOrderBy("o.url desc");
	}
	
	@Override
	public List<Painel> getList() {
		setConsultaGeral(new ConsultaGeral<Painel>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Painel o where 1=1"));
		return super.getList();
	}
}
