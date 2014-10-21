package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Cargo;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class CargoConsulta extends PadraoConsulta<Cargo> {
	
	public CargoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.cargoPai", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Cargo> getList() {
		setConsultaGeral(new ConsultaGeral<Cargo>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Cargo o where 1=1"));
		return super.getList();
	}
}
