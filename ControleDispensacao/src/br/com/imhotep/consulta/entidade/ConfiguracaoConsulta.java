package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Configuracao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class ConfiguracaoConsulta extends PadraoConsulta<Configuracao> {
	public ConfiguracaoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Configuracao> getList() {
		setConsultaGeral(new ConsultaGeral<Configuracao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Configuracao o where 1=1"));
		return super.getList();
	}
}
