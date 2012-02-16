package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoMaterial;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

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
