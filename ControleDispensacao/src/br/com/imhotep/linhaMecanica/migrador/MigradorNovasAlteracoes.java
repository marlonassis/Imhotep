package br.com.imhotep.linhaMecanica.migrador;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class MigradorNovasAlteracoes {

	private static final String banco = "db_imhotep";
	private static final String ip = Constantes.IP_LOCAL;

	private static final String bancoRemoto = "db_imhotep";
	private static final String ipRemoto = Constantes.IP_IMHOTEP_REMOTO;
	
	public static void main(String[] args) {
//		migrarProfissionais();
		
		
		LinhaMecanica lmRemoto = new LinhaMecanica("db_imhotep", "200.133.41.8");
		try {
			lmRemoto.criarConexao();
			String sql = "SELECT "+
  "cv_nome, "+
  "id_usuario_inclusao, "+
  "id_usuario, "+
  "id_profissional, "+
  "in_matricula, "+
  "dt_data_nascimento, "+
  "dt_data_inclusao, "+
  "cv_chave_verificacao, "+
  "cv_email, "+
  "tp_sexo, "+
  "tp_tipo_vinculo, "+
  "cv_cpf "+
  "FROM tb_profissional where id_profissional > 1847 order by id_profissional";
			LinhaMecanica lmLocal = new LinhaMecanica("db_imhotep_hom", ip);
			List<Object[]> listaResultado = lmLocal.getListaResultado(sql);
			int cont = 2003;
			for(Object[] obj : listaResultado){
				String cv_nome = "'" + String.valueOf(obj[0]) + "'";
				Integer id_usuario_inclusao = obj[1] == null ? null : Integer.valueOf(String.valueOf(obj[1]));
				Integer id_usuario = obj[2] == null ? null : Integer.valueOf(String.valueOf(obj[2]));
				Integer id_profissional = obj[3] == null ? null : Integer.valueOf(String.valueOf(obj[3]));
				Integer in_matricula = obj[4] == null ? null : Integer.valueOf(String.valueOf(obj[4]));
				String dt_data_nascimento = obj[5] == null ? null : "'" + String.valueOf(obj[5]) + "'";
				String dt_data_inclusao  = obj[6] == null ? null : "'" + String.valueOf(obj[6]) + "'";
				String cv_chave_verificacao = obj[7] == null ? null : "'" + String.valueOf(obj[7]) + "'";
				String cv_email = obj[8] == null ? null : "'" + String.valueOf(obj[8]) + "'";
				String tp_sexo = obj[9] == null ? null : "'" + String.valueOf(obj[9]) + "'";
				String tp_tipo_vinculo = obj[10] == null ? null : "'" + String.valueOf(obj[10]) + "'";
				String cv_cpf  = obj[11] == null ? null : "'" + String.valueOf(obj[11]) + "'";
				
				String sqlInsert = "insert into tb_profissional (cv_nome, id_usuario_inclusao, id_usuario, id_profissional, in_matricula, dt_data_nascimento, dt_data_inclusao, "+
  "cv_chave_verificacao, cv_email, tp_sexo, tp_tipo_vinculo, cv_cpf ) "+
						"values ("+cv_nome+", "+id_usuario_inclusao+", '"+id_usuario+"', "+id_profissional+", "+in_matricula+", "
								+dt_data_nascimento+", "+dt_data_inclusao+", "+cv_chave_verificacao+""
								+ ", "+cv_email+", "+tp_sexo+", "+tp_tipo_vinculo+", "+cv_cpf+")";
				
				System.out.println(sqlInsert);
				if(!lmRemoto.fastExecutarCUD(sqlInsert)){
					System.out.println("erro");
				}
				cont++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		lmRemoto.fecharConexoes();
		
		
		
		
		
//		migrarMenu();
//		migrarPainel();
//		atribuirMenuSGPTI();
//		atualizarColunaProfissionalInclusaoMovimentoLivro();
		
//		atualizarColunaProfissionalInclusaoEstoque();
		
//		atualizarProfissional();
//		atualizarFuncaoCargo();
		
//		String sql = "SELECT id_estrutura_organizacional, cv_nome, id_estrutura_pai FROM administrativo.tb_estrutura_organizacional "
//				+ "order by id_estrutura_pai, id_estrutura_organizacional";
//		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
//		ResultSet rs = lm.consultar(sql);
//		try {
//			LinhaMecanica lm2 = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
//			while(rs.next()){
//				int id_estrutura_organizacional = rs.getInt("id_estrutura_organizacional");
//				
//				String sql2 = "INSERT INTO administrativo.tb_grupo_estrutura( "+
//								"id_grupo, id_estrutura_organizacional) "+
//								"VALUES (1, "+id_estrutura_organizacional+")";
//				lm2.executarCUD(sql2);
//				System.out.println(sql2);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} 
		
		
	}

	private static void migrarProfissionais() {
//		LinhaMecanica lmRemoto = new LinhaMecanica(bancoRemoto, ipRemoto);
//		try {
//			lmRemoto.criarConexao();
//			String sql = "SELECT cv_nome, "+
//  "tp_status, "+
//  "id_usuario_inclusao, "+
//  "id_usuario, "+
//  "id_profissional, "+
//  "in_matricula, "+
//  "dt_data_nascimento, "+
//  "dt_data_inclusao, "+
//  "cv_chave_verificacao, "+
//  "cv_email, "+
//  "tp_sexo, "+
//  "tp_qualidade_digital, "+
//  "tp_tipo_vinculo, "+
//  "cv_senha_digital, "+
//  "cv_cpf FROM tb_profissional order by id_profissional";
//
//			LinhaMecanica lmLocal = new LinhaMecanica("db_imhotep_2", ip);
//			List<Object[]> listaResultado = lmLocal.getListaResultadoFast(sql, 1);
//			for(Object[] obj : listaResultado){
//				Integer idProfissional = Integer.valueOf(String.valueOf(obj[4]));
//				String cv_nome = String.valueOf(obj[0]);
//				String tp_status = String.valueOf(obj[1]);
//				Integer idUsuarioInclusao = Integer.valueOf(String.valueOf(obj[2]));
//				Integer idUsuario = Integer.valueOf(String.valueOf(obj[3]));
//				  "id_usuario, "+
//				  "id_profissional, "+
//				  "in_matricula, "+
//				  "dt_data_nascimento, "+
//				  "dt_data_inclusao, "+
//				  "cv_chave_verificacao, "+
//				  "cv_email, "+
//				  "tp_sexo, "+
//				  "tp_qualidade_digital, "+
//				  "tp_tipo_vinculo, "+
//				  "cv_senha_digital, "+
//				  "cv_cpf
//				sql = "select id_profissional from tb_profissional where id_profissional = " + idProfissional;
//				ResultSet rs = lmRemoto.consultar(sql);
//				if(!rs.next()){
//					System.out.println("I");
//					if(!lmRemoto.fastExecutarCUD("delete from tb_profissional where id_profissional = "+idProfissional)){
//						System.out.println(idProfissional);
//					}
//				
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		lmRemoto.fecharConexoes();
		
	}

	private static void atualizarFuncaoCargo() {
		String sql = "select * from tb_especialidade where id_especialidade > 54 order by to_ascii(cv_descricao)";
		LinhaMecanica lm = new LinhaMecanica(banco, ip);
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				String sql2 = "insert into administrativo.tb_funcao(cv_nome) values ('"+rs.getString("cv_descricao")+"') ";
				lm.executarCUD(sql2);
				sql2 = "insert into administrativo.tb_cargo(cv_nome) values ('"+rs.getString("cv_descricao")+"') ";
				lm.executarCUD(sql2);

				System.out.println(sql2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

//	private static void atribuirAcessoMarlon() {
//		LinhaMecanica lm = new LinhaMecanica(banco, ip);
//		lm.executarCUD("INSERT INTO administrativo.tb_lotacao_profissional(id_estrutura_organizacional, id_profissional) "+
//						"VALUES (8, 1);");
//		
////		lm.executarCUD("INSERT INTO administrativo.tb_cargo_profissional(id_cargo, id_profissional) "+
////						"VALUES (?, ?);");
//		
//		
//		
//		String sql = "select id_estrutura_organizacional_menu from administrativo.tb_estrutura_organizacional_menu";
//		List<Object[]> listaResultado = lm.getListaResultado(sql);
//		for(Object[] obj : listaResultado){
//			Integer idEstruturaOrganizacional = Integer.valueOf(String.valueOf(obj[0]));
//			sql = "INSERT INTO controle.tb_acesso_lotacao( "+
//						"id_lotacao_profissional, id_estrutura_organizacional_menu) "+
//					    "VALUES (2, "+idEstruturaOrganizacional+");";
//			lm.executarCUD(sql);
//			System.out.println(sql);
//		}
//	}
	
	private static void atualizarProfissional() {
		System.out.println("inicio");
		LinhaMecanica lmIMHOTEP = new LinhaMecanica(banco, ip);
		ResultSet rs2 = lmIMHOTEP.consultar("select a.* from tb_profissional a order by to_ascii(a.cv_nome)");
		try {
			System.out.println("meio");
			while(rs2.next()){
				LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_AGHU, Constantes.IP_AGHU);
				lm.setUsuarioBanco(Constantes.USUARIO_BANCO_AGHU);
				lm.setSenhaBanco(Constantes.SENHA_BANCO_AGHU);
				lm.setPorta("6544");
				String nomeAscii = lm.removeAcentos(rs2.getString("cv_nome"));
				String sqlAGHU = "SELECT "+
									"nome, serv.email, CPF, dt_nascimento, matricula "+
									"FROM agh.rap_pessoas_fisicas pf "+
									"JOIN agh.rap_servidores serv ON pf.codigo = serv.pes_codigo "+
									"LEFT JOIN agh.rap_ocupacoes_cargo func ON serv.oca_codigo = func.codigo "+
									"LEFT JOIN agh.fcc_centro_custos cc ON serv.cct_codigo = cc.codigo "+
									"LEFT JOIN agh.rap_cargos cargo ON serv.oca_car_codigo = cargo.codigo "+
									"where (nome) ilike ('%"+nomeAscii+"%')";
				
				ResultSet rs = lm.consultar(sqlAGHU);
				int cont=0;
				if(rs.next()){
					if(cont == 1){
						System.out.println("Duplicidade: "+nomeAscii);
					}
					String email = rs.getString("email");
					Long cpf = rs.getLong("CPF");
					String cpf_imhotep = (cpf.toString().length() < 11 ? "0" + cpf : cpf.toString());
					Date nascimento = rs.getDate("dt_nascimento");
					int matricula = rs.getInt("matricula");
					
					String sqlUPD = "UPDATE tb_profissional "+
									 "SET ";
					if(matricula != 0)
						sqlUPD += "in_matricula="+matricula+", ";
					if(nascimento != null && !new SimpleDateFormat("yyyy").format(nascimento).equals("1900"))
						sqlUPD += "dt_data_nascimento='" + new SimpleDateFormat("yyyy-MM-dd").format(nascimento) + "', ";
					else{
						if(new SimpleDateFormat("yyyy").format(nascimento).equals("1900")){
							sqlUPD += "dt_data_nascimento=null, ";
						}
					}
					if(email != null && !email.isEmpty())
						sqlUPD += "cv_email='"+ email +"', ";
					if(cpf_imhotep != null && !cpf_imhotep.equals("0"))
						sqlUPD += "cv_cpf='"+ cpf_imhotep +"' ";
					
					sqlUPD += "WHERE id_profissional = "+rs2.getInt("id_profissional");
					
					if(!lmIMHOTEP.executarCUD(sqlUPD))
						System.out.println(sqlUPD);
					cont++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println("fim");
	}

	private static void atualizarColunaProfissionalInclusaoEstoque() {
		String sql = "select a.id_estoque, c.id_profissional, b.cv_login, c.cv_nome "+
						"from tb_estoque a "+
						"inner join tb_usuario b on a.id_usuario_inclusao = b.id_usuario "+
						"inner join tb_profissional c on b.id_usuario = c.id_usuario order by a.id_estoque";
		LinhaMecanica lm = new LinhaMecanica(bancoRemoto, ipRemoto);
		List<Object[]> listaResultado = lm.getListaResultado(sql, 4);
		for(Object[] obj : listaResultado){
			Integer idES = Integer.valueOf(String.valueOf(obj[0]));
			Integer idPF = Integer.valueOf(String.valueOf(obj[1]));
			sql = "UPDATE tb_estoque SET id_profissional_movimentacao = " + idPF + " WHERE id_estoque = " + idES;
			lm.executarCUD(sql);
			System.out.println(sql);
		}
	}

	private static void atualizarColunaProfissionalInclusaoMovimentoLivro() {
		LinhaMecanica lmRemoto = new LinhaMecanica(bancoRemoto, ipRemoto);
		try {
			lmRemoto.criarConexao();
			String sql = "select a.id_movimento_livro, b.id_usuario, c.id_profissional, "+ 
							"b.cv_login, c.cv_nome from tb_movimento_livro a "+
							"inner join tb_usuario b on a.id_usuario_movimentacao = b.id_usuario "+
							"inner join tb_profissional c on b.id_usuario = c.id_usuario";
			List<Object[]> listaResultado = lmRemoto.getListaResultadoFast(sql, 5);
			for(Object[] obj : listaResultado){
				Integer idML = Integer.valueOf(String.valueOf(obj[0]));
				Integer idPF = Integer.valueOf(String.valueOf(obj[2]));
				sql = "UPDATE tb_movimento_livro SET id_profissional_movimentacao = " + idPF + " WHERE id_movimento_livro = " + idML;
				lmRemoto.fastExecutarCUD(sql);
				System.out.println(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		lmRemoto.fecharConexoes();
	}

	private static void atribuirMenuSGPTI() {
		String sql = "select id_menu from controle.tb_menu";
		LinhaMecanica lm = new LinhaMecanica(banco, ip);
		List<Object[]> listaResultado = lm.getListaResultado(sql);
		for(Object[] obj : listaResultado){
			Integer idMenu = Integer.valueOf(String.valueOf(obj[0]));
			sql = "INSERT INTO administrativo.tb_estrutura_organizacional_menu( "+
		            "id_estrutura_organizacional, id_menu) "+
					    "VALUES (8, "+idMenu+");";
			lm.executarCUD(sql);
			System.out.println(sql);
		}
	}

	private static void migrarPainel() {
		LinhaMecanica lmRemoto = new LinhaMecanica(bancoRemoto, ipRemoto);
		String sql = "SELECT id_painel, cv_url, cv_descricao FROM tb_painel order by id_painel";
		LinhaMecanica lm = new LinhaMecanica(banco, ip);
//		lm.executarCUD("delete from controle.tb_painel;");
		List<Object[]> listaResultado = lm.getListaResultado(sql);
		int cont = 0;
		for(Object[] obj : listaResultado){
			Integer idPainel = Integer.valueOf(String.valueOf(obj[0]));
			String url = "'"+String.valueOf(obj[1])+"'" == "''" ? null : "'"+String.valueOf(obj[1])+"'";
			String descricao = "'"+String.valueOf(obj[2])+"'" == "''" ? null : "'"+String.valueOf(obj[2])+"'";
			if(idPainel > cont){
				cont = idPainel;
			}
			sql = "INSERT INTO controle.tb_painel(id_painel, cv_url, cv_descricao) VALUES ("+idPainel+", "+url+", "+descricao+");";
			lmRemoto.executarCUD(sql);
			System.out.println(sql);
		}
		lmRemoto.executarCUD("ALTER SEQUENCE controle.tb_painel_id_painel_seq RESTART WITH "+cont);
	}
	
	private static void migrarMenu() {
		LinhaMecanica lmRemoto = new LinhaMecanica(bancoRemoto, ipRemoto);
		try {
			lmRemoto.criarConexao();
			String sql = "SELECT id_menu, id_menu_pai, cv_descricao, cv_url, "+
							"cv_url_ajuda, bl_bloqueado, bl_interno, bl_construcao FROM controle.tb_menu order by id_menu";
			LinhaMecanica lmLocal = new LinhaMecanica(banco, ip);
			lmRemoto.fastExecutarCUD("ALTER TABLE controle.tb_menu "+
					  "drop CONSTRAINT tb_menu_tb_menu_fk_id_menu_pai_id_menu;");
			List<Object[]> listaResultado = lmLocal.getListaResultado(sql);
			int cont = 0;
			for(Object[] obj : listaResultado){
				Integer idMenu = Integer.valueOf(String.valueOf(obj[0]));
				Integer idMenuPai = obj[1] == null ? null : Integer.valueOf(String.valueOf(obj[1]));
				String descricao = "'"+String.valueOf(obj[2])+"'" == "''" ? null : "'"+String.valueOf(obj[2])+"'";
				String url = "'"+String.valueOf(obj[3])+"'" == "''" ? null : "'"+String.valueOf(obj[3])+"'";
				String urlAjuda = obj[4] == null ? null : ("'"+String.valueOf(obj[4])+"'" == "''" ? null : "'"+String.valueOf(obj[4])+"'");
				Boolean bloqueado = obj[5] == null ? null : Boolean.valueOf(String.valueOf(obj[5]));
				Boolean interno = obj[6] == null ? null : Boolean.valueOf(String.valueOf(obj[6]));
				Boolean construcao = obj[7] == null ? null : Boolean.valueOf(String.valueOf(obj[7]));
				if(idMenu > cont){
					cont = idMenu;
				}
				sql = "INSERT INTO controle.tb_menu( "+
					            "id_menu, id_menu_pai, cv_descricao, cv_url, cv_url_ajuda, bl_bloqueado, "+ 
					            "bl_interno, bl_construcao) "+
					    "VALUES ("+idMenu+", "+idMenuPai+", "+descricao+", "+(url.equals("'null'") || url.equals("''") ? null : url)+", "+((urlAjuda == null || urlAjuda.equals("'null'") || urlAjuda.equals("''") ? null : urlAjuda))+", "+bloqueado+", "+interno+", "+construcao+");";
				lmRemoto.fastExecutarCUD(sql);
				System.out.println(sql);
			}
			lmRemoto.fastExecutarCUD("ALTER SEQUENCE controle.tb_menu_id_menu_seq RESTART WITH "+cont);
			lmRemoto.fastExecutarCUD("ALTER TABLE controle.tb_menu "+
							  "add CONSTRAINT tb_menu_tb_menu_fk_id_menu_pai_id_menu FOREIGN KEY (id_menu_pai) "+ 
							  "REFERENCES controle.tb_menu (id_menu) ON UPDATE CASCADE ON DELETE NO ACTION;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		lmRemoto.fecharConexoes();
	}
}
