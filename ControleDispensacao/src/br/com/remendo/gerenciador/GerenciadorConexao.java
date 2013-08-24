package br.com.remendo.gerenciador;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class GerenciadorConexao {
	protected Session session;
	protected static Configuration cfg = new AnnotationConfiguration().configure("hibernate.cfg.xml");
	protected Transaction tx;
	protected static SessionFactory factory = cfg.buildSessionFactory();
	
	public void iniciarTransacao(){
		session = factory.openSession();
		tx = session.beginTransaction();
	}
	
	public void finalizaTransacao(String msg){
		session.flush();  
		tx.commit(); 
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,msg, ""));
	}
	
	public void catchTransacao(String msg, Exception e){
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, e.getMessage()));
		session.getTransaction().rollback();
	}
	
	public void finallyTransacao(){
		if(session != null)
			session.close();
		if(factory != null)
			factory.close();	}
	
	protected void mensagem(String msg, String msg2, Severity tipoMensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipoMensagem,msg, msg2));
	}
}

