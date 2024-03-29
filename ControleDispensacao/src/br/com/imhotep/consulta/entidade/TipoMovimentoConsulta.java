package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoMovimento;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class TipoMovimentoConsulta extends PadraoConsulta<TipoMovimento> {
	public TipoMovimentoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoOperacao", IGUAL);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<TipoMovimento> getList() {
		setConsultaGeral(new ConsultaGeral<TipoMovimento>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TipoMovimento o where 1=1"));
		return super.getList();
	}
}
