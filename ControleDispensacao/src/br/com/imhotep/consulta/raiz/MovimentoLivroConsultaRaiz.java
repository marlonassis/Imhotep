package br.com.imhotep.consulta.raiz;

import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.enums.TipoOperacaoEnum;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="ultimoMovimentoLivroConsultaRaiz")
@RequestScoped
public class MovimentoLivroConsultaRaiz  extends ConsultaGeral<MovimentoLivro>{

	public int saldoAtualMaterial(Material material){
		MovimentoLivro ml = consultaMaiorMovimentoMaterial(material);
		if(ml != null){
			boolean tipoEntrada = ml.getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.Entrada);
			return  tipoEntrada ? ml.getSaldoAnterior() + ml.getQuantidadeMovimentacao() : ml.getSaldoAnterior() - ml.getQuantidadeMovimentacao();
		}
		return 0;
	}
	
	private MovimentoLivro consultaMaiorMovimentoMaterial(Material material) {
		StringBuilder sb = new StringBuilder("select a from MovimentoLivro a");
		sb.append(" where a.dataMovimento = (select max(b.dataMovimento) from MovimentoLivro b where b.estoque.material.idMaterial = :idMaterial)");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", material.getIdMaterial());
		
		ConsultaGeral<MovimentoLivro> cg = new ConsultaGeral<MovimentoLivro>();
		return cg.consultaUnica(sb, map);
	}
	
}
