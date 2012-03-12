package br.com.nucleo.gerenciador;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class GerenciadorConexao {
	protected Session session;
	protected Configuration cfg;
	protected Transaction tx;
	protected SessionFactory factory;
	
	public void iniciarTransacao(){
		cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém a configurações
		cfg.configure("hibernate.cfg.xml");
		factory = cfg.buildSessionFactory();
		session = factory.openSession();
		tx = session.beginTransaction();
	}
	
}
