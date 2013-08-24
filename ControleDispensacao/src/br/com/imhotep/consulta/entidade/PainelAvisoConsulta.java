package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PainelAviso;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class PainelAvisoConsulta extends PadraoConsulta<PainelAviso> {
	public PainelAvisoConsulta(){
		getCamposConsulta().put("o.dataInicio", IGUAL);
		getCamposConsulta().put("o.dataFim", IGUAL);
		setOrderBy("o.dataInsercao desc");
		carregarResultado();
	}
	
	@Override
	public void carregarResultado() {
		setConsultaGeral(new ConsultaGeral<PainelAviso>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from PainelAviso o where 1=1"));
		super.carregarResultado();
	}
	
}
