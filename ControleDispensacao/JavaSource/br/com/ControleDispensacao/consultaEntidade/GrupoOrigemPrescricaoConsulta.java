package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.GrupoOrigemPrescricao;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="grupoOrigemPrescricaoConsulta")
@SessionScoped
public class GrupoOrigemPrescricaoConsulta extends PadraoConsulta<GrupoOrigemPrescricao> {
	public GrupoOrigemPrescricaoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<GrupoOrigemPrescricao> getList() {
		setConsultaGeral(new ConsultaGeral<GrupoOrigemPrescricao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from GrupoOrigemPrescricao o where 1=1"));
		return super.getList();
	}
}
