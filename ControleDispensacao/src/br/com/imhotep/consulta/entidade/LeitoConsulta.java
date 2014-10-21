package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Leito;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class LeitoConsulta extends PadraoConsulta<Leito> {
	public LeitoConsulta(){
		getCamposConsulta().put("o.unidade", IGUAL);
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.patrimonio", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
		carregarResultado();
	}
	
	@Override
	public List<Leito> getList(){
		setConsultaGeral(new ConsultaGeral<Leito>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Leito o where o.statusLeito != 'E' "));
		return super.getList();
	}
}
