package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidade;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidadeItem;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SolicitacaoMaterialAlmoxarifadoUnidadeItemConsultaRaiz  extends ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidadeItem>{

	public Double totalReservardo(MaterialAlmoxarifado material) {
		String hql = "select coalesce(sum(o.quantidadeSolicitada), 0) from SolicitacaoMaterialAlmoxarifadoUnidadeItem o where "
				+ "(o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'P' or o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'A') and o.materialAlmoxarifado.idMaterialAlmoxarifado = "+material.getIdMaterialAlmoxarifado();
		Double total = new ConsultaGeral<Double>().consultaUnica(new StringBuilder(hql), null);
		return total;
	}
	
	public Double totalReservardo(MaterialAlmoxarifado material, SolicitacaoMaterialAlmoxarifadoUnidade sma) {
		String hql = "select coalesce(sum(o.quantidadeSolicitada), 0) from SolicitacaoMaterialAlmoxarifadoUnidadeItem o where "
				+ "(o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'P' or o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'A') and o.solicitacaoMaterialAlmoxarifadoUnidade.idSolicitacaoMaterialAlmoxarifadoUnidade != "+sma.getIdSolicitacaoMaterialAlmoxarifadoUnidade()+" and o.materialAlmoxarifado.idMaterialAlmoxarifado = "+material.getIdMaterialAlmoxarifado();
		Double total = new ConsultaGeral<Double>().consultaUnica(new StringBuilder(hql), null);
		return total;
	}
	
}