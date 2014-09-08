package br.com.imhotep.consulta.raiz;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.AlmoxarifadoUnidadeCota;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MaterialAlmoxarifadoConsultaRaiz  extends ConsultaGeral<AlmoxarifadoUnidadeCota>{

	public Integer consumoMaterialPeriodo(Integer idMaterial, Date dataIni, Date dataFim){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ini = "";
		String fim = "";
		if(dataIni != null && dataFim != null){
			ini = sdf.format(dataIni);
			fim = sdf.format(dataFim);
		}
		String hql = "select coalesce(sum(a.quantidadeMovimentacao), 0) from MovimentoLivroAlmoxarifado a "+ 
						"where a.tipoMovimentoAlmoxarifado.idTipoMovimentoAlmoxarifado = "+ Constantes.ID_TIPO_MOVIMENTO_SAIDA_DISPENSACAO_ALMOXARIFADO +
						" and a.estoqueAlmoxarifado.materialAlmoxarifado.idMaterialAlmoxarifado = "+idMaterial+
						" and cast(a.dataMovimento as date) between '"+ini+"' and '"+fim+"'";
		StringBuilder sb = new StringBuilder(hql);
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		Long total = cg.consultaUnica(sb, null);
		return total.intValue();
	}
	
	public Integer saldoTotalEstoque(Integer id) {
		String sql = "select coalesce(sum(o.quantidadeAtual), 0) from EstoqueAlmoxarifado o where o.materialAlmoxarifado.idMaterialAlmoxarifado = "
						+id;
		StringBuilder sb = new StringBuilder(sql);
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		Long total = cg.consultaUnica(sb, null);
		return total.intValue();
	}
	
	public Integer saldoTotalEstoque(MaterialAlmoxarifado ma) {
		Integer total = saldoTotalEstoque(ma.getIdMaterialAlmoxarifado());
		return total.intValue();
	}
	
}