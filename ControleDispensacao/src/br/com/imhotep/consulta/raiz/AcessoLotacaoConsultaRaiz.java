package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.AcessoLotacao;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class AcessoLotacaoConsultaRaiz  extends ConsultaGeral<AcessoLotacao>{
	
	
	
	public List<String> funcoesProfissional(LotacaoProfissional lotacao){
		List<String> funcoes = new ArrayList<String>();
		if(lotacao != null){
			int idEO = lotacao.getEstruturaOrganizacional().getIdEstruturaOrganizacional();
			int idP = lotacao.getProfissional().getIdProfissional();
			String sql = "select b.cv_nome funcao from administrativo.tb_lotacao_profissional_funcao a "+
							"   inner join administrativo.tb_estrutura_organizacional_funcao d "+
							"		on d.id_estrutura_organizacional_funcao = a.id_estrutura_organizacional_funcao "+
							"	inner join administrativo.tb_funcao b on b.id_funcao = d.id_funcao "+
							"	inner join administrativo.tb_lotacao_profissional c "+
							"		on a.id_lotacao_profissional = c.id_lotacao_profissional "+
							"where c.id_estrutura_organizacional = "+ idEO +" and c.id_profissional =  " + idP;
			List<Object[]> listaResultado = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL).getListaResultado(sql);
			for(Object[] obj : listaResultado){
				funcoes.add(String.valueOf(obj[0]));
			}
		}
		return funcoes;
	}
	
}