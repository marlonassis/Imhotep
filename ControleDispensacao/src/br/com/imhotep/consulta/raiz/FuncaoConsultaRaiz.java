package br.com.imhotep.consulta.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalFuncao;
import br.com.imhotep.entidade.Funcao;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class FuncaoConsultaRaiz  extends ConsultaGeral<Funcao>{
	
	public List<EstruturaOrganizacionalFuncao> funcoesEstruturaExcetoFuncaoProfissionalExcetoChefia(EstruturaOrganizacional estruturaOrganizacional, Profissional profissionalLotado){
		List<EstruturaOrganizacionalFuncao> resultado = new ArrayList<EstruturaOrganizacionalFuncao>();
		if(estruturaOrganizacional != null && profissionalLotado != null){
			int idEO = estruturaOrganizacional.getIdEstruturaOrganizacional();
			int idPro = profissionalLotado.getIdProfissional();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select o from EstruturaOrganizacionalFuncao o where o.estruturaOrganizacional.idEstruturaOrganizacional = ");
			stringBuilder.append(idEO);
			stringBuilder.append(" and o.funcao.chefia is false ");
			stringBuilder.append(" and o.funcao.idFuncao not in ");
			stringBuilder.append("(select a.estruturaOrganizacionalFuncao.funcao.idFuncao from LotacaoProfissionalFuncao a ");
			stringBuilder.append("where a.estruturaOrganizacionalFuncao.estruturaOrganizacional.idEstruturaOrganizacional = ");
			stringBuilder.append(idEO);
			stringBuilder.append(" and a.lotacaoProfissional.profissional.idProfissional = ");
			stringBuilder.append(idPro);
			stringBuilder.append(") order by lower(to_ascii(o.funcao.nome))");
			String hql = stringBuilder.toString();
			
			resultado = new ArrayList<EstruturaOrganizacionalFuncao>(new ConsultaGeral<EstruturaOrganizacionalFuncao>().consulta(new StringBuilder(hql), null));
		}
		return resultado;
	}
	
	public List<EstruturaOrganizacionalFuncao> funcoesLotadas(EstruturaOrganizacional estruturaOrganizacional){
			int id = estruturaOrganizacional.getIdEstruturaOrganizacional();
			String hql = "select o from EstruturaOrganizacionalFuncao o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id;
			List<EstruturaOrganizacionalFuncao> list = new ArrayList<EstruturaOrganizacionalFuncao>(new ConsultaGeral<EstruturaOrganizacionalFuncao>().consulta(new StringBuilder(hql), null));
			return list;
	}
	
	public List<String> funcoesProfissional(LotacaoProfissional lotacao){
		List<String> funcoes = new ArrayList<String>();
		if(lotacao != null){
			int idEO = lotacao.getEstruturaOrganizacional().getIdEstruturaOrganizacional();
			int idP = lotacao.getProfissional().getIdProfissional();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select b.cv_nome funcao from administrativo.tb_lotacao_profissional_funcao a ");
			stringBuilder.append("   inner join administrativo.tb_estrutura_organizacional_funcao d ");
			stringBuilder.append("		on d.id_estrutura_organizacional_funcao = a.id_estrutura_organizacional_funcao ");
			stringBuilder.append("	inner join administrativo.tb_funcao b on b.id_funcao = d.id_funcao ");
			stringBuilder.append("	inner join administrativo.tb_lotacao_profissional c ");
			stringBuilder.append("		on a.id_lotacao_profissional = c.id_lotacao_profissional ");
			stringBuilder.append("where c.id_estrutura_organizacional = ");
			stringBuilder.append(idEO);
			stringBuilder.append(" and c.id_profissional =  ");
			stringBuilder.append(idP);
			String sql = stringBuilder.toString();
			List<Object[]> listaResultado = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).getListaResultado(sql);
			for(Object[] obj : listaResultado){
				funcoes.add(String.valueOf(obj[0]));
			}
		}
		return funcoes;
	}
	
	
	
	public List<Funcao> getFuncoesEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		List<Funcao> funcoes = new ArrayList<Funcao>();
		if(estruturaOrganizacional != null){
			String sql = "select b.* from administrativo.tb_estrutura_organizacional_funcao a "+
							"	inner join administrativo.tb_funcao b on a.id_funcao = b.id_funcao "+
							"where a.id_estrutura_organizacional = " + estruturaOrganizacional.getIdEstruturaOrganizacional();
			
			LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
			ResultSet rs = lm.consultar(sql);
			try {
				while(rs.next()){
					Funcao funcao = new Funcao();
					funcao.setIdFuncao(rs.getInt("id_funcao"));
					funcao.setNome(rs.getString("cv_nome"));
					funcao.setChefia(rs.getBoolean("bl_chefia"));
					funcao.setFuncaoPai(null);
					funcoes.add(funcao);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return funcoes;
	}
	
	public List<Funcao> getFuncoesExcetoEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		List<Funcao> funcoes = new ArrayList<Funcao>();
		if(estruturaOrganizacional != null){
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select d.* from administrativo.tb_lotacao_profissional_funcao a ");
			stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_funcao e ");
			stringBuilder.append("	on e.id_estrutura_organizacional_funcao = a.id_estrutura_organizacional_funcao ");
			stringBuilder.append("inner join administrativo.tb_lotacao_profissional b ");
			stringBuilder.append("	on a.id_lotacao_profissional = b.id_lotacao_profissional ");
			stringBuilder.append("inner join administrativo.tb_lotacao_profissional c ");
			stringBuilder.append("	on b.id_lotacao_profissional = c.id_lotacao_profissional ");
			stringBuilder.append("right join administrativo.tb_funcao d on d.id_funcao = e.id_funcao ");
			stringBuilder.append("where c.id_estrutura_organizacional is null or c.id_estrutura_organizacional != ");
			stringBuilder.append(estruturaOrganizacional.getIdEstruturaOrganizacional());
			String sql = stringBuilder.toString();
			LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
			ResultSet rs = lm.consultar(sql);
			try {
				while(rs.next()){
					Funcao funcao = new Funcao();
					funcao.setIdFuncao(rs.getInt("id_funcao"));
					funcao.setNome(rs.getString("cv_nome"));
					funcao.setChefia(rs.getBoolean("bl_chefia"));
					funcao.setFuncaoPai(null);
					funcoes.add(funcao);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return funcoes;
	}

	public List<Funcao> getFuncoesLista() {
		String hql = "select o from Funcao o order by to_ascii(lower(o.nome))";
		List<Funcao> list = new ArrayList<Funcao>(new ConsultaGeral<Funcao>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Funcao> getFuncoesGroup() {
		List<Funcao> lista = new ArrayList<Funcao>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select id_funcao, cv_nome from ");
		stringBuilder.append("administrativo.tb_funcao ");
		stringBuilder.append("where id_funcao_pai is null ");
		stringBuilder.append("order by cv_nome");
		String sql = stringBuilder.toString();
		List<Object[]> listaResultado = new LinhaMecanica("db_imhotep").getListaResultado(sql);
		for(Object[] obj : listaResultado){
			Funcao funcao = new Funcao();
			funcao.setIdFuncao(Integer.valueOf(String.valueOf(obj[0])));
			funcao.setNome(String.valueOf(obj[1]));
			lista.add(funcao);
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append("select id_funcao, cv_nome from ");
			stringBuilder2.append("administrativo.tb_funcao ");
			stringBuilder2.append("where id_funcao_pai = ");
			stringBuilder2.append(funcao.getIdFuncao());
			stringBuilder2.append("order by cv_nome");
			String sqlfuncaosFilho = stringBuilder2.toString();
			
			List<Object[]> listaFilhos = new LinhaMecanica("db_imhotep").getListaResultado(sqlfuncaosFilho);
			for(Object[] obj2 : listaFilhos){
				Funcao funcaoFilho = new Funcao();
				funcaoFilho.setIdFuncao(Integer.valueOf(String.valueOf(obj2[0])));
				funcaoFilho.setNome(String.valueOf(obj2[1]));
				funcaoFilho.setFuncaoPai(funcao);
				lista.add(funcaoFilho);
			}
		}
		return lista;
	}
	
}