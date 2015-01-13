package br.com.imhotep.auxiliar;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;

@ManagedBean
@ViewScoped
public class Variavel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public boolean variavelAutorizada(String variavel){
		if(new Parametro().isProfissionalAdministrador()){
			return true;
		}
		String pagina = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI().replace("/imhotep", "");
		String sql  = getSqlVerificaAcessoVariavelLotacaoProfissional(pagina, variavel, getProfissionalAtual().getIdProfissional());
		return isVariavelValidada(sql);
	}
	
	private boolean isVariavelValidada(String sql) {
		ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).consultar(sql);
		try {
			rs.next();
			return rs.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getSqlVerificaAcessoVariavelLotacaoProfissional(String pagina, String variavel, int idProfissional){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select exists ( ");
		stringBuilder.append(getSqlVariavelFuncao(pagina, variavel, idProfissional));
		stringBuilder.append(" union ");
		stringBuilder.append(getSqlVariavelLotacao(pagina, variavel, idProfissional));
		stringBuilder.append(")");
		return stringBuilder.toString();
	}
	
	private String getSqlVariavelLotacao(String pagina, String variavel, int idProfissional){
		String sql = "select distinct h.id_menu_variavel from controle.tb_acesso_lotacao a "+
					"		inner join administrativo.tb_estrutura_organizacional_menu b "+
					"			on a.id_estrutura_organizacional_menu = b.id_estrutura_organizacional_menu "+ 
					"		inner join administrativo.tb_estrutura_organizacional c "+
					"			on b.id_estrutura_organizacional = c.id_estrutura_organizacional "+ 
					"		inner join administrativo.tb_lotacao_profissional d "+
					"			on d.id_estrutura_organizacional = c.id_estrutura_organizacional "+ 
					"			and d.id_lotacao_profissional = d.id_lotacao_profissional "+ 
					"		inner join controle.tb_acesso_lotacao f "+
					"			on f.id_lotacao_profissional = d.id_lotacao_profissional "+
					"			and f.id_estrutura_organizacional_menu = b.id_estrutura_organizacional_menu "+ 
					"		inner join controle.tb_menu e on e.id_menu = b.id_menu "+ 
					"	inner join controle.tb_menu_variavel h on h.id_menu = e.id_menu "+
					"	inner join controle.tb_acesso_lotacao_variavel i on i.id_menu_variavel = h.id_menu_variavel "+
					"							and i.id_acesso_lotacao = f.id_acesso_lotacao "+
					"	inner join controle.tb_variavel j on j.id_variavel = h.id_variavel "+
					"	where d.id_profissional = "+idProfissional+" and "+
					"		e.cv_url = '"+pagina+"' and "+ 
					"		j.cv_nome = '"+variavel+"' ";
						
		return sql;
	}
	
	private String getSqlVariavelFuncao(String pagina, String variavel, int idProfissional){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select distinct h.id_menu_variavel from controle.tb_menu a ");
		stringBuilder.append("	inner join administrativo.tb_estrutura_organizacional_menu b ");
		stringBuilder.append("		on a.id_menu = b.id_menu ");
		stringBuilder.append("	inner join controle.tb_acesso_funcao c ");
		stringBuilder.append("		on c.id_estrutura_organizacional_menu = b.id_estrutura_organizacional_menu ");
		stringBuilder.append("	inner join administrativo.tb_estrutura_organizacional_funcao d ");
		stringBuilder.append("		on d.id_estrutura_organizacional_funcao = c.id_estrutura_organizacional_funcao ");
		stringBuilder.append("	inner join administrativo.tb_estrutura_organizacional e ");
		stringBuilder.append("		on e.id_estrutura_organizacional = d.id_estrutura_organizacional ");
		stringBuilder.append("	inner join administrativo.tb_lotacao_profissional f ");
		stringBuilder.append("		on f.id_estrutura_organizacional = e.id_estrutura_organizacional ");
		stringBuilder.append("	inner join administrativo.tb_lotacao_profissional_funcao g ");
		stringBuilder.append("		on g.id_lotacao_profissional = f.id_lotacao_profissional ");
		stringBuilder.append("		and d.id_estrutura_organizacional_funcao = g.id_estrutura_organizacional_funcao ");
		stringBuilder.append("	inner join controle.tb_menu_variavel h on h.id_menu = a.id_menu ");
		stringBuilder.append("	inner join controle.tb_acesso_funcao_variavel i on i.id_menu_variavel = h.id_menu_variavel ");
		stringBuilder.append("							and i.id_acesso_funcao = c.id_acesso_funcao ");
		stringBuilder.append("	inner join controle.tb_variavel j on j.id_variavel = h.id_variavel ");
		stringBuilder.append("	where f.id_profissional = ");
		stringBuilder.append(idProfissional);
		stringBuilder.append(" and ");
		stringBuilder.append("		a.cv_url = '");
		stringBuilder.append(pagina);
		stringBuilder.append("' and ");
		stringBuilder.append("		j.cv_nome = '");
		stringBuilder.append(variavel);
		stringBuilder.append("' ");
		return stringBuilder.toString();
	}
	
	private Profissional getProfissionalAtual(){
		try {
			return Autenticador.getProfissionalLogado();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
