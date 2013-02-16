package br.com.Imhotep.raiz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.auxiliar.Utilities;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="movimentoLivroRaiz")
@SessionScoped
public class MovimentoLivroRaiz extends PadraoHome<MovimentoLivro>{
	
	public List<MovimentoLivro> listaMovimentoLivroPeriodo(Material material, Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento){
		Calendar df = Utilities.ajustarUltimaHoraDia(dataFim);
		return buscarMovimentoPorPeriodo(material, dataIni, df.getTime(), unidade, tipoMovimento);
	}
	
	public List<MovimentoLivro> listaMovimentoLivroPeriodo(Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento){
		Calendar df = Utilities.ajustarUltimaHoraDia(dataFim);
		return buscarMovimentoPorPeriodo(null, dataIni, df.getTime(), unidade, tipoMovimento);
	}

	private List<MovimentoLivro> buscarMovimentoPorPeriodo(Material material, Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento) {
		ConsultaGeral<MovimentoLivro> cg = new ConsultaGeral<MovimentoLivro>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select o from MovimentoLivro o where o.dataMovimento >= :dataIni and o.dataMovimento <= :dataFim ";
		
		if(material != null){
			sql += " and o.material.idMaterial = :idMaterial ";
			map.put("idMaterial", material.getIdMaterial());
		}
		
		if(unidade != null){
			sql += " and o.unidadeReceptora.idUnidade = :idUnidade ";
			map.put("idUnidade", unidade.getIdUnidade());
		}
		
		if(tipoMovimento != null && tipoMovimento.getIdTipoMovimento() != 0){
			sql += " and o.tipoMovimento.idTipoMovimento = :idTipoMovimento";
			map.put("idTipoMovimento", tipoMovimento.getIdTipoMovimento());
		}
		
		sql += " order by o.dataMovimento desc";
		ArrayList<MovimentoLivro> list = new ArrayList<MovimentoLivro>(cg.consulta(new StringBuilder(sql), map));
		return (list==null || list.size() == 0) ? null : list;
	}
}
