package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AlmoxarifadoUnidadeCota;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;


@ManagedBean
@SessionScoped
public class AlmoxarifadoUnidadeCotaConsulta extends PadraoConsulta<AlmoxarifadoUnidadeCota> {
	public AlmoxarifadoUnidadeCotaConsulta(){
		getCamposConsulta().put("o.materialAlmoxarifado", IGUAL);
		getCamposConsulta().put("o.unidade", IGUAL);
		setOrderBy("to_ascii(o.unidade.nome), lower(to_ascii(o.materialAlmoxarifado.descricao))");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<AlmoxarifadoUnidadeCota>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AlmoxarifadoUnidadeCota o where 1=1"));
		super.carregarResultado();
	}
}
