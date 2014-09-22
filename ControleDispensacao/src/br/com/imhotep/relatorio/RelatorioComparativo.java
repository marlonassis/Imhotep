package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.extra.MaterialSaldoComparativo;
import br.com.imhotep.entidade.extra.EstoqueApoio;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@ViewScoped
public class RelatorioComparativo extends PadraoRelatorio{
	
	private static final String BANCO = Constantes.NOME_BANCO_IMHOTEP;
	private static final String IP_CONSULTA = Constantes.IP_LOCAL;
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioComparativoEstoqueFarmacia.jasper";
		String nomeRelatorio = "EstoqueComparativo-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<MaterialSaldoComparativo> list = getLista();
		HashMap<String, Object> map = new HashMap<String, Object>();
		InputStream subInputStreamEstoques = this.getClass().getResourceAsStream("RelatorioComparativoEstoqueFarmaciaLote.jasper");
		map.put("SUBREPORT_INPUT_STREAM_ESTOQUES_INVENTARIADOS", subInputStreamEstoques);
		map.put("SUBREPORT_INPUT_STREAM_ESTOQUES_SISTEMA", subInputStreamEstoques);
		super.geraRelatorio(caminho, nomeRelatorio, list, map);
	}

	
	private List<MaterialSaldoComparativo> getLista(){
		String sql = "select a.id_material, upper(a.cv_descricao) material, coalesce(b.in_saldo_transportado, 0) saldoTransportado, "+ 
	
				"(coalesce((select sum(c.in_quantidade_movimentacao) from tb_movimento_livro c "+
				"left join tb_estoque d on d.id_estoque = c.id_estoque  "+
				"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento "+  
				"where f.tp_operacao = 'E' and  "+
				"d.id_material = a.id_material and "+ 
				"cast(c.dt_data_movimento as timestamp) <= cast('2014-07-13 23:59:59' as timestamp) and "+
				"cast(d.dt_data_validade as timestamp) >= cast('2014-07-13 23:59:59' as timestamp) and  "+
				"cast(d.dt_data_inclusao as timestamp) <= cast('2014-07-13 23:59:59' as timestamp)), 0) -  "+
				"coalesce((select sum(c.in_quantidade_movimentacao) from tb_movimento_livro c "+
				"left join tb_estoque d on d.id_estoque = c.id_estoque  "+
				"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento "+  
				"where f.tp_operacao != 'E' and  "+
				"d.id_material = a.id_material and  "+
				"cast(c.dt_data_movimento as timestamp) <= cast('2014-07-13 23:59:59' as timestamp) and "+
				"cast(d.dt_data_validade as timestamp) >= cast('2014-07-13 23:59:59' as timestamp) and  "+
				"cast(d.dt_data_inclusao as timestamp) <= cast('2014-07-13 23:59:59' as timestamp)), 0)) saldoDomingo, "+
				
				"coalesce(sum(c.in_quantidade_contada), 0) qtdInventario  "+
				
				"from tb_material a  "+
				"left join tb_preco_medio_transportado_medlynx b on a.id_material = b.id_material "+
				"left join farmacia.tb_inventario c on c.id_material = a.id_material "+
				"group by a.id_material, a.cv_descricao, b.in_saldo_transportado "+
				"order by to_ascii(lower(a.cv_descricao))";
		
		LinhaMecanica lm = new LinhaMecanica(BANCO, IP_CONSULTA);
		List<MaterialSaldoComparativo> lista = new ArrayList<MaterialSaldoComparativo>();
		try {
			lm.criarConexao();
			ResultSet rs = lm.fastConsulta(sql);
			while(rs.next()){
				String material = rs.getString("material");
				Integer saldoTransportado = rs.getInt("saldoTransportado");
				Integer saldoDomingo = rs.getInt("saldoDomingo");
				Integer qtdInventario = rs.getInt("qtdInventario");
				Integer idMaterial = rs.getInt("id_material");
				MaterialSaldoComparativo msc = new MaterialSaldoComparativo();
				msc.setMaterial(material);
				msc.setSaldoTransportado(saldoTransportado);
				msc.setSaldoDomingo(saldoDomingo);
				msc.setQtdInventario(qtdInventario);
				String sqlEstoque = "select cv_lote, in_quantidade_contada from farmacia.tb_inventario where id_material = "+idMaterial;
				ResultSet rs2 = lm.fastConsulta(sqlEstoque);
				while(rs2.next()){
					String lote = rs2.getString("cv_lote");
					Integer quantidadeContada = rs2.getInt("in_quantidade_contada");
					EstoqueApoio mce = new EstoqueApoio();
					mce.setLote(lote);
					mce.setQuantidadeAtual(quantidadeContada);
					mce.setTipo("Inventariado");
					msc.getListaEstoqueInventariado().add(mce);
				}
				
				sqlEstoque = "select d.id_estoque, d.cv_lote, d.dt_data_validade from tb_estoque d "+
								"where cast(d.dt_data_validade as timestamp) >= cast('2014-07-13 23:59:59' as timestamp) and "+  
								"cast(d.dt_data_inclusao as timestamp) <= cast('2014-07-13 23:59:59' as timestamp) and "+ 
								"d.id_material = " + idMaterial; 
				rs2 = lm.fastConsulta(sqlEstoque);
				while(rs2.next()){
					String lote = rs2.getString("cv_lote");
					Integer idEstoque = rs2.getInt("id_estoque");
					Date validade = rs2.getTimestamp("dt_data_validade");
					EstoqueApoio mce = new EstoqueApoio();
					mce.setLote(lote);
					mce.setValidade(new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(validade));
					
					String sql3 = "select (coalesce((select sum(c.in_quantidade_movimentacao) from tb_movimento_livro c "+ 
									"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento  "+
									"where f.tp_operacao = 'E' and c.id_estoque = "+idEstoque+" and "+
									"cast(c.dt_data_movimento as timestamp) <= cast('2014-07-13 23:59:59' as timestamp)), 0) - "+  
									"coalesce((select sum(c.in_quantidade_movimentacao) from tb_movimento_livro c "+ 
									"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento  "+
									"where f.tp_operacao != 'E' and c.id_estoque = "+idEstoque+" and "+
									"cast(c.dt_data_movimento as timestamp) <= cast('2014-07-13 23:59:59' as timestamp)), 0)) saldo";
					
					ResultSet rs3 = lm.fastConsulta(sql3);
					rs3.next();
					int saldo = rs3.getInt("saldo");
					mce.setQuantidadeAtual(saldo);
					mce.setTipo("Sistema");
					if(saldo > 0)
						msc.getListaEstoqueSistema().add(mce);
				}
				
				lista.add(msc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
		return lista;
	}
}
