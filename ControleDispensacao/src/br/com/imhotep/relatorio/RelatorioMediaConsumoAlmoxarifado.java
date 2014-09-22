package br.com.imhotep.relatorio;

/**
 * Funcionalidade: Relatório de média de consumo e previsão de estoque
 * XHTML: /PaginasWeb/Relatorios/Almoxarifado/Financeiro/financeiroAlmoxarifadoMediaConsumo.xhtml
 */

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.SubGrupoAlmoxarifadoConsultaRaiz;
import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.entidade.extra.MediaConsumoAlmoxarifado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

//TODO Rel 2

@ManagedBean
@SessionScoped
public class RelatorioMediaConsumoAlmoxarifado extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private Date dataIni = new Date();
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList;
	
	public void atualizaSubGrupoAmoxarifado(){
		if(getGrupoAlmoxarifado() != null){
			setSubGrupoAlmoxarifadoList(new SubGrupoAlmoxarifadoConsultaRaiz().consultarSubGrupoGrupo(getGrupoAlmoxarifado().getIdGrupoAlmoxarifado()));
			if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty()){
				setSubGrupoAlmoxarifado(null);
			}
		}else
			setSubGrupoAlmoxarifadoList(new ArrayList<SubGrupoAlmoxarifado>());
	}
	
	public boolean getExibirComboSubGrupo(){
		if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty())
			return false;
		return true;
	}
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException{
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMediaConsumoAlmoxarifado.jasper";
		String nomeRelatorio = "RelatorioMediaConsumoAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		
		String grupo = montarDescricaoGrupo();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("DATA_INI", new SimpleDateFormat("MM/yyyy").format(dataIni));
		map.put("DATA_FIM", new SimpleDateFormat("MM/yyyy").format(getSeisMesesAnteriores()));
		map.put("GRUPO", grupo);
		map.put("Setor", "Almoxarifado");
		
		int idGrupo = getGrupoAlmoxarifado() == null ? 0 : getGrupoAlmoxarifado().getIdGrupoAlmoxarifado();
		int idSubGrupo = (getGrupoAlmoxarifado() != null && getSubGrupoAlmoxarifado() != null) ? getSubGrupoAlmoxarifado().getIdSubGrupoAlmoxarifado() : 0;
		
		List<MediaConsumoAlmoxarifado> listaMediaConsumoAlmoxarifado = listaMediaConsumoAlmoxarifado(idGrupo, idSubGrupo, map, 6);
		
		super.geraRelatorio(caminho, nomeRelatorio, listaMediaConsumoAlmoxarifado, map);
	}

	private Date getSeisMesesAnteriores(){
		Calendar dataAnterior = Calendar.getInstance();
		dataAnterior.setTime(getDataIni());
		dataAnterior.add(Calendar.MONTH, -5);
		return dataAnterior.getTime();
	}
	
	private String montarDescricaoGrupo() {
		String grupo = getGrupoAlmoxarifado() == null ? "[Todos]" : getGrupoAlmoxarifado().getDescricao();
		grupo += (getGrupoAlmoxarifado() != null && getSubGrupoAlmoxarifado() != null) ? " / ".concat(getSubGrupoAlmoxarifado().getDescricao()) : "";
		return grupo;
	}
	
	private List<MediaConsumoAlmoxarifado> listaMediaConsumoAlmoxarifado(int idGrupo, int idSubGrupo, HashMap<String, Object> map, int qtdPrevisaoMeses){
		List<MediaConsumoAlmoxarifado> res = new ArrayList<MediaConsumoAlmoxarifado>();
		LinhaMecanica lm = new LinhaMecanica();
		try {
			lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
			lm.setIp(Constantes.IP_LOCAL);
			lm.criarConexao();
			String sqlMaterial = "select a.id_material_almoxarifado, a.cv_descricao, b.cv_sigla, coalesce(sum(c.in_quantidade_atual), 0) from tb_material_almoxarifado a"
					+ " inner join tb_unidade_material_almoxarifado b on b.id_unidade_material_almoxarifado = a.id_unidade_material_almoxarifado"
					+ " left join tb_estoque_almoxarifado c on c.id_material_almoxarifado = a.id_material_almoxarifado and"
					+ " (c.dt_data_validade is null or cast(dt_data_validade as date) >= cast(now() as date)) and c.in_quantidade_atual > 0 and c.bl_bloqueado is false"
					+ " inner join tb_grupo_almoxarifado f on f.id_grupo_almoxarifado = a.id_grupo_almoxarifado"
					+ " left join tb_sub_grupo_almoxarifado g on g.id_sub_grupo_almoxarifado = a.id_sub_grupo_almoxarifado"
					+ (idGrupo != 0 ? " where f.id_grupo_almoxarifado = "+idGrupo : "")
					+ (idSubGrupo != 0 ? " and g.id_sub_grupo_almoxarifado = "+idSubGrupo : "")
					+ " group by a.id_material_almoxarifado, b.cv_sigla "
					+ " order by lower(to_ascii(a.cv_descricao))";
			List<Object[]> listaResultado = lm.getListaResultadoFast(sqlMaterial, 4);
			for(Object[] material : listaResultado){
				Integer idMaterialAlmoxarfiado = (Integer) material[0];
				String descricao = String.valueOf(material[1]).toUpperCase();
				String sigla = String.valueOf(material[2]);
				Integer saldoAtual = ((Long) material[3]).intValue();
				List<Integer> totalConsumo=new ArrayList<Integer>();
				
				Calendar dataReferencia = Calendar.getInstance(Constantes.LOCALE_BRASIL);
				dataReferencia.setTime(getDataIni());
				for(int i = 5; i >= 0; i--){
					map.put("MES_"+i, Utilitarios.mesAnoDescricaoResumido(dataReferencia.getTime()));
					int idConsumo = Constantes.ID_TIPO_MOVIMENTO_SAIDA_DISPENSACAO_ALMOXARIFADO;
					String data = new SimpleDateFormat("yyyy-MM").format(dataReferencia.getTime());
					String sqlConsumo = "select coalesce(sum(c.in_quantidade_movimentacao), 0) totalConsumo"
								+" from tb_material_almoxarifado a"
								+" left join tb_estoque_almoxarifado b on a.id_material_almoxarifado = b.id_material_almoxarifado"
								+" left join tb_movimento_livro_almoxarifado c on b.id_estoque_almoxarifado = c.id_estoque_almoxarifado and" 
								+" c.id_tipo_movimento_almoxarifado = "+idConsumo+" and to_char(c.dt_data_movimento, 'YYYY-MM') = '"+data+"'"
								+" where a.id_material_almoxarifado = "+idMaterialAlmoxarfiado;
						List<Object[]> listaResultado2 = lm.getListaResultadoFast(sqlConsumo, 1);
						for(Object[] consumo : listaResultado2){
							totalConsumo.add(((Long) consumo[0]).intValue());
						}

						dataReferencia.add(Calendar.MONTH, -1);
				}
				
				MediaConsumoAlmoxarifado mediaConsumoAlmoxarifado = new MediaConsumoAlmoxarifado(idMaterialAlmoxarfiado, totalConsumo , saldoAtual, descricao, sigla, null);
				
				//Inicio: Previsão de estoque
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				long saldoPrev = saldoAtual.longValue();
								
				List<Long> previsao = new ArrayList<Long>();
				for(int j=0; j<qtdPrevisaoMeses;j++){
					c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
					
					Long venc = getQtdVencimentoMes( format.format(c.getTime()).toString(), mediaConsumoAlmoxarifado.getIdMaterialAlmoxarfiado());
					saldoPrev = saldoPrev - (venc.longValue() + mediaConsumoAlmoxarifado.getMediaConsumo());
					previsao.add( new Long(saldoPrev) );
				}
				
		 		mediaConsumoAlmoxarifado.setPrevEstoque(previsao);
				//Fim: Previsão de estoque
				
				res.add(mediaConsumoAlmoxarifado);
			}
			
			//Inicio: Previsão de estoque
			Calendar dataReferencia = Calendar.getInstance(Constantes.LOCALE_BRASIL);
			dataReferencia.setTime(getDataIni());
			for(int i = 5; i <= 11; i++){
				map.put("MES_"+i, Utilitarios.mesAnoDescricaoResumido(dataReferencia.getTime()));
				dataReferencia.add(Calendar.MONTH, 1);
			}
			//Fim: Previsão de estoque
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}

		return res;
	}
	
	private Long getQtdVencimentoMes( String data, int id_material )throws SQLException{
				
		String sql = 
			"SELECT SUM(est.in_quantidade_atual) "+
			"FROM tb_material_almoxarifado mat "+
				"LEFT JOIN tb_estoque_almoxarifado est ON est.id_material_almoxarifado = mat.id_material_almoxarifado "+
					"WHERE mat.id_material_almoxarifado= "+id_material+" "+
						"AND est.bl_bloqueado IS FALSE "+
						"AND est.dt_data_validade IS NOT NULL "+
						"AND date_trunc('month',dt_data_validade) >= date_trunc('month',(TIMESTAMP '" + data +"')) "+
						"AND date_trunc('month',dt_data_validade) < date_trunc('month', (TIMESTAMP '" + data + "' + INTERVAL '1 MONTH')) "+
						"AND est.in_quantidade_atual > 0 "+
				"GROUP BY mat.id_material_almoxarifado ";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql.toString()));
					
		if (rs.next()) { 
			return rs.getLong(1);
		}
		
		return 0l;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
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

	
}
