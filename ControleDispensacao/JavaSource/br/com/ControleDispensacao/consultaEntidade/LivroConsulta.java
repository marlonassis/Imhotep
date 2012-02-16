package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Livro;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="livroConsulta")
@SessionScoped
public class LivroConsulta extends PadraoConsulta<Livro> {
	public LivroConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<Livro> getList() {
		setConsultaGeral(new ConsultaGeral<Livro>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Livro o where 1=1"));
		return super.getList();
	}
}
