package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="estoqueBloqueadoConsulta")
@SessionScoped
public class EstoqueBloqueadoConsulta extends PadraoConsulta<Estoque> {
	public EstoqueBloqueadoConsulta(){
		getCamposConsulta().put("o.fabricante", IGUAL);
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.dataValidade", IGUAL);
		setOrderBy("o.lote, to_ascii(o.material.descricao), o.dataValidade");
	}
	
	@Override
	public List<Estoque> getList() {
		setConsultaGeral(new ConsultaGeral<Estoque>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Estoque o where o.bloqueado = true "));
		return super.getList();
	}
}
