package br.com.imhotep.consulta.relatorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.imhotep.entidade.Familia;
import br.com.imhotep.entidade.Grupo;
import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.SubGrupo;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.extra.RelMaterialPorUnidade;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioMateriaisPorUnidade extends ConsultaGeral<Object[]>{
	//TODO Rel 1
	public List<RelMaterialPorUnidade> consultaAlmoxarifado( Unidade unidade, GrupoAlmoxarifado grupoAlmoxarifado, 
			SubGrupoAlmoxarifado subGrupoAlmoxarifado, Date dataIni, Date dataFim ) throws SQLException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT uni.id_unidade as codUnidade, UPPER(uni.cv_nome) AS unidade, mat.cv_descricao AS material, SUM(item.in_quantidade_solicitada) as qtd_solicitada,SUM(mov.in_quantidade_movimentacao) AS qtd_consumida, grupo.cv_descricao AS grupo, subg.cv_descricao AS subgrupo, mat.id_material_almoxarifado AS codigo ");
		sql.append("FROM tb_solicitacao_material_almoxarifado_unidade sol ");
			sql.append("INNER JOIN tb_unidade uni ON uni.id_unidade=sol.id_unidade_destino ");
			sql.append("INNER JOIN tb_solicitacao_material_almoxarifado_unidade_item item ON sol.id_solicitacao_material_almoxarifado_unidade=item.id_solicitacao_material_almoxarifado_unidade ");
			sql.append("INNER JOIN tb_material_almoxarifado mat ON mat.id_material_almoxarifado=item.id_material_almoxarifado ");
			sql.append("INNER JOIN tb_grupo_almoxarifado grupo ON grupo.id_grupo_almoxarifado=mat.id_grupo_almoxarifado ");
			sql.append("LEFT JOIN tb_sub_grupo_almoxarifado subg ON subg.id_sub_grupo_almoxarifado=mat.id_sub_grupo_almoxarifado ");
			
			sql.append("INNER JOIN tb_dispensacao_simples_almoxarifado disp ON disp.id_solicitacao_material_almoxarifado_unidade_item=item.id_solicitacao_material_almoxarifado_unidade_item ");
			sql.append("LEFT JOIN tb_movimento_livro_almoxarifado mov ON mov.id_movimento_livro_almoxarifado=disp.id_movimento_livro_almoxarifado ");			
		sql.append("WHERE date(sol.dt_data_insercao) BETWEEN date('"+dataIni.toString()+"') AND date('"+dataFim.toString()+"') ");
			
			if(unidade!=null){
				sql.append("AND uni.id_unidade= "+unidade.getIdUnidade()+"  ");
			}

			if(grupoAlmoxarifado!=null){
				sql.append("AND grupo.id_grupo_almoxarifado= "+grupoAlmoxarifado.getIdGrupoAlmoxarifado()+"  ");
			}
			if(subGrupoAlmoxarifado!=null){
				sql.append("AND subg.id_sub_grupo_almoxarifado= "+subGrupoAlmoxarifado.getIdSubGrupoAlmoxarifado()+" ");
			}
		sql.append("AND mov.id_tipo_movimento_almoxarifado=6 ");
		sql.append("GROUP BY codUnidade, unidade, materiaL, codigo, grupo, subgrupO ");
		sql.append("ORDER BY uni.cv_nome, grupo, subgrupo, material ");
			
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql.toString()));
		
		List<RelMaterialPorUnidade> lista = new ArrayList<RelMaterialPorUnidade>();
			
		int idUnidadeAnt = -1;
		RelMaterialPorUnidade unid = null;
		while (rs.next()) {
			
			int codUnidAtual = rs.getInt("codUnidade");
			if(idUnidadeAnt != codUnidAtual){
				if(unid!=null)
					lista.add(unid);
				unid = 
					new RelMaterialPorUnidade(rs.getString("unidade") );
				idUnidadeAnt = codUnidAtual;
			}
			unid.addItem(rs.getString("material"), rs.getString("qtd_solicitada"), rs.getString("qtd_consumida"), rs.getString("grupo"), rs.getString("subgrupo"), rs.getString("codigo"), null);
			
		}
		
		if(unid!=null)
			lista.add(unid);
			
		return lista;
		
	}

	public List<RelMaterialPorUnidade> consultaFarmacia( Unidade unidade, Grupo grupo, 
			SubGrupo subGrupo, Date dataIni, Date dataFim, Familia familia ) throws SQLException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT uni.id_unidade as codUnidade, UPPER(uni.cv_nome) AS unidade, mat.cv_descricao AS material, SUM(item.in_quantidade_solicitada) as qtd_solicitada,SUM(mov.in_quantidade_movimentacao) AS qtd_consumida, "+ 
					"grupo.cv_descricao AS grupo, subg.cv_descricao AS subgrupo, mat.id_material AS codigo, fam.cv_descricao as familia ");
		sql.append("FROM tb_solicitacao_medicamento_unidade sol ");
			sql.append("INNER JOIN tb_unidade uni ON uni.id_unidade=sol.id_unidade_destino ");
			sql.append("INNER JOIN tb_solicitacao_medicamento_unidade_item item ON sol.id_solicitacao_medicamento_unidade=item.id_solicitacao_medicamento_unidade ");
			sql.append("INNER JOIN tb_material mat ON mat.id_material=item.id_material ");
			sql.append("INNER JOIN tb_familia fam ON fam.id_familia=mat.id_familia ");
			sql.append("INNER JOIN tb_sub_grupo subg ON subg.id_sub_grupo=fam.id_sub_grupo ");
			
			sql.append("INNER JOIN tb_grupo grupo ON grupo.id_grupo=subg.id_grupo ");
			sql.append("LEFT JOIN tb_dispensacao_simples disp ON disp.id_solicitacao_medicamento_unidade_item=item.id_solicitacao_medicamento_unidade_item ");
			sql.append("LEFT JOIN tb_movimento_livro mov ON mov.id_movimento_livro=disp.id_movimento_livro  ");
		sql.append("WHERE date(sol.dt_data_insercao) BETWEEN date('"+dataIni.toString()+"') AND date('"+dataFim.toString()+"') ");
			sql.append("AND mov.id_tipo_movimento=21 ");
			
		if(unidade!=null){
			sql.append("AND uni.id_unidade= "+unidade.getIdUnidade()+"  ");
		}

		if(grupo!=null){
			sql.append("AND grupo.id_grupo= "+grupo.getIdGrupo()+"  ");
		}
		if(subGrupo!=null){
			sql.append("AND subg.id_sub_grupo= "+subGrupo.getIdSubGrupo()+" ");
		}
		if(familia!=null){
			sql.append("AND subg.id_sub_grupo= "+familia.getIdFamilia()+" ");
		}
		
		sql.append("GROUP BY codUnidade, unidade, material, codigo, grupo, subgrupo, familia ");
		sql.append("ORDER BY uni.cv_nome, material ");
			
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql.toString()));
		
		List<RelMaterialPorUnidade> lista = new ArrayList<RelMaterialPorUnidade>();
			
		int idUnidadeAnt = -1;
		RelMaterialPorUnidade unid = null;
		while (rs.next()) {
			
			int codUnidAtual = rs.getInt("codUnidade");
			if(idUnidadeAnt != codUnidAtual){
				if(unid!=null)
					lista.add(unid);
				unid = 
					new RelMaterialPorUnidade(rs.getString("unidade") );
				idUnidadeAnt = codUnidAtual;
			}
			unid.addItem(rs.getString("material"), rs.getString("qtd_solicitada"), rs.getString("qtd_consumida"), rs.getString("grupo"), 
					rs.getString("subgrupo"), rs.getString("codigo"), rs.getString("familia"));
			
		}
		
		if(unid!=null)
			lista.add(unid);
			
		return lista;
		
	}
}
