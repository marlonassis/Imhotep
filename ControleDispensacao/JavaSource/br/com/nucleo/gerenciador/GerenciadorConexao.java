package br.com.nucleo.gerenciador;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import br.com.ControleDispensacao.auxiliar.Parametro;

public class GerenciadorConexao {
	protected Session session;
	protected Configuration cfg;
	protected Transaction tx;
	protected SessionFactory factory;
	
	public void iniciarTransacao(){
		cfg = new AnnotationConfiguration();
		//Informe o arquivo XML que contém as configurações
		if(Parametro.isUsuarioTeste()){
			cfg.configure("hibernateTeste.cfg.xml");
		}else{
			cfg.configure("hibernate.cfg.xml");
		}
		factory = cfg.buildSessionFactory();
		session = factory.openSession();
		tx = session.beginTransaction();
	}
	
}
