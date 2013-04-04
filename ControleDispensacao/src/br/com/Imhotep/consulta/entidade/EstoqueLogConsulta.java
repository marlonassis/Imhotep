package br.com.Imhotep.consulta.entidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.EstoqueLog;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class EstoqueLogConsulta extends PadraoConsulta<EstoqueLog> {

	private void carregarCamposPesquisa() {
		setCamposConsulta(new HashMap<String, String>());
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.material", INCLUINDO_TUDO);
		getCamposConsulta().put("o.profissionalAlteracao", IGUAL);
		getCamposConsulta().put("o.dataLog", MAIOR_IGUAL);
		if(getInstancia().getDataLog() != null)
			setOrderBy("o.dataLog asc");
		else
			setOrderBy("o.dataLog desc");
	}
	
	@Override
	public List<EstoqueLog> getList() {
		carregarCamposPesquisa();
		setConsultaGeral(new ConsultaGeral<EstoqueLog>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EstoqueLog o where 1=1"));
		return super.getList();
	}
}
