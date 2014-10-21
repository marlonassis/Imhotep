package br.com.imhotep.consulta.relatorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.imhotep.entidade.Familia;
import br.com.imhotep.entidade.Grupo;
import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.SubGrupo;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.extra.RelMaterialPorUnidade;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

/**
 * Criada por Asclepíades Neto 
 * Data: 09/09/2014
 * Funcionalidade: Relatório de materiais do almoxarifado/farmácia sem consumo.
 */
//TODO REL3 - ALMOXARIFADO
public class ConsultaMateriaisSemConsumo extends ConsultaGeral<Object[]> {
	public List<MaterialAlmoxarifado> consultaAlmoxarifado( GrupoAlmoxarifado grupoAlmoxarifado, 
			SubGrupoAlmoxarifado subGrupoAlmoxarifado, Date dataIni, Date dataFim ) throws SQLException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT mat.id_material_almoxarifado as idMaterial, mat.cv_descricao as material, grupo.cv_descricao AS grupo, subg.cv_descricao AS subgrupo  ");
		sql.append("FROM tb_material_almoxarifado mat ");
			sql.append("LEFT JOIN ( ");
				sql.append("SELECT mat2.id_material_almoxarifado as idMaterial, COUNT(mat2.id_material_almoxarifado) AS qtdDispensa ");
				sql.append("FROM tb_material_almoxarifado mat2  ");
					sql.append("LEFT JOIN tb_estoque_almoxarifado est ON est.id_material_almoxarifado = mat2.id_material_almoxarifado ");
					sql.append("LEFT JOIN tb_movimento_livro_almoxarifado mov ON mov.id_estoque_almoxarifado=est.id_estoque_almoxarifado  ");
				sql.append("WHERE date(mov.dt_data_movimento) BETWEEN date('"+dataIni.toString()+"') AND date('"+dataFim.toString()+"') ");
					sql.append("AND mov.id_tipo_movimento_almoxarifado=6 ");
				sql.append("GROUP BY mat2.id_material_almoxarifado	) disp ON mat.id_material_almoxarifado=disp.idMaterial ");
			sql.append("INNER JOIN tb_grupo_almoxarifado grupo ON grupo.id_grupo_almoxarifado=mat.id_grupo_almoxarifado ");
			sql.append("LEFT JOIN tb_sub_grupo_almoxarifado subg ON subg.id_sub_grupo_almoxarifado=mat.id_sub_grupo_almoxarifado  ");
			sql.append("WHERE disp.idMaterial IS NULL ");	
		if(grupoAlmoxarifado!=null){
			sql.append("AND grupo.id_grupo_almoxarifado= "+grupoAlmoxarifado.getIdGrupoAlmoxarifado()+"  ");
		}
		if(subGrupoAlmoxarifado!=null){
			sql.append("AND subg.id_sub_grupo_almoxarifado= "+subGrupoAlmoxarifado.getIdSubGrupoAlmoxarifado()+" ");
		}
		sql.append("ORDER BY grupo, subgrupo, material ");
			
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql.toString()));
		
		List<MaterialAlmoxarifado> lista = new ArrayList<MaterialAlmoxarifado>();

		MaterialAlmoxarifado novo = null;
		while(rs.next()) {
			novo = new MaterialAlmoxarifado();
			novo.setIdMaterialAlmoxarifado(rs.getInt("idMaterial"));
			novo.setDescricao(rs.getString("material"));
//			novo.setQuantidadeMinima(rs.getInt("estoque"));
			
			GrupoAlmoxarifado grupo = new GrupoAlmoxarifado();
			grupo.setDescricao(rs.getString("grupo"));
			novo.setGrupoAlmoxarifado(grupo );
			
			SubGrupoAlmoxarifado subgrupo = new SubGrupoAlmoxarifado();
			subgrupo.setDescricao(rs.getString("subgrupo"));
			novo.setSubGrupoAlmoxarifado(subgrupo);
			
//			novo.setDataInclusao(rs.getDate("validade"));
			lista.add( novo  );
		}
					
		return lista;
		
	}
	
	public List<Material> consultaFarmacia( Grupo grupo, 
			SubGrupo subGrupo, Date dataIni, Date dataFim ) throws SQLException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT mat.id_material as idMaterial, mat.cv_descricao as material, grupo.cv_descricao AS grupo, subg.cv_descricao AS subgrupo   ");
		sql.append("FROM tb_material mat ");
			sql.append("LEFT JOIN ( ");
				sql.append("SELECT mat2.id_material as idMaterial, COUNT(mat2.id_material) AS qtdDispensa ");
				sql.append("FROM tb_material mat2  ");
					sql.append("LEFT JOIN tb_estoque est ON est.id_material = mat2.id_material ");
					sql.append("LEFT JOIN tb_movimento_livro mov ON mov.id_estoque=est.id_estoque   ");
				sql.append("WHERE date(mov.dt_data_movimento) BETWEEN date('"+dataIni.toString()+"') AND date('"+dataFim.toString()+"') ");
					sql.append("AND mov.id_tipo_movimento=6 ");
				sql.append("GROUP BY mat2.id_material	) disp ON mat.id_material=disp.idMaterial ");
			sql.append("INNER JOIN tb_familia fam ON fam.id_familia=mat.id_familia  ");
			sql.append("INNER JOIN tb_sub_grupo subg ON subg.id_sub_grupo=fam.id_sub_grupo  ");
			sql.append("INNER JOIN tb_grupo grupo ON grupo.id_grupo=subg.id_grupo ");
			sql.append("WHERE disp.idMaterial IS NULL ");	
		if(grupo!=null){
			sql.append("AND grupo.id_grupo= "+grupo.getIdGrupo()+"  ");
		}
		if(subGrupo!=null){
			sql.append("AND subg.id_sub_grupo= "+subGrupo.getIdSubGrupo()+" ");
		}
		sql.append("ORDER BY material ");
			
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql.toString()));
		
		List<Material> lista = new ArrayList<Material>();

		Material novo = null;
		while(rs.next()) {
			novo = new Material();
			novo.setIdMaterial(rs.getInt("idMaterial"));
			novo.setDescricao(rs.getString("material"));
			
			Grupo gr = new Grupo();
			gr.setDescricao(rs.getString("grupo"));
			
			SubGrupo sub = new SubGrupo();
			sub.setDescricao(rs.getString("subgrupo"));
			sub.setGrupo(gr);
			
			Familia familia = new Familia();
			familia.setSubGrupo(sub);
			
			novo.setFamilia(familia);
			
			lista.add( novo  );
		}
					
		return lista;

	}
	
}
