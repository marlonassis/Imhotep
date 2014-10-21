package br.com.remendo.gerenciador;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.remendo.utilidades.Utilitarios;


public class GerenciadorConsultaLazy<T> extends GerenciadorConexao{
	
	@SuppressWarnings("unchecked")
	public Collection<T> consultarLista(Object pai, Object filho, Collection<T> filhos){
		iniciarTransacao();
		String nomePropriedadeId = Utilitarios.getNomePropriedadeId(pai);
		Integer id = (Integer) Utilitarios.getValorPropriedadeId(pai);
		if(nomePropriedadeId != null && id != null && !id.equals(0)){
			Criteria cr = session.createCriteria(filho.getClass());
			cr.add(Restrictions.eq(nomePropriedadeId, id));
			filhos = cr.list();
		}
		return filhos;
	}
	
	@SuppressWarnings("unchecked")
	public T consultar(Object pai, T filho){
		iniciarTransacao();
		String nomePropriedadeId = Utilitarios.getNomePropriedadeId(pai);
		Integer id = (Integer) Utilitarios.getValorPropriedadeId(pai);
		if(nomePropriedadeId != null && id != null){
			Criteria cr = session.createCriteria(filho.getClass());
			cr.add(Restrictions.eq(nomePropriedadeId, id));
			cr.setMaxResults(1);
			filho = (T) cr.list().get(0);
		}
		return filho;
	}
	
}
