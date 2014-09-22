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
import br.com.imhotep.entidade.extra.EstoqueInventario;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@ViewScoped
public class RelatorioInventario extends PadraoRelatorio{
	
	private static final String BANCO = Constantes.NOME_BANCO_IMHOTEP;
	private static final String IP_CONSULTA = Constantes.IP_LOCAL;
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorioAlmoxarifado() throws ClassNotFoundException, IOException, JRException, SQLException{
		List<EstoqueInventario> listaInventariada = null;
		List<EstoqueInventario> listaNaoInventariada = null;
		gerarRelatorio("Almoxarifado", listaInventariada, listaNaoInventariada);
	}
	
	public void gerarRelatorioFarmacia() throws ClassNotFoundException, IOException, JRException, SQLException{
		List<EstoqueInventario> listaInventariada = getListaInventariadaFarmacia();
		List<EstoqueInventario> listaNaoInventariada = getListaNaoInventariadaFarmacia();
		gerarRelatorio("Farm‡cia", listaInventariada, listaNaoInventariada);
	}
	
	private List<EstoqueInventario> getListaNaoInventariadaFarmacia(){
		String sql = "select c.cv_descricao material, upper(a.cv_lote) lote, a.dt_data_validade, "+
						"(coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b "+ 
						"inner join tb_tipo_movimento c on b.id_tipo_movimento = c.id_tipo_movimento  "+
						"where c.tp_operacao = 'E' and b.id_estoque = a.id_estoque and b.dt_data_movimento <= cast('2014-07-13 23:59:59' as timestamp)), 0) - "+
						"coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b  "+
						"inner join tb_tipo_movimento c on b.id_tipo_movimento = c.id_tipo_movimento  "+
						"where c.tp_operacao != 'E' and b.id_estoque = a.id_estoque and b.dt_data_movimento <= cast('2014-07-13 23:59:59' as timestamp)), 0) ) qtdAtual "+
						" from tb_estoque a  "+
						"inner join tb_material c on a.id_material = c.id_material "+
						"left join farmacia.tb_inventario b on upper(trim(b.cv_lote)) = upper(trim(a.cv_lote)) "+ 
						"where cast(a.dt_data_validade as timestamp) > cast('2014-07-13 23:59:59' as timestamp) and "+ 
						"cast(a.dt_data_inclusao as timestamp) < cast('2014-07-13 23:59:59' as timestamp) and "+
						"b.id_inventario is null and a.in_quantidade_atual > 0 "+
						"order by lower(to_ascii(c.cv_descricao)), lower(to_ascii(a.cv_lote))";
		
		LinhaMecanica lm = new LinhaMecanica(BANCO, IP_CONSULTA);
		List<EstoqueInventario> lista = new ArrayList<EstoqueInventario>();
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				String material = rs.getString("material");
				String lote = rs.getString("lote");
				Integer qtdAtual = rs.getInt("qtdAtual");
				Date validade = rs.getTimestamp("dt_data_validade");
				EstoqueInventario ei = new EstoqueInventario();
				ei.setMaterial(material);
				ei.setLote(lote);
				ei.setQuantidadeAtual(qtdAtual);
				if(validade != null)
					ei.setValidade(new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(validade));
				else
					ei.setValidade("");
				lista.add(ei);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	private List<EstoqueInventario> getListaInventariadaFarmacia(){
		String sql = "select b.cv_descricao material, (c.id_estoque is not null) cadastrado, a.dt_data_validade, "+
						"upper(a.cv_lote) lote, a.in_quantidade_contada qtdContada, a.in_quantidade_atual_estoque qtdAtual "+
						"from farmacia.tb_inventario a "+ 
						"inner join tb_material b on a.id_material = b.id_material "+
						"left join tb_estoque c on upper(trim(c.cv_lote)) = upper(trim(a.cv_lote)) "+
						"order by lower(to_ascii(b.cv_descricao)), lower(to_ascii(a.cv_lote))";
		LinhaMecanica lm = new LinhaMecanica(BANCO, IP_CONSULTA);
		List<EstoqueInventario> lista = new ArrayList<EstoqueInventario>();
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				String material = rs.getString("material");
				String lote = rs.getString("lote");
				Boolean cadastrado = rs.getBoolean("cadastrado");
				Integer qtdContada = rs.getInt("qtdContada");
				Integer quantidadeAtual = rs.getInt("qtdAtual");
				Date validade = rs.getTimestamp("dt_data_validade");
				EstoqueInventario ei = new EstoqueInventario();
				ei.setMaterial(material);
				ei.setLote(lote);
				ei.setCadastrado(cadastrado ? "Sim" : "N‹o");
				ei.setQuantidadeContada(qtdContada);
				ei.setQuantidadeAtual(quantidadeAtual);
				if(validade != null)
					ei.setValidade(new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(validade));
				else
					ei.setValidade("");
				lista.add(ei);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	private void gerarRelatorio(String setor, List<EstoqueInventario> listaInventariada, List<EstoqueInventario> listaNaoInventariada) throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioEstoqueInventario.jasper";
		String nomeRelatorio = "Inventario-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("SETOR", setor);
		map.put("LISTA_ESTOQUE_INVENTARIO", listaInventariada);
		map.put("LISTA_ESTOQUE_NAO_INVENTARIO",  listaNaoInventariada);
		
		InputStream subInputEstoqueInventarioEstoqueInventariado = this.getClass().getResourceAsStream("RelatorioEstoqueInventarioEstoqueInventariado.jasper");
		InputStream subInputRelatorioEstoqueInventarioEstoqueNaoInventariado = this.getClass().getResourceAsStream("RelatorioEstoqueInventarioEstoqueNaoInventariado.jasper");
		map.put("SUBREPORT_ESTOQUE_INVENTARIO", subInputEstoqueInventarioEstoqueInventariado);
		map.put("SUBREPORT_ESTOQUE_NAO_INVENTARIO", subInputRelatorioEstoqueInventarioEstoqueNaoInventariado);
		ArrayList<EstoqueInventario> list = new ArrayList<EstoqueInventario>();
		list.add(new EstoqueInventario());
		super.geraRelatorio(caminho, nomeRelatorio, list, map);
	}

}
