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

	public Long totalReservardo(MaterialAlmoxarifado material) {
		String hql = "select coalesce(sum(o.quantidadeSolicitada), 0) from SolicitacaoMaterialAlmoxarifadoUnidadeItem o where "
				+ "(o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'P' or o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'A') and o.materialAlmoxarifado.idMaterialAlmoxarifado = "+material.getIdMaterialAlmoxarifado();
		Long total = new ConsultaGeral<Long>().consultaUnica(new StringBuilder(hql), null);
		return total;
	}
	
	public Long totalReservardo(MaterialAlmoxarifado material, SolicitacaoMaterialAlmoxarifadoUnidade sma) {
		String hql = "select coalesce(sum(o.quantidadeSolicitada), 0) from SolicitacaoMaterialAlmoxarifadoUnidadeItem o where "
				+ "(o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'P' or o.solicitacaoMaterialAlmoxarifadoUnidade.statusDispensacao = 'A') and o.solicitacaoMaterialAlmoxarifadoUnidade.idSolicitacaoMaterialAlmoxarifadoUnidade != "+sma.getIdSolicitacaoMaterialAlmoxarifadoUnidade()+" and o.materialAlmoxarifado.idMaterialAlmoxarifado = "+material.getIdMaterialAlmoxarifado();
		Long total = new ConsultaGeral<Long>().consultaUnica(new StringBuilder(hql), null);
		return total;
	}
	
}