package br.com.imhotep.consulta.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.UnidadeMaterial;
import br.com.imhotep.entidade.extra.MaterialFaltaEstoque;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.raiz.MaterialRaiz;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MaterialConsultaRaiz  extends ConsultaGeral<Material>{
	
	private Material material;
	
	public void atualizaMateriaisQuantidadeAbaixoPermitido() {
		List<MaterialFaltaEstoque> list = consultarMateriaisAbaixoQuantidadeMinima();
		MaterialRaiz.getInstanciaAtual().setMateriaisAbaixoQuantidadeMinima(list);
	}
	
	public List<MaterialFaltaEstoque> consultarMateriaisAbaixoQuantidadeMinima(){
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		StringBuilder stringB = new StringBuilder("select new br.com.imhotep.entidade.extra.MaterialFaltaEstoque(o.idMaterial, o.codigoMaterial, o.descricao, o.quantidadeMinima, sum(a.quantidadeAtual)) ");
		stringB.append("from Material o ");
		stringB.append("join o.estoques a  ");
		stringB.append("where o.quantidadeMinima is not null and o.quantidadeMinima != 0 and ");
		stringB.append("o.quantidadeMinima >=  ");
		stringB.append("(select sum(b.quantidadeAtual) from Estoque b where b.material.idMaterial = o.idMaterial ");
		stringB.append("and b.bloqueado = false and b.dataValidade >= '"+dataS+"') ");
		stringB.append("and a.bloqueado = false and a.dataValidade >= '"+dataS+"' ");
		stringB.append("group by o.descricao, o.quantidadeMinima, o.idMaterial, o.codigoMaterial ");
		stringB.append("order by to_ascii(o.descricao) ");
		
		List<MaterialFaltaEstoque> list = new ArrayList<MaterialFaltaEstoque>(new ConsultaGeral<MaterialFaltaEstoque>().consulta(stringB, null));
		return list;
	}
	
	public Integer quantidadeMaterialEstoqueSemReserva(Material material) throws SQLException{
		String sql = "select "+ 
						"(coalesce((select sum(a.in_quantidade_atual) from tb_estoque a "+
						"inner join tb_material b on a.id_material = b.id_material "+
						"where a.bl_bloqueado = false and a.dt_data_validade >= cast(now() as date) and a.in_quantidade_atual > 0 "+
						"and a.id_material = "+material.getIdMaterial()+"), 0) "+
						"- "+
						"coalesce((select sum(c.in_quantidade_solicitada) from tb_solicitacao_medicamento_unidade_item c "+
						"inner join tb_solicitacao_medicamento_unidade d on d.id_solicitacao_medicamento_unidade = c.id_solicitacao_medicamento_unidade "+
						"where d.tp_status_dispensacao = 'P' and c.id_material = "+material.getIdMaterial()+"), 0)) qtd "; 
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(sql);
		rs.next();
		return rs.getInt("qtd");
	}
	
	public Integer quantidadeMaterialSolicitadoUltimaSolicitacao(Material material) throws SQLException{
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP);
		lm.criarConexao();
		Integer quantidadeMaterialSolicitadoUltimaSolicitacao = quantidadeMaterialSolicitadoUltimaSolicitacao(material, lm);
		lm.fecharConexoes();
		return quantidadeMaterialSolicitadoUltimaSolicitacao;
	}
	
	public Integer quantidadeMaterialSolicitadoUltimaSolicitacao(Material material, LinhaMecanica lm) throws SQLException{
		String sql = sqlUltimoConsumo(material); 
		ResultSet rs = lm.fastConsulta(sql);
		if(rs.next())
			return rs.getInt("in_quantidade_solicitada");
		return null;
	}

	private String sqlUltimoConsumo(Material material) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -4);
		String data = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		String sql = "select a.in_quantidade_solicitada from tb_solicitacao_medicamento_unidade_item a "+
						"where a.id_material = "+material.getIdMaterial()+" and date(a.dt_data_insercao) > date('"+data+"') "+
						"order by a.dt_data_insercao desc "+
						"limit 1";
		return sql;
	}
	
	public Integer quantidadeMaterialEstoque(){
		if(getMaterial() != null){
			String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			String hql = "select sum(o.quantidadeAtual) from Estoque o where o.quantidadeAtual > 0 and o.bloqueado = false and o.dataValidade >= '"+dataS+"' and o.material.idMaterial = "+getMaterial().getIdMaterial();
			Long quantidade = new ConsultaGeral<Long>(new StringBuilder(hql)).consultaUnica();
			if(quantidade == null){
				return 0;
			}else{
				return quantidade.intValue();
			}
		}
		return null;
	}
	
	public List<Material> getMateriaisLiberadosInventario(){
		List<Material> materiais = new ArrayList<Material>();
		String sql  = "select b.id_material, b.cv_descricao, b.in_codigo_material, c.id_unidade_material, c.cv_unidade, c.cv_sigla "+ 
						"from farmacia.tb_inventario_controle a "+
						"inner join tb_material b on a.id_material = b.id_material "+
						"inner join tb_unidade_material c on c.id_unidade_material = b.id_unidade_material "+
						"order by lower(to_ascii(b.cv_descricao)) ";
		ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar(sql);
		try {
			while(rs.next()){
				UnidadeMaterial unidadeMaterial = new UnidadeMaterial();
				unidadeMaterial.setIdUnidadeMaterial(rs.getInt("id_unidade_material"));
				unidadeMaterial.setDescricao(rs.getString("cv_unidade"));
				unidadeMaterial.setSigla(rs.getString("cv_sigla"));
				Material material = new Material();
				material.setUnidadeMaterial(unidadeMaterial);
				material.setIdMaterial(rs.getInt("id_material"));
				material.setCodigoMaterial(rs.getInt("in_codigo_material"));
				material.setDescricao(rs.getString("cv_descricao"));
				materiais.add(material);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materiais;
	}
	
	public List<Material> getMateriaisNaoLiberadosInventario(){
		List<Material> materiais = new ArrayList<Material>();
		String sql  = "select a.id_material, a.cv_descricao, a.in_codigo_material, c.id_unidade_material, c.cv_unidade, c.cv_sigla "+ 
						"from tb_material a "+
						"left join farmacia.tb_inventario_controle b on a.id_material = b.id_material "+
						"inner join tb_unidade_material c on c.id_unidade_material = a.id_unidade_material "+
						"where b.id_material is null "+
						"order by lower(to_ascii(a.cv_descricao))";
		ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP).consultar(sql);
		try {
			while(rs.next()){
				UnidadeMaterial unidadeMaterial = new UnidadeMaterial();
				unidadeMaterial.setIdUnidadeMaterial(rs.getInt("id_unidade_material"));
				unidadeMaterial.setDescricao(rs.getString("cv_unidade"));
				unidadeMaterial.setSigla(rs.getString("cv_sigla"));
				Material material = new Material();
				material.setUnidadeMaterial(unidadeMaterial);
				material.setIdMaterial(rs.getInt("id_material"));
				material.setCodigoMaterial(rs.getInt("in_codigo_material"));
				material.setDescricao(rs.getString("cv_descricao"));
				materiais.add(material);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materiais;
	}
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
}
