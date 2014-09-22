package br.com.remendo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import br.com.remendo.gerenciador.GerenciadorConexao;


public class ConsultaGeral<E> extends GerenciadorConexao {

	private StringBuilder sqlConsultaSB = new StringBuilder();
	private HashMap<Object, Object> addValorConsulta =  new HashMap<Object, Object>();
	private Integer maximoResultados;
	
	public ConsultaGeral() {

	}
	
	public ConsultaGeral(StringBuilder sb) {
		this.setSqlConsultaSB(sb);
	}
	
	public ConsultaGeral(StringBuilder sb, HashMap<Object, Object> hm) {
		this.setSqlConsultaSB(sb);
		this.setAddValorConsulta(hm);
	}
	
	/**
	 * método chamado para retorna o resultado da busca em que no seu instanciamento foi informado a string e o hashmap com os valores dos parâmetros
	 * @return coleção de objetos do tipo informado
	 */
	public Collection<E> resultadoBusca(){
		return consulta(getSqlConsultaSB(), getAddValorConsulta());
	}
 	
	/**
	 * método que retorna a consulta do tipo List
	 * @return lista de objetos do tipo informado
	 */
	public List<E> resultadoBuscaList(){
		List<E> listaList = new ArrayList<E>();
//		System.out.println(getSqlConsultaSB().toString());
		for(E e : consulta(getSqlConsultaSB(), getAddValorConsulta())){
			listaList.add(e);
		}
		return listaList;
	}
	
	public Collection<E> consulta(StringBuilder stringB, HashMap<Object, Object> hashMap ){
		return consulta(stringB, hashMap, false);
	}
	
	public Collection<E> consulta(){
		return consulta(getSqlConsultaSB(), null, false);
	}
	
	/**
	 * Método responsável por setar os valores e fazer a consulta no banco 
	 * @param String|Builder com o sql
	 * @param hashMap com os valores as serem setados
	 * @return Coleção de objetos encontrados
	 */
	@SuppressWarnings("unchecked")
	public Collection<E> consulta(StringBuilder stringB, HashMap<Object, Object> hashMap, boolean consultaUnica ){
		List<E> objects = null;
		StringBuilder hql = new StringBuilder(stringB);
		try{
			iniciarTransacao();
			Query query = session.createQuery(hql.toString());
			if(hashMap != null){
				Set<Object> set = hashMap.keySet();
				for(Object obj : set)
					query.setParameter((String) obj, hashMap.get(obj));
			}
			
			if(consultaUnica){
				query.setMaxResults(1);
			}else{
				if(getMaximoResultados() != null && getMaximoResultados() != 0){
					query.setMaxResults(getMaximoResultados());
				}
			}
			
			objects = (List<E>) query.list();
		}catch (Exception e) {
			e.printStackTrace();
			if(session != null){
				session.getTransaction().rollback();
			}
		}finally{
			finallyTransacao();
		}
		return objects;
	}

	public E consultaUnica(){
		Collection<E> consulta = consulta(sqlConsultaSB, addValorConsulta);
		return consulta == null || consulta.size() == 0 ? null : consulta.iterator().next();
	}	

	public E consultaUnica(StringBuilder stringB){
		return consultaUnica(stringB, getAddValorConsulta());
	}
	
	public E consultaUnica(StringBuilder stringB, HashMap<Object, Object> hashMap ){
		Collection<E> consulta = consulta(stringB, hashMap);
		return consulta == null || consulta.size() == 0 ? null : consulta.iterator().next();
	}
	
	public void setAddValorConsulta(HashMap<Object, Object> addValorConsulta) {
		this.addValorConsulta = addValorConsulta;
	}

	public HashMap<Object, Object> getAddValorConsulta() {
		return addValorConsulta;
	}

	public void setSqlConsultaSB(StringBuilder sqlConsultaSB) {
		this.sqlConsultaSB = sqlConsultaSB;
	}

	public StringBuilder getSqlConsultaSB() {
		return sqlConsultaSB;
	}

	public Integer getMaximoResultados() {
		return maximoResultados;
	}

	public void setMaximoResultados(Integer maximoResultados) {
		this.maximoResultados = maximoResultados;
	}
	
}
