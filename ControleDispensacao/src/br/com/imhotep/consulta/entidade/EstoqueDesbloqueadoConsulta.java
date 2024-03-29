package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="estoqueDesbloqueadoConsulta")
@SessionScoped
public class EstoqueDesbloqueadoConsulta extends PadraoConsulta<Estoque> {
	public EstoqueDesbloqueadoConsulta(){
		getCamposConsulta().put("o.fabricante", IGUAL);
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.dataValidade", IGUAL);
		setOrderBy("to_ascii(o.material.descricao), o.lote, o.dataValidade");
	}
	
	@Override
	public List<Estoque> getList() {
		setConsultaGeral(new ConsultaGeral<Estoque>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Estoque o where 1=1"));
		return super.getList();
	}
}
