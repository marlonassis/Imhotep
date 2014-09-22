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
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.TextoStringComparador;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterial;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterialGrupo;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterialLote;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterialSubGrupo;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@ViewScoped
public class RelatorioEstoqueAlmoxarifadoCompleto extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRalatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioEstoqueAlmoxarifado.jasper";
		String nomeRelatorio = "RelatorioEstoqueAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
		
		InputStream subInputStreamEstoque = this.getClass().getResourceAsStream("RelatorioEstoqueAlmoxarifadoMaterialEstoque.jasper");
		map.put("SUBREPORT_INPUT_STREAM_ESTOQUE", subInputStreamEstoque);
		InputStream subInputStreamMaterial = this.getClass().getResourceAsStream("RelatorioEstoqueAlmoxarifadoMaterial.jasper");
		map.put("SUBREPORT_INPUT_STREAM_MATERIAL", subInputStreamMaterial);
		InputStream subInputStreamMovimentoSubGrupo = this.getClass().getResourceAsStream("RelatorioEstoqueAlmoxarifadoSubGrupo.jasper");
		map.put("SUBREPORT_INPUT_STREAM_SUB_GRUPO", subInputStreamMovimentoSubGrupo);
		
		super.geraRelatorio(caminho, nomeRelatorio, getLista(), map);
	}
	
	private List<EstoqueAlmoxarifadoMaterialGrupo> getLista(){
		String sql = getSqlEstoqueAlmoxarifado();
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		List<EstoqueAlmoxarifadoMaterialGrupo> lista = new ArrayList<EstoqueAlmoxarifadoMaterialGrupo>();
		try {
			Map<String, EstoqueAlmoxarifadoMaterialGrupo> mapGrupo = new HashMap<String, EstoqueAlmoxarifadoMaterialGrupo>();
			while (rs.next()) {
				//add grupo
				String grupo = rs.getString("grupo");
				if(!mapGrupo.containsKey(grupo)){
					mapGrupo.put(grupo, new EstoqueAlmoxarifadoMaterialGrupo(grupo));
				}
				
				//add subgrupo
				String subGrupo = rs.getString("subGrupo");
				if(subGrupo != null && !mapGrupo.get(grupo).getMapSubGrupo().containsKey(subGrupo)){
					mapGrupo.get(grupo).getMapSubGrupo().put(subGrupo, new EstoqueAlmoxarifadoMaterialSubGrupo(subGrupo));
				}
				
				//add material
				String material = rs.getString("material");
				String materialId = rs.getInt("idMaterial") + " - " + material;
				if(subGrupo != null){
					if(!mapGrupo.get(grupo).getMapSubGrupo().get(subGrupo).getMapMaterial().containsKey(material)){
						mapGrupo.get(grupo).getMapSubGrupo().get(subGrupo).getMapMaterial().put(material, new EstoqueAlmoxarifadoMaterial(material, materialId));
					}
				}else{
					if(!mapGrupo.get(grupo).getMapMaterial().containsKey(material)){
						mapGrupo.get(grupo).getMapMaterial().put(material, new EstoqueAlmoxarifadoMaterial(material, materialId));
					}
				}
				
				//add lote
				Integer idEstoque = rs.getInt("idEstoque");
				if(subGrupo != null){
					if(!mapGrupo.get(grupo).getMapSubGrupo().get(subGrupo).getMapMaterial().get(material).getMapLote().containsKey(idEstoque)){
						String responsavel = new Utilitarios().nomeResumido(rs.getString("responsavel"));
						EstoqueAlmoxarifadoMaterialLote estoqueAlmoxarifadoMaterialLote = new EstoqueAlmoxarifadoMaterialLote(idEstoque, 
																															rs.getString("lote"), 
																															rs.getString("fabricante"), 
																															rs.getDate("dataValidade"), 
																															rs.getInt("quantidadeAtual"), 
																															responsavel);
						mapGrupo.get(grupo).getMapSubGrupo().get(subGrupo).getMapMaterial().get(material).getMapLote().put(idEstoque, estoqueAlmoxarifadoMaterialLote);
					}
				}else{
					if(!mapGrupo.get(grupo).getMapMaterial().get(material).getMapLote().containsKey(idEstoque)){
						String responsavel = new Utilitarios().nomeResumido(rs.getString("responsavel"));
						EstoqueAlmoxarifadoMaterialLote estoqueAlmoxarifadoMaterialLote = new EstoqueAlmoxarifadoMaterialLote(idEstoque, 
																															  rs.getString("lote"), 
																															  rs.getString("fabricante"), 
																															  rs.getDate("dataValidade"), 
																															  rs.getInt("quantidadeAtual"),
																															  responsavel);
						mapGrupo.get(grupo).getMapMaterial().get(material).getMapLote().put(idEstoque, estoqueAlmoxarifadoMaterialLote);
					}
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
	
	private String getSqlEstoqueAlmoxarifado(){
		
		return "select to_ascii(c.cv_descricao) as grupo, to_ascii(d.cv_descricao) as subGrupo, "+
				"to_ascii(b.cv_descricao) material, b.id_material_almoxarifado as idMaterial, e.cv_descricao as fabricante, "+ 
				"a.id_estoque_almoxarifado idEstoque, a.cv_lote lote, a.dt_data_validade as dataValidade, a.in_quantidade_atual as quantidadeAtual, " +
				"f.cv_nome responsavel "+ 
				"from tb_estoque_almoxarifado a "+
				"left join tb_fabricante_almoxarifado e on e.id_fabricante_almoxarifado = a.id_fabricante_almoxarifado "+
				"inner join tb_material_almoxarifado b on b.id_material_almoxarifado = a.id_material_almoxarifado "+
				"inner join tb_grupo_almoxarifado c on c.id_grupo_almoxarifado = b.id_grupo_almoxarifado "+
				"left join tb_sub_grupo_almoxarifado d on d.id_sub_grupo_almoxarifado = b.id_sub_grupo_almoxarifado "+
				"inner join tb_profissional f on f.id_profissional = a.id_profissional_inclusao "+
//				"where a.in_quantidade_atual > 0 and (cast(a.dt_data_validade as date) >= cast('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' as date) or a.dt_data_validade is null) and a.bl_bloqueado is false "+
				"group by grupo, subGrupo, material, idMaterial, fabricante, idEstoque, lote, dataValidade, quantidadeAtual, f.cv_nome "+
				"order by grupo, subGrupo, lower(to_ascii(b.cv_descricao)), fabricante, lote, dataValidade, quantidadeAtual, f.cv_nome ";
	}
	
}
