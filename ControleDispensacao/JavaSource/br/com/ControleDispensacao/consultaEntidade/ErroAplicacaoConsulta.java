package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Aplicacao;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="aplicacaoConsulta")
@SessionScoped
public class ErroAplicacaoConsulta extends PadraoConsulta<Aplicacao> {
	public ErroAplicacaoConsulta(){
		getCamposConsulta().put("o.dataOcorrencia", MAIOR_IGUAL);
		getCamposConsulta().put("o.message", INCLUINDO_TUDO);
		getCamposConsulta().put("o.stackTrace", INCLUINDO_TUDO);
		getCamposConsulta().put("o.pagina", INCLUINDO_TUDO);
		setOrderBy("o.atendido, o.dataOcorrencia desc");
	}
	
	@Override
	public List<Aplicacao> getList() {
		setConsultaGeral(new ConsultaGeral<Aplicacao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from ErroAplicacao o where 1=1"));
		return super.getList();
	}
}