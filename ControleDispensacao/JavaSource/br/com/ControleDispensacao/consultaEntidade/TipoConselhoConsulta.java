package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoConselho;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="tipoConselhoConsulta")
@SessionScoped
public class TipoConselhoConsulta extends PadraoConsulta<TipoConselho> {
	public TipoConselhoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.sigla", IGUAL);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<TipoConselho> getList() {
		setConsultaGeral(new ConsultaGeral<TipoConselho>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TipoConselho o where 1=1"));
		return super.getList();
	}
}
