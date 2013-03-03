package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;
import br.com.Imhotep.entidade.Cidade;

@ManagedBean(name="cidadeConsulta")
@SessionScoped
public class CidadeConsulta extends PadraoConsulta<Cidade> {
	public CidadeConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Cidade> getList() {
		setConsultaGeral(new ConsultaGeral<Cidade>());
		String sql = "select o from Cidade o where 1=1";
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
		return super.getList();
	}
}
