package br.com.imhotep.consulta.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstruturaOrganizacionalConsultaRaiz  extends ConsultaGeral<EstruturaOrganizacional>{
	
	public List<EstruturaOrganizacional> getSetores(){
		List<EstruturaOrganizacional> resultado = new ArrayList<EstruturaOrganizacional>();
		String hql = "select o from EstruturaOrganizacional o order by to_ascii(lower(o.nome))";
		Collection<EstruturaOrganizacional> consulta = new ConsultaGeral<EstruturaOrganizacional> (new StringBuilder(hql)).consulta();
		resultado = new ArrayList<EstruturaOrganizacional>(consulta);
		return resultado;
	}
	
	public List<EstruturaOrganizacional> getSetoresProfissional(){
		List<EstruturaOrganizacional> resultado = new ArrayList<EstruturaOrganizacional>();
		try {
			String sql = null;
			boolean administrador = new Parametro().isProfissionalAdministrador();
			if(administrador){
				sql = getSqlEstrututraTodas();
			}else{
				sql = getSqlEstrututraChefia();
			}
			LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
			ResultSet rs = lm.consultar(sql);
			String idsEstruturas = "";
			while(rs.next()){
				int idEstruturaOrganizacional = rs.getInt("id_estrutura_organizacional");
				idsEstruturas += ", ".concat(String.valueOf(idEstruturaOrganizacional));
			}
			if(!idsEstruturas.isEmpty()){
				idsEstruturas = idsEstruturas.replaceFirst(",", "");
				String hql = "select o from EstruturaOrganizacional o where o.idEstruturaOrganizacional in (" 
								+ idsEstruturas + 
								") order by to_ascii(lower(o.nome))";
				Collection<EstruturaOrganizacional> lista = new ConsultaGeral<EstruturaOrganizacional>(new StringBuilder(hql)).consulta();
				resultado = new ArrayList<EstruturaOrganizacional>(lista);
			}
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public String getSqlEstrututraTodas() throws ExcecaoProfissionalLogado{
		String sql = "Select id_estrutura_organizacional, cv_nome, id_estrutura_pai from administrativo.tb_estrutura_organizacional order by to_ascii(lower(cv_nome))";
		return sql;
	}
	
	public String getSqlEstrututraChefia() throws ExcecaoProfissionalLogado{
		int idProfissional = Autenticador.getProfissionalLogado().getIdProfissional();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("WITH RECURSIVE estruturaRecursiva(id_estrutura_organizacional, cv_nome, id_estrutura_pai) AS ( ");
		stringBuilder.append("		select a.id_estrutura_organizacional, a.cv_nome, id_estrutura_pai ");
		stringBuilder.append("			from administrativo.tb_estrutura_organizacional a where a.id_estrutura_organizacional in ( ");
		stringBuilder.append("				select a.id_estrutura_organizacional from administrativo.tb_lotacao_profissional f ");
		stringBuilder.append("					inner join administrativo.tb_lotacao_profissional_funcao g ");
		stringBuilder.append("						on g.id_lotacao_profissional = f.id_lotacao_profissional ");
		stringBuilder.append("					inner join administrativo.tb_estrutura_organizacional_funcao h ");
		stringBuilder.append("						on h.id_estrutura_organizacional_funcao = g.id_estrutura_organizacional_funcao ");
		stringBuilder.append("					inner join administrativo.tb_funcao i ");
		stringBuilder.append("						on i.id_funcao = h.id_funcao ");
		stringBuilder.append("					inner join administrativo.tb_estrutura_organizacional a ");
		stringBuilder.append("						on a.id_estrutura_organizacional = h.id_estrutura_organizacional ");
		stringBuilder.append("					where i.bl_chefia is true and f.id_profissional = ");
		stringBuilder.append(idProfissional);
		stringBuilder.append("			) ");
		stringBuilder.append("		  UNION ");
		stringBuilder.append("		select b.id_estrutura_organizacional, b.cv_nome, b.id_estrutura_pai from administrativo.tb_estrutura_organizacional  b ");
		stringBuilder.append("		    JOIN estruturaRecursiva rt ON rt.id_estrutura_organizacional = b.id_estrutura_pai ");
		stringBuilder.append("		  ) ");
		stringBuilder.append("		SELECT id_estrutura_organizacional, cv_nome, id_estrutura_pai FROM estruturaRecursiva order by to_ascii(lower(cv_nome)); ");
		return stringBuilder.toString();
	}
	
}