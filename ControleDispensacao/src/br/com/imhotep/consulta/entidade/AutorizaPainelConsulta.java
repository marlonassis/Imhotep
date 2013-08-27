package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AutorizaPainel;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class AutorizaPainelConsulta extends PadraoConsulta<AutorizaPainel> {
	public AutorizaPainelConsulta(){
		getCamposConsulta().put("o.especialidade", IGUAL);
		getCamposConsulta().put("o.painel", IGUAL);
		setOrderBy("to_ascii(o.especialidade.descricao), o.painel.url");
	}
	
	@Override
	public List<AutorizaPainel> getList() {
		setConsultaGeral(new ConsultaGeral<AutorizaPainel>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AutorizaPainel o where 1=1"));
		return super.getList();
	}
}
