package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthEstabelecimento;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;


@ManagedBean
@SessionScoped
public class EhealthEstabelecimentoConsulta extends PadraoConsulta<EhealthEstabelecimento> {
	public EhealthEstabelecimentoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.razaoSocial", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoNatureza", IGUAL);
		getCamposConsulta().put("o.ehealthMunicipio", IGUAL);
		setOrderBy("to_ascii(o.nome), to_ascii(o.especialidade.descricao)");
	}
	
	@Override
	public List<EhealthEstabelecimento> getList() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<EhealthEstabelecimento>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EhealthEstabelecimento o where 1=1"));
		return super.getList();
	}
}
