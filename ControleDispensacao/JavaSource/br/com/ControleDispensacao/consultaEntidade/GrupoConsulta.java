package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Grupo;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="grupoConsulta")
@SessionScoped
public class GrupoConsulta extends PadraoConsulta<Grupo> {
	public GrupoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<Grupo> getList() {
		setConsultaGeral(new ConsultaGeral<Grupo>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Grupo o where 1=1"));
		return super.getList();
	}
}
