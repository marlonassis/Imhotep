package br.com.imhotep.relatorio;

/**
 * Funcionalidade: Relatório de média de consumo e previsão de estoque
 * XHTML: /PaginasWeb/Relatorios/Farmacia/Financeiro/financeiroFarmaciaMediaConsumo.xhtml
 */

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.imhotep.comparador.MediaConsumoFarmaciaComparador;
import br.com.imhotep.entidade.extra.MediaConsumoFarmacia;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@SessionScoped
public class RelatorioMediaConsumoFarmacia extends PadraoRelatorio{
//TODO REL 2	
	private static final long serialVersionUID = 1L;
	private Date dataIni = new Date();
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException{
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMediaConsumoFarmacia.jasper";
		String nomeRelatorio = "RelatorioMediaConsumoFarmacia-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("DATA_INI", new SimpleDateFormat("MM/yyyy").format(dataIni));
		map.put("DATA_FIM", new SimpleDateFormat("MM/yyyy").format(getSeisMesesAnteriores()));
		
		List<MediaConsumoFarmacia> listaMediaConsumoFarmacia = listaMediaConsumoFarmacia(map, 6);
//		Collections.sort(listaMediaConsumoFarmacia, new MediaConsumoFarmaciaComparador());
		super.geraRelatorio(caminho, nomeRelatorio, listaMediaConsumoFarmacia, map);
	}

	private Date getSeisMesesAnteriores(){
		Calendar dataAnterior = Calendar.getInstance();
		dataAnterior.setTime(getDataIni());
		dataAnterior.add(Calendar.MONTH, -5);
		return dataAnterior.getTime();
	}
	
	private List<MediaConsumoFarmacia> listaMediaConsumoFarmacia(HashMap<String, Object> map, int qtdPrevisaoMeses){
		List<MediaConsumoFarmacia> res = new ArrayList<MediaConsumoFarmacia>();
		LinhaMecanica lm = new LinhaMecanica();
		try {
			lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
			lm.setIp(Constantes.IP_LOCAL);
			lm.criarConexao();
			String sqlMaterial = "select a.in_codigo_material, a.cv_descricao, b.cv_sigla, coalesce(sum(c.in_quantidade_atual), 0) from tb_material a "+
									"left join tb_unidade_material b on b.id_unidade_material = a.id_unidade_material "+
									"left join tb_estoque c on c.id_material = a.id_material and "+
									"(c.dt_data_validade is null or cast(dt_data_validade as date) >= cast(now() as date)) and c.in_quantidade_atual > 0 and c.bl_bloqueado is false "+
									"group by a.id_material, b.cv_sigla "+ 
									"order by lower(to_ascii(a.cv_descricao))";
			
			List<Object[]> listaResultado = lm.getListaResultadoFast(sqlMaterial, 4);
			for(Object[] material : listaResultado){
				Integer idMaterial = (Integer) material[0];
				String descricao = String.valueOf(material[1]).toUpperCase();
				String sigla = String.valueOf(material[2]);
				Integer saldoAtual = ((Long) material[3]).intValue();
				List<Integer> totalConsumo = new ArrayList<Integer>();
				
				Calendar dataReferencia = Calendar.getInstance(Constantes.LOCALE_BRASIL);
				dataReferencia.setTime(getDataIni());
				int idConsumo = Constantes.ID_TIPO_MOVIMENTO_SAIDA_DISPENSACAO_FARMACIA;
				
				Date mesI = new Utilitarios().ajustarPrimeiroDiaMes(getSeisMesesAnteriores());
				Calendar mesF = Calendar.getInstance(); 
				mesF.setTime(new Utilitarios().ajustarUltimoDiaMes(getDataIni()));
				
				String ini = new SimpleDateFormat("yyyy-MM-dd").format(mesI);
				String fim = new SimpleDateFormat("yyyy-MM-dd").format(mesF.getTime());
				Map<String, Long> consumoMesesMap = montarConsumoMeses(lm, idMaterial, idConsumo, ini, fim);
				for(int i = 5; i >= 0; i--){
					String mesRelatorio = new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(mesF.getTime());
					String mesNumero = new SimpleDateFormat("yyyy-MM", Constantes.LOCALE_BRASIL).format(mesF.getTime());
					map.put("MES_"+i, mesRelatorio);
					
					if(consumoMesesMap.containsKey(mesNumero))
						totalConsumo.add(consumoMesesMap.get(mesNumero).intValue());
					else
						totalConsumo.add(0);
					
					mesF.add(Calendar.MONTH, -1);
				}
				MediaConsumoFarmacia mediaConsumoFarmacia = new MediaConsumoFarmacia(idMaterial, totalConsumo , saldoAtual, descricao, sigla, null);
				
				//Inicio: Previsão de estoque
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				long saldoPrev = saldoAtual.longValue();				
				
				List<Long> previsao = new ArrayList<Long>();
				for(int j=0; j<qtdPrevisaoMeses;j++){
					c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
					
					Long venc = getQtdVencimentoMes( format.format(c.getTime()).toString(), mediaConsumoFarmacia.getIdMaterialAlmoxarfiado() );
					saldoPrev = saldoPrev - (venc.longValue() + mediaConsumoFarmacia.getMediaConsumo());
					previsao.add( new Long(saldoPrev) );
				}
				
				mediaConsumoFarmacia.setPrevEstoque(previsao);
				//Fim: Previsão de estoque
				
				res.add(mediaConsumoFarmacia);
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
		} finally{
			lm.fecharConexoes();
		}

		return res;
	}
	
	private Long getQtdVencimentoMes( String data, int id_material )throws SQLException{		
		String sql = 
			"SELECT SUM(est.in_quantidade_atual) "+
			"FROM tb_material mat "+
				"LEFT JOIN tb_estoque est ON est.id_material = mat.id_material "+
					"WHERE mat.in_codigo_material= "+id_material+" "+
						"AND est.bl_bloqueado IS FALSE "+
						"AND est.dt_data_validade IS NOT NULL "+
						"AND date_trunc('month',dt_data_validade) >= date_trunc('month',(TIMESTAMP '" + data +"')) "+
						"AND date_trunc('month',dt_data_validade) < date_trunc('month', (TIMESTAMP '" + data + "' + INTERVAL '1 MONTH')) "+
						"AND est.in_quantidade_atual > 0 "+
				"GROUP BY mat.in_codigo_material ";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql.toString()));
					
		if (rs.next()) { 
			return rs.getLong(1);
		}
		
		return 0l;
	}


	private Map<String, Long> montarConsumoMeses(LinhaMecanica lm, Integer idMaterial, int idConsumo,
			String ini, String fim) throws SQLException {
		Map<String, Long> map = new HashMap<String, Long>();
		String sqlConsumo = "select to_char(c.dt_data_movimento, 'YYYY-MM') mes, coalesce(sum(c.in_quantidade_movimentacao), 0) totalConsumo "+
				"from tb_material a "+
				"left join tb_estoque b on a.id_material = b.id_material "+
				"left join tb_movimento_livro c on b.id_estoque = c.id_estoque and "+
				"c.id_tipo_movimento = "+idConsumo+" and cast(c.dt_data_movimento as date) between '"+ini+"' and '"+fim+"' "+
				"where a.in_codigo_material = "+idMaterial+
				" group by mes "+
				"order by mes";
		ResultSet rs = lm.fastConsulta(sqlConsumo);
		while(rs.next()){
			map.put(rs.getString(1), rs.getLong(2));
		}
		return map;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

}
