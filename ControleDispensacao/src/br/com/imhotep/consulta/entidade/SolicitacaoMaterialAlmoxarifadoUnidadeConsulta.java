package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class SolicitacaoMaterialAlmoxarifadoUnidadeConsulta extends PadraoConsulta<SolicitacaoMaterialAlmoxarifadoUnidade> {
	public SolicitacaoMaterialAlmoxarifadoUnidadeConsulta(){
		getCamposConsulta().put("o.idSolicitacaoMaterialAlmoxarifadoUnidade", IGUAL);
		getCamposConsulta().put("o.unidadeDestino", IGUAL);
		getCamposConsulta().put("o.profissionalInsercao", IGUAL);
		getCamposConsulta().put("o.dataInsercao", IGUAL);
		setOrderBy("to_ascii(o.unidadeDestino.nome)");
	}
	
	@Override
	public List<SolicitacaoMaterialAlmoxarifadoUnidade> getList() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SolicitacaoMaterialAlmoxarifadoUnidade o where (o.statusDispensacao = 'D' or o.statusDispensacao = 'DP' or o.statusDispensacao = 'R')"));
		return super.getList();
	}

}