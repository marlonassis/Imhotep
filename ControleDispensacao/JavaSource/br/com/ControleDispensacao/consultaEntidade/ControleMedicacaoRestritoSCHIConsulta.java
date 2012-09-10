package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="controleMedicacaoRestritoSCHIConsulta")
@SessionScoped
public class ControleMedicacaoRestritoSCHIConsulta extends PadraoConsulta<ControleMedicacaoRestritoSCHI> {
	public ControleMedicacaoRestritoSCHIConsulta(){
		setOrderBy("o.dataCriacaoAssistente desc");
	}
	
	@Override
	public List<ControleMedicacaoRestritoSCHI> getList() {
		setConsultaGeral(new ConsultaGeral<ControleMedicacaoRestritoSCHI>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from ControleMedicacaoRestritoSCHI o where o.idControleMedicacaoRestritoSCHI in (select a.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI from PrescricaoItem a where a.prescricao.dispensavel = 'S') "));
		return super.getList();
	}
}
