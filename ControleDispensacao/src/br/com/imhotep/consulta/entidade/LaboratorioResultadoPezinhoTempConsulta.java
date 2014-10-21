package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioResultadoPezinhoTemp;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class LaboratorioResultadoPezinhoTempConsulta extends PadraoConsulta<LaboratorioResultadoPezinhoTemp> {
	public LaboratorioResultadoPezinhoTempConsulta(){
		getCamposConsulta().put("o.exameIdentificacao", IGUAL);
		getCamposConsulta().put("o.prontuario", IGUAL);
		getCamposConsulta().put("o.dataCadastro", IGUAL_DATA);
		setOrderBy("o.dataCadastro desc");
	}
	
	@Override
	public List<LaboratorioResultadoPezinhoTemp> getList() {
		setConsultaGeral(new ConsultaGeral<LaboratorioResultadoPezinhoTemp>());
		if(isPesquisaValorada())
			getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from LaboratorioResultadoPezinhoTemp o where 1=1"));
		else
			getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from LaboratorioResultadoPezinhoTemp o "
					+ "where to_char(o.dataCadastro, 'YYYY-MM-DD') = to_char(now(), 'YYYY-MM-DD')"));
		return super.getList();
	}
}
