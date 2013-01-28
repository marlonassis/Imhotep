package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.ErroAplicacao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="aplicacaoConsulta")
@SessionScoped
public class ErroAplicacaoConsulta extends PadraoConsulta<ErroAplicacao> {
	public ErroAplicacaoConsulta(){
		getCamposConsulta().put("o.dataOcorrencia", MAIOR_IGUAL);
		getCamposConsulta().put("o.message", INCLUINDO_TUDO);
		getCamposConsulta().put("o.stackTrace", INCLUINDO_TUDO);
		getCamposConsulta().put("o.pagina", INCLUINDO_TUDO);
		setOrderBy("o.atendido, o.dataOcorrencia desc");
	}
	
	@Override
	public List<ErroAplicacao> getList() {
		setConsultaGeral(new ConsultaGeral<ErroAplicacao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from ErroAplicacao o where 1=1"));
		return super.getList();
	}
}
