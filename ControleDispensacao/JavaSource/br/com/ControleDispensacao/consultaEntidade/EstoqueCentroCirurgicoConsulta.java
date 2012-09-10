package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.EstoqueCentroCirurgico;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="estoqueCentroCirurgicoConsulta")
@SessionScoped
public class EstoqueCentroCirurgicoConsulta extends PadraoConsulta<EstoqueCentroCirurgico> {
	public EstoqueCentroCirurgicoConsulta(){
		getCamposConsulta().put("o.fabricante", IGUAL);
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.dataValidade", IGUAL);
		setOrderBy("o.material.descricao, o.lote, o.dataValidade");
	}
	
	@Override
	public List<EstoqueCentroCirurgico> getList() {
		setConsultaGeral(new ConsultaGeral<EstoqueCentroCirurgico>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EstoqueCentroCirurgico o where o.bloqueado = 'N'"));
		return super.getList();
	}
}
