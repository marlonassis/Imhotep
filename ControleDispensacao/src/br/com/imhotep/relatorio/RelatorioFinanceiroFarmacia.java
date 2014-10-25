package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
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
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.TextoStringComparador;
import br.com.imhotep.entidade.extra.FinanceiroGrupo;
import br.com.imhotep.entidade.extra.FinanceiroGrupoMaterial;
import br.com.imhotep.entidade.extra.FinanceiroGrupoMaterialData;
import br.com.imhotep.entidade.extra.FinanceiroGrupoMaterialDataMovimento;
import br.com.imhotep.excecoes.ExcecaoDataAcimaCorteMedLynx;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@ViewScoped
public class RelatorioFinanceiroFarmacia extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private Date dataIni = new Date();
	private Date dataFim = new Date();
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException{
		if(dataIni.before(Utilitarios.getDataCorte())){
			try {
				throw new ExcecaoDataAcimaCorteMedLynx();
			} catch (ExcecaoDataAcimaCorteMedLynx e) {
				e.printStackTrace();
				return;
			}
		}
		
		String caminho = Constantes.DIR_RELATORIO + "RelatorioFinanceiroFarmacia.jasper";
		String nomeRelatorio = "RelatorioFinanceiro-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(dataFim) );
		map.put("OPERACAO_ENTRADA", "Entrada");
		map.put("OPERACAO_SAIDA", "Saída");
		
		InputStream subInputStreamMaterial = this.getClass().getResourceAsStream("RelatorioFinanceiroFarmaciaMaterial.jasper");
		map.put("SUBREPORT_INPUT_STREAM_MATERIAIS", subInputStreamMaterial);
		InputStream subInputStreamPeriodo = this.getClass().getResourceAsStream("RelatorioFinanceiroFarmaciaMaterialPeriodo.jasper");
		map.put("SUBREPORT_INPUT_STREAM_PERIODO", subInputStreamPeriodo);
		InputStream subInputStreamMovimentoEntrada = this.getClass().getResourceAsStream("RelatorioFinanceiroFarmaciaMaterialPeriodoMovimento.jasper");
		map.put("SUBREPORT_INPUT_STREAM_ENTRADA", subInputStreamMovimentoEntrada);
		InputStream subInputStreamMovimentoSaida = this.getClass().getResourceAsStream("RelatorioFinanceiroFarmaciaMaterialPeriodoMovimento.jasper");
		map.put("SUBREPORT_INPUT_STREAM_SAIDA", subInputStreamMovimentoSaida);
		
		super.geraRelatorio(caminho, nomeRelatorio, listaCustaEstoque(), map);
	}
	
	private List<FinanceiroGrupo> listaCustaEstoque(){
		String sql = sqlRelatorioCustoEstoque();
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		List<FinanceiroGrupo> lista = new ArrayList<FinanceiroGrupo>();
		try {
			Map<String, FinanceiroGrupo> mapGrupo = new HashMap<String, FinanceiroGrupo>();
			while (rs.next()) {
				//add grupo
				String grupo = rs.getString("grupo");
				if(!mapGrupo.containsKey(grupo)){
					mapGrupo.put(grupo, new FinanceiroGrupo(grupo));
				}
				
				//add material
				String material = rs.getString("material");
				String materialCodigo = rs.getString("codigoMaterial") + " - " + material;
				if(!mapGrupo.get(grupo).getMapMaterial().containsKey(material)){
					mapGrupo.get(grupo).getMapMaterial().put(material, new FinanceiroGrupoMaterial(material, materialCodigo));
				}
				
				//add período
				int mes = rs.getInt("mes");
				int ano = rs.getInt("ano");
				if(mes != 0 && ano != 0){
					String periodo = Utilitarios.mesAnoDescricao(mes, ano);
					if(!mapGrupo.get(grupo).getMapMaterial().get(material).getMapPeriodo().containsKey(periodo)){
						int idMaterial = rs.getInt("idMaterial");
						Double precoMedio = getPrecoMedio(mes, ano, idMaterial);
						Date dataBruta = new SimpleDateFormat("yyyy-MM-dd").parse(ano+"-"+mes+"-"+"01");
						String data = mes+"-"+ ano;
						if(mes < 10){
							data = "0"+data;
						}
						int saldoInicial = getSaldoInicial(idMaterial, data);
						int saldoFinal = getSaldoFinal(idMaterial, data);
						FinanceiroGrupoMaterialData objPeriodo = new FinanceiroGrupoMaterialData(periodo, precoMedio, dataBruta, saldoInicial, saldoFinal);
						mapGrupo.get(grupo).getMapMaterial().get(material).getMapPeriodo().put(periodo, objPeriodo);
					}
					//add movimento
					String operacao = rs.getString("tipoOperacao");
					FinanceiroGrupoMaterialDataMovimento movimento = new FinanceiroGrupoMaterialDataMovimento(rs.getString("tipoMovimento"), rs.getInt("totalMovimentado"));
					if(operacao.equals("E"))
						mapGrupo.get(grupo).getMapMaterial().get(material).getMapPeriodo().get(periodo).getEntradas().add(movimento);
					else
						mapGrupo.get(grupo).getMapMaterial().get(material).getMapPeriodo().get(periodo).getSaidas().add(movimento);
				}
			}
			
			List<String> gs = new ArrayList<String>(mapGrupo.keySet());
			Collections.sort(gs, new TextoStringComparador());
			for(String g : gs){
				lista.add(mapGrupo.get(g));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}

	private int getSaldoInicial(int idMaterial, String data){
		String sql = "select  "+
						"sum(case when c.in_quantidade_atual = 0 then c.in_quantidade_movimentacao else c.in_quantidade_atual end) as saldo "+
						"from tb_material a  "+
						"left join tb_estoque b on a.id_material = b.id_material "+
						"left join tb_movimento_livro c on c.id_estoque = b.id_estoque and c.dt_data_movimento = "+ 
						"	(select min(d.dt_data_movimento) from tb_movimento_livro d where d.id_estoque = b.id_estoque and to_char(d.dt_data_movimento, 'MM-yyyy') = to_char(c.dt_data_movimento, 'MM-yyyy')) "+
						"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento "+
						"where a.id_material = "+idMaterial+" and to_char(c.dt_data_movimento, 'MM-yyyy') = '"+data+"'";
				
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while(rs.next())
				return rs.getInt("saldo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getSaldoFinal(int idMaterial, String data){
		String sql = "select  "+
						"sum(case when f.tp_operacao = 'E' then c.in_quantidade_atual + c.in_quantidade_movimentacao "+ 
						"	else c.in_quantidade_atual - c.in_quantidade_movimentacao end) as saldo "+
						"from tb_material a  "+
						"left join tb_estoque b on a.id_material = b.id_material "+
						"left join tb_movimento_livro c on c.id_estoque = b.id_estoque and c.dt_data_movimento = "+ 
						"	(select max(e.dt_data_movimento) from tb_movimento_livro e where e.id_estoque = b.id_estoque and to_char(e.dt_data_movimento, 'MM-yyyy') = to_char(c.dt_data_movimento, 'MM-yyyy')) "+
						"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento "+
						"where a.id_material = "+idMaterial+" and to_char(c.dt_data_movimento, 'MM-yyyy') = '"+data+"' ";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while(rs.next())
				return rs.getInt("saldo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private Double getPrecoMedio(int mes, int ano, Integer idMaterial){
		if(mes == 0 || ano == 0){
			return 0d;
		}
		
		//ajustando a data para o último dia do mês
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, mes-1);
		c.set(Calendar.YEAR, ano);
		String data = ano+"-"+mes+"-"+c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		String sql = "select "+
						"j.id_material as idMaterial, j.cv_descricao as material, "+ 
						"coalesce(avg(h.db_valor_unitario), 0) precoMedio, coalesce(l.db_preco_medio_transportado, 0) as precoMedioMedLynx, "+ 
						"(case  "+
						"   when avg(h.db_valor_unitario) != 0 and l.db_preco_medio_transportado != 0 then "+ 
						"	((avg(h.db_valor_unitario) + coalesce(l.db_preco_medio_transportado, 0)) / 2) "+
						"   when l.db_preco_medio_transportado != 0 then l.db_preco_medio_transportado  "+
						"   else coalesce(avg(h.db_valor_unitario), 0) end) as media "+
						"from tb_material j "+
						"left join tb_estoque i on j.id_material = i.id_material "+
						"left join tb_nota_fiscal_estoque h on h.id_estoque = i.id_estoque "+
						"left join tb_nota_fiscal k on h.id_nota_fiscal = k.id_nota_fiscal "+
						"left join tb_preco_medio_transportado_medlynx l on l.id_material = j.id_material "+
						"where j.id_material = "+idMaterial+" and  "+
						"((k.dt_data_contabil between cast('2013-06-01' as date) and cast((date '"+data+"' + integer '1') as date) and k.bl_doacao is false) or k.id_nota_fiscal is null) "+
						"group by idMaterial, material, l.db_preco_medio_transportado";
				
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while(rs.next())
				return rs.getDouble("media");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0d;
	}
	
	private String sqlRelatorioCustoEstoque() {
		Calendar dataI = Calendar.getInstance();
		Calendar dataF = Calendar.getInstance();
		dataI.setTime(dataIni);
		dataI.set(Calendar.DAY_OF_MONTH, 01);
		
		dataF.setTime(dataFim);
		dataF.set(Calendar.DAY_OF_MONTH, dataF.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "select to_ascii(o.cv_descricao) as grupo, "+
				"a.id_material as idMaterial, "+
				"a.cv_codigo_material as codigoMaterial, "+
				"to_ascii(a.cv_descricao) as material, "+ 
				"a.id_material as idMaterial, "+
				"extract(month from c.dt_data_movimento) as mes, "+
				"extract(year from c.dt_data_movimento) as ano, "+
				"d.tp_operacao tipoOperacao, "+
				"to_ascii(d.cv_descricao) tipoMovimento, "+
				"sum(c.in_quantidade_movimentacao) as totalMovimentado "+
				"from tb_material a  "+
				"inner join tb_familia m on m.id_familia = a.id_familia "+
				"inner join tb_sub_grupo n on n.id_sub_grupo = m.id_sub_grupo "+
				"inner join tb_grupo o on o.id_grupo = n.id_grupo "+
				"left join tb_estoque b on b.id_material = a.id_material "+
				"left join tb_movimento_livro c on c.id_estoque = b.id_estoque and "
					+ "(c.dt_data_movimento is null or c.dt_data_movimento between cast('"+sdf.format(dataI.getTime())+"' as date) and cast((date '"+sdf.format(dataF.getTime())+"' + integer '1') as date)) "+
				"left join tb_tipo_movimento d on d.id_tipo_movimento = c.id_tipo_movimento "+
				"group by grupo, idMaterial, material, codigoMaterial, a.id_material, ano, mes, tipoOperacao, tipoMovimento "+
				"order by grupo, lower(to_ascii(a.cv_descricao)), ano, mes, d.tp_operacao, tipoMovimento";
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

	
}
