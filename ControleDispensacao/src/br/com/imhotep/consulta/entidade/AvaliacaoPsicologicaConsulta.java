package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AvaliacaoPsicologica;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class AvaliacaoPsicologicaConsulta extends PadraoConsulta<AvaliacaoPsicologica> {
	
	public AvaliacaoPsicologicaConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		setOrderBy("o.dataAvaliacao desc");
	}
	
	@Override
	public List<AvaliacaoPsicologica> getList() {
		setConsultaGeral(new ConsultaGeral<AvaliacaoPsicologica>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AvaliacaoPsicologica o where 1=1"));
		return super.getList();
	}
	
}
