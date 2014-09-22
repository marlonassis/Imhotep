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
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.comparador.FinanceiroGrupoAlmoxarifadoComparador;
import br.com.imhotep.comparador.FinanceiroGrupoAlmoxarifadoGrupoComparador;
import br.com.imhotep.entidade.relatorio.FinanceiroGrupoAlmoxarifado;
import br.com.imhotep.entidade.relatorio.FinanceiroGrupoAlmoxarifadoGrupo;
import br.com.imhotep.entidade.relatorio.FinanceiroGrupoAlmoxarifadoGrupoMaterial;
import br.com.imhotep.linhaMecanica.LinhaMecanica;


@ManagedBean
@ViewScoped
public class RelatorioFinanceiroGrupoAlmoxarifado extends PadraoRelatorio {
	private static final String DB_BANCO_IMHOTEP = LinhaMecanica.DB_BANCO_IMHOTEP;

	private static final long serialVersionUID = 1L;
	
	private Date dataReferencia;

	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException{
		String mesReferencia = new SimpleDateFormat("MMMM/yyyy", Constantes.LOCALE_BRASIL).format(getDataReferencia());
		String caminho = Constantes.DIR_RELATORIO + "RelatorioFinanceiroGrupoAlmoxarifado.jasper";
		String nomeRelatorio = "RelatorioFinanceiroAlmoxarifado"+mesReferencia.replaceFirst("/", "-")+"-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<FinanceiroGrupoAlmoxarifado> lista = getResultadoConsultaRelatorioFinal();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mesConsultado", mesReferencia);
		map.put("grupoMateriais", getResultadoDetalhadoPorMaterial());
		InputStream subInputStreamGrupo = this.getClass().getResourceAsStream("RelatorioFinanceiroGrupoAlmoxarifadoGrupo.jasper");
		map.put("SUBREPORT_INPUT_STREAM_GRUPO", subInputStreamGrupo);
		InputStream subInputStreamGrupoMaterial = this.getClass().getResourceAsStream("RelatorioFinanceiroGrupoAlmoxarifadoGrupoMaterial.jasper");
		map.put("SUBREPORT_INPUT_STREAM_MATERIAIS", subInputStreamGrupoMaterial);
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
		
	}
	
	public void gerarRelatorioDetalhado() throws ClassNotFoundException, IOException, JRException, SQLException{
		String mesReferencia = new SimpleDateFormat("MMMM/yyyy", Constantes.LOCALE_BRASIL).format(getDataReferencia());
		String caminho = Constantes.DIR_RELATORIO + "RelatorioFinanceiroGrupoAlmoxarifadoGrupo.jasper";
		String nomeRelatorio = "RelatorioFinanceiroAlmoxarifado"+mesReferencia.replaceFirst("/", "-")+"-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<FinanceiroGrupoAlmoxarifadoGrupo> lista = getResultadoDetalhadoPorMaterial();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mesConsultado", mesReferencia);
		InputStream subInputStreamGrupoMaterial = this.getClass().getResourceAsStream("RelatorioFinanceiroGrupoAlmoxarifadoGrupoMaterial.jasper");
		map.put("SUBREPORT_INPUT_STREAM_MATERIAL", subInputStreamGrupoMaterial);
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
		
	}
	
	private List<FinanceiroGrupoAlmoxarifadoGrupo> getResultadoDetalhadoPorMaterial(){
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		String dataString = new SimpleDateFormat("yyyy-MM").format(getDataReferencia());
		String sqlFinanceiroAlmoxarifado = "SELECT a.id_material_almoxarifado idMaterial, "+
										       "c.cv_descricao material, "+
										       "a.db_preco_medio_transportado precoMedioTransportado,  "+
										       "a.db_preco_medio_atual precoMedioAtual,  "+
										       "a.in_saldo_transportado saldoTransportado,  "+
										       "a.in_total_entrada totalEntrada, "+  
										       "a.in_total_saida totalSaida,  "+
										       "a.in_saldo_atual saldoAtual, "+
										       "a.db_preco_medio precoMedio, "+ 
										       "a.db_valor_final, "+
										       "b.cv_descricao grupo "+
										  "FROM tb_financeiro_mensal_almoxarifado a "+
										  "inner join tb_grupo_almoxarifado b on a.id_grupo_almoxarifado = b.id_grupo_almoxarifado "+
										  "inner join tb_material_almoxarifado c on c.id_material_almoxarifado = a.id_material_almoxarifado "+
										"where cv_mes_referencia = '"+dataString+"' "+
										"order by to_ascii(lower(b.cv_descricao)), to_ascii(lower(c.cv_descricao))";
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiroAlmoxarifado));
		Map<String, List<FinanceiroGrupoAlmoxarifadoGrupoMaterial>> map = new HashMap<String, List<FinanceiroGrupoAlmoxarifadoGrupoMaterial>>();
		try {
			while (rs.next()) {
				String grupo = rs.getString("grupo");
				int id = rs.getInt("idMaterial");
				String material = rs.getString("material");
				int saldoAtual = rs.getInt("saldoAtual");
				int saldoTransportado = rs.getInt("saldoTransportado");
				Double precoMedioTransportado = rs.getDouble("precoMedioTransportado");
				Double precoMedioAtual = rs.getDouble("precoMedioAtual");
				Double precoMedio = rs.getDouble("precoMedio");
				Double total = rs.getDouble("db_valor_final");
				int totalEntrada = rs.getInt("totalEntrada");
				int totalSaida = rs.getInt("totalSaida");
				
				if(map.containsKey(grupo)){
					map.get(grupo).add(new FinanceiroGrupoAlmoxarifadoGrupoMaterial(id+" - "+material, precoMedioTransportado, precoMedioAtual, 
																					saldoTransportado, saldoAtual, totalEntrada, totalSaida, precoMedio, total));
				}else{
					List<FinanceiroGrupoAlmoxarifadoGrupoMaterial> temp = new ArrayList<FinanceiroGrupoAlmoxarifadoGrupoMaterial>();
					temp.add(new FinanceiroGrupoAlmoxarifadoGrupoMaterial(id+" - "+material, precoMedioTransportado, precoMedioAtual, 
																			saldoTransportado, saldoAtual, totalEntrada, totalSaida, precoMedio, total));
					map.put(grupo, temp);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		List<FinanceiroGrupoAlmoxarifadoGrupo> resultado = new ArrayList<FinanceiroGrupoAlmoxarifadoGrupo>();
		for(String key : map.keySet()){
			resultado.add(new FinanceiroGrupoAlmoxarifadoGrupo(key, map.get(key)));
		}
		Collections.sort(resultado, new FinanceiroGrupoAlmoxarifadoGrupoComparador());
		return resultado;
	}
	
	private List<FinanceiroGrupoAlmoxarifado> getResultadoConsultaRelatorioFinal(){
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		String sqlFinanceiroAlmoxarifadoFinal = "select b.cv_descricao grupo, a.db_saldo_transportado, "
												+ "db_valor_entrada, "
												+ "db_valor_saida, "
												+ "db_valor_final "
												+ "from tb_financeiro_mensal_grupo_almoxarifado a "
												+ "inner join tb_grupo_almoxarifado b on a.id_grupo_almoxarifado = b.id_grupo_almoxarifado "
												+ "where a.cv_mes_referencia = '"+sdf.format(getDataReferencia())+"'";
		
		List<FinanceiroGrupoAlmoxarifado> resultado = new ArrayList<FinanceiroGrupoAlmoxarifado>();
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiroAlmoxarifadoFinal));
		try {
			while (rs.next()) {
				String grupo = rs.getString("grupo");
				Double saldoTransportado = rs.getDouble("db_saldo_transportado");
				Double totalEntrada = rs.getDouble("db_valor_entrada");
				Double totalSaida = rs.getDouble("db_valor_saida");
				Double saldoFinal = rs.getDouble("db_valor_final");
				resultado.add(new FinanceiroGrupoAlmoxarifado(grupo , saldoTransportado , totalEntrada , totalSaida , saldoFinal ));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Collections.sort(resultado, new FinanceiroGrupoAlmoxarifadoComparador());
		return resultado;
	}
	
	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}
}
