package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="ajusteEstoqueConsulta")
@SessionScoped
public class AjusteEstoqueConsulta extends PadraoConsulta<Estoque> {
	public AjusteEstoqueConsulta(){
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.fabricante", IGUAL);
		setOrderBy("to_ascii(o.material.descricao), o.lote");
	}
	
	@Override
	public List<Estoque> getList() {
		setConsultaGeral(new ConsultaGeral<Estoque>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Estoque o where 1=1"));
		return super.getList();
	}
}
