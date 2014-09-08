package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class TipoMovimentoAlmoxarifadoConsulta extends PadraoConsulta<TipoMovimentoAlmoxarifado> {
	public TipoMovimentoAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoOperacao", IGUAL);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<TipoMovimentoAlmoxarifado> getList() {
		setConsultaGeral(new ConsultaGeral<TipoMovimentoAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TipoMovimentoAlmoxarifado o where 1=1"));
		return super.getList();
	}
}
