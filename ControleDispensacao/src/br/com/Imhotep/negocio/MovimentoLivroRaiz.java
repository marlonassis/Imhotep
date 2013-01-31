package br.com.Imhotep.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="movimentoLivroRaiz")
@SessionScoped
public class MovimentoLivroRaiz extends PadraoHome<MovimentoLivro>{
	public List<MovimentoLivro> listaMovimentoLivroPeriodo(Material material, Date dataIni, Date dataFim){
		return buscarTestesPorPeriodo(material, dataIni, dataFim);
	}

	private List<MovimentoLivro> buscarTestesPorPeriodo(Material material, Date dataIni, Date dataFim) {
		ConsultaGeral<MovimentoLivro> cg = new ConsultaGeral<MovimentoLivro>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		map.put("idMaterial", material.getIdMaterial());
		String sql = "select o from MovimentoLivro o where o.dataMovimento >= :dataIni and o.dataMovimento <= :dataFim and o.material.idMaterial = :idMaterial order by o.dataMovimento desc";
		return new ArrayList<MovimentoLivro>(cg.consulta(new StringBuilder(sql), map));
	}
}
