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
		boolean ret = false;
		try{
			Configuration cfg = new AnnotationConfiguration();
			//Informe o arquivo XML que contém a configurações
			cfg.configure("hibernate.cfg.xml");
			//Cria uma fábrica de sessões.
			//Deve existir apenas uma instância na aplicação
			SessionFactory sf = cfg.buildSessionFactory();
			// Abre sessão com o Hibernate
			session = sf.openSession();
			//Cria uma transação
			Transaction tx = session.beginTransaction();
			// Cria objeto Aluno
			session.get(instancia.getClass(), (Serializable) o);
			tx.commit(); // Finaliza transação
			ret = true;
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
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
		Session session=null;
		boolean ret = false;
		try{
			Configuration cfg = new AnnotationConfiguration();
			//Informe o arquivo XML que contém a configurações
			cfg.configure("hibernate.cfg.xml");
			//Cria uma fábrica de sessões.
			//Deve existir apenas uma instância na aplicação
			SessionFactory sf = cfg.buildSessionFactory();
			// Abre sessão com o Hibernate
			session = sf.openSession();
			//Cria uma transação
			Transaction tx = session.beginTransaction();
			// Cria objeto Aluno
			session.save(instancia); // Realiza persistência
			tx.commit(); // Finaliza transação
			ret = true;
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
		}
		
		return ret;
	}

	@Override
	public boolean apagar() {
		Session session=null;
		boolean ret = false;
		try{
			Configuration cfg = new AnnotationConfiguration();
			//Informe o arquivo XML que contém a configurações
			cfg.configure("hibernate.cfg.xml");
			//Cria uma fábrica de sessões.
			//Deve existir apenas uma instância na aplicação
			SessionFactory sf = cfg.buildSessionFactory();
			// Abre sessão com o Hibernate
			session = sf.openSession();
			//Cria uma transação
			Transaction tx = session.beginTransaction();
			// Cria objeto Aluno
			session.delete(instancia); // Realiza persistência
			tx.commit(); // Finaliza transação
			ret = true;
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
		}
		
		return ret;
	}
	
	@Override
	public boolean atualizar() {
		return atualizarGenerico(instancia) != null;
	}
	
	@Override
	public Object atualizarGenerico(Object obj) {
		Session session=null;
		boolean ret = false;
		try{
			Configuration cfg = new AnnotationConfiguration();
			//Informe o arquivo XML que contém a configurações
			cfg.configure("hibernate.cfg.xml");
			//Cria uma fábrica de sessões.
			//Deve existir apenas uma instância na aplicação
			SessionFactory sf = cfg.buildSessionFactory();
			// Abre sessão com o Hibernate
			session = sf.openSession();
			//Cria uma transação
			Transaction tx = session.beginTransaction();
			// Cria objeto Aluno
			session.merge(instancia); // Realiza persistência
			tx.commit(); // Finaliza transação
			ret = true;
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			session.close(); // Fecha sessão
		}
		
		return ret;	
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
        try{
			Configuration cfg = new AnnotationConfiguration();
			//Informe o arquivo XML que contém a configurações
			cfg.configure("hibernate.cfg.xml");
			//Cria uma fábrica de sessões.
			//Deve existir apenas uma instância na aplicação
			SessionFactory sf = cfg.buildSessionFactory();
			// Abre sessão com o Hibernate
			session = sf.openSession();
			Query query = session.createQuery(sql);
            lista = query.list();     
        }catch (Exception e) {
        	if(session != null){
        		session.getTransaction().rollback();
        	}
        	e.printStackTrace();
        }finally{
        	if(session != null){
        		session.close();
        	}
        }
        return lista;
	}
	
	@Override
	public Integer executa(String sql) {
		Integer res = null; 
		Session session=null;
        try{
			Configuration cfg = new AnnotationConfiguration();
			//Informe o arquivo XML que contém a configurações
			cfg.configure("hibernate.cfg.xml");
			//Cria uma fábrica de sessões.
			//Deve existir apenas uma instância na aplicação
			SessionFactory sf = cfg.buildSessionFactory();
			// Abre sessão com o Hibernate
			session = sf.openSession();
            res = session.createQuery(sql).executeUpdate();
        }catch (Exception e) {
        	//se der algo de errado vem parar aqui, onde eh cancelado
        	if(session != null){
        		session.getTransaction().rollback();
        	}
        	e.printStackTrace();
        }finally{
        	if(session != null){
        		session.close();
        	}
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
		try{
			Configuration cfg = new AnnotationConfiguration();
			//Informe o arquivo XML que contém a configurações
			cfg.configure("hibernate.cfg.xml");
			//Cria uma fábrica de sessões.
			//Deve existir apenas uma instância na aplicação
			SessionFactory sf = cfg.buildSessionFactory();
			// Abre sessão com o Hibernate
			session = sf.openSession();
			
			Query query = session.createQuery("from "+nomeClasse()+" u");
			lista = query.list();
        }catch (Exception e) {
            //se der algo de errado vem parar aqui, onde eh cancelado
        	if(session != null){
        		session.getTransaction().rollback();
        	}
            e.printStackTrace();
        }finally{
        	if(session != null){
        		session.close();
        	}
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
