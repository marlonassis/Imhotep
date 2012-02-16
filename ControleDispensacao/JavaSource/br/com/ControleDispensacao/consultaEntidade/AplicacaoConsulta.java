package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Aplicacao;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="aplicacaoConsulta")
@SessionScoped
public class AplicacaoConsulta extends PadraoConsulta<Aplicacao> {
	public AplicacaoConsulta(){
		getCamposConsulta().put("o.executavel", INCLUINDO_TUDO);
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<Aplicacao> getList() {
		setConsultaGeral(new ConsultaGeral<Aplicacao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Aplicacao o where 1=1"));
		return super.getList();
	}
}
