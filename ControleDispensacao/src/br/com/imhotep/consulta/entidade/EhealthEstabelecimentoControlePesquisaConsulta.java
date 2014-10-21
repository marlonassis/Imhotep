package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthEstabelecimento;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;


@ManagedBean
@SessionScoped
public class EhealthEstabelecimentoControlePesquisaConsulta extends PadraoConsulta<EhealthEstabelecimento> {
	
	public EhealthEstabelecimentoControlePesquisaConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.razaoSocial", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoUnidade", IGUAL);
		getCamposConsulta().put("o.tipoNatureza", IGUAL);
		getCamposConsulta().put("o.ehealthMunicipio", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public void carregarResultado(){
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<EhealthEstabelecimento>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EhealthEstabelecimento o where 1=1"));
		super.carregarResultado();
	}
	
}
