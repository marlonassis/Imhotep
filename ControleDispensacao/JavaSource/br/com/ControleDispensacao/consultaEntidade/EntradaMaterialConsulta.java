package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ItensMovimentoGeral;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="entradaMaterialConsulta")
@SessionScoped
public class EntradaMaterialConsulta extends PadraoConsulta<ItensMovimentoGeral> {
	public EntradaMaterialConsulta(){
		getCamposConsulta().put("o.material", INCLUINDO_TUDO);
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.fabricante", INCLUINDO_TUDO);
		getCamposConsulta().put("o.dataValidade", IGUAL);
		setOrderBy("o.material.descricao");
	}
	
	@Override
	public List<ItensMovimentoGeral> getList() {
		setConsultaGeral(new ConsultaGeral<ItensMovimentoGeral>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from ItensMovimentoGeral o where 1=1"));
		return super.getList();
	}
}
