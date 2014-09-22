package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.EstoqueABCValorTotalComparador;
import br.com.imhotep.entidade.extra.ItemEstoqueABC;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@SessionScoped
public class RelatorioEstoqueCurvaABC extends PadraoRelatorio{
	private static final long serialVersionUID = 2923122889819782024L;
	
	private Date dataIni = new Date();
	private Date dataFim = new Date();
	
	public void gerarRelatorioAmbos(){
		setDataFim(new Utilitarios().ajustarUltimoDiaMesHoraMaximo(getDataFim()));
		List<ItemEstoqueABC> lista = getResultado(getSqlAlmoxarifado());
		lista.addAll(getResultado(getSqlFarmacia()));
		ordenarClassificar(lista);
		try {
			gerarRelatorio("Almoxarifado/Farm‡cia", lista );
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void gerarRelatorioAlmoxarifado(){
		setDataFim(new Utilitarios().ajustarUltimoDiaMesHoraMaximo(getDataFim()));
		List<ItemEstoqueABC> lista = getResultado(getSqlAlmoxarifado());
		try {
			gerarRelatorio("Almoxarifado", lista );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gerarRelatorioFarmacia(){
		List<ItemEstoqueABC> lista = getResultado(getSqlFarmacia());
		try {
			gerarRelatorio("Farm‡cia", lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<ItemEstoqueABC> getResultado(String sql){
		List<ItemEstoqueABC> lista = carregarLista(sql);
		ordenarClassificar(lista);
		return lista;
	}

	private void ordenarClassificar(List<ItemEstoqueABC> lista) {
		Collections.sort(lista, new EstoqueABCValorTotalComparador());
		for(int i = 0; i < lista.size(); i++){
			lista.get(i).setClassificacao((i+1) + "¼");
		}
	}

	private List<ItemEstoqueABC> carregarLista(String sql) {
		List<ItemEstoqueABC> lista = new ArrayList<ItemEstoqueABC>();
		try {
			ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).consultar(sql);
			while(rs.next()){
				double valorUnitario = rs.getDouble("vlUnit");
				int consumo = rs.getInt("consumo");
				if((valorUnitario * consumo) != 0d){
					ItemEstoqueABC item = new ItemEstoqueABC();
					item.setConsumo(consumo);
					item.setMaterial(rs.getString("material"));
					item.setValorUnitario(valorUnitario);
					lista.add(item);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	private String getSqlFarmacia(){
		String ini = new SimpleDateFormat("yyyy-MM-dd").format(getDataIni());
		String fim = new SimpleDateFormat("yyyy-MM-dd").format(getDataFim());
		return "select a.id_material, trim(a.cv_descricao) material, "+ 
				"coalesce(sum(( select sum(e.in_quantidade_movimentacao) from tb_movimento_livro e "+ 
				"where cast(e.dt_data_movimento as date) between cast('"+ini+"' as date) and cast('"+fim+"' as date) and "+ 
				"e.id_estoque in (select c.id_estoque from tb_estoque c  "+
				"where c.id_material = a.id_material) and e.id_tipo_movimento in "+ 
				"(select b.id_tipo_movimento from tb_tipo_movimento b where b.tp_operacao != 'E'))), 0) consumo, "+ 
				"( "+
				"select avg(c.db_valor_unitario) from tb_estoque b "+ 
				"inner join tb_nota_fiscal_estoque c on c.id_estoque = b.id_estoque "+
				"inner join tb_nota_fiscal d on d.id_nota_fiscal = c.id_nota_fiscal  "+
				"where cast(d.dt_data_contabil as date) between cast('"+ini+"' as date) and cast('"+fim+"' as date) "+ 
				"and a.id_material = b.id_material "+
				") vlUnit  "+
				"from tb_material a "+ 
				"group by a.id_material, trim(a.cv_descricao) "+
				"order by to_ascii(trim(a.cv_descricao))";
	}
	
	private String getSqlAlmoxarifado(){
		String ini = new SimpleDateFormat("yyyy-MM-dd").format(getDataIni());
		String fim = new SimpleDateFormat("yyyy-MM-dd").format(getDataFim());
		
		return	"select a.id_material_almoxarifado, trim(a.cv_descricao) material, "+ 
				"coalesce(sum(( select sum(e.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado e "+ 
				"where cast(e.dt_data_movimento as date) between cast('"+ini+"' as date) and cast('"+fim+"' as date) and "+ 
				"e.id_estoque_almoxarifado in (select c.id_estoque_almoxarifado from tb_estoque_almoxarifado c  "+
				"where c.id_material_almoxarifado = a.id_material_almoxarifado) and e.id_tipo_movimento_almoxarifado in "+ 
				"(select b.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado b where b.tp_operacao != 'E'))), 0) consumo, "+ 
				"( "+
				"select avg(c.db_valor_unitario) from tb_estoque_almoxarifado b "+ 
				"inner join tb_nota_fiscal_estoque_almoxarifado c on c.id_estoque_almoxarifado = b.id_estoque_almoxarifado "+
				"inner join tb_nota_fiscal_almoxarifado d on d.id_nota_fiscal_almoxarifado = c.id_nota_fiscal_almoxarifado  "+
				"where cast(d.dt_data_contabil as date) between cast('"+ini+"' as date) and cast('"+fim+"' as date)  "+
				"and a.id_material_almoxarifado = b.id_material_almoxarifado "+
				") vlUnit  "+
				"from tb_material_almoxarifado a "+ 
				"group by a.id_material_almoxarifado, trim(a.cv_descricao) "+
				"order by to_ascii(trim(a.cv_descricao))";
		
	}
	
	private void gerarRelatorio(String setor, List<ItemEstoqueABC> lista) throws ClassNotFoundException, IOException, JRException, SQLException{
		String caminho = Constantes.DIR_RELATORIO + "RelatorioEstoqueCurvaABC.jasper";
		String nomeRelatorio = "CurvaABC-"+setor+"-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("SETOR", setor);
		map.put("DATA_INI", new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(getDataIni()));
		map.put("DATA_FIM", new SimpleDateFormat("MMM/yyyy", Constantes.LOCALE_BRASIL).format(getDataFim()));
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
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
