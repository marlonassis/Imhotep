package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoProfissional;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="tipoProfissionalConsulta")
@SessionScoped
public class TipoProfissionalConsulta extends PadraoConsulta<TipoProfissional> {
	public TipoProfissionalConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoConselho", IGUAL);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<TipoProfissional> getList() {
		setConsultaGeral(new ConsultaGeral<TipoProfissional>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TipoProfissional o where 1=1"));
		return super.getList();
	}
}
