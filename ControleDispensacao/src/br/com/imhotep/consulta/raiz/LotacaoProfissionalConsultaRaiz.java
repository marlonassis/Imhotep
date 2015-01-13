package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class LotacaoProfissionalConsultaRaiz  extends ConsultaGeral<LotacaoProfissional>{
	
	public List<LotacaoProfissional> lotacoesProfissional(Profissional profissional){
		if(profissional != null){
			int id = profissional.getIdProfissional();
			String hql = "select o from LotacaoProfissional o where o.profissional.idProfissional = " + id;
			Collection<LotacaoProfissional> consulta = new ConsultaGeral<LotacaoProfissional> (new StringBuilder(hql)).consulta();
			return new ArrayList<LotacaoProfissional>(consulta);
		}
		return new ArrayList<LotacaoProfissional>();
	}
	
}