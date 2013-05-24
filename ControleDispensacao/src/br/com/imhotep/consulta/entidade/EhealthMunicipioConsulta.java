package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthMunicipio;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;


@ManagedBean
@SessionScoped
public class EhealthMunicipioConsulta extends PadraoConsulta<EhealthMunicipio> {
	public EhealthMunicipioConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.ehealthEstado", IGUAL);
		setOrderBy("to_ascii(o.ehealthEstado.nome), to_ascii(o.nome)");
	}
	
	@Override
	public List<EhealthMunicipio> getList() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<EhealthMunicipio>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EhealthMunicipio o where 1=1"));
		return super.getList();
	}
}
