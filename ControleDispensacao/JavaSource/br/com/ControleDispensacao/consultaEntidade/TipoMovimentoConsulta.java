package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoMovimento;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="tipoMovimentoConsulta")
@SessionScoped
public class TipoMovimentoConsulta extends PadraoConsulta<TipoMovimento> {
	public TipoMovimentoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<TipoMovimento> getList() {
		setConsultaGeral(new ConsultaGeral<TipoMovimento>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TipoMovimento o where 1=1"));
		return super.getList();
	}
}
