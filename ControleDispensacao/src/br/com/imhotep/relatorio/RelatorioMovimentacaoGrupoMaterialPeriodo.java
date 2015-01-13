package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.MovimentacoesMaterialAlmoxarifadoGrupoComparador;
import br.com.imhotep.consulta.raiz.MaterialAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.raiz.SubGrupoAlmoxarifadoConsultaRaiz;
import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupo;
import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupoMaterial;
import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.relatorio.excel.RelatorioMaterialAlmoxarifadoCompletoExcel;
import br.com.imhotep.relatorio.excel.RelatorioMovimentacaoGrupoMaterialPeriodoExcel;

@ManagedBean
@SessionScoped
public class RelatorioMovimentacaoGrupoMaterialPeriodo extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList;
	
	private boolean excel;
	
	public void atualizaSubGrupoAmoxarifado(){
		if(getGrupoAlmoxarifado() != null){
			setSubGrupoAlmoxarifadoList(new SubGrupoAlmoxarifadoConsultaRaiz().consultarSubGrupoGrupo(getGrupoAlmoxarifado().getIdGrupoAlmoxarifado()));
			if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty()){
				setSubGrupoAlmoxarifado(null);
			}
		}
	}
	
	public boolean getExibirComboSubGrupo(){
		if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty())
			return false;
		return true;
	}
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio;
		
		String dataIni = new SimpleDateFormat("dd/MM/yyyy").format(this.dataIni);
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		String dataFim = new SimpleDateFormat("dd/MM/yyyy").format(this.dataFim);
		
		//Requisito Funcional #18
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioMovimentacaoDetalhadaEstoqueMaterialAlmoxarifadoGrupo.jasper";
			nomeRelatorio = "GrupoMaterialMovimentacao-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dataIni", dataIni );
			map.put("dataFim", dataFim );
			
			InputStream subInputStream0 = this.getClass().getResourceAsStream("RelatorioMovimentacaoDetalhadaEstoqueMaterialAlmoxarifadoGrupoMaterial.jasper");
			map.put("SUBREPORT_INPUT_STREAM_MATERIAIS", subInputStream0);
			
			InputStream subInputStream1 = this.getClass().getResourceAsStream("RelatorioMovimentacaoDetalhadaEstoqueMaterialAlmoxarifadoGrupoMaterialMovimentacao.jasper");
			map.put("SUBREPORT_INPUT_STREAM_MATERIAL_MOVIMENTACOES", subInputStream1);
			
			super.geraRelatorio(caminho, nomeRelatorio, getResultadoDetalhadoPorMaterial(), map);
		}
		else{
			nomeRelatorio = "RelatorioMovimentacaoDetalhadaEstoqueMaterialAlmoxarifadoGrupo-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioMovimentacaoGrupoMaterialPeriodoExcel exc;
	        exc = new RelatorioMovimentacaoGrupoMaterialPeriodoExcel(getResultadoDetalhadoPorMaterial(), "Almoxarifado", dataIni+" a "+dataFim,3);
	        exc.gerarPlanilha();
			super.geraRelatorioExcel(nomeRelatorio, exc.getWorkbook());
		}
	}

	private List<MovimentacoesGrupoAlmoxarifadoGrupo> getResultadoDetalhadoPorMaterial(){
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ini = sdf.format(getDataIni());
		String fim = sdf.format(getDataFim());
		int idGrupo = getGrupoAlmoxarifado() == null ? 0 : getGrupoAlmoxarifado().getIdGrupoAlmoxarifado();
		int idSubGrupo = getSubGrupoAlmoxarifado() == null ? 0 : getSubGrupoAlmoxarifado().getIdSubGrupoAlmoxarifado();
		String sqlFinanceiroAlmoxarifado = "select d.cv_descricao grupo, g.cv_descricao subgrupo, UPPER(c.cv_descricao) material, c.id_material_almoxarifado idMaterial, b.cv_lote lote, a.in_quantidade_atual qtdAtual, " + 
											"a.in_quantidade_movimentacao qtdMovimentada, a.cv_justificativa justificativa, e.cv_descricao tipoMovimento, "+
											"e.tp_operacao tipoOperacao, a.dt_data_movimento dataMovimento, f.cv_nome profissional " +
											"from tb_movimento_livro_almoxarifado a " +
											"inner join tb_estoque_almoxarifado b on a.id_estoque_almoxarifado = b.id_estoque_almoxarifado " +
											"inner join tb_material_almoxarifado c on b.id_material_almoxarifado = c.id_material_almoxarifado " +
											"inner join tb_grupo_almoxarifado d on d.id_grupo_almoxarifado = c.id_grupo_almoxarifado " +
											"inner join tb_tipo_movimento_almoxarifado e on e.id_tipo_movimento_almoxarifado = a.id_tipo_movimento_almoxarifado " +
											"inner join tb_profissional f on f.id_profissional = a.id_profissional_insercao "+
											"left join tb_sub_grupo_almoxarifado g on g.id_sub_grupo_almoxarifado = c.id_sub_grupo_almoxarifado "+
											"where a.dt_data_movimento between cast('"+ini+"' as date) and cast('"+fim+"' as date) " +  
											(idGrupo == 0 ? "" : "and c.id_grupo_almoxarifado = "+idGrupo+" ") + 
											(idSubGrupo == 0 ? "" : "and c.id_sub_grupo_almoxarifado = "+idSubGrupo+" ") +
											"group by grupo, subgrupo, material, idMaterial, lote, qtdAtual, qtdMovimentada, justificativa, tipoMovimento, tipoOperacao, dataMovimento, profissional " +
											"order by grupo, subgrupo, material, lote, tipoMovimento, tipoOperacao, dataMovimento, justificativa, qtdAtual, qtdMovimentada ";
		
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiroAlmoxarifado));
		Map<String, MovimentacoesGrupoAlmoxarifadoGrupo> grupoMap = new HashMap<String, MovimentacoesGrupoAlmoxarifadoGrupo>();
		try {
			while (rs.next()) {
				String grupo = rs.getString("grupo");
				String subGrupo = rs.getString("subgrupo");
				int idMaterial = rs.getInt("idMaterial");
				String material = rs.getString("material");
				String lote = rs.getString("lote");
				int qtdAtual = rs.getInt("qtdAtual");
				int qtdMovimentada = rs.getInt("qtdMovimentada");
				String justificativa = rs.getString("justificativa");
				String tipoMovimento = rs.getString("tipoMovimento");
				String tipoOperacao = rs.getString("tipoOperacao");
				Date dataMovimento = rs.getTimestamp("dataMovimento");
				String usuario = rs.getString("profissional");
				
				MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes e = new MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes();
				e.setDataMovimento(dataMovimento);
				e.setJustificativa(justificativa);
				e.setLote(lote);
				e.setQtdAtual(qtdAtual);
				e.setQtdMovimentada(qtdMovimentada);
				e.setTipoMovimento(tipoMovimento);
				e.setTipoOperacao(tipoOperacao);
				e.setUsuario(new Utilitarios().nomeResumido(usuario));
				
				if(!grupoMap.containsKey(grupo)){
					List<MovimentacoesGrupoAlmoxarifadoGrupoMaterial> materiais = new ArrayList<MovimentacoesGrupoAlmoxarifadoGrupoMaterial>();
					grupoMap.put(grupo, new MovimentacoesGrupoAlmoxarifadoGrupo(grupo, subGrupo, materiais));
				}
				
				Double saldoEstoque = new MaterialAlmoxarifadoConsultaRaiz().saldoTotalEstoque(idMaterial);
				Double consumo = new MaterialAlmoxarifadoConsultaRaiz().consumoMaterialPeriodo(idMaterial, dataIni, dataFim);
				MovimentacoesGrupoAlmoxarifadoGrupoMaterial magm = new MovimentacoesGrupoAlmoxarifadoGrupoMaterial(idMaterial, material, consumo, saldoEstoque, null);
				List<MovimentacoesGrupoAlmoxarifadoGrupoMaterial> materiais = grupoMap.get(grupo).getMateriais();
				if(!materiais.contains(magm)){
					List<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes> movimentacoes = new ArrayList<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes>();
					movimentacoes.add(e);
					magm.setMovimentacoes(movimentacoes);
					materiais.add(magm);
				}else{
					materiais.get(materiais.indexOf(magm)).getMovimentacoes().add(e);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		List<MovimentacoesGrupoAlmoxarifadoGrupo> resultado = new ArrayList<MovimentacoesGrupoAlmoxarifadoGrupo>();
		for(String grupo : grupoMap.keySet()){
			MovimentacoesGrupoAlmoxarifadoGrupo objGrupo = grupoMap.get(grupo);
			resultado.add(new MovimentacoesGrupoAlmoxarifadoGrupo(grupo, objGrupo.getSubGrupo(), objGrupo.getMateriais()));
		}
		Collections.sort(resultado, new MovimentacoesMaterialAlmoxarifadoGrupoComparador());
		return resultado;
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

	public GrupoAlmoxarifado getGrupoAlmoxarifado() {
		return grupoAlmoxarifado;
	}

	public void setGrupoAlmoxarifado(GrupoAlmoxarifado grupoAlmoxarifado) {
		this.grupoAlmoxarifado = grupoAlmoxarifado;
	}

	public SubGrupoAlmoxarifado getSubGrupoAlmoxarifado() {
		return subGrupoAlmoxarifado;
	}

	public void setSubGrupoAlmoxarifado(SubGrupoAlmoxarifado subGrupoAlmoxarifado) {
		this.subGrupoAlmoxarifado = subGrupoAlmoxarifado;
	}

	public List<SubGrupoAlmoxarifado> getSubGrupoAlmoxarifadoList() {
		return subGrupoAlmoxarifadoList;
	}

	public void setSubGrupoAlmoxarifadoList(List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList) {
		this.subGrupoAlmoxarifadoList = subGrupoAlmoxarifadoList;
	}

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	
}
