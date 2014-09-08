package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Funcao;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class FuncaoConsultaRaiz  extends ConsultaGeral<Funcao>{
	
	public List<Funcao> getFuncoesLista() {
		String hql = "select o from Funcao o order by to_ascii(lower(o.nome))";
		List<Funcao> list = new ArrayList<Funcao>(new ConsultaGeral<Funcao>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Funcao> funcoesProfissionalLista(Profissional prof) {
		String hql = "select o.funcao from FuncaoProfissional o where o.profissional.idProfissional = "+prof.getIdProfissional()+
					" order by to_ascii(lower(o.funcao.nome))";
		List<Funcao> list = new ArrayList<Funcao>(new ConsultaGeral<Funcao>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Funcao> getFuncoesGroup() {
		List<Funcao> lista = new ArrayList<Funcao>();
		String sql = "select id_funcao, cv_nome from "
						+ "administrativo.tb_funcao "
						+ "where id_funcao_pai is null "
						+ "order by cv_nome";
		List<Object[]> listaResultado = new LinhaMecanica("db_imhotep").getListaResultado(sql);
		for(Object[] obj : listaResultado){
			Funcao funcao = new Funcao();
			funcao.setIdFuncao(Integer.valueOf(String.valueOf(obj[0])));
			funcao.setNome(String.valueOf(obj[1]));
			lista.add(funcao);
			String sqlfuncaosFilho = "select id_funcao, cv_nome from "
										+ "administrativo.tb_funcao "
										+ "where id_funcao_pai = " + funcao.getIdFuncao()
										+ "order by cv_nome";
			
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