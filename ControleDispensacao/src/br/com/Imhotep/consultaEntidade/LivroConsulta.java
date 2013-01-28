package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Livro;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="livroConsulta")
@SessionScoped
public class LivroConsulta extends PadraoConsulta<Livro> {
	public LivroConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<Livro> getList() {
		setConsultaGeral(new ConsultaGeral<Livro>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Livro o where 1=1"));
		return super.getList();
	}
}
