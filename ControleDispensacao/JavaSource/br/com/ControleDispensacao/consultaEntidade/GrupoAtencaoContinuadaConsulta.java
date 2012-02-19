package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.GrupoAtencaoContinuada;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="grupoAtencaoContinuadaConsulta")
@SessionScoped
public class GrupoAtencaoContinuadaConsulta extends PadraoConsulta<GrupoAtencaoContinuada> {
	public GrupoAtencaoContinuadaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<GrupoAtencaoContinuada> getList() {
		setConsultaGeral(new ConsultaGeral<GrupoAtencaoContinuada>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from GrupoAtencaoContinuada o where 1=1"));
		return super.getList();
	}
}
