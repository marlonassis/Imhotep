package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstoqueAlmoxarifadoLog;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class EstoqueAlmoxarifadoLogConsulta extends PadraoConsulta<EstoqueAlmoxarifadoLog> {
	public EstoqueAlmoxarifadoLogConsulta(){
		setOrderBy("o.dataLog, o.tipoAlteracao");
	}
	
	@Override
	public List<EstoqueAlmoxarifadoLog> getList() {
		setConsultaGeral(new ConsultaGeral<EstoqueAlmoxarifadoLog>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EstoqueAlmoxarifadoLog o where 1=1"));
		return super.getList();
	}
}
