package br.com.nucleo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;

import br.com.nucleo.gerenciador.GerenciadorConexao;
import br.com.nucleo.interfaces.IPadraoHome;

public abstract class PadraoHome<T> extends GerenciadorConexao implements IPadraoHome {

	private T instancia;
	
	public Object getInstanciaHome(String nome){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		return session.getAttribute(nome);
	}
	
	protected String getIdSessao(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getId();
	}
	
	public void setId(Object o){
		try{
			iniciarTransacao();
			session.get(instancia.getClass(), (Serializable) o);
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao setar o id", e.getCause().getMessage()));
			if(session != null){
				session.getTransaction().rollback();
			}
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
		try{
			iniciarTransacao();
			session.save(instancia);  
			session.flush();  
			tx.commit();  
			ret = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Cadastro realizado com sucesso", "Registro cadastrado!"));
			aposEnviar();
		}
		catch (org.hibernate.exception.ConstraintViolationException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro já cadastrado.","Ocorreu uma tentativa de duplicação!"));
			zeraId();
		}
		catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao cadastrar", e.getCause().getMessage()));
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}

	public void aposEnviar() {
		
	}


	/**
	 * Método que seta 0 ao ID da entidade para evitar que o registro entre em edição ao tentar gravar o registro sem sucesso
	 */
	private void zeraId(){
		try {
			Class partypes[] = new Class[1];  
            partypes[0] = Integer.TYPE;  
  
            Class cls = Class.forName(nomeCompletoClasse());  
            Method meth = cls.getMethod("setId"+nomeClasse(), partypes);  
  
            Object arglist[] = new Object[1];  
            arglist[0] = new Integer(0);  
  
            meth.invoke(instancia, arglist);  
            
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean apagar() {
		boolean ret = false;
		try{
			iniciarTransacao();
			session.delete(instancia); // Realiza persistência
			tx.commit(); // Finaliza transação
			novaInstancia();
			ret = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Deleção realizada com sucesso", "Registro apagado!"));
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao apagar", e.getCause().getMessage()));
			if(session != null){
				session.getTransaction().rollback();
			}
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
		try{
			iniciarTransacao();
			session.merge(obj); // Realiza persistência
			tx.commit(); // Finaliza transação
			o = obj;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Atualização realizada com sucesso", "Registro atualizado!"));
		}catch (org.hibernate.exception.ConstraintViolationException e) {
			session.getTransaction().rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro já cadastrado.","Ocorreu uma tentativa de duplicação!"));
		}catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao atualizar", e.getCause().getMessage()));
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
		try{
			iniciarTransacao();
			Query query = session.createQuery(sql);
            lista = query.list();    
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu ao reallizar a busca", e.getCause().getMessage()));
			if(session != null){
				session.getTransaction().rollback();
			}
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
        return lista;
	}
	
	@Override
	public Integer executa(String sql) {
		Integer res = null; 
		try{
			iniciarTransacao();
			res = session.createQuery(sql).executeUpdate();
//			novaInstancia();
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao executar o comando", e.getCause().getMessage()));
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		
		return res;
	}
	
	private  String nomeCompletoClasse(){
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
		try{
			iniciarTransacao();
			Query query = session.createQuery("from "+nomeClasse()+" u");
			lista = query.list(); 
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao efetuar a busca", e.getCause().getMessage()));
			if(session != null){
				session.getTransaction().rollback();
			}
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
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		return (T) session.getAttribute(nomeClasse());
	}
}
