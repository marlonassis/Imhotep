package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="estoqueConsulta")
@SessionScoped
public class EstoqueConsulta extends PadraoConsulta<Estoque> {
	public EstoqueConsulta(){
		getCamposConsulta().put("o.fabricante", IGUAL);
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.dataValidade", IGUAL);
		setOrderBy("o.lote, o.material.descricao, o.dataValidade");
	}
	
	@Override
	public List<Estoque> getList() {
		setConsultaGeral(new ConsultaGeral<Estoque>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Estoque o where 1=1"));
		return super.getList();
	}
}
