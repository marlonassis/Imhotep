package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.LotacaoProfissionalFuncao;
import br.com.imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class LotacaoProfissionalFuncaoConsultaRaiz  extends ConsultaGeral<LotacaoProfissionalFuncao>{
	
	public List<LotacaoProfissionalFuncao> lotacoesProfissional(Profissional profissional){
		List<LotacaoProfissionalFuncao> resultado = new ArrayList<LotacaoProfissionalFuncao>();
		if(profissional != null){
			int idP = profissional.getIdProfissional();
			String hql = "select o from LotacaoProfissionalFuncao o where o.lotacaoProfissional.profissional.idProfissional = " + idP 
							+ " order by lower(to_ascii(o.estruturaOrganizacionalFuncao.estruturaOrganizacional.nome))";
			Collection<LotacaoProfissionalFuncao> consulta = new ConsultaGeral<LotacaoProfissionalFuncao> (new StringBuilder(hql)).consulta();
			resultado = new ArrayList<LotacaoProfissionalFuncao>(consulta);
		}
		return resultado;
	}
	
}