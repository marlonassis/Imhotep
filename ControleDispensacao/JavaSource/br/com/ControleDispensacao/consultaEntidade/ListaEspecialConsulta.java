package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ListaEspecial;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="listaEspecialConsulta")
@SessionScoped
public class ListaEspecialConsulta extends PadraoConsulta<ListaEspecial> {
	public ListaEspecialConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.livro", IGUAL);
		getCamposConsulta().put("o.lista", INCLUINDO_TUDO);
		getCamposConsulta().put("o.receitaControlada", IGUAL);
		getCamposConsulta().put("o.medicamentoControlado", IGUAL);
		setOrderBy("to_ascii(o.livro.descricao), to_ascii(o.descricao)");
	}
	
	@Override
	public List<ListaEspecial> getList() {
		setConsultaGeral(new ConsultaGeral<ListaEspecial>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from ListaEspecial o where 1=1"));
		return super.getList();
	}
}
