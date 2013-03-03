package br.com.Imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.entidade.Unidade;
import br.com.Imhotep.raiz.MovimentoLivroRaiz;

@ManagedBean(name="relatorioMovimentacaoEstoqueFarmacia")
@ViewScoped
public class RelatorioConsumoFarmacia extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	private Material material;
	private TipoMovimento tipoMovimento;
	private Unidade unidade;
	
	public void relatorioMovimentacao() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = "/WEB-INF/classes/br/com/Imhotep/relatorio/RelatorioMovimentacaoEstoqueFarmacia.jasper";
		String nomeRelatorio = "EstoqueMovimentacao-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<MovimentoLivro> listaEstoqueRelatorioGeral = null;//new MovimentoLivroRaiz().listaMovimentoLivroPeriodo(getMaterial(), dataIni, dataFim, getUnidade(), getTipoMovimento());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		super.geraRelatorio(caminho, nomeRelatorio, listaEstoqueRelatorioGeral, map);
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
}
