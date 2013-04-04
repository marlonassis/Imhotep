package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.AvaliacaoPsicologica;
import br.com.Imhotep.entidade.AvaliacaoPsicologicaLog;
import br.com.Imhotep.entidade.Paciente;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class AvaliacaoPsicologicaLogConsulta extends PadraoConsulta<AvaliacaoPsicologicaLog> {
	
	private Paciente paciente;
	
	private void carregarCamposConsulta(){
		if(paciente != null){
			getInstancia().setAvaliacaoPsicologica(new AvaliacaoPsicologica());
			getInstancia().getAvaliacaoPsicologica().setPaciente(getPaciente());
		}
		getCamposConsulta().put("o.avaliacaoPsicologica.paciente", IGUAL);
		getCamposConsulta().put("o.dataModificacao", MAIOR_IGUAL);
		getCamposConsulta().put("o.profissionalModificador", IGUAL);
		if(getInstancia().getDataModificacao() != null)
			setOrderBy("o.dataModificacao asc");
		else
			setOrderBy("o.dataModificacao desc");
	}
	
	@Override
	public List<AvaliacaoPsicologicaLog> getList() {
		carregarCamposConsulta();
		setConsultaGeral(new ConsultaGeral<AvaliacaoPsicologicaLog>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AvaliacaoPsicologicaLog o where 1=1"));
		return super.getList();
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}
