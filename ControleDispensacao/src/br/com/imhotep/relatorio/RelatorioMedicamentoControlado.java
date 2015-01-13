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
import br.com.imhotep.comparador.TextoStringComparador;
import br.com.imhotep.entidade.extra.MedicamentoControladoLista;
import br.com.imhotep.entidade.extra.MedicamentoControladoListaMedicamento;
import br.com.imhotep.entidade.extra.MedicamentoControladoListaMedicamentoEstoque;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.relatorio.excel.RelatorioEstoqueVencimentoExcel;
import br.com.imhotep.relatorio.excel.RelatorioMedicamentoControladoExcel;

@ManagedBean
@ViewScoped
public class RelatorioMedicamentoControlado extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private boolean excel;
	
	public void gerarRalatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio;
		
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioMedicamentoControlado.jasper";
			nomeRelatorio = "RelatorioMedicamentoControlado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			HashMap<String, Object> map = new HashMap<String, Object>();
	
			InputStream subInputStream = this.getClass().getResourceAsStream("RelatorioMedicamentoControladoMedicamento.jasper");
			map.put("SUBREPORT_INPUT_STREAM_MATERIAL", subInputStream);
			
			InputStream subInputStream2 = this.getClass().getResourceAsStream("RelatorioMedicamentoControladoMedicamentoEstoque.jasper");
			map.put("SUBREPORT_INPUT_STREAM_ESTOQUE", subInputStream2);
			
			super.geraRelatorio(caminho, nomeRelatorio, getLista(), map);
		}
		else{
			nomeRelatorio = "RelatorioEstoqueVencido-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioMedicamentoControladoExcel exc;
	        exc = new RelatorioMedicamentoControladoExcel(  getLista(), "Farm�cia", null,4);
	        exc.gerarPlanilha();
			super.geraRelatorioExcel(nomeRelatorio, exc.getWorkbook());
		}
	}
	
	private List<MedicamentoControladoLista> getLista(){
		String sql = getSqlControlado();
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		List<MedicamentoControladoLista> lista = new ArrayList<MedicamentoControladoLista>();
		try {
			Map<String, MedicamentoControladoLista> mapLista = new HashMap<String, MedicamentoControladoLista>();
			while (rs.next()) {
				//add lista
				String listaDes = rs.getString("lista") + " - " + rs.getString("descricaoLista");
				if(!mapLista.containsKey(listaDes)){
					mapLista.put(listaDes, new MedicamentoControladoLista(listaDes));
				}
				
				//add material
				String material = rs.getString("codigoMaterial") + " - " + rs.getString("material");
				if(!mapLista.get(listaDes).getMapMaterial().containsKey(material)){
					mapLista.get(listaDes).getMapMaterial().put(material, new MedicamentoControladoListaMedicamento(material));
				}
				
				//add estoque
				String lote = rs.getString("lote");
				if(lote != null){
					if(!mapLista.get(listaDes).getMapMaterial().get(material).getMapEstoque().containsKey(lote)){
						MedicamentoControladoListaMedicamentoEstoque estoque = new MedicamentoControladoListaMedicamentoEstoque(lote, rs.getString("fabricante"), rs.getDate("dataValidade"), rs.getInt("quantidadeAtual"));
						mapLista.get(listaDes).getMapMaterial().get(material).getMapEstoque().put(lote, estoque);
					}
				}
					
			}
			
			List<String> gs = new ArrayList<String>(mapLista.keySet());
			Collections.sort(gs, new TextoStringComparador());
			for(String g : gs){
				lista.add(mapLista.get(g));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	private String getSqlControlado(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return "select "+
				"b.cv_lista as lista, b.cv_descricao as descricaoLista, a.cv_codigo_material as codigoMaterial, to_ascii(a.cv_descricao) material, "+ 
				"c.cv_lote as lote, d.cv_descricao as fabricante, c.dt_data_validade as dataValidade, c.in_quantidade_atual as quantidadeAtual "+
				"from tb_material a  "+
				"inner join tb_lista_especial b on b.id_lista_especial = a.id_lista_especial "+
				"left join tb_estoque c on a.id_material = c.id_material "+
				"left join tb_fabricante d on d.id_fabricante = c.id_fabricante "+
				"where c.in_quantidade_atual > 0 and c.dt_data_validade >= '"+new SimpleDateFormat("yyyy-MM-dd").format(c.getTime())+"' and c.bl_bloqueado is false "+
				"group by lista, descricaoLista, codigoMaterial, material, lote, fabricante, dataValidade, quantidadeAtual "+
				"order by lista, descricaoLista, codigoMaterial, material, lote, fabricante, dataValidade, quantidadeAtual";
	}

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	
}
