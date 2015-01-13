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

	public Double consumoMaterialPeriodo(Integer idMaterial, Date dataIni, Date dataFim){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ini = "";
		String fim = "";
		if(dataIni != null && dataFim != null){
			ini = sdf.format(dataIni);
			fim = sdf.format(dataFim);
		}
		String hql = "select sum(a.quantidadeMovimentacao) from MovimentoLivroAlmoxarifado a "+ 
						"where a.tipoMovimentoAlmoxarifado.idTipoMovimentoAlmoxarifado = "+ Constantes.ID_TIPO_MOVIMENTO_SAIDA_DISPENSACAO_ALMOXARIFADO +
						" and a.estoqueAlmoxarifado.materialAlmoxarifado.idMaterialAlmoxarifado = "+idMaterial+
						" and cast(a.dataMovimento as date) between '"+ini+"' and '"+fim+"'";
		StringBuilder sb = new StringBuilder(hql);
		ConsultaGeral<Double> cg = new ConsultaGeral<Double>();
		Double total = cg.consultaUnica(sb, null);
		return total == null ? 0d : total.doubleValue();
	}
	
	public Double saldoTotalEstoque(Integer id) {
		String sql = "select sum(o.quantidadeAtual) from EstoqueAlmoxarifado o where o.materialAlmoxarifado.idMaterialAlmoxarifado = "
						+id;
		StringBuilder sb = new StringBuilder(sql);
		ConsultaGeral<Double> cg = new ConsultaGeral<Double>();
		Double total = cg.consultaUnica(sb, null);
		return total == null ? 0d : total.doubleValue();
	}
	
	public Double saldoTotalEstoque(MaterialAlmoxarifado ma) {
		Double total = saldoTotalEstoque(ma.getIdMaterialAlmoxarifado());
		return total.doubleValue();
	}
	
}