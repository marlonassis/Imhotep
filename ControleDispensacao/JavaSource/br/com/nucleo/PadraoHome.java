package br.com.nucleo;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import br.com.nucleo.gerenciador.GerenciadorConexao;
import br.com.nucleo.interfaces.IPadraoHome;

public abstract class PadraoHome<T> extends GerenciadorConexao implements IPadraoHome {

	private T instancia;
	
	@SuppressWarnings("unchecked")
	public void setId(Object o){
		Session session=null;
		Configuration cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();  
		try{
			Transaction tx = session.beginTransaction();  
			session.get(instancia.getClass(), (Serializable) o);
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public PadraoHome() {
		try {
			instancia = (T) Class.forName(nomeCompletoClasse()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void novaInstancia(){
		try {
			instancia = (T) Class.forName(nomeCompletoClasse()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
	@Override
	public boolean enviar() {
		boolean ret = false;
		Session session=null;
		Configuration cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();  
		try{
			Transaction tx = session.beginTransaction();  
			session.save(instancia);  
			session.flush();  
			tx.commit();  
			ret = true;
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}

	@Override
	public boolean apagar() {
		boolean ret = false;
		Session session=null;
		Configuration cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();  
		try{
			Transaction tx = session.beginTransaction();  
			session.delete(instancia); // Realiza persistência
			tx.commit(); // Finaliza transação
			novaInstancia();
			ret = true;
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}
	
	@Override
	public boolean atualizar() {
		return atualizarGenerico(instancia) != null;
	}
	
	@Override
	public Object atualizarGenerico(Object obj) {
		Object o = null;
		Session session=null;
		Configuration cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();  
		try{
			Transaction tx = session.beginTransaction();  
			session.merge(obj); // Realiza persistência
			tx.commit(); // Finaliza transação
			o = obj;
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return o;
	}
		
	/**
	 * Método que retorna todos os registros do tipo genérico a partir de uma consulta guiada
	 * @param String arg0
	 * @return Coleção de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<T> getBusca(String sql) {
		List<T> lista = null;
		Session session=null;
		Configuration cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();  
		try{
			Transaction tx = session.beginTransaction();  
			Query query = session.createQuery(sql);
            lista = query.list();    
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
        return lista;
	}
	
	@Override
	public Integer executa(String sql) {
		Integer res = null; 
		Session session=null;
		Configuration cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();  
		try{
			Transaction tx = session.beginTransaction();  
			res = session.createQuery(sql).executeUpdate();
			novaInstancia();
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		
		return res;
	}
	
	private String nomeCompletoClasse(){
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();  
        String s = ((ParameterizedType) superclass).getActualTypeArguments()[0].toString();
        int fim = s.length();  
        return s.substring(6,fim);
	}
	
	private String nomeClasse(){
		String s = nomeCompletoClasse();
        int inicio = s.lastIndexOf(".") + 1;  
        int fim = s.length();  
        return s.substring(inicio,fim);
	}
	
	/**
	 * Método que retorna todos os registros do tipo genérico
	 * @param String arg0
	 * @return Coleção de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<T> getBusca(){
		List<T> lista = null;
		Session session=null;
		Configuration cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();  
		try{
			Transaction tx = session.beginTransaction();  
			Query query = session.createQuery("from "+nomeClasse()+" u");
			lista = query.list(); 
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return lista;
	}
	
	public boolean isEdicao(){
		Object retId = null;
		try{
	        Method meth = Class.forName(instancia.getClass().getName()).getMethod("getId"+nomeClasse());
	        retId = meth.invoke(instancia);
		}
        catch (Throwable e) {
           e.printStackTrace();
        }
		
		if( ((Integer) retId) != null && ((Integer) retId).intValue() != 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setInstancia(T instancia) {
		this.instancia = instancia;
	}

	public T getInstancia() {
		return instancia;
	}
	
	/**
	 * Método que retorna a sessão da classe desejada
	 * @param Nome da classe arg0
	 * @return 
	 * @return classe do tipo escolhido
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T instanciaAtual(){
		return (T) FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{"+nomeClasse()+"}", instancia.getClass());
	}
}
