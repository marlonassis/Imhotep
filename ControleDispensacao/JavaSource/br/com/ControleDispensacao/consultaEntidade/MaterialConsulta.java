package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Material;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="materialConsulta")
@SessionScoped
public class MaterialConsulta extends PadraoConsulta<Material> {
	public MaterialConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.codigoMaterial", IGUAL);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<Material> getList() {
		setConsultaGeral(new ConsultaGeral<Material>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Material o where 1=1 "));
		return super.getList();
	}
}
