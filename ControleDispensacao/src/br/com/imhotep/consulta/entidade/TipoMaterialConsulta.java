package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoMaterial;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="tipoMaterialConsulta")
@SessionScoped
public class TipoMaterialConsulta extends PadraoConsulta<TipoMaterial> {
	public TipoMaterialConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<TipoMaterial> getList() {
		setConsultaGeral(new ConsultaGeral<TipoMaterial>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TipoMaterial o where 1=1"));
		return super.getList();
	}
}
