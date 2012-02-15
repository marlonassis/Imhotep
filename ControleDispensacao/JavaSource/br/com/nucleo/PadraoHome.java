package br.com.nucleo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.nucleo.gerenciador.GerenciadorConexao;
import br.com.nucleo.interfaces.IPadraoHome;

public abstract class PadraoHome<T> extends GerenciadorConexao implements IPadraoHome {

	private T instancia;
	
	@SuppressWarnings("unchecked")
	public void setId(Object o){
		EntityManager em = getEntityManager();
		instancia = (T) em.find(instancia.getClass(), o);
		em.close();
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
		EntityManager em = getEntityManager();
		boolean result = false;
        try{
	        //inicia o processo de transacao
	        em.getTransaction().begin();
	        //faz a persistencia
	        em.persist(instancia);
	        //manda bala para o BD
	        em.getTransaction().commit();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Cadastro bem sucedido!", "O cadastro foi efetuado com sucesso!"));
	        result = true;
		}
        catch (Exception e) {
			e.printStackTrace();
			if(e.getCause().toString().contains("org.hibernate.exception.ConstraintViolationException"))
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro de constraint", "Cadastro não realizado!"));
			else
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro", "Cadastro não realizado!"));
        }finally{
        	em.close();
        }
		return result;
	}

	@Override
	public boolean apagar() {
		EntityManager em = getEntityManager();
		boolean result = false;
        try{
	        //inicia o processo de transacao
	        em.getTransaction().begin();
	        //faz a persistencia
	        Object obj = em.merge(instancia);
	        em.remove(obj);
	        //manda bala para o BD
	        em.getTransaction().commit();
	
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro apagado!", "O registro removido com sucesso!"));
	        result = true;
	        novaInstancia();
        }catch (Exception e) {
			e.printStackTrace();
	        //se der algo de errado vem parar aqui, onde eh cancelado
	        em.getTransaction().rollback();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro", "Exclusão não realizada!"));  
        }finally{
        	em.close();
        }
		return result;
	}
	
	@Override
	public boolean atualizar() {
		return atualizarGenerico(instancia) != null;
	}
	
	@Override
	public Object atualizarGenerico(Object obj) {
		EntityManager em = getEntityManager();
        try{
	        //inicia o processo de transacao
	        em.getTransaction().begin();
	        //faz a persistencia
	        em.merge(obj);
	        //manda bala para o BD
	        em.getTransaction().commit();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro atualizado com sucesso!", "O cadastro efetuado com sucesso!"));
	        em.close();
	        return obj;
        }catch (Exception e) {
			e.printStackTrace();
	        //se der algo de errado vem parar aqui, onde eh cancelado
	        em.getTransaction().rollback();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro", "Registro não atualizado!"));
	        em.close();
	        return null;
        }
	}
	
	/**
	 * Método que retorna todos os registros do tipo genérico a partir de uma consulta guiada
	 * @param String arg0
	 * @return Coleção de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<T> getBusca(String sql) {
		EntityManager em = getEntityManager();
		List<T> lista = null;
        try{
            //inicia o processo de transacao
            em.getTransaction().begin();
            lista = em.createQuery(sql).getResultList();     
        }catch (Exception e) {
        	e.printStackTrace();
            //se der algo de errado vem parar aqui, onde eh cancelado
            em.getTransaction().rollback();
        }finally{
        	em.close();
        }
        return lista;
	}
	
	@Override
	public Integer executa(String sql) {
		EntityManager em = getEntityManager();
		Integer res = null; 
        try{
            //inicia o processo de transacao
            em.getTransaction().begin();
            res = em.createQuery(sql).executeUpdate();
            em.getTransaction().commit();
        }catch (Exception e) {
        	e.printStackTrace();
            //se der algo de errado vem parar aqui, onde eh cancelado
            em.getTransaction().rollback();
        }finally{
        	em.close();
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
		EntityManager em = getEntityManager();
		List<T> lista = null;
        try{
            //inicia o processo de transacao
            em.getTransaction().begin();
            lista = em.createQuery("from "+nomeClasse()+" u").getResultList();
        }catch (Exception e) {
                //se der algo de errado vem parar aqui, onde eh cancelado
                em.getTransaction().rollback();
                e.printStackTrace();
        }finally{
        	em.close();
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
