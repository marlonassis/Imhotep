package br.com.imhotep.relatorio;

import java.io.IOException;
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
import br.com.imhotep.entidade.extra.ConsumoAnualAlmoxarifado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.relatorio.excel.RelatorioConsumoAnualAlmoxarifadoExcel;

@ManagedBean
@SessionScoped
public class RelatorioConsumoAnualAlmoxarifado extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private String ano = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	private boolean excel;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException{
		String nomeRelatorio;
		List<ConsumoAnualAlmoxarifado> listaConsumoAnualAlmoxarifado = listaMediaConsumoAlmoxarifado();
			
		//Solicitação de Mudança #12
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioConsumoAnualAlmoxarifado.jasper";
			nomeRelatorio = "RelatorioConsumoAnual-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ANO", getAno());
		
			super.geraRelatorio(caminho, nomeRelatorio, listaConsumoAnualAlmoxarifado, map);
		}
		else{
			nomeRelatorio = "RelatorioConsumoAnual-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioConsumoAnualAlmoxarifadoExcel exc;
	        exc = new RelatorioConsumoAnualAlmoxarifadoExcel(listaConsumoAnualAlmoxarifado, "Almoxarifado", getAno(),13);
	        exc.gerarPlanilha();
			super.geraRelatorioExcel(nomeRelatorio, exc.getWorkbook());
		}
	}

	private List<ConsumoAnualAlmoxarifado> listaMediaConsumoAlmoxarifado(){
		List<ConsumoAnualAlmoxarifado> res = new ArrayList<ConsumoAnualAlmoxarifado>();
		LinhaMecanica lm = new LinhaMecanica();
		try {
			lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
			lm.setIp(Constantes.IP_LOCAL);
			lm.criarConexao();
			String sqlMaterial = "select a.id_material_almoxarifado, a.cv_descricao, b.cv_sigla from tb_material_almoxarifado a "
								+" inner join tb_unidade_material_almoxarifado b on a.id_unidade_material_almoxarifado = b.id_unidade_material_almoxarifado";
			List<Object[]> listaResultado = lm.getListaResultadoFast(sqlMaterial);
			for(Object[] material : listaResultado){
				Integer idMaterialAlmoxarfiado = (Integer) material[0];
				String descricao = String.valueOf(material[1]).toUpperCase().trim();
				String sigla = String.valueOf(material[2]);
				List<String> totalConsumo=new ArrayList<String>();
				
				for(int i = 1; i < 13; i++){
					int idConsumo = Constantes.ID_TIPO_MOVIMENTO_SAIDA_DISPENSACAO_ALMOXARIFADO;
					String data = getAno()+"-"+(i < 10 ? "0"+i : i);
					String sqlConsumo = "select" 
										+" coalesce("
										+" (select "
										+" sum(d.in_quantidade_movimentacao)"
										+" from tb_movimento_livro_almoxarifado d "
										+" left join tb_estoque_almoxarifado b on d.id_estoque_almoxarifado = b.id_estoque_almoxarifado and"
										+" d.id_tipo_movimento_almoxarifado in (select o.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado o where o.tp_operacao = 'E')" 
										+" where a.id_material_almoxarifado = b.id_material_almoxarifado and to_char(d.dt_data_movimento, 'YYYY-MM') = '"+data+"')"
										+" , 0) totalEntrada," 
										+" coalesce("
										+" (select "
										+" sum(e.in_quantidade_movimentacao)"
										+" from tb_movimento_livro_almoxarifado e"
										+" left join tb_estoque_almoxarifado f on f.id_estoque_almoxarifado = e.id_estoque_almoxarifado"
										+" where a.id_material_almoxarifado = f.id_material_almoxarifado and e.id_tipo_movimento_almoxarifado = "+idConsumo 
										+" and to_char(e.dt_data_movimento, 'YYYY-MM') = '"+data+"')"
										+" , 0) totalConsumo"
										+" from tb_material_almoxarifado a "
										+" where a.id_material_almoxarifado = "+idMaterialAlmoxarfiado;
					List<Object[]> listaResultado2 = lm.getListaResultadoFast(sqlConsumo, 2);
					for(Object[] consumo : listaResultado2){
						totalConsumo.add(((Long) consumo[0]) + "/" + ((Long) consumo[1]));
					}
				}
				ConsumoAnualAlmoxarifado consumoAnualAlmoxarifado = new ConsumoAnualAlmoxarifado(idMaterialAlmoxarfiado, totalConsumo, descricao, sigla);
				res.add(consumoAnualAlmoxarifado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}

		return res;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}

}