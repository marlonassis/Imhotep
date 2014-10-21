package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Prescricao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="prescricaoPendenteUsuarioConsulta")
@SessionScoped
public class PrescricaoPendenteUsuarioConsulta extends PadraoConsulta<Prescricao> {
	public PrescricaoPendenteUsuarioConsulta(){
		setOrderBy("o.dataInclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.dataConclusao is null and o.dataBloqueio is null"));
		return super.getList();
	}
	
}
