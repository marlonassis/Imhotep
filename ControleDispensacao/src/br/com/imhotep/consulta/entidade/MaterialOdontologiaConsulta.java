package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.MaterialOdontologia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class MaterialOdontologiaConsulta extends PadraoConsulta<MaterialOdontologia> {
	public MaterialOdontologiaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public void carregarResultado() {
		setConsultaGeral(new ConsultaGeral<MaterialOdontologia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from MaterialOdontologia o where 1=1 "));
		super.carregarResultado();
	}
	
}
