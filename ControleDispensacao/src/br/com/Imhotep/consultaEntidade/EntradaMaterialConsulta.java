package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="entradaMaterialConsulta")
@SessionScoped
public class EntradaMaterialConsulta extends PadraoConsulta<Estoque> {
	public EntradaMaterialConsulta(){
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.fabricante", IGUAL);
		getCamposConsulta().put("o.dataValidade", IGUAL);
		setOrderBy("to_ascii(o.material.descricao), o.lote");
	}
	
	@Override
	public List<Estoque> getList() {
		setConsultaGeral(new ConsultaGeral<Estoque>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Estoque o where o.bloqueado = false"));
		return super.getList();
	}
}
