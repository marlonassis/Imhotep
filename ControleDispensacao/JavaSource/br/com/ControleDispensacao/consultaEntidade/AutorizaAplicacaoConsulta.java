package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.AutorizaAplicacao;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="autorizaAplicacaoConsulta")
@SessionScoped
public class AutorizaAplicacaoConsulta extends PadraoConsulta<AutorizaAplicacao> {
	public AutorizaAplicacaoConsulta(){
		getCamposConsulta().put("o.usuario", INCLUINDO_TUDO);
		getCamposConsulta().put("o.aplicacao", INCLUINDO_TUDO);
		setOrderBy("o.usuario.nome");
	}
	
	@Override
	public List<AutorizaAplicacao> getList() {
		setConsultaGeral(new ConsultaGeral<AutorizaAplicacao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AutorizaAplicacao o where 1=1"));
		return super.getList();
	}
}
